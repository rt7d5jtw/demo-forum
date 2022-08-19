package demo.dao;

import java.util.List;

import demo.entities.Reply;

public interface ReplyDao {
  List<Reply> findAll();
  Reply findById(Integer id);
  Reply save(Reply reply);
  void delete(Reply reply);
}
