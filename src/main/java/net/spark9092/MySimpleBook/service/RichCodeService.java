package net.spark9092.MySimpleBook.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spark9092.MySimpleBook.dto.richCode.ListDto;
import net.spark9092.MySimpleBook.dto.richCode.ListMsgDto;
import net.spark9092.MySimpleBook.mapper.IRichCodeMapper;

@Service
public class RichCodeService {

	private static final Logger logger = LoggerFactory.getLogger(RichCodeService.class);

	@Autowired
	private IRichCodeMapper iRichCodeMapper;

	public ListMsgDto getRichCodeList() {

		logger.info("隨機產生10組財富密碼");

		ListMsgDto listMsgDto = new ListMsgDto();

		try {
			List<ListDto> listDto = iRichCodeMapper.selectList(new Date());

			// logger.debug("listDto.size: " + listDto.size());

			// for(ListDto list : listDto) {
			// logger.debug("RichCodeId: " + String.valueOf(list.getRichCodeId()));
			// logger.debug("RichCode: " + list.getRichCode());
			// logger.debug("RichCode showtime(s): " +
			// String.valueOf(list.getRichCodeShowTime()));
			// }

			// 避免因為資料數量不夠，跑入無窮迴圈，
			// 所以如果資料庫的資料筆數小於10筆，
			// 就直接全部取用
			Set<Integer> richCodeSet = new HashSet<Integer>();
			if (listDto.size() < 10) {
				for (ListDto list : listDto) {
					richCodeSet.add(list.getRichCodeId());
				}
			} else {
				// 超過10筆再隨機挑選
				while (richCodeSet.size() < 10) {
					// logger.debug("richCodeSet size now: " + richCodeSet.size());
					int randomLocation = (int) (Math.random() * listDto.size());
					// logger.debug("randomLocation: " + randomLocation);
					int richCodeId = listDto.get(randomLocation).getRichCodeId();
					richCodeSet.add(richCodeId);
				}
			}

			// String allRichCodeRandomId = "";
			// for(int richCodeId : richCodeSet) {
			// allRichCodeRandomId += richCodeId + ", ";
			// }
			// logger.debug("allRichCodeRandomId: " + allRichCodeRandomId);

			// 取到隨機ID後，再根據ID找出對應的資料
			List<ListDto> randomDtos = new ArrayList<ListDto>();
			for (int richCodeId : richCodeSet) {
				for (ListDto dto : listDto) {
					ListDto randomDto = new ListDto();
					if (dto.getRichCodeId() == richCodeId) {
						BeanUtils.copyProperties(dto, randomDto);
						randomDtos.add(randomDto);
						break;
					}
				}
			}

			// for (ListDto dto : randomDtos) {
			// logger.debug("RandomRichCodeId: " + String.valueOf(dto.getRichCodeId()));
			// logger.debug("RandomRichCode: " + dto.getRichCode());
			// logger.debug("RandomRichCode showtime(s): " +
			// String.valueOf(dto.getRichCodeShowTime()));
			// }

			listMsgDto.setListDtos(randomDtos);
			listMsgDto.setStatus(true);

		} catch (Exception e) {

			logger.debug(e.getMessage());

			listMsgDto.setStatus(false);
			listMsgDto.setMsg("取得10組財富密碼發生錯誤");
		}

		return listMsgDto;
	}
}
