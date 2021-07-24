package net.spark9092.MySimpleBook.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;

@Component
public class LoginStatusInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(LoginStatusInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		logger.info("登入攔截，Request URL：{}", request.getServletPath());

		UserInfoEntity userInfoEntity = (UserInfoEntity) request.getSession().getAttribute(SessinNameEnum.USER_INFO.getName());

		if (null == userInfoEntity) {
			
			logger.info("使用者未登入，返回登入頁");

			response.sendRedirect("/");

			return false;

		} else {

			return true;
		}
	}
}
