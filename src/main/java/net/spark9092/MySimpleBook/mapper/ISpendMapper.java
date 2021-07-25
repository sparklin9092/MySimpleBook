package net.spark9092.MySimpleBook.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.spark9092.MySimpleBook.dto.main.SpendListDto;
import net.spark9092.MySimpleBook.dto.spend.OneDto;
import net.spark9092.MySimpleBook.dto.spend.RecListDto;
import net.spark9092.MySimpleBook.dto.spend.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.spend.SelectItemListDto;

@Mapper
public interface ISpendMapper {

	@Select("select id, name from spend_items where user_id=#{userId} and is_active=1 and is_delete=0 order by is_default desc")
	@Results({
		@Result(column="id", property="itemId"),
		@Result(column="name", property="itemName")
	})
	List<SelectItemListDto> selectItemListByUserId(@Param("userId") int userId);


	@Select("select id, name from account where user_id=#{userId} and is_active=1 and is_delete=0 order by is_default desc")
	@Results({
		@Result(column="id", property="accountId"),
		@Result(column="name", property="accountName")
	})
	List<SelectAccountListDto> selectAccountListByUserId(@Param("userId") int userId);

	@Select("select id, date_format(spend_date, '%Y年%m月%d日') as spendDate, amount, "
			+ " (select name from spend_items where id=item_id) as spendItmeName "
			+ " from spend "
			+ " where user_id=#{userId} and is_delete=0 and spend_date >= #{startDate} and spend_date <= #{endDate}")
	@Results({
		@Result(column="id", property="spendId"),
		@Result(column="spendDate", property="spendDate"),
		@Result(column="amount", property="amount"),
		@Result(column="spendItmeName", property="spendItmeName")
	})
	List<RecListDto> selectRecordsByUserId(@Param("userId") int userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

	@Select("select spend_date, item_id, account_id, amount, create_datetime, remark "
			+ " from spend "
			+ " where is_delete=0 and id=#{spendId} and user_id=#{userId}")
	@Results({
		@Result(column="spend_date", property="spendDate"),
		@Result(column="item_id", property="spendItemId"),
		@Result(column="account_id", property="accountId"),
		@Result(column="amount", property="amount"),
		@Result(column="create_datetime", property="createDateTime"),
		@Result(column="remark", property="remark")
	})
	OneDto selectOneByIds(@Param("spendId") int spendId, @Param("userId") int userId);

	@Insert("insert into spend(user_id, item_id, account_id, spend_date, amount, create_user_id, remark) "
			+ " values(#{userId}, #{spendItemId}, #{accountItemId}, #{spendDate}, #{amount}, #{userId}, #{remark})")
	boolean createByValues(@Param("userId") int userId, @Param("spendItemId") int spendItemId, 
			@Param("accountItemId") int accountItemId, @Param("spendDate") String spendDate, 
			@Param("amount") BigDecimal amount, @Param("remark") String remark);

	@Update("update spend set "
			+ " item_id=#{spendItemId}, account_id=#{accountId}, spend_date=#{spendDate}, "
			+ " amount=#{amount}, remark=#{remark} "
			+ " where id=#{spendId} and user_id=#{userId}")
	boolean modifyByValues(@Param("userId") int userId, @Param("spendId") int spendId, 
			@Param("spendItemId") int spendItemId, @Param("accountId") int accountId, 
			@Param("spendDate") String spendDate, @Param("amount") BigDecimal amount,
			@Param("remark") String remark);

	@Update("update spend set "
			+ " is_delete=1 "
			+ " where id=#{spendId} and user_id=#{userId}")
	boolean deleteByIds(@Param("userId") int userId, @Param("spendId") int spendId);

	@Update("update spend set "
			+ " is_delete=1 "
			+ " where user_id=#{userId}")
	boolean deleteAllByUserId(@Param("userId") int userId);

	/**
	 * 首頁查詢當日最新5筆支出紀錄
	 * @param userId
	 * @return
	 */
	@Select("select (select name from spend_items where id = item_id) as itemName,	amount as amnt "
			+ " from spend "
			+ " where user_id = #{userId} and is_delete=0 and spend_date = date_sub(curdate(), interval 0 day) "
			+ " order by id desc limit 5")
	@Results({
		@Result(column="itemName", property="itemName"),
		@Result(column="amnt", property="amnt")
	})
	List<SpendListDto> getTodayListForMain(@Param("userId") int userId);
}
