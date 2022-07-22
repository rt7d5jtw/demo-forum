package demo.dao;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import demo.dao.exceptions.UserNotFoundException;
import demo.entities.User;

import java.util.List;

@SuppressWarnings("unchecked")
@Transactional
@Repository("userDao")
public class UserDaoImpl implements UserDao {

  private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
  private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  @Autowired
  @Qualifier("sessionFactory")
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional
  public boolean verifyPassword(String plainPassword, String email) {
    User user = this.findByEmail(email);
    if (user != null)
      return BCrypt.checkpw(plainPassword, user.getPassword());

    throw new UserNotFoundException(email);
  }

  @Transactional(readOnly = true)
  public List<User> findAll() {
    return sessionFactory
      .getCurrentSession()
      .getNamedQuery("User.findAll")
      .list();
  }

  @Transactional(readOnly = true)
  public User findById(Integer id) {
    return (User) sessionFactory
      .getCurrentSession()
      .getNamedQuery("User.findById")
      .setParameter("id", id)
      .uniqueResult();
  }

  @Transactional(readOnly = true)
  public User findByEmail(String email) {
    return (User) sessionFactory
      .getCurrentSession()
      .getNamedQuery("User.findByEmail")
      .setParameter("email", email)
      .uniqueResult();
  }

  @Transactional
  public User save(User user) {
    // Hash and salt the user password
    String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
    // Set the hashed password
    user.setPassword(hashedPassword);
    sessionFactory
      .getCurrentSession()
      .saveOrUpdate(user);
    logger.info("User saved with id: " + user.getId());
    return user;
  }

  @Transactional
  public void delete(User user) {
    sessionFactory
      .getCurrentSession()
      .delete(user);
    logger.info("User deleted with id: " + user.getId());
  }
}
