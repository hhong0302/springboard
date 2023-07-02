package com.hong.spbbs.util;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.hong.spbbs.dao.SpDAO;
import com.hong.spbbs.dto.PageDTO;

public class Pagination {

	private int totalCount;
	   private int startPage;
	   private int endPage;
	   private boolean prev;
	   private boolean next;
	   private int displayPageNum;
	   
	   private PageDTO pdto;
	   
	   
	   public PageDTO getPdto() {
	   return pdto;
	   }

	   public void setPdto(PageDTO pdto) {
	     this.pdto = pdto;
	   }
	   
	   public int getTotalCount() {
	   return totalCount;
	   }
	   
	   public void setTotalCount() {
	      SpDAO dao = new SpDAO();
	      this.totalCount = dao.totalRecord();
	      calcData();
	   }
	      
	   public int getStartPage() {
	      return startPage;
	   }
	      
	   public int getEndPage() {
	      return endPage;
	   }
	   
	   public boolean isPrev() {
	      return prev;
	   }
	   
	   public boolean isNext() {
	      return next;
	   }
	   
	   public int getDisplayPageNum() {
	      return displayPageNum;
	   }
	   
	   
	   public void setDisplayPageNum(int displayPageNum) {
	      if(displayPageNum == 0)  displayPageNum = 10;
	      this.displayPageNum = displayPageNum;
	   }
	   
	   private void calcData() {

	    endPage = (int)( Math.ceil(pdto.getPage() / (double) displayPageNum) * displayPageNum);
	    startPage = (endPage - displayPageNum) + 1;
	    
	    int tempPage = (int) (Math.ceil(getTotalCount() / (double) pdto.getPerPageNum()));
	    if(endPage > tempPage) {
	       endPage = tempPage;            
	    }
	    prev = startPage == 1 ? false : true;
	    next = endPage * pdto.getPerPageNum() >= getTotalCount() ? false : true;
	   
	   }
	   
	   public String makeQuery(int page) {
	      
	      UriComponents uriComponents = UriComponentsBuilder
	                                        .newInstance()      
	                                        .queryParam("page", page)
	                                        .build();
	      return uriComponents.toString();
	   }


}
