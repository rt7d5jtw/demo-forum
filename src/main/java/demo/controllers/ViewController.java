package demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.entities.dtos.UserDto;
import demo.entities.enums.Role;
import demo.entities.enums.UserStatus;
import demo.entities.dtos.RegistrationDto;
import demo.entities.User;
import demo.dao.UserDao;
import demo.utils.Timestamper;

import java.sql.Timestamp;
import java.util.Date;

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
  public String signin(@ModelAttribute("userDto") UserDto userDto, Model model) {
    System.out.println("Input from the form -> " + userDto.toString());
    // 1. Verify the password hash against the plain password
    // 2. If password is correct, route user homepage and include jwt
    // 3. Store jwt in a cookie with XSS and CSRF protection (with short lifespan)
    // 4. If password was incorrect, display an error.
    boolean result = userDao.verifyPassword(userDto.getPassword(), userDto.getEmail());
    if (result)
      return "index";

    return "login";
  }

  @GetMapping(path = "/register")
  public String registerPage(Model model) {
    return "registration";
  }

  @PostMapping(path = "/register")
  public String register(
      @ModelAttribute("registrationDto") RegistrationDto registrationDto, 
      Model model
      ) {
    System.out.println(
        "Input from a form received for registration :: "
        + timestamper.timestamp()
        + " ->" + registrationDto.toString()
        );
    userDao.save(
        new User(
          registrationDto.getEmail(),
          registrationDto.getPassword(), 
          Role.USER, 
          UserStatus.ACTIVE, 
          new Timestamp(new Date().getTime())
        ));
    return "registration";
  }

  @GetMapping(path = "/faq")
  public String faq(Model model) {
    return "faq";
  }
}
