package net.spark9092.MySimpleBook.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.spark9092.MySimpleBook.dto.verify.MailVerifyCodeLastTimeDto;
import net.spark9092.MySimpleBook.dto.verify.UserMailVerifyDataDto;

@Mapper
public interface IUserVerifyMapper {

	/**
	 * 根據使用者 ID 查詢認證碼資料
	 * @param userId
	 * @return
	 */
	@Select("select id, verify_code, system_send_datetime, is_used "
			+ " from user_verify "
			+ " where user_id=#{userId} and is_active=1 and is_delete=0 "
			+ " order by system_send_datetime desc "
			+ " limit 1")
	@Results({
		@Result(column="id", property="verifyId"),
		@Result(column="verify_code", property="verifyCode"),
		@Result(column="system_send_datetime", property="systemSendDatetime"),
		@Result(column="is_used", property="used")
	})
	UserMailVerifyDataDto selectByUserId(@Param("userId") int userId);

	/**
	 * 根據使用者ID，取得最後一次電子信箱(Email)認證碼發送的時間
	 * @param userId
	 * @return
	 */
	@Select("select system_send_datetime, is_used from user_verify "
			+ " where user_id=#{userId} and verify_type='mail' "
			+ " and is_active=1 and is_delete=0 "
			+ " order by system_send_datetime desc "
			+ " limit 1")
	@Results({
		@Result(column="system_send_datetime", property="systemSendTime"),
		@Result(column="is_used", property="used")
	})
	MailVerifyCodeLastTimeDto selectLastCodeTimeByUserId(@Param("userId") int userId);

	/**
	 * 根據使用者ID，新增電子信箱(Email)的認證碼
	 * @param userId
	 * @param verifyCode
	 * @return
	 */
	@Insert("insert into user_verify(user_id, verify_type, verify_code) "
			+ " values(#{userId}, #{verifyType}, #{verifyCode})")
	boolean insertMailVerifyCodeByUserId(@Param("userId") int userId, 
			@Param("verifyType") String verifyType, 
			@Param("verifyCode") String verifyCode);

	/**
	 * 更新認證碼為「已使用」
	 * @param verifyId
	 * @param userId
	 * @return
	 */
	@Update("update user_verify set "
			+ " user_used_datetime=now(), is_used=1 "
			+ " where id=#{verifyId} and user_id=#{userId}")
	boolean updateByUserId(@Param("verifyId") int verifyId, @Param("userId") int userId);

	/**
	 * 因為service判斷使用者更新基本資料後，與原本的Email不相同，
	 * 所以把原本已經驗證通過的認證碼，全部改為未使用，
	 * 目的是要讓使用者重新認證新的Email
	 * @param userId
	 */
	@Update("update user_verify set "
			+ " is_used=0 "
			+ " where user_id=#{userId}")
	void updateUnUsedByUserId(@Param("userId") int userId);
}
