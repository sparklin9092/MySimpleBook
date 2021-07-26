package net.spark9092.MySimpleBook.mapper;

import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.spark9092.MySimpleBook.dto.verify.MailBindMsgDto;
import net.spark9092.MySimpleBook.dto.verify.UserMailMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.dto.user.UserMailDto;

@Mapper
public interface IUserInfoMapper {

	/**
	 * 根據使用者 ID，查詢使用者「全部」資料
	 * @param userId
	 * @return
	 */
	@Select("select * from user_info where id=#{userId}")
	@Results({
		@Result(column="id", property="id"),
		@Result(column="user_name", property="userName"),
		@Result(column="user_account", property="userAccount"),
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
	 * 根據使用者帳號(user_account)，查詢使用者「全部」資料
	 * @param userAcc
	 * @return
	 */
	@Select("select * from user_info where user_account=#{userAcc}")
	@Results({
		@Result(column="id", property="id"),
		@Result(column="user_name", property="userName"),
		@Result(column="user_account", property="userAccount"),
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
	UserInfoEntity selectUserInfoByAccount(@Param("userAcc") String userAcc);

	/**
	 * 根據訪客序號，查詢訪客類型的使用者「全部」資料
	 * @param guestSeq
	 * @return
	 */
	@Select("select * from user_info where guest_seq=#{guestSeq} and is_guest=1")
	@Results({
		@Result(column="id", property="id"),
		@Result(column="user_name", property="userName"),
		@Result(column="user_account", property="userAccount"),
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
	 * 根據使用者帳號，查詢使用者電子信箱(Email)
	 * @param userAccount
	 * @return
	 */
	@Select("select id, email from user_info where user_account=#{userAccount}")
	@Results({
		@Result(column="id", property="userId"),
		@Result(column="email", property="userMail")
	})
	UserMailMsgDto selectMailByAccount(@Param("userAccount") String userAccount);

	/**
	 * 根據使用者ID，查詢使用者電子信箱(Email)
	 * @param userAccount
	 * @return
	 */
	@Select("select email from user_info where id=#{userId}")
	@Results({
		@Result(column="email", property="userMail")
	})
	UserMailDto selectMailByUserId(@Param("userId") int userId);

	/**
	 * 根據使用者帳號，查詢使用者ID
	 * @param userAccount
	 * @return
	 */
	@Select("select id from user_info where user_account=#{userAccount}")
	@Results({
		@Result(column="id", property="userId")
	})
	MailBindMsgDto selectUserIdByAccount(@Param("userAccount") String userAccount);

	/**
	 * 根據使用者帳號，查詢已存在的數量，排除自己的 user_acoount
	 * @param userAcc
	 * @param userId
	 * @return
	 */
	@Select("select count(id) from user_info where user_account=#{userAcc} and id!=#{userId}")
	int selectExistUserCountByAcc(@Param("userAcc") String userAcc, @Param("userId") int userId);

	/**
	 * 計算訪客目前的資料數量
	 * @param userId
	 * @return
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
	int selectGuestDataCount(@Param("userId") int userId);

	/**
	 * 根據Email，查詢是否已被綁定
	 * @param userMail
	 * @return 查出來的結果如果大於1筆資料，表示這個Email已經綁定過了
	 */
	@Select("select count(b.id) "
			+ " from user_info a, user_verify b "
			+ " where a.id=b.user_id "
			+ "	and a.email=#{userMail} "
			+ "	and b.is_used=1")
	int selectExistBindMailCount(@Param("userMail") String userMail);

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
	boolean createGuest(@Param("userName") String userName, @Param("userPwd") String userPwd,
			@Param("systemUserId") int systemUserId, @Param("guestSeq") int guestSeq);

	/**
	 * 更新使用者基本資料
	 * @param userId
	 * @param userName
	 * @param userAcc
	 * @param userEmail
	 * @param userPhone
	 * @return
	 */
	@Update("update user_info set "
			+ " user_name=#{userName}, user_account=#{userAcc}, email=#{userEmail}, phone=#{userPhone} "
			+ " where id=#{userId}")
	boolean updateUserInfoById(@Param("userId") int userId, @Param("userName") String userName, 
			@Param("userAcc") String userAcc, @Param("userEmail") String userEmail, 
			@Param("userPhone") String userPhone);

	/**
	 * 更新使用者密碼
	 * @param userId
	 * @param enNewPwd
	 * @return
	 */
	@Update("update user_info set "
			+ " user_password=#{enNewPwd} "
			+ " where id=#{userId}")
	boolean updateUserNewPwdById(@Param("userId") int userId, @Param("enNewPwd") String enNewPwd);

	/**
	 * 更新使用者的最後登入時間，通常是登入時候會更新
	 * @param lastLoginDateTime
	 * @param userId
	 * @return
	 */
	@Update("update user_info set last_login_datetime=#{lastLoginDateTime} where id=#{userId}")
	boolean updateLastLoginTimeById(@Param("lastLoginDateTime") LocalDateTime lastLoginDateTime, @Param("userId") int userId);

	/**
	 * 根據使用者輸入的帳號、加密後的密碼，更新使用者資料，並修改「訪客身份」為「使用者」
	 * @param userId
	 * @param userAcc
	 * @param enPwd
	 * @return
	 */
	@Update("update user_info set "
			+ " user_name=#{userAcc}, user_account=#{userAcc}, "
			+ " user_password=#{enPwd}, is_guest=0 "
			+ " where id=#{userId}")
	boolean updateAccPwdByUserId(@Param("userId") int userId,
			@Param("userAcc") String userAcc, @Param("enPwd") String enPwd);

	/**
	 * 根據使用者ID，更新Email
	 * @param userId
	 * @param userMail
	 * @return
	 */
	@Update("update user_info set "
			+ " email=#{userMail} "
			+ " where id=#{userId}")
	boolean updateMailByUserId(@Param("userId") int userId, @Param("userMail") String userMail);
	
	/**
	 * 根據使用者ID，將使用者Email更新為帳號，並更新加密後的密碼
	 * @param userId
	 * @param enPwd
	 * @return
	 */
	@Update("update user_info set "
			+ " user_account=email, user_password=#{enPwd}, is_guest=0 "
			+ " where id=#{userId}")
	boolean updateMailToAccWithPwdById(@Param("userId") int userId, @Param("enPwd") String enPwd);

	/**
	 * 刪除使用者，假刪除，把刪除標記改為 1
	 * @param userId
	 * @return
	 */
	@Update("update user_info set is_delete=1 where id=#{userId}")
	boolean deleteByUserId(@Param("userId") int userId);
}
