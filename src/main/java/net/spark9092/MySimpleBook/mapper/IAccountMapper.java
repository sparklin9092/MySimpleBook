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

import net.spark9092.MySimpleBook.dto.account.TypeListDto;
import net.spark9092.MySimpleBook.dto.main.AccountListDto;
import net.spark9092.MySimpleBook.dto.account.DetailListDto;
import net.spark9092.MySimpleBook.dto.account.ListDto;
import net.spark9092.MySimpleBook.dto.account.OneDto;

@Mapper
public interface IAccountMapper {

	/**
	 * 根據 User ID，查詢帳戶的清單
	 * @param userId
	 * @return
	 */
	@Select("select id, name, amount "
			+ " from account "
			+ " where user_id=#{userId} and is_delete=0")
	@Results({
		@Result(column="id", property="accountId"),
		@Result(column="name", property="accountName"),
		@Result(column="amount", property="accountAmnt")
	})
	List<ListDto> selectListByUserId(@Param("userId") int userId);

	/**
	 * 根據 User ID，查詢帳戶類型的清單，user_id=0 表示是系統預設的資料
	 * @param userId
	 * @return
	 */
	@Select("select id, if(user_id=0, concat(name, '(系統預設)'), name) name "
			+ " from account_types "
			+ " where (user_id=#{userId} or user_id=0) and is_active=1 and is_delete=0 "
			+ " order by is_default desc")
	@Results({
		@Result(column="id", property="typeId"),
		@Result(column="name", property="typeName")
	})
	List<TypeListDto> selectTypeListByUserId(@Param("userId") int userId);

	/**
	 * 查詢某一筆帳戶紀錄
	 * @param accountId
	 * @param userId
	 * @return
	 */
	@Select("select b.name as typeName, a.name, a.amount, a.is_default, a.is_active, "
			+ " if(a.limit_date regexp '[0-9]{4}-[0-9]{2}-[0-9]{2}', true, false) as enableLimitDate, "
			+ " date_format(a.limit_date, '%Y') as limitYear, date_format(a.limit_date, '%m') as limitMonth, "
			+ " a.create_datetime "
			+ " from account a "
			+ " left join account_types b on b.id = a.type_id "
			+ " where a.is_delete=0 and a.id=#{accountId} and a.user_id=#{userId}")
	@Results({
		@Result(column="typeName", property="accountTypeName"),
		@Result(column="name", property="accountName"),
		@Result(column="amount", property="accountAmnt"),
		@Result(column="is_default", property="accountDefault"),
		@Result(column="is_active", property="accountActive"),
		@Result(column="enableLimitDate", property="enableLimitDate"),
		@Result(column="limitYear", property="limitYear"),
		@Result(column="limitMonth", property="limitMonth"),
		@Result(column="create_datetime", property="createDateTime")
	})
	OneDto selectOneByIds(@Param("accountId") int accountId, @Param("userId") int userId);
	
	/**
	 * 首頁查詢餘額最高的前5筆帳戶與餘額
	 * @param userId
	 * @return
	 */
	@Select("select name, amount from account where user_id = #{userId} and is_delete=0 order by amount desc limit 5")
	@Results({
		@Result(column="name", property="accName"),
		@Result(column="amount", property="amnt")
	})
	List<AccountListDto> selectTodayListForMain(@Param("userId") int userId);

	/**
	 * 根據 User ID，新增一個帳戶
	 * @param userId
	 * @param accountType
	 * @param accountName
	 * @param accountDefault
	 * @param limitDate
	 * @return
	 */
	@Insert("insert into account(user_id,   type_id,        name,           init_amount, amount,      limit_date,   is_default,        create_user_id) "
			+ "           values(#{userId}, #{accountType}, #{accountName}, #{initAmnt}, #{initAmnt}, #{limitDate}, #{accountDefault}, #{userId})")
	boolean insertByValues(@Param("userId") int userId, @Param("accountType") int accountType,
			@Param("accountName") String accountName, @Param("initAmnt") BigDecimal initAmnt,
			@Param("accountDefault") boolean accountDefault, @Param("limitDate") String limitDate);

