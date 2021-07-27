package net.spark9092.MySimpleBook.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import net.spark9092.MySimpleBook.dto.richCode.ListDto;

@Mapper
public interface IRichCodeMapper {

	/**
	 * 根據登入日期，查詢符合資格、可以顯示的財富密碼清單
	 * @param loginDate
	 * @return
	 */
	@Select("select id, name, show_time "
			+ " from rich_code "
			+ " where ((show_start_date is null) or (show_start_date is not null and show_start_date <= #{loginDate})) "
			+ " and ((show_end_date is null) or (show_end_date is not null and show_end_date >= #{loginDate})) "
			+ " and is_active = 1 "
			+ " and is_delete = 0 ")
	@Results({
		@Result(column="id", property="richCodeId"),
		@Result(column="name", property="richCode"),
		@Result(column="show_time", property="richCodeShowTime")
	})
	public List<ListDto> selectList(@Param("loginDate") Date loginDate);
}
