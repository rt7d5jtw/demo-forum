package demo.dao;

import java.util.List;

import demo.entities.User;

// Encapsulates all the data access services for User information.
public interface UserDao {
  boolean verifyPassword(String plainPassword, String email);
  List<User> findAll();
  User findById(Integer id);
  User findByEmail(String email);
  User save(User user);
  void delete(User user);
}
