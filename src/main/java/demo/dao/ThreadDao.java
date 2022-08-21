package demo.dao;

import java.util.List;

import demo.entities.Thread;
import demo.entities.Category;

public interface ThreadDao {
  List<Thread> findAll();
  List<Thread> findRelated(Category category);
  List<Thread> forumThreadSearch(String keywords);
  Thread findById(Integer id);
  Thread eagerFindById(Integer id);
  Thread save(Thread thread);
  void delete(Thread thread);
}
