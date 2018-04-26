package spittr.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import spittr.dao.SpittleRepository;
import spittr.domain.Spittle;
import spittr.exception.DuplicateSpittleException;
import spittr.exception.SpittleNotFoundException;

@Repository
public class JdbcSpittleRepository implements SpittleRepository {

  private JdbcOperations jdbc;

  @Autowired
  public JdbcSpittleRepository(JdbcOperations jdbc) {
    this.jdbc = jdbc;
  }

  public List<Spittle> findRecentSpittles() {
    return jdbc.query(
        "select id, message, created_at, latitude, longitude" +
        " from Spittle" +
        " order by created_at desc limit 20",
        new SpittleRowMapper());
  }

  public List<Spittle> findSpittles(long max, int count) {
    return jdbc.query(
        "select id, message, created_at, latitude, longitude" +
        " from Spittle" +
        " where id < ?" +
        " order by created_at desc limit 20",
        new SpittleRowMapper(), max);
  }

  public Spittle findOne(long id) {
    try {
    return jdbc.queryForObject(
        "select id, message, created_at, latitude, longitude" +
        " from Spittle" +
        " where id = ?",
        new SpittleRowMapper(), id);
    } catch (EmptyResultDataAccessException e) {
      throw new SpittleNotFoundException(id);
    }
  }

  public Spittle save(Spittle spittle) {
    String sql = "insert into Spittle (message, created_at, latitude, longitude)" +
        " values (?, ?, ?, ?)";
    PreparedStatementCreatorFactory statementCreatorFactory = new PreparedStatementCreatorFactory(sql, Types.VARCHAR, Types.TIMESTAMP, Types.DOUBLE, Types.DOUBLE);
    statementCreatorFactory.setReturnGeneratedKeys(true);
    PreparedStatementCreator creator = statementCreatorFactory.newPreparedStatementCreator(new Object[] {
        spittle.getMessage(),
        spittle.getTime(),
        spittle.getLatitude(),
        spittle.getLongitude()
    });

    
    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    try {
		jdbc.update(creator, keyHolder);
	} catch (DataAccessException e) {
		e.printStackTrace();
		throw new DuplicateSpittleException();
	}
    
    return new Spittle(
        keyHolder.getKey().longValue(), 
        spittle.getMessage(), 
        spittle.getTime(), 
        spittle.getLongitude(), 
        spittle.getLatitude());
    
    
  }

  private static class SpittleRowMapper implements RowMapper<Spittle> {
    public Spittle mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new Spittle(
          rs.getLong("id"),
          rs.getString("message"), 
          rs.getDate("created_at"), 
          rs.getDouble("longitude"), 
          rs.getDouble("latitude"));
    }
  }
  
}
