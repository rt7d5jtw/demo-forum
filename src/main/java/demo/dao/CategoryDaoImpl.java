package demo.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Timestamp;
import java.util.Date;

import demo.entities.Category;
import demo.entities.Authority;

import java.util.List;

@SuppressWarnings("unchecked")
@Transactional
@Repository("categoryDao")
public class CategoryDaoImpl implements CategoryDao {

  private static Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);
  private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  @Autowired
  @Qualifier("sessionFactory")
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true)
  public List<Category> findAll() {
    return sessionFactory
      .getCurrentSession()
      .getNamedQuery("Category.findAll")
      .list();
  }

  @Transactional(readOnly = true)
  public Category findById(Integer id) {
    return (Category) sessionFactory
      .getCurrentSession()
      .getNamedQuery("Category.findById")
      .setParameter("id", id)
      .uniqueResult();
  }

  @Transactional(readOnly = true)
  public Category findByTopic(String topic) {
    return (Category) sessionFactory
      .getCurrentSession()
      .getNamedQuery("Category.findByTopic")
      .setParameter("topic", topic)
      .uniqueResult();
  }

  @Transactional
  public Category save(Category category) {
    sessionFactory
      .getCurrentSession()
      .saveOrUpdate(category);
    logger.info("Category saved with id: " + category.getId());
    return category;
  }

  @Transactional
  public void delete(Category category) {
    sessionFactory
      .getCurrentSession()
      .delete(category);
    logger.info("Category deleted with id: " + category.getId());
  }

}
