package demo.dao;

import java.util.List;

import demo.entities.User;
import demo.entities.Authority;

// Encapsulates all the data access services for User information.
public interface UserDao {
  boolean verifyPassword(String plainPassword, String email);
  List<User> findAll();
  User findById(Integer id);
  User findByEmail(String email);
  Authority findUserAuthorities(String email);
  Authority saveUserAuthorities(Authority authority);
  User save(User user);
  void updateLastLoggedIn(String email);
  void delete(User user);
}
