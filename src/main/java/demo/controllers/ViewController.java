package demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.FlashMap;

import demo.entities.dtos.UserDto;
import demo.entities.enums.Role;
import demo.entities.enums.UserStatus;
import demo.entities.dtos.RegistrationDto;
import demo.entities.User;
import demo.entities.Category;
import demo.entities.Authority;
import demo.dao.UserDao;
import demo.dao.CategoryDao;
import demo.utils.JwtHandler;
import demo.utils.Timestamper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping(
  value = "/"
  )
public class ViewController {

  private UserDao userDao;
  private CategoryDao categoryDao;
  private Timestamper timestamper;
  private JwtHandler jwtHandler;

  @Autowired
  private ViewController(
      UserDao userDao,
      CategoryDao categoryDao,
      Timestamper timestamper,
      JwtHandler jwtHandler
      ) {
    this.userDao = userDao;
    this.categoryDao = categoryDao;
    this.timestamper = timestamper;
    this.jwtHandler = jwtHandler;
  }

  @GetMapping(path = "/")
  public String index(
      Model model,
      RedirectAttributes redirectAttributes,
      HttpServletRequest request,
      HttpServletResponse response
      ) {
    System.out.println("auth header: ");
    System.out.println(request.getHeader("Authorization"));

    // Get all categories for the homepage.
    List<Category> categories = categoryDao.findAll();
    model.addAttribute("categories", categories);

    System.out.println("cookies: ");
    Cookie[] cookies = request.getCookies();
    System.out.println(cookies);
    Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

    if (cookies != null) {
      for (var cookie : cookies) {
        if ("token".equals(cookie.getName())) {

          String email = (String)jwtHandler.verifyToken(cookie.getValue()).get("email");
          var result = userDao.findByEmail(email);

          if (result != null) {
            if (email.equals(result.getEmail())) {
              System.out.println("You are authenticated!");
              System.out.println(request.getQueryString());
              System.out.println(redirectAttributes.getFlashAttributes());

              // "notifyString" key contains alert message for alert.js
              if (inputFlashMap != null && inputFlashMap.get("notifyString") != null) {
                model.addAttribute("notify", true);
                model.addAttribute("notifyString", inputFlashMap.get("notifyString"));
              }

              return "index-auth";
            }
          }
        }
      }
    }

    // Argument for javascript notify() function.
    if (inputFlashMap != null && inputFlashMap.get("notifyString") != null) {
      model.addAttribute("notify", true);
      model.addAttribute("notifyString", inputFlashMap.get("notifyString"));
    }

    return "index";
  }

  @GetMapping(path = "/signin")
  public String signin(Model model) {
    return "login";
  }

  @GetMapping(path = "/signin-error.html")
  public String signinError(Model model) {
    model.addAttribute("signinError", true);
    return "login";
  }

  @PostMapping(path = "/signin")
  public String signin(
      @Valid
      @ModelAttribute("userDto") UserDto userDto,
      Model model,
      RedirectAttributes redirectAttributes,
      HttpServletResponse response
      ) {
    System.out.println("Input from the form -> " + userDto.toString());
    // 1. Verify the password hash against the plain password
    // 2. If password is correct, route user homepage and include jwt
    // 3. Store jwt in a cookie with XSS and CSRF protection (with short lifespan)
    // 4. If password was incorrect, display an error.
    boolean result = userDao.verifyPassword(userDto.getPassword(), userDto.getEmail());
    if (!result)
      model.addAttribute("incorrectCredentials", "Credentials are incorrect");

    User foundUser = userDao.findByEmail(userDto.getEmail());
    if (foundUser != null) {
      // Update last logged in.
      userDao.updateLastLoggedIn(userDto.getEmail());
      Authority userAuthorities = userDao.findUserAuthorities(userDto.getEmail());
      System.out.println(userAuthorities);
      System.out.println(foundUser.getLastloggedin());

      String token = jwtHandler.createToken(
          Map.of(
              "name", foundUser.getUsername(),
              "email", userDto.getEmail(),
              "role", foundUser.getRole(),
              "authorities", userAuthorities.getAuthority(),
              "created", foundUser.getCreated()
              ),
          LocalDateTime.now().plusMinutes(20)
          );
      System.out.println("Token -> " + token);

      // Store JWT access token to http only cookies
      var cookie = new Cookie(
          "token",
          token
          );

      cookie.setHttpOnly(true);
      cookie.setMaxAge(1200);
      // HTTPS ONLY!
      //cookie.setSecure(true);
      cookie.setDomain("localhost");
      cookie.setPath("/");

      response.addCookie(cookie);

      // Argument for javascript notify() function.
      // notify attribute includes alert.html fragment.
      redirectAttributes.addFlashAttribute("notify", true);
      redirectAttributes.addFlashAttribute("notifyString", "Signed in succesfully!");

      return "redirect:/";
    }

    return "login";
  }

