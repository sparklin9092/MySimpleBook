package net.spark9092.MySimpleBook.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ISystemSeqsMapper {

	/**
	 * 查詢某一個序號目前的數值
	 * @param seqName
	 * @return
	 */
	@Select("select select_seq(#{seqName})")
	int getSeq(@Param("seqName") String seqName);
}
