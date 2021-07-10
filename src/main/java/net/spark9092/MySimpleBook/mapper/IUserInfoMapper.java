package net.spark9092.MySimpleBook.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import net.spark9092.MySimpleBook.entity.UserInfoEntity;

@Mapper
public interface IUserInfoMapper {

	@Select("Select * From user_info Where id = #{id}")
	UserInfoEntity selectAllById(@Param("id") int id);
}
