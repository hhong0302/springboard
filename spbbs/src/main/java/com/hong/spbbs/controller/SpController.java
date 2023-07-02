package com.hong.spbbs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hong.spbbs.command.SpCommand;
import com.hong.spbbs.command.SpDeleteCommand;
import com.hong.spbbs.command.SpDetailCommand;
import com.hong.spbbs.command.SpListCommand;
import com.hong.spbbs.command.SpModifyCommand;
import com.hong.spbbs.command.SpModifyOkCommand;
import com.hong.spbbs.command.SpReplyCommand;
import com.hong.spbbs.command.SpReplyOkCommand;
import com.hong.spbbs.command.SpWriteCommand;
import com.hong.spbbs.dto.PageDTO;
import com.hong.spbbs.util.Pagination;
import com.hong.spbbs.util.Static;

@Controller
public class SpController {
	
	//모든 command가 갖고 있는 인터페이스 타입을 선언
	SpCommand command;
		
	//jdbc Spring template
	public JdbcTemplate template;
	
	@Autowired
	public void setTemplate(JdbcTemplate template)
	{
		this.template = template;
		Static.template=this.template;
	}
	
	@RequestMapping("/list")
	public String list(HttpServletRequest req,Model model)
	{
		model.addAttribute("request",req);
		command = new SpListCommand();
		command.execute(model);
		PageDTO pdto = new PageDTO();
	    Pagination pages = new Pagination();
	    pages.setDisplayPageNum(10);
	    pages.setPdto(pdto);
	    pages.setTotalCount();
	    model.addAttribute("pages", pages);
		return "list";
	}
	
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request, Model model)
	{
		System.out.println("detail()");
		model.addAttribute("request",request);
		command = new SpDetailCommand();
		command.execute(model);
		return "detail";
	}
	
	@RequestMapping("/write")
	public String write(Model model)
	{
		System.out.println("write()");
		return "write";
	}
	
	@RequestMapping(value="/writeok",method=RequestMethod.POST)
	public String writeok(HttpServletRequest req, Model model)
	{
		System.out.println("writeok");
		model.addAttribute("request",req);
		command = new SpWriteCommand();
		command.execute(model);
		return "redirect:list";
	}
	
	@RequestMapping("/reply")
	public String reply(HttpServletRequest req, Model model)
	{
		System.out.println("reply()");
		model.addAttribute("request",req);
		command = new SpReplyCommand();
		command.execute(model);
		return "reply";
	}
	
	@RequestMapping(value="/replyok",method=RequestMethod.POST)
	public String replyok(HttpServletRequest req, Model model)
	{
		System.out.println("replyok()");
		model.addAttribute("request",req);
		command = new SpReplyOkCommand();
		command.execute(model);
		return "redirect:list";
	}
	
	@RequestMapping("/modify")
	public String modify(HttpServletRequest req, Model model)
	{
		System.out.println("modify()");
		model.addAttribute("request",req);
		command = new SpModifyCommand();
		command.execute(model);
		return "modify";
	}
	
	@RequestMapping(value="/modifyok",method=RequestMethod.POST)
	public String modifyok(HttpServletRequest req, Model model)
	{
		System.out.println("modifyok()");
		model.addAttribute("request",req);
		command = new SpModifyOkCommand();
		command.execute(model);
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest req, Model model)
	{
		System.out.println("delete()");
		model.addAttribute("request",req);
		command = new SpDeleteCommand();
		command.execute(model);
		return "redirect:list";
	}

}









