package net.spark9092.MySimpleBook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import net.spark9092.MySimpleBook.dto.whiteList.WhiteListValueDto;

@Mapper
public interface ISystemWhiteListMapper {

	@Select("select id, white_value from system_whitelist "
			+ " where white_type=#{whiteType} "
			+ " and is_active=1 and is_delete=0")
	@Results({
		@Result(column="id", property="id"),
		@Result(column="white_value", property="value")
	})
	List<WhiteListValueDto> selectListByType(@Param("whiteType") String whiteType);
}
