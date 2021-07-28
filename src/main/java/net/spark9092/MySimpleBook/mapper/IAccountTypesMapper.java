package net.spark9092.MySimpleBook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.spark9092.MySimpleBook.dto.items.accountType.ListDto;
import net.spark9092.MySimpleBook.dto.items.accountType.OneDto;

@Mapper
public interface IAccountTypesMapper {

	/**
	 * 根據 User ID，查詢帳戶類型項目管理的清單
	 * @param userId
	 * @return
	 */
	@Select("select id, name, is_active "
			+ " from account_types "
			+ " where user_id=#{userId} and is_delete=0")
	@Results({
		@Result(column="id", property="typeId"),
		@Result(column="name", property="typeName"),
		@Result(column="is_active", property="typeActive")
	})
	List<ListDto> selectItemListByUserId(@Param("userId") int userId);

	/**
	 * 根據 User ID、Item ID，查詢某一筆帳戶類型項目
	 * @param typeId
	 * @param userId
	 * @return
	 */
	@Select("select name, is_default, is_active, create_datetime "
			+ " from account_types "
			+ " where is_delete=0 and id=#{typeId} and user_id=#{userId}")
	@Results({
		@Result(column="name", property="typeName"),
		@Result(column="is_default", property="typeDefault"),
		@Result(column="is_active", property="typeActive"),
		@Result(column="create_datetime", property="createDateTime")
	})
	OneDto selectOneByIds(@Param("typeId") int typeId, @Param("userId") int userId);

	/**
	 * 根據 User ID，新增一筆帳戶類型項目
	 * @param userId
	 * @param typeName
	 * @param typeDefault
	 * @return
	 */
	@Insert("insert into account_types(user_id, name, is_default, create_user_id) "
			+ " values(#{userId}, #{typeName}, #{typeDefault}, #{userId})")
	boolean insertByValues(@Param("userId") int userId, @Param("typeName") String typeName,
			@Param("typeDefault") boolean typeDefault);

	/**
	 * 根據 User ID、Item ID，更新某一筆帳戶類型項目
	 * @param userId
	 * @param typeId
	 * @param typeName
	 * @param typeActive
	 * @param typeDefault
	 * @return
	 */
	@Update("update account_types set "
			+ " name=#{typeName}, is_active=#{typeActive}, is_default=#{typeDefault} "
			+ " where id=#{typeId} and user_id=#{userId}")
	boolean updateByValues(@Param("userId") int userId, @Param("typeId") int typeId,
			@Param("typeName") String typeName, @Param("typeActive") boolean typeActive,
			@Param("typeDefault") boolean typeDefault);

	/**
	 * 根據 User ID、Item ID，刪除某一筆帳戶類型項目(假刪除，把刪除標記設為True)
	 * @param userId
	 * @param typeId
	 * @return
	 */
	@Update("update account_types set "
			+ " is_delete=1 "
			+ " where id=#{typeId} and user_id=#{userId}")
	boolean deleteByIds(@Param("userId") int userId, @Param("typeId") int typeId);

	/**
	 * 根據使用者ID刪除某一筆帳戶類型，假刪除，把刪除標記改為1
	 * @param userId
	 * @return
	 */
	@Update("update account_types set "
			+ " is_delete=1 "
			+ " where user_id=#{userId}")
	boolean deleteAllByUserId(@Param("userId") int userId);
}
