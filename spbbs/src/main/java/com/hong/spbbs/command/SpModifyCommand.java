package com.hong.spbbs.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.hong.spbbs.dao.SpDAO;
import com.hong.spbbs.dto.SpDTO;

public class SpModifyCommand implements SpCommand {

	@Override
	public void execute(Model model) {

		Map<String,Object> map = model.asMap();
		HttpServletRequest req = (HttpServletRequest) map.get("request");
		String num = req.getParameter("num");
		
		SpDAO dao = new SpDAO();
		SpDTO dto = new SpDTO();
		dto = dao.modify(num);
		
		model.addAttribute("modify",dto);

	}

}