  @GetMapping(path = "/register")
  public String registerPage(
      @ModelAttribute("registrationDto") RegistrationDto registrationDto,
      Model model
      ) {
    return "registration";
  }

  @PostMapping(path = "/register")
  public String register(
      @ModelAttribute("registrationDto") RegistrationDto registrationDto,
      Model model, Errors errors,
      RedirectAttributes redirectAttributes
      ) {

    System.out.println(
        "Input from a form received for registration :: "
        + timestamper.timestamp()
        + " ->" + registrationDto.toString()
        );

    // Check that "repeat password" input matches first "password" input.
    if (!registrationDto.getPassword().equals(registrationDto.getPassword_verify())) {
      model.addAttribute("verifyPasswordError", "Passwords did not match each other!");
      return "registration";
    }

    userDao.save(
        new User(
          registrationDto.getEmail(),
          registrationDto.getUsername(),
          registrationDto.getPassword(),
          Role.USER,
          UserStatus.ACTIVE,
          new Date(System.currentTimeMillis())
        ));

    userDao.saveUserAuthorities(
        new Authority(registrationDto.getEmail(), "READ")
        );

    // Argument for javascript notify() function.
    // notify attribute includes alert.html fragment.
    redirectAttributes.addFlashAttribute("notify", true);
    redirectAttributes.addFlashAttribute("notifyString", "Registration was succesful!");

    return "redirect:/";
  }

  @PostMapping(path = "/logout")
  public String logout(
      Model model,
      RedirectAttributes redirectAttributes,
      HttpServletResponse response
      ) {
    var cookie = new Cookie("token", "");
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);

    // Argument for javascript notify() function.
    // notify attribute includes alert.html fragment.
    redirectAttributes.addFlashAttribute("notify", true);
    redirectAttributes.addFlashAttribute("notifyString", "Logged out succesfully");

    return "redirect:/";
  }

  @GetMapping(path = "/faq")
  public String faq(
      Model model
      ) {
    return "faq";
  }

  @GetMapping(path = "/profile")
  public String profile(
      Model model,
      RedirectAttributes redirectAttributes,
      HttpServletRequest request
      ) {

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (var cookie : cookies) {
        if ("token".equals(cookie.getName())) {
          HashMap<String, Object> jwtBody = jwtHandler.verifyToken(cookie.getValue());
          String email = (String)jwtBody.get("email");
          String username = (String)jwtBody.get("username");
          //String role = (String)jwtBody.get("role");
          //String created = (String)jwtBody.get("created");
          //String authorities = (String)jwtBody.get("authorities");

          var result = userDao.findByEmail(email);

          if (result != null) {
            if (email.equals(result.getEmail())) {
              System.out.println("You are authenticated!");
              System.out.println(result);
              System.out.println("--- JWT ---");
              System.out.println(jwtBody);
              model.addAttribute("user", result);
              return "profile";
            }
          }
        }
      }
    }

    return "redirect:/";
  }

  // Describes a specific topic
  // e.g. /topic/categoryId=2
  // Fetch topic relevant threads, posts and comments
  @GetMapping(path = "/topic")
  public String faq(
      @RequestParam(required = true, name = "categoryId") Integer id
      Model model,
      RedirectAttributes redirectAttributes,
      HttpServletRequest request,
      HttpServletResponse response
      ) {
    List<Category> category = categoryDao.findById(id);
    if (category != null) {
      // @TODO: Implement rest of topic-main.html page
      // and add rest of the stuff here.
      model.addAttribute("category", category);
      return "topic";
    }

    return "redirect:/"
  }

}
