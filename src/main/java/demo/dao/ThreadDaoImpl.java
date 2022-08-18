package demo.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Hibernate;
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

import demo.entities.Thread;
import demo.entities.Authority;

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
      Transaction tx = session.beginTransaction();

      Thread forumThread = (Thread) session
        .getNamedQuery("Thread.findById")
        .setParameter("id", id)
        .uniqueResult();

      Hibernate.initialize(forumThread);
      Hibernate.isInitialized(forumThread.getUser());
      Hibernate.initialize(forumThread.getUser());

      tx.commit();
      session.close();
      return forumThread;
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
