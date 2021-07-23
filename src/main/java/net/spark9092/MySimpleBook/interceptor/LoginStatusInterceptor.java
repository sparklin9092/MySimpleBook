package net.spark9092.MySimpleBook.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import net.spark9092.MySimpleBook.entity.UserInfoEntity;
import net.spark9092.MySimpleBook.enums.SessinNameEnum;

@Component
public class LoginStatusInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println("登入攔截成功！");

		UserInfoEntity userInfoEntity = (UserInfoEntity) request.getSession().getAttribute(SessinNameEnum.USER_INFO.getName());

		if (null == userInfoEntity) {

			response.sendRedirect("/");
			
			return false;

		} else {

			return true;
		}
	}
}
