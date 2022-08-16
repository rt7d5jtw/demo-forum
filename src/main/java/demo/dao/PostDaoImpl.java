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

import demo.entities.Post;
import demo.entities.Authority;

import java.util.List;

@SuppressWarnings("unchecked")
@Transactional
@Repository("postDao")
public class PostDaoImpl implements PostDao {

  private static Logger logger = LoggerFactory.getLogger(PostDaoImpl.class);
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
  public List<Post> findAll() {
    return sessionFactory
      .getCurrentSession()
      .getNamedQuery("Post.findAll")
      .list();
  }

  @Transactional(readOnly = true)
  public Post findById(Integer id) {
    return (Post) sessionFactory
      .getCurrentSession()
      .getNamedQuery("Post.findById")
      .setParameter("id", id)
      .uniqueResult();
  }

  @Transactional
  public Post save(Post post) {
    sessionFactory
      .getCurrentSession()
      .saveOrUpdate(post);
    logger.info("Post saved with id: " + post.getId());
    return post;
  }

  @Transactional
  public void delete(Post post) {
    sessionFactory
      .getCurrentSession()
      .delete(post);
    logger.info("Post deleted with id: " + post.getId());
  }

}
