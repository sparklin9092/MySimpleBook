package net.spark9092.MySimpleBook.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import net.spark9092.MySimpleBook.dto.transfer.SelectAccountListDto;

@Mapper
public interface ITransferMapper {


	@Select("select id, name from account where user_id=#{userId} and is_active=1 and is_delete=0 order by is_default desc")
	@Results({
		@Result(column="id", property="accountId"),
		@Result(column="name", property="accountName")
	})
	List<SelectAccountListDto> selectAccountListByUserId(@Param("userId") int userId);

	@Insert("insert into transfer(user_id, transfer_date, "
			+ " trans_in_acc_id, trans_in_amnt, "
			+ " trans_out_acc_id, trans_out_amnt, "
			+ " create_user_id, remark) "
			+ " values(#{userId}, #{transferDate}, "
			+ " #{tInAccId}, #{tInAmnt}, "
			+ " #{tOutAccId}, #{tOutAmnt}, "
			+ " #{userId}, #{remark})")
	boolean createByValues(@Param("userId") int userId, @Param("transferDate") String transferDate,
			@Param("tInAccId") int tInAccId, @Param("tInAmnt") BigDecimal tInAmnt,
			@Param("tOutAccId") int tOutAccId, @Param("tOutAmnt") BigDecimal tOutAmnt,
			@Param("remark") String remark);
}
