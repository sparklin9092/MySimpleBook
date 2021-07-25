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

import net.spark9092.MySimpleBook.dto.income.OneDto;
import net.spark9092.MySimpleBook.dto.income.RecListDto;
import net.spark9092.MySimpleBook.dto.income.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.income.SelectItemListDto;
import net.spark9092.MySimpleBook.dto.main.IncomeListDto;

@Mapper
public interface IIncomeMapper {

	@Select("select id, name from income_items where user_id=#{userId} and is_active=1 and is_delete=0 order by is_default desc")
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

	@Select("select id, date_format(income_date, '%Y年%m月%d日') as incomeDate, amount, "
			+ " (select name from income_items where id=item_id) as incomeItemName "
			+ " from income "
			+ " where user_id=#{userId} and is_delete=0 and income_date >= #{startDate} and income_date <= #{endDate}")
	@Results({
		@Result(column="id", property="incomeId"),
		@Result(column="incomeDate", property="incomeDate"),
		@Result(column="amount", property="amount"),
		@Result(column="incomeItemName", property="incomeItemName")
	})
	List<RecListDto> selectRecordsByUserId(@Param("userId") int userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

	@Select("select income_date, item_id, account_id, amount, create_datetime, remark "
			+ " from income "
			+ " where is_delete=0 and id=#{incomeId} and user_id=#{userId}")
	@Results({
		@Result(column="income_date", property="incomeDate"),
		@Result(column="item_id", property="incomeItemId"),
		@Result(column="account_id", property="accountId"),
		@Result(column="amount", property="amount"),
		@Result(column="create_datetime", property="createDateTime"),
		@Result(column="remark", property="remark")
	})
	OneDto selectOneByIds(@Param("incomeId") int incomeId, @Param("userId") int userId);

	@Insert("insert into income(user_id, item_id, account_id, income_date, amount, create_user_id, remark) "
			+ " values(#{userId}, #{incomeItemId}, #{accountItemId}, #{incomeDate}, #{amount}, #{userId}, #{remark})")
	boolean createByValues(@Param("userId") int userId, @Param("incomeItemId") int incomeItemId, 
			@Param("accountItemId") int accountItemId, @Param("incomeDate") String incomeDate, 
			@Param("amount") BigDecimal amount, @Param("remark") String remark);

	@Update("update income set "
			+ " item_id=#{incomeItemId}, account_id=#{accountId}, income_date=#{incomeDate}, "
			+ " amount=#{amount}, remark=#{remark} "
			+ " where id=#{incomeId} and user_id=#{userId}")
	boolean modifyByValues(@Param("userId") int userId, @Param("incomeId") int incomeId, 
			@Param("incomeItemId") int incomeItemId, @Param("accountId") int accountId, 
			@Param("incomeDate") String incomeDate, @Param("amount") BigDecimal amount, 
			@Param("remark") String remark);

	@Update("update income set "
			+ " is_delete=1 "
			+ " where id=#{incomeId} and user_id=#{userId}")
	boolean deleteByIds(@Param("userId") int userId, @Param("incomeId") int incomeId);

	@Update("update income set "
			+ " is_delete=1 "
			+ " where user_id=#{userId}")
	boolean deleteAllByUserId(@Param("userId") int userId);

	/**
	 * 首頁查詢當日最新5筆收入紀錄
	 * @param userId
	 * @return
	 */
	@Select("select (select name from income_items where id = item_id) as itemName,	amount as amnt "
			+ " from income "
			+ " where user_id = #{userId} and is_delete=0 and income_date = date_sub(curdate(), interval 0 day) "
			+ " order by id desc limit 5")
	@Results({
		@Result(column="itemName", property="itemName"),
		@Result(column="amnt", property="amnt")
	})
	List<IncomeListDto> getTodayListForMain(@Param("userId") int userId);
}
