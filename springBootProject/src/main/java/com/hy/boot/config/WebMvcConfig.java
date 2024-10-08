package com.hy.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hy.boot.interceptor.LoginCheckInterceptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	private final LoginCheckInterceptor loginCheckInterceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 레거시에서 부트로 마이그레이션 하면서 기존 resources 폴더 경로를 전부 static 폴더로 바꾸는 구문
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("classpath:/static/");
		// 레거시에서 부트로 마이그레이션 하면서 기존 upload 폴더 경로를 "file:///upload" 폴더로 바꾸는 구문
		registry.addResourceHandler("/upload/**")
				.addResourceLocations("file:///upload/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/*
		<interceptors>
			<interceptor>
				<mapping path="/member/myinfo.page" />
				<mapping path="/board/registForm.page" />
				<beans:bean class="com.br.spring.Interceptor.LoginCheckInterceptor" id="loginCheckInterceptor "/>
			</interceptor>
		</interceptors>
		*/
		registry.addInterceptor(loginCheckInterceptor)
				.addPathPatterns("/member/myinfo.page")
				.addPathPatterns("/board/registForm.page");
	}
	
	
	
}
