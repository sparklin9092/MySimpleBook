package net.spark9092.MySimpleBook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.spark9092.MySimpleBook.dto.items.income.ListDto;
import net.spark9092.MySimpleBook.dto.items.income.OneDto;

@Mapper
public interface IIncomeItemsMapper {

	/**
	 * 根據 User ID，查詢「新增一筆收入」功能的下拉選單
	 * @param userId
	 * @return
	 */
	@Select("select id, name from income_items where user_id=#{userId} and is_active=1 and is_delete=0 order by is_default desc")
	@Results({
		@Result(column="id", property="itemId"),
		@Result(column="name", property="itemName")
	})
	List<ListDto> selectListByUserId(@Param("userId") int userId);

	/**
	 * 根據 User ID，查詢收入項目管理的清單
	 * @param userId
	 * @return
	 */
	@Select("select id, name, is_active "
			+ " from income_items "
			+ " where user_id=#{userId} and is_delete=0")
	@Results({
		@Result(column="id", property="itemId"),
		@Result(column="name", property="itemName"),
		@Result(column="is_active", property="itemActive")
	})
	List<ListDto> selectItemListByUserId(@Param("userId") int userId);

	/**
	 * 根據 User ID、Item ID，查詢某一筆收入項目
	 * @param itemId
	 * @param userId
	 * @return
	 */
	@Select("select name, is_default, is_active, create_datetime "
			+ " from income_items "
			+ " where is_delete=0 and id=#{itemId} and user_id=#{userId}")
	@Results({
		@Result(column="name", property="itemName"),
		@Result(column="is_default", property="itemDefault"),
		@Result(column="is_active", property="itemActive"),
		@Result(column="create_datetime", property="createDateTime")
	})
	OneDto selectOneByIds(@Param("itemId") int itemId, @Param("userId") int userId);

	/**
	 * 根據 User ID，新增一筆收入項目
	 * @param userId
	 * @param itemName
	 * @param itemDefault
	 * @return
	 */
	@Insert("insert into income_items(user_id, name, is_default, create_user_id) "
			+ " values(#{userId}, #{itemName}, #{itemDefault}, #{userId})")
	boolean insertByValues(@Param("userId") int userId, @Param("itemName") String itemName, @Param("itemDefault") boolean itemDefault);

	/**
	 * 根據 User ID、Item ID，更新某一筆收入項目
	 * @param userId
	 * @param itemId
	 * @param itemName
	 * @param itemActive
	 * @param itemDefault
	 * @return
	 */
	@Update("update income_items set "
			+ " name=#{itemName}, is_active=#{itemActive}, is_default=#{itemDefault} "
			+ " where id=#{itemId} and user_id=#{userId}")
	boolean updateByValues(@Param("userId") int userId, @Param("itemId") int itemId,
			@Param("itemName") String itemName, @Param("itemActive") boolean itemActive,
			@Param("itemDefault") boolean itemDefault);

	/**
	 * 根據 User ID、Item ID，刪除某一筆收入項目(假刪除，把刪除標記設為True)
	 * @param userId
	 * @param itemId
	 * @return
	 */
	@Update("update income_items set "
			+ " is_delete=1 "
			+ " where id=#{itemId} and user_id=#{userId}")
	boolean deleteByIds(@Param("userId") int userId, @Param("itemId") int itemId);

	/**
	 * 根據使用者ID刪除某一筆收入項目，假刪除，把刪除標記改為1
	 * @param userId
	 * @return
	 */
	@Update("update income_items set "
			+ " is_delete=1 "
			+ " where user_id=#{userId}")
	boolean deleteAllByUserId(@Param("userId") int userId);
}
