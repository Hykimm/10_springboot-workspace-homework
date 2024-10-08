package com.hy.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.hy.boot.handler.ChatEchoHandler;

import lombok.RequiredArgsConstructor;

@EnableWebSocket
@RequiredArgsConstructor
@Configuration
public class WebSocketConfig implements WebSocketConfigurer{

	private final ChatEchoHandler chatEchoHandler;
	
	@Bean
	HttpSessionHandshakeInterceptor httpSessionHandshakeInterceptor() {
		return new HttpSessionHandshakeInterceptor();
	}
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		/*
		<websocket:handlers>
			<websocket:mapping path="/chat" handler="chatEchoHandler" />
			<websocket:handshake-interceptors>
				<beans:bean class="org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor" />
			</websocket:handshake-interceptors>
			<websocket:sockjs />
		</websocket:handlers>
		*/
		registry.addHandler(chatEchoHandler, "/chat")
				.addInterceptors(httpSessionHandshakeInterceptor())
				.withSockJS();
	}

}
