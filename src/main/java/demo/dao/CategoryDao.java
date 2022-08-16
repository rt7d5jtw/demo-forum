package demo.dao;

import java.util.List;

import demo.entities.Category;

public interface CategoryDao {
  List<Category> findAll();
  Category findById(Integer id);
  Category findByTopic(String topic);
  Category save(Category category);
  void delete(Category category);
}
