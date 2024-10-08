package com.hy.boot.handler;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.hy.boot.dto.MemberDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ChatEchoHandler extends TextWebSocketHandler{
	
	// sessionList : 현재 웹소켓과 연결된 클라이언트들을 저장해놓는 리스트
	private List<WebSocketSession> sessionList = new ArrayList<>();
	
	//private final ChatService charService;
	
	/**
	 * 1) afterConnectionEstablished : 클라이언트와 연결되었을 때 처리할 내용을 정의
	 * 
	 * @param session - 현재 웹소켓과 연결된 클라이언트 정보를 가지고 있는 객체
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("=========== WebSocket 연결됨 ===========");
		log.debug("WebSocketSession객체: {}", session);
		log.debug("session Id: {}", session.getId());;
		log.debug("session Attributes: {}", session.getAttributes());
		log.debug("현재 채팅방에 참가한 로그인한 회원: {}", session.getAttributes().get("loginUser"));
		sessionList.add(session);
		
		// 모든 클라리언트드에게 입장메세지 전달
		for(WebSocketSession client : sessionList) {
			// 전달하고자 하는 메세지의 형식 : 메세지유형(chat/entry/exit)|메세지내용
			String msg = "entry|" + ((MemberDto)session.getAttributes().get("loginUser")).getUserId() + "님이 입장하였습니다.";
			
			// 메세지를 보내는 구문
			client.sendMessage(new TextMessage(msg)); // * 채팅방jsp의 onMessage function실행됨
		}
		
	}
	
	/**
	 * 2) handleMessage : 데이터(텍스트, 파일 등)가 웹소켓으로 전송되었을 때 처리할 내용을 정의
	 * 
	 * @param message - 현재 웹소켓으로 전달된 데이터에 대한 정보를 가지고 있는 객체
	 */
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		log.debug("=========== 메세지 들어옴 ===========");
		log.debug("현재 웹소켓으로 메세지를 보낸 회원의 정보: {}", session.getAttributes().get("loginUser"));
		log.debug("현재 웹소켓으로 전달된 메세지 내용: {}", message.getPayload());
		
		// 현재 해당 웹소켓에 연결되어있는 모든 클라리언트들에게 현재 들어온 메세지를 재발송함 (작성자본인포함)
		for(WebSocketSession client : sessionList) {
			// 전달하고자 하는 메세지의 형식 : 메세지유형(chat/entry/exit)|메세지내용|발신자아이디|...
			String msg = "chat|" + message.getPayload() + "|" 
							+ ((MemberDto)session.getAttributes().get("loginUser")).getUserId();
			
			// 메세지를 보내는 구문
			client.sendMessage(new TextMessage(msg)); // * 채팅방jsp의 onMessage function실행됨
		}
		
		// db에 채팅메세지 내역을 남기고자할 경우
		// EchoHandler에서 Service 연결해서
		// insert 요청하기
		
	}
	
	/**
	 * 3) afterConnectionClosed : 클라이언트와 연결이 끊겼을 때 처리할 내용을 정의
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.debug("=========== WebSocket 연결끊김 ===========");
		log.debug("WebSocketSession객체: {}", session);
		log.debug("session Id: {}", session.getId());
		log.debug("현재 채팅방에서 나간 회원: {}", session.getAttributes().get("loginUser"));
		sessionList.remove(session);
		
		// 모든 클라리언트드에게 퇴장메세지 전달
		for(WebSocketSession client : sessionList) {
			// 전달하고자 하는 메세지의 형식 : 메세지유형(chat/entry/exit)|메세지내용
			String msg = "exit|" + ((MemberDto)session.getAttributes().get("loginUser")).getUserId() + "님이 퇴장하였습니다.";
			
			// 메세지를 보내는 구문
			client.sendMessage(new TextMessage(msg)); // * 채팅방jsp의 onMessage function실행됨
		}
		
	}
	
}
