package com.hy.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/chat")
@Controller
public class ChatController {

	@GetMapping("/room.page")
	public void chatRommPage() {
		//return "chat/room";
	}
	
}