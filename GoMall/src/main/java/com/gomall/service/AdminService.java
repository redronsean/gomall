package com.gomall.service;

import com.gomall.domain.AdminVO;
import com.gomall.dto.AdminDTO;

public interface AdminService {

	// 로그인
	public AdminVO login(AdminDTO dto) throws Exception;
	
}
