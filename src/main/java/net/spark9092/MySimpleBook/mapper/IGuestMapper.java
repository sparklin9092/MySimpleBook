package net.spark9092.MySimpleBook.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IGuestMapper {

	@Select("select count(id) from guest "
			+ " where ip_address=#{ipAddress} "
			+ " and date_format(create_datetime, '%Y-%m-%d')=date_sub(curdate(), interval 0 day)")
	int getLoginTimes(@Param("ipAddress") String ipAddress);
	
	@Insert("insert into guest(guest_seq, ip_address, guest_device) "
			+ " values(#{guestSeq}, #{ipAddress}, #{guestDevice})")
	boolean createByValues(@Param("guestSeq") int guestSeq, @Param("ipAddress") String ipAddress, 
			@Param("guestDevice") String guestDevice);
}
