package net.spark9092.MySimpleBook.mapper;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.spark9092.MySimpleBook.entity.UserInfoEntity;

@Mapper
public interface IUserInfoMapper {

	@Select("select * from user_info where id=#{id}")
	@Results({
		@Result(column="id", property="id"),
		@Result(column="user_name", property="userName"),
		@Result(column="user_password", property="userPwd"),
		@Result(column="last_login_datetime", property="lastLoginDateTime"),
		@Result(column="is_active", property="isActive"),
		@Result(column="is_delete", property="isDelete"),
		@Result(column="create_user_id", property="createUserId"),
		@Result(column="create_datetime", property="createDateTime")
	})
	UserInfoEntity selectAllById(@Param("id") int id);
	
	@Select("select * from user_info where user_name=#{userName}")
	@Results({
		@Result(column="id", property="id"),
		@Result(column="user_name", property="userName"),
		@Result(column="user_password", property="userPwd"),
		@Result(column="last_login_datetime", property="lastLoginDateTime"),
		@Result(column="is_active", property="isActive"),
		@Result(column="is_delete", property="isDelete"),
		@Result(column="create_user_id", property="createUserId"),
		@Result(column="create_datetime", property="createDateTime")
	})
	UserInfoEntity selectByUserName(@Param("userName") String userName);

	@Insert("insert into user_info(user_name, user_password, create_user_id) "
			+ "values(#{userName}, #{userPwd}, #{systemUserId})")
	boolean createUserByGuest(@Param("userName") String userName, @Param("userPwd") String userPwd, 
			@Param("systemUserId") int systemUserId);
	
	@Update("update user_info set last_login_datetime=#{lastLoginDateTime} where id=#{id}")
	boolean updateById(@Param("lastLoginDateTime") LocalDateTime lastLoginDateTime, @Param("id") int id);
}
