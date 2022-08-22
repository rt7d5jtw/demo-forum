package demo.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Hibernate;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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

import demo.entities.Thread;
import demo.entities.Authority;
import demo.entities.Category;
import demo.entities.Reply;

import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

@SuppressWarnings("unchecked")
@Transactional
@Repository("threadDao")
public class ThreadDaoImpl implements ThreadDao {

  private static Logger logger = LoggerFactory.getLogger(ThreadDaoImpl.class);
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
  public List<Thread> findAll() {
    return sessionFactory
      .getCurrentSession()
      .getNamedQuery("Thread.findAll")
      .list();
  }

  /** 
   * Eagerly fetches only the threads relevant 
   * to a specific category by categoryId. 
   * */
  @Transactional(readOnly = true)
  public List<Thread> findRelated(Category category) {
    try (Session session = sessionFactory.openSession()) {
      List<Thread> threadlist = (List<Thread>) session
        .getNamedQuery("Thread.findRelated")
        .setParameter("category", category)
        .list();

      Hibernate.initialize(threadlist);
      Hibernate.isInitialized(threadlist);
      for (Thread forumThread : threadlist) {
        Hibernate.initialize(forumThread);
        Hibernate.isInitialized(forumThread);
        Hibernate.isInitialized(forumThread.getUser());
        Hibernate.initialize(forumThread.getUser());
        Hibernate.isInitialized(forumThread.getCategory());
        Hibernate.initialize(forumThread.getCategory());
      }

      session.close();
      return threadlist;
    }
  }

  @Transactional(readOnly = true)
  public Thread findById(Integer id) {
    return (Thread) sessionFactory
      .getCurrentSession()
      .getNamedQuery("Thread.findById")
      .setParameter("id", id)
      .uniqueResult();
  }

  /** Eager fetching user for the findById method. */
  @Transactional(readOnly = true)
  public Thread eagerFindById(Integer id) {
    try (Session session = sessionFactory.openSession()) {

      Thread forumThread = (Thread) session
        .getNamedQuery("Thread.findById")
        .setParameter("id", id)
        .uniqueResult();

      Hibernate.initialize(forumThread);
      Hibernate.isInitialized(forumThread.getUser());
      Hibernate.initialize(forumThread.getUser());
      Hibernate.isInitialized(forumThread.getCategory());
      Hibernate.initialize(forumThread.getCategory());

      session.close();
      return forumThread;
    }
  }

  /** 
   * Eagerly fetches only the threads relevant 
   * to the specific keywords in subject and possible author and/or category. 
   * */
  @Transactional(readOnly = true)
  public List<Thread> forumThreadSearch(String keywords) {
    try (Session session = sessionFactory.openSession()) {
      List<Thread> threadlist = (List<Thread>) session
        .getNamedQuery("Thread.findByKeywords")
        .setParameter("keywords", keywords)
        .list();

      Hibernate.initialize(threadlist);
      Hibernate.isInitialized(threadlist);
      for (Thread forumThread : threadlist) {
        Hibernate.initialize(forumThread);
        Hibernate.isInitialized(forumThread);
        Hibernate.isInitialized(forumThread.getUser());
        Hibernate.initialize(forumThread.getUser());
        Hibernate.isInitialized(forumThread.getCategory());
        Hibernate.initialize(forumThread.getCategory());
      }

      session.close();
      return threadlist;
    }
  }

  /** 
   * Finds most recently created reply in a thread of a specific category.
   * */
  @Transactional(readOnly = true)
  public Reply findLatest(Category category) {
    try (Session session = sessionFactory.openSession()) {
      CriteriaBuilder builder = (CriteriaBuilder) 
        session.getCriteriaBuilder();

      CriteriaQuery<Thread> query = builder.createQuery(Thread.class);
      Root<Thread> root = query.from(Thread.class);
      query.select(root);

      // Get only the threads from this specfic category
      query.where(builder.equal(root.get("category"), category.getId()));

      Query<Thread> sessionQuery = session.createQuery(query);
      List<Thread> results = sessionQuery.getResultList();

      for (var result : results) {
        System.out.println("\n");
        System.out.println("Result -> " + result.getContent());
        System.out.println("\n");
      }

      session.close();
      return new Reply();
    }
  }

  @Transactional
  public Thread save(Thread thread) {
    sessionFactory
      .getCurrentSession()
      .saveOrUpdate(thread);
    logger.info("Thread saved with id: " + thread.getId());
    return thread;
  }

  @Transactional
  public void delete(Thread thread) {
    sessionFactory
      .getCurrentSession()
      .delete(thread);
    logger.info("Thread deleted with id: " + thread.getId());
  }

}
