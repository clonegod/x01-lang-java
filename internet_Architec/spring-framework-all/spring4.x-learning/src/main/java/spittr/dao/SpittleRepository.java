package spittr.dao;

import java.util.List;

import spittr.domain.Spittle;

public interface SpittleRepository {

  List<Spittle> findRecentSpittles();

  List<Spittle> findSpittles(long max, int count);
  
  Spittle findOne(long id);

  Spittle save(Spittle spittle);

}