	/**
	 * 根據 User ID、Account ID，更新某一筆帳戶的帳戶名稱、使用期限、預設帳戶、帳戶狀態，
	 * 其他的欄位不給使用者修改，不然帳務會出問題
	 * @param userId
	 * @param accountId
	 * @param limitDate
	 * @param accountDefault
	 * @param accountActive
	 * @return
	 */
	@Update("update account set "
			+ " name=#{accountName}, limit_date=#{limitDate}, "
			+ " is_default=#{accountDefault}, is_active=#{accountActive} "
			+ " where id=#{accountId} and user_id=#{userId}")
	boolean updateByValues(@Param("userId") int userId, @Param("accountId") int accountId,
			@Param("accountName") String accountName, @Param("limitDate") String limitDate, 
			@Param("accountDefault") boolean accountDefault, @Param("accountActive") boolean accountActive);

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

	/**
	 * 根據使用者ID刪除某一筆帳戶，假刪除，把刪除標記改為1
	 * @param userId
	 * @return
	 */
	@Update("update account set "
			+ " is_delete=1 "
			+ " where user_id=#{userId}")
	boolean deleteAllByUserId(@Param("userId") int userId);
	
	/**
	 * 減少帳戶餘額，會觸發 DB Trigger-change_amnt
	 * @param userId
	 * @param accountId
	 * @param amount
	 * @return
	 */
	@Update("update account set "
			+ " amount = amount - #{amount} "
			+ " where id=#{accountId} and user_id=#{userId}")
	boolean decreaseAmnt(@Param("userId") int userId, @Param("accountId") int accountId, @Param("amount") BigDecimal amount);

	/**
	 * 增加帳戶餘額，會觸發 DB Trigger-change_amnt
	 * @param userId
	 * @param accountId
	 * @param amount
	 * @return
	 */
	@Update("update account set "
			+ " amount = amount + #{amount} "
			+ " where id=#{accountId} and user_id=#{userId}")
	boolean increaseAmnt(@Param("userId") int userId, @Param("accountId") int accountId, @Param("amount") BigDecimal amount);

	/**
	 * 查詢帳戶的收支轉明細
	 * @param userId
	 * @param accountId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Select("select s.id, date_format(s.spend_date, '%m月%d日') as dDate, si.name as itemName, s.amount as amnt, s.remark as remark, 's' as dType "
			+ " from spend s left join spend_items si on si.id = s.item_id "
			+ " where s.is_delete='0' and s.user_id=#{userId} and s.account_id=#{accountId} and s.spend_date >= #{startDate} and s.spend_date <= #{endDate} "
			+ " UNION ALL "
			+ " select i.id, date_format(i.income_date, '%m月%d日') as dDate, ii.name as itemName, i.amount as amnt, i.remark as remark, 'i' as dType "
			+ " from income i left join income_items ii on ii.id = i.item_id "
			+ " where i.is_delete='0' and i.user_id=#{userId} and i.account_id=#{accountId} and i.income_date >= #{startDate} and i.income_date <= #{endDate} "
			+ " UNION ALL "
			+ " select t.id, date_format(t.trans_date, '%m月%d日') as dDate, '轉出' as itemName, t.amount as amnt, t.remark as remark, 't' as dType "
			+ " from transfer t "
			+ " where t.is_delete='0' and t.user_id=#{userId} and t.out_acc_id=#{accountId} and t.trans_date >= #{startDate} and t.trans_date <= #{endDate} "
			+ " UNION ALL "
			+ " select t.id, date_format(t.trans_date, '%m月%d日') as dDate, '轉入' as itemName, t.amount as amnt, t.remark as remark, 't' as dType "
			+ " from transfer t "
			+ " where t.is_delete='0' and t.user_id=#{userId} and t.in_acc_id=#{accountId} and t.trans_date >= #{startDate} and t.trans_date <= #{endDate}")
	@Results({
		@Result(column="id", property="itemId"),
		@Result(column="dDate", property="date"),
		@Result(column="itemName", property="itemName"),
		@Result(column="amnt", property="amnt"),
		@Result(column="remark", property="remark"),
		@Result(column="dType", property="type")
	})
	List<DetailListDto> selectDetailListByIds(@Param("userId") int userId, @Param("accountId") int accountId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
