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

import demo.entities.Reply;
import demo.entities.Authority;

import java.util.List;

@SuppressWarnings("unchecked")
@Transactional
@Repository("replyDao")
public class ReplyDaoImpl implements ReplyDao {

  private static Logger logger = LoggerFactory.getLogger(ReplyDaoImpl.class);
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
  public List<Reply> findAll() {
    return sessionFactory
      .getCurrentSession()
      .getNamedQuery("Reply.findAll")
      .list();
  }

  @Transactional(readOnly = true)
  public Reply findById(Integer id) {
    return (Reply) sessionFactory
      .getCurrentSession()
      .getNamedQuery("Reply.findById")
      .setParameter("id", id)
      .uniqueResult();
  }

  @Transactional
  public Reply save(Reply reply) {
    sessionFactory
      .getCurrentSession()
      .saveOrUpdate(reply);
    logger.info("Reply saved with id: " + reply.getId());
    return reply;
  }

  @Transactional
  public void delete(Reply reply) {
    sessionFactory
      .getCurrentSession()
      .delete(reply);
    logger.info("Reply deleted with id: " + reply.getId());
  }

}
