package bhz.mapper;

import bhz.entity.Pay;

public interface PayMapper {
    int deleteByPrimaryKey(String userid);

    int insert(Pay record);

    int insertSelective(Pay record);

    Pay selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(Pay record);

    int updateByPrimaryKey(Pay record);
}