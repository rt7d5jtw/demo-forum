package demo.dao;

import java.util.List;

import demo.entities.Thread;

public interface ThreadDao {
  List<Thread> findAll();
  Thread findById(Integer id);
  Thread save(Thread thread);
  void delete(Thread thread);
}
