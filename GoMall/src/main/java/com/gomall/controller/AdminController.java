package com.gomall.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gomall.domain.AdminVO;
import com.gomall.dto.AdminDTO;
import com.gomall.service.AdminService;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

	@Autowired
	private AdminService service;
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	// 관리자 메인
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String adminMain() {
		return "admin/main";
	}
	
	// 관리자 로그인(POST)
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String loginPOST(AdminDTO dto, RedirectAttributes rttr, HttpSession session) throws Exception {
		logger.info("=====login(POST) execute()");
		logger.info("=====AdminDTO : " + dto.toString());
		
		AdminVO vo = null; 
		vo = service.login(dto);
		String msg = "";
		
		if(vo!= null) {
			// remember me 체크 부분
			// TODO: 인터셉터 구현
			/*
			if (dto.isUseCookie()) {
				int amount = 60 * 60 * 24 * 7; // 7일 간 쿠키 저장
				Date sessionLimit = new Date(System.currentTimeMillis() + (1000 * amount)); 
				service.keepLogin(vo.getAdmin_id(), session.getId(), sessionLimit);
			}
			*/
			// 로그인 성공 시
			session.setAttribute("admin", vo);
			
			// 관리자 로그인 시간 갱신 업데이트.
			
			
			msg = "LOGIN_SUCCESS";
		} else {
			// 로그인 실패 시
			msg = "LOGIN_FAIL";
		}
		rttr.addFlashAttribute("msg", msg);
		return "redirect:/admin/main";
	}
	
	// 로그아웃(GET)
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session, RedirectAttributes rttr) {
		
		logger.info("====== logout(POST) execute");
		
		session.invalidate();
		rttr.addFlashAttribute("msg", "LOGOUT_SUCCESS");
		
		return "redirect:/admin/main";
	}
	
}
