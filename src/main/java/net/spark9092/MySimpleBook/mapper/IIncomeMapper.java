package net.spark9092.MySimpleBook.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import net.spark9092.MySimpleBook.dto.income.SelectAccountListDto;
import net.spark9092.MySimpleBook.dto.income.SelectItemListDto;
import net.spark9092.MySimpleBook.dto.main.IncomeListDto;

@Mapper
public interface IIncomeMapper {

	@Select("select id, name from items_income where user_id=#{userId} and is_active=1 and is_delete=0 order by is_default desc")
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

	@Insert("insert into income(user_id, item_id, account_id, income_date, amount, create_user_id, remark) "
			+ " values(#{userId}, #{incomeItemId}, #{accountItemId}, #{incomeDate}, #{amount}, #{userId}, #{remark})")
	boolean createByValues(@Param("userId") int userId, @Param("incomeItemId") int incomeItemId, 
			@Param("accountItemId") int accountItemId, @Param("incomeDate") String incomeDate, 
			@Param("amount") BigDecimal amount, @Param("remark") String remark);

	/**
	 * 首頁查詢當日最新5筆收入紀錄
	 * @param userId
	 * @return
	 */
	@Select("select (select name from items_income where id = item_id) as itemName,	amount as amnt "
			+ " from income "
			+ " where user_id = #{userId} and income_date = date_sub(curdate(), interval 0 day) "
			+ " order by id desc limit 5")
	@Results({
		@Result(column="itemName", property="itemName"),
		@Result(column="amnt", property="amnt")
	})
	List<IncomeListDto> getTodayListForMain(@Param("userId") int userId);
}
