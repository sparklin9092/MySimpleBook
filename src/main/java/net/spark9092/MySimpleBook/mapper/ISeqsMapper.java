package net.spark9092.MySimpleBook.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ISeqsMapper {

	@Select("select select_seq(#{seqName})")
	int getSeq(@Param("seqName") String seqName);
}
