package com.hong.spbbs.command;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.hong.spbbs.dao.SpDAO;
import com.hong.spbbs.dto.SpDTO;

public class SpListCommand implements SpCommand {

	
	
	@Override
	public void execute(Model model) {
		
		SpDAO dao = new SpDAO();
		Map<String,Object> map = model.asMap();
		HttpServletRequest req = (HttpServletRequest) map.get("request");
		String page = req.getParameter("page");
		if(page==null) page="1";
		ArrayList<SpDTO> dtos = dao.list(page);
		model.addAttribute("list",dtos);

	}

}
