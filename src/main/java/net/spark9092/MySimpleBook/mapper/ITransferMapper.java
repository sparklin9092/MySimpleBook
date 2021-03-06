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

import net.spark9092.MySimpleBook.dto.main.TransRecordDto;
import net.spark9092.MySimpleBook.dto.transfer.OneDto;
import net.spark9092.MySimpleBook.dto.transfer.RecListDto;
import net.spark9092.MySimpleBook.dto.transfer.SelectAccountListDto;

@Mapper
public interface ITransferMapper {

	/**
	 * 根據 User ID 查詢可以轉帳的帳戶列表
	 * @param userId
	 * @return
	 */
	@Select("select id, name from account where user_id=#{userId} and is_active=1 and is_delete=0 order by is_default desc")
	@Results({
		@Result(column="id", property="accountId"),
		@Result(column="name", property="accountName")
	})
	List<SelectAccountListDto> selectAccountListByUserId(@Param("userId") int userId);

	/**
	 * 根據 User ID、日期範圍，查詢轉帳紀錄
	 * @param userId
	 * @return
	 */
	@Select("select id, date_format(trans_date, '%Y/%m/%d') as transDate, "
			+ " (select name from account where id = out_acc_id) transOutAccName, "
			+ "	if(is_outside = 1, "
			+ "    if(outside_acc_name is null or outside_acc_name = '', '(外部帳戶)', concat('(外部帳戶) ', outside_acc_name)), "
			+ "    (select name from account where id = in_acc_id) "
			+ " ) transInAccName, amount "
			+ " from transfer "
			+ " where user_id=#{userId} and is_delete=0 and trans_date >= #{startDate} and trans_date <= #{endDate}")
	@Results({
		@Result(column="id", property="transId"),
		@Result(column="transDate", property="transDate"),
		@Result(column="transOutAccName", property="transOutAccName"),
		@Result(column="transInAccName", property="transInAccName"),
		@Result(column="amount", property="transAmnt")
	})
	List<RecListDto> selectRecordsByUserId(@Param("userId") int userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

	/**
	 * 查詢某一筆轉帳資料
	 * @param transferId
	 * @param userId
	 * @return
	 */
	@Select("select trans_date, amount, out_acc_id, in_acc_id, is_outside, outside_acc_name, remark "
			+ " from transfer "
			+ " where is_delete=0 and id=#{transferId} and user_id=#{userId}")
	@Results({
		@Result(column="trans_date", property="transDate"),
		@Result(column="amount", property="amount"),
		@Result(column="out_acc_id", property="outAccId"),
		@Result(column="in_acc_id", property="inAccId"),
		@Result(column="is_outside", property="isOutside"),
		@Result(column="outside_acc_name", property="outsideAccName"),
		@Result(column="remark", property="remark")
	})
	OneDto selectOneByIds(@Param("transferId") int transferId, @Param("userId") int userId);

	/**
	 * 首頁查詢當日最新5筆轉帳紀錄
	 * @param userId
	 * @return
	 */
	@Select("select "
			+ "	(select name from account where id = out_acc_id) transOutAccName, "
			+ "	if(is_outside = 1, "
			+ "    if(outside_acc_name is null or outside_acc_name = '', '(外部帳戶)', concat('(外部帳戶) ', outside_acc_name)), "
			+ "    (select name from account where id = in_acc_id) "
			+ " ) transInAccName, amount as transAmnt "
			+ " from transfer "
			+ " where user_id = #{userId} and is_delete=0 and trans_date = date_sub(curdate(), interval 0 day) "
			+ " order by id desc limit 5")
	@Results({
		@Result(column="transOutAccName", property="accOutName"),
		@Result(column="transInAccName", property="accoInName"),
		@Result(column="transAmnt", property="transAmnt")
	})
	List<TransRecordDto> selectTodayListForMain(@Param("userId") int userId);

	/**
	 * 新增一般轉帳
	 * @param userId
	 * @param transferDate
	 * @param amnt
	 * @param tOutAccId
	 * @param tInAccId
	 * @param remark
	 * @return
	 */
	@Insert("insert into transfer(user_id, trans_date, "
			+ " amount, out_acc_id, in_acc_id, "
			+ " create_user_id, remark) "
			+ " values(#{userId}, #{transferDate}, "
			+ " #{amnt}, #{tOutAccId}, #{tInAccId}, "
			+ " #{userId}, #{remark})")
	boolean insertByValues(@Param("userId") int userId, @Param("transferDate") String transferDate,
			@Param("amnt") BigDecimal amnt, @Param("tOutAccId") int tOutAccId, @Param("tInAccId") int tInAccId,
			@Param("remark") String remark);

	/**
	 * 新增外部轉帳
	 * @param userId
	 * @param transferDate
	 * @param amnt
	 * @param tOutAccId
	 * @param tOutsideAccName
	 * @param remark
	 * @return
	 */
	@Insert("insert into transfer(user_id, trans_date, "
			+ " amount, out_acc_id, "
			+ " is_outside, outside_acc_name, "
			+ " create_user_id, remark) "
			+ " values(#{userId}, #{transferDate}, "
			+ " #{amnt}, #{tOutAccId}, "
			+ " 1, #{tOutsideAccName}, "
			+ " #{userId}, #{remark})")
	boolean insertOutsideByValues(@Param("userId") int userId, @Param("transferDate") String transferDate,
			@Param("amnt") BigDecimal amnt, @Param("tOutAccId") int tOutAccId,
			@Param("tOutsideAccName") String tOutsideAccName, @Param("remark") String remark);

	/**
	 * 更新某一筆一般轉帳資料
	 * @param userId
	 * @param transferId
	 * @param transferDate
	 * @param amnt
	 * @param tOutAccId
	 * @param tInAccId
	 * @param remark
	 * @return
	 */
	@Update("update transfer set "
			+ " trans_date=#{transferDate}, amount=#{amnt}, out_acc_id=#{tOutAccId}, "
			+ " in_acc_id=#{tInAccId}, is_outside=0, "
			+ " outside_acc_name=null, remark=#{remark} "
			+ " where id=#{transferId} and user_id=#{userId}")
	boolean updateByValues(@Param("userId") int userId, @Param("transferId") int transferId, 
			@Param("transferDate") String transferDate, @Param("amnt") BigDecimal amnt, 
			@Param("tOutAccId") int tOutAccId, @Param("tInAccId") int tInAccId, 
			@Param("remark") String remark);

	/**
	 * 更新某一筆轉帳資料
	 * @param userId
	 * @param transferId
	 * @param transferDate
	 * @param amnt
	 * @param tOutAccId
	 * @param tOutsideAccName
	 * @param remark
	 * @return
	 */
	@Update("update transfer set "
			+ " trans_date=#{transferDate}, amount=#{amnt}, out_acc_id=#{tOutAccId}, "
			+ " in_acc_id=null, is_outside=1, "
			+ " outside_acc_name=#{tOutsideAccName}, remark=#{remark} "
			+ " where id=#{transferId} and user_id=#{userId}")
	boolean updateOutsideByValues(@Param("userId") int userId, @Param("transferId") int transferId, 
			@Param("transferDate") String transferDate, @Param("amnt") BigDecimal amnt, 
			@Param("tOutAccId") int tOutAccId, @Param("tOutsideAccName") String tOutsideAccName, 
			@Param("remark") String remark);

	/**
	 * 根據 User ID、Transfer ID，刪除某一筆轉帳(假刪除，把刪除標記設為True)
	 * @param userId
	 * @param transferId
	 * @return
	 */
	@Update("update transfer set "
			+ " is_delete=1 "
			+ " where id=#{transferId} and user_id=#{userId}")
	boolean deleteByIds(@Param("userId") int userId, @Param("transferId") int transferId);

	/**
	 * 根據使用者ID，刪除這一位使用者全部的轉帳資料
	 * @param userId
	 * @return
	 */
	@Update("update transfer set "
			+ " is_delete=1 "
			+ " where user_id=#{userId}")
	boolean deleteAllByUserId(@Param("userId") int userId);
}
