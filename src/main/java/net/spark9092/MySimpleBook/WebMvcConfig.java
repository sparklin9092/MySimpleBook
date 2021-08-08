package net.spark9092.MySimpleBook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import net.spark9092.MySimpleBook.interceptor.LoginStatusInterceptor;

@SpringBootConfiguration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	LoginStatusInterceptor loginStatusInterceptor;

	/**
	 * 原本ViewResolvers是寫在resources/application.properties裡面，
	 * 但是因為要使用MVC攔截器的關係，
	 * 所以就設定在這裡。
	 */
	@Override
	public void configureViewResolvers(final ViewResolverRegistry registry) {
		registry.jsp("/", ".jsp");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**", "/css/**")
				.addResourceLocations("/js/", "/css/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginStatusInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns(
						"/**/*.css", "/**/*.css.map",
						"/**/*.js", "/**/*.js.map",
						"/robots.txt", "/sitemap.txt",
						"", "/", "/index",
						"/login", "/login/guest", "/login/check", "/logout",
						"/verify/mail/**", "/verify/phone/**");
	}
}
