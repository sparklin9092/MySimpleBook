package net.spark9092.MySimpleBook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.spark9092.MySimpleBook.dto.spend.SelectItemListDto;
import net.spark9092.MySimpleBook.dto.spend.SelectItemMsgDto;
import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.service.SpendService;
import net.spark9092.MySimpleBook.service.UserLoginService;

@RequestMapping("/spend")
@RestController
public class SpendController {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	@Autowired
	private SpendService spendService;

	@PostMapping("/itemList")
    @ResponseBody
	public SelectItemMsgDto spendItemList(HttpSession session) {

		SelectItemMsgDto selectItemMsgDto = new SelectItemMsgDto();
		
		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());
		
		if(null == userInfoEntity) {
			
			selectItemMsgDto.setStatus(false);
			selectItemMsgDto.setMsg("使用者未登入");
			
		} else {

			logger.debug(userInfoEntity.toString());
			
			List<SelectItemListDto> spendItemListDto = spendService.getSpendListByUserId(userInfoEntity.getId());
			
			selectItemMsgDto.setSpendItemListDto(spendItemListDto);
			selectItemMsgDto.setStatus(true);
		}

		return selectItemMsgDto;
	}

}
