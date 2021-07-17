package net.spark9092.MySimpleBook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.spark9092.MySimpleBook.dto.AccountListDto;
import net.spark9092.MySimpleBook.dto.AccountOneDto;
import net.spark9092.MySimpleBook.dto.AccountTypeListDto;

@Mapper
public interface IAccountMapper {

	/**
	 * 根據 User ID，查詢帳戶的清單
	 * @param userId
	 * @return
	 */
	@Select("select a.id, b.name as typeName, a.name, a.is_active "
			+ " from account a "
			+ " left join items_account_type b on b.id = a.type_id "
			+ " where a.user_id=#{userId} and a.is_delete=0")
	@Results({
		@Result(column="id", property="accountId"),
		@Result(column="typeName", property="typeName"),
		@Result(column="name", property="accountName"),
		@Result(column="is_active", property="itemActive")
	})
	List<AccountListDto> selectListByUserId(@Param("userId") int userId);

	/**
	 * 根據 User ID，查詢帳戶類型的清單
	 * @param userId
	 * @return
	 */
	@Select("select id, name "
			+ " from items_account_type "
			+ " where user_id=#{userId} and is_active=1 and is_delete=0 "
			+ " order by is_default desc")
	@Results({
		@Result(column="id", property="typeId"),
		@Result(column="name", property="typeName")
	})
	List<AccountTypeListDto> selectTypeListByUserId(@Param("userId") int userId);

	@Select("select a.type_id, a.name, a.is_default, a.is_active, "
			+ " if(a.limit_date regexp '[0-9]{4}-[0-9]{2}-[0-9]{2}', true, false) as enableLimitDate, "
			+ " date_format(a.limit_date, '%Y') as limitYear, date_format(a.limit_date, '%m') as limitMonth, "
			+ " b.user_name, a.create_datetime "
			+ " from account a "
			+ " left join user_info b on b.id = a.create_user_id "
			+ " where a.is_delete=0 and a.id=#{accountId} and a.user_id=#{userId}")
	@Results({
		@Result(column="type_id", property="accountTypeId"),
		@Result(column="name", property="accountName"),
		@Result(column="is_default", property="accountDefault"),
		@Result(column="is_active", property="accountActive"),
		@Result(column="enableLimitDate", property="enableLimitDate"),
		@Result(column="limitYear", property="limitYear"),
		@Result(column="limitMonth", property="limitMonth"),
		@Result(column="user_name", property="createUserName"),
		@Result(column="create_datetime", property="createDateTime")
	})
	AccountOneDto selectOneByIds(@Param("accountId") int accountId, @Param("userId") int userId);

	/**
	 * 根據 User ID，新增一個帳戶
	 * @param userId
	 * @param accountType
	 * @param accountName
	 * @param accountDefault
	 * @param limitDate
	 * @return
	 */
	@Insert("insert into account(user_id, type_id, name, limit_date, is_default, create_user_id) "
			+ " values(#{userId}, #{accountType}, #{accountName}, #{limitDate}, #{accountDefault}, #{userId})")
	boolean createByValues(@Param("userId") int userId, @Param("accountType") int accountType, 
			@Param("accountName") String accountName, @Param("accountDefault") boolean accountDefault, 
			@Param("limitDate") String limitDate);

	@Update("update account set "
			+ " type_id=#{accountType}, name=#{accountName}, limit_date=#{limitDate}, "
			+ " is_default=#{accountDefault}, is_active=#{accountActive} "
			+ " where id=#{accountId} and user_id=#{userId}")
	boolean modifyByValues(@Param("userId") int userId, @Param("accountId") int accountId, 
			@Param("accountType") int accountType, @Param("accountName") String accountName, 
			@Param("limitDate") String limitDate, @Param("accountDefault") boolean accountDefault, 
			@Param("accountActive") boolean accountActive);
	
	/**
	 * 根據 User ID、Account ID，刪除某一筆帳戶(假刪除，把刪除標記設為True)
	 * @param userId
	 * @param accountId
	 * @return
	 */
	@Update("update account set "
			+ " is_delete=1 "
			+ " where id=#{accountId} and user_id=#{userId}")
	boolean deleteByIds(@Param("userId") int userId, @Param("accountId") int accountId);
}
