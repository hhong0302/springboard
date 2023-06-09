package com.hong.spbbs.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.hong.spbbs.dao.SpDAO;

public class SpWriteCommand implements SpCommand {

	@Override
	public void execute(Model model) {

		Map<String,Object> map = model.asMap();
		
		HttpServletRequest req = (HttpServletRequest) map.get("request");
		
		String uname = req.getParameter("uname");
		String upass = req.getParameter("upass");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		SpDAO dao = new SpDAO();
		dao.write(uname, upass, title, content);

	}

}
