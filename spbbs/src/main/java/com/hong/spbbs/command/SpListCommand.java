package com.hong.spbbs.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.hong.spbbs.dao.SpDAO;
import com.hong.spbbs.dto.SpDTO;

public class SpListCommand implements SpCommand {

	
	
	@Override
	public void execute(Model model) {
		
		SpDAO dao = new SpDAO();
		ArrayList<SpDTO> dtos = dao.list();
		model.addAttribute("list",dtos);

	}

}
