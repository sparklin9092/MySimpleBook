package net.spark9092.MySimpleBook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.richCode.ListDto;
import net.spark9092.MySimpleBook.dto.richCode.ListMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;

@RequestMapping("/main")
@RestController
public class MainController {

	@SuppressWarnings("unchecked")
	@PostMapping("/richCodeList")
    @ResponseBody
    public ListMsgDto getRishCodeList(HttpSession session) {

		ListMsgDto listMsgDto = new ListMsgDto();
		List<ListDto> dtos = new ArrayList<>();

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		if(null != userInfoEntity) {

			dtos = (List<ListDto>) session.getAttribute(SessinNameEnum.RICH_CODE.getName());

			listMsgDto.setListDtos(dtos);
			listMsgDto.setStatus(true);
			
		} else {
			
			listMsgDto.setStatus(false);
			listMsgDto.setMsg("無法取得Rich Code");
			
		}

		return listMsgDto;
	}
}
