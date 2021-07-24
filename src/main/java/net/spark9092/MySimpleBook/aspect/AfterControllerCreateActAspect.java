package net.spark9092.MySimpleBook.aspect;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;
import net.spark9092.MySimpleBook.service.UserInfoService;

@Aspect
@Component
public class AfterControllerCreateActAspect {

	private static final Logger logger = LoggerFactory.getLogger(AfterControllerCreateActAspect.class);

	@Autowired
	private HttpSession session;

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * AOP 切入點
	 */
	@Pointcut("execution(* net.spark9092.MySimpleBook.controller.*Controller.createAct(..))")
	public void Pointcut() {

	}

	/**
	 * Controller 的 createAct 方法有回傳值之後，才進切入點處理，
	 * 有 createAct 方法的 Controller：
	 * 1. AccountController
	 * 2. AccountTypesController
	 * 3. IncomeController
	 * 4. IncomeItemsController
	 * 5. SpendController
	 * 6. SpendItemsController
	 * 7. TransferController
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(pointcut = "Pointcut()", returning = "result")
	public void before(JoinPoint joinPoint, Object result) {

		UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(SessinNameEnum.USER_INFO.getName());

		logger.debug("目前訪客資料：{}", userInfoEntity.toString());

		if(null != userInfoEntity && userInfoEntity.isGuest()) {

			int dataCount = 0;

			dataCount = userInfoService.getGuestDataCount(userInfoEntity.getId());

			logger.info("訪客已經在系統新增 {} 筆資料，提示綁定帳號", dataCount);

			session.setAttribute(SessinNameEnum.GUEST_DATA_COUNT.getName(), dataCount);

		}
	}
}
