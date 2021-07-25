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

	/**
	 * 根據使用者 ID，查詢使用者「全部」資料
	 * @param id
	 * @return
	 */
	@Select("select * from user_info where id=#{userId}")
	@Results({
		@Result(column="id", property="id"),
		@Result(column="user_name", property="userName"),
		@Result(column="user_password", property="userPwd"),
		@Result(column="last_login_datetime", property="lastLoginDateTime"),
		@Result(column="is_active", property="isActive"),
		@Result(column="is_delete", property="isDelete"),
		@Result(column="is_guest", property="isGuest"),
		@Result(column="guest_seq", property="guestSeq"),
		@Result(column="email", property="userEmail"),
		@Result(column="phone", property="userPhone"),
		@Result(column="create_user_id", property="createUserId"),
		@Result(column="create_datetime", property="createDateTime")
	})
	UserInfoEntity selectUserInfoById(@Param("userId") int userId);
	
	/**
	 * 根據使用者名稱(user_name)，查詢使用者「全部」資料
	 * @param userName
	 * @return
	 */
	@Select("select * from user_info where user_name=#{userName}")
	@Results({
		@Result(column="id", property="id"),
		@Result(column="user_name", property="userName"),
		@Result(column="user_password", property="userPwd"),
		@Result(column="last_login_datetime", property="lastLoginDateTime"),
		@Result(column="is_active", property="isActive"),
		@Result(column="is_delete", property="isDelete"),
		@Result(column="is_guest", property="isGuest"),
		@Result(column="guest_seq", property="guestSeq"),
		@Result(column="email", property="userEmail"),
		@Result(column="phone", property="userPhone"),
		@Result(column="create_user_id", property="createUserId"),
		@Result(column="create_datetime", property="createDateTime")
	})
	UserInfoEntity selectByUserName(@Param("userName") String userName);

	/**
	 * 新增一位訪客類型的使用者
	 * @param userName
	 * @param userPwd
	 * @param systemUserId
	 * @param guestSeq
	 * @return
	 */
	@Insert("insert into user_info(user_name, user_account, user_password, is_guest, guest_seq, create_user_id) "
			+ "values(#{userName}, concat('guest', #{guestSeq}), #{userPwd}, 1, #{guestSeq}, #{systemUserId})")
	boolean createUserByGuest(@Param("userName") String userName, @Param("userPwd") String userPwd,
			@Param("systemUserId") int systemUserId, @Param("guestSeq") int guestSeq);

	/**
	 * 更新使用者的最後登入時間，通常是登入時候會更新
	 * @param lastLoginDateTime
	 * @param userId
	 * @return
	 */
	@Update("update user_info set last_login_datetime=#{lastLoginDateTime} where id=#{userId}")
	boolean updateById(@Param("lastLoginDateTime") LocalDateTime lastLoginDateTime, @Param("userId") int userId);

	/**
	 * 根據訪客序號，查詢訪客類型的使用者「全部」資料
	 * @param guestSeq 訪客序號
	 * @return
	 */
	@Select("select * from user_info where guest_seq=#{guestSeq} and is_guest=1")
	@Results({
		@Result(column="id", property="id"),
		@Result(column="user_name", property="userName"),
		@Result(column="user_password", property="userPwd"),
		@Result(column="last_login_datetime", property="lastLoginDateTime"),
		@Result(column="is_active", property="isActive"),
		@Result(column="is_delete", property="isDelete"),
		@Result(column="is_guest", property="isGuest"),
		@Result(column="guest_seq", property="guestSeq"),
		@Result(column="email", property="userEmail"),
		@Result(column="phone", property="userPhone"),
		@Result(column="create_user_id", property="createUserId"),
		@Result(column="create_datetime", property="createDateTime")
	})
	UserInfoEntity selectGuestBySeq(@Param("guestSeq") int guestSeq);

	/**
	 * 計算訪客目前的資料數量
	 * @param userId
	 * @return Guest data count 訪客資料數量
	 */
	@Select("select "
			+ "	(select count(b.id) from transfer b where b.user_id=a.id and b.is_delete=0)+ "
			+ "	(select count(c.id) from income c where c.user_id=a.id and c.is_delete=0)+ "
			+ "	(select count(d.id)-1 from income_items d where d.user_id=a.id and d.is_delete=0)+ "
			+ "	(select count(e.id) from spend e where e.user_id=a.id and e.is_delete=0)+ "
			+ "	(select count(f.id)-1 from spend_items f where f.user_id=a.id and f.is_delete=0)+ "
			+ "	(select count(g.id)-2 from account g where g.user_id=a.id and g.is_delete=0)+ "
			+ "	(select count(h.id) from account_types h where h.user_id=a.id and h.is_delete=0) as guestDataCount "
			+ " from user_info a "
			+ " where a.id=#{userId} and a.is_guest=1")
	int getGuestDataCount(@Param("userId") int userId);

	/**
	 * 根據使用者帳號，查詢已存在的數量
	 * @param userAcc
	 * @return
	 */
	@Select("select count(id) from user_info where user_account=#{userAcc}")
	int selectUserCountByUserAcc(@Param("userAcc") String userAcc);

	/**
	 * 根據使用者輸入的帳號、密碼，更新使用者資料，並修改「訪客身份」為「使用者」
	 * @param userId
	 * @param userAcc
	 * @param enPwd
	 * @return
	 */
	@Update("update user_info set "
			+ " user_name=#{userAcc}, user_account=#{userAcc}, "
			+ " user_password=#{enPwd}, is_guest=0 "
			+ " where id=#{userId}")
	boolean bindAccPwdByUserId(@Param("userId") int userId,
			@Param("userAcc") String userAcc, @Param("enPwd") String enPwd);
}
