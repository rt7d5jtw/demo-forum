package demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import demo.entities.dtos.UserDto;
import demo.entities.enums.Role;
import demo.entities.enums.UserStatus;
import demo.entities.dtos.RegistrationDto;
import demo.entities.User;
import demo.dao.UserDao;
import demo.utils.Timestamper;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(
  value = "/"
  )
public class ViewController {

  private UserDao userDao;
  private Timestamper timestamper;

  @Autowired
  private ViewController(UserDao userDao, Timestamper timestamper) {
    this.userDao = userDao;
    this.timestamper = timestamper;
  }

  @GetMapping(path = "")
  public String index(Model model) {
    model.addAttribute("chizkeyk", "I like cheesecake");
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
      @ModelAttribute("userDto") UserDto userDto, 
      Model model,
      HttpServletResponse httpServletResponse
      ) {
    System.out.println("Input from the form -> " + userDto.toString());
    // 1. Verify the password hash against the plain password
    // 2. If password is correct, route user homepage and include jwt
    // 3. Store jwt in a cookie with XSS and CSRF protection (with short lifespan)
    // 4. If password was incorrect, display an error.
    boolean result = userDao.verifyPassword(userDto.getPassword(), userDto.getEmail());
    if (!result)
      model.addAttribute("incorrectCredentials", "Credentials are incorrect");

    // Store JWT access token to http only cookies
    var cookie = new Cookie("access-token", "c2FtLnNtaXRoQGV4YW1wbGUuY29t; SameSite=strict");
    cookie.setHttpOnly(true);
    cookie.setMaxAge(1200);
    cookie.setSecure(true);
    cookie.setDomain("localhost");
    cookie.setPath("/");

    httpServletResponse.addCookie(cookie);
    
    return "redirect:/";
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
      Model model, Errors errors, RedirectAttributes redirectAttributes,
      HttpSession httpSession
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
          registrationDto.getPassword(), 
          Role.USER, 
          UserStatus.ACTIVE, 
          new Timestamp(new Date().getTime())
        ));

    // Add loggedIn attribute to the http session to indicate user is loggedIn
    httpSession.setAttribute("loggedIn", true);
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
      Model model
      ) {
    return "profile";
  }
}
