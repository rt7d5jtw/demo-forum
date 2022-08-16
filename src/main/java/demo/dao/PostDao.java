package demo.dao;

import java.util.List;

import demo.entities.Post;

public interface PostDao {
  List<Post> findAll();
  Post findById(Integer id);
  Post save(Post post);
  void delete(Post post);
}
