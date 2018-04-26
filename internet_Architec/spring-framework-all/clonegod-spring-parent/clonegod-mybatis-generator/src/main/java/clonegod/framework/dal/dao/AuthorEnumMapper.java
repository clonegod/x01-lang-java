package clonegod.framework.dal.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorEnumMapper extends AuthorMapper {
	int insertEnum(Author record);

    Author selectByPrimaryKeyEnum(Integer id);
}
