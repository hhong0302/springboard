package com.hong.spbbs.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.hong.spbbs.dao.SpDAO;

public class SpReplyOkCommand implements SpCommand {

	@Override
	public void execute(Model model) {

		Map<String,Object> map = model.asMap();
		HttpServletRequest req = (HttpServletRequest) map.get("request");
		
		String uname = req.getParameter("uname");
		String upass = req.getParameter("upass");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		int s_group = Integer.parseInt(req.getParameter("s_group"));
		int s_step = Integer.parseInt(req.getParameter("s_step"))+1;
		int s_indent = Integer.parseInt(req.getParameter("s_indent"))+1;
		
		SpDAO dao = new SpDAO();
		dao.replyok(s_group,s_step,s_indent,uname,upass,title,content);

	}

}
