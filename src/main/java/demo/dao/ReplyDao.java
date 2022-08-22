package demo.dao;

import java.util.List;

import demo.entities.Reply;
import demo.entities.Thread;

public interface ReplyDao {
  List<Reply> findAll();
  Reply findById(Integer id);
  List<Reply> findRelated(Thread thread);
  Reply findLatest(Thread thread);
  Reply save(Reply reply);
  void delete(Reply reply);
}
