package clonegod.framework.dal.dao;

import clonegod.framework.dal.dao.Author;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Author record);

    int insertSelective(Author record);

    Author selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Author record);

    int updateByPrimaryKey(Author record);
}