package net.spark9092.MySimpleBook.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import net.spark9092.MySimpleBook.dto.main.TransferListDto;
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
	boolean createByValues(@Param("userId") int userId, @Param("transferDate") String transferDate,
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
	boolean createOutsideByValues(@Param("userId") int userId, @Param("transferDate") String transferDate,
			@Param("amnt") BigDecimal amnt, @Param("tOutAccId") int tOutAccId,
			@Param("tOutsideAccName") String tOutsideAccName, @Param("remark") String remark);

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
			+ " where user_id = #{userId} and trans_date = date_sub(curdate(), interval 0 day) "
			+ " order by id desc limit 5")
	@Results({
		@Result(column="transOutAccName", property="accOutName"),
		@Result(column="transInAccName", property="accoInName"),
		@Result(column="transAmnt", property="transAmnt")
	})
	List<TransferListDto> getTodayListForMain(@Param("userId") int userId);
}
