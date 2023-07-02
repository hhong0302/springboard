package com.hong.spbbs.dto;

import com.hong.spbbs.dao.SpDAO;

public class PageDTO {
	
	private int page;
	   private int perPageNum;
	   private int rowStart;
	   private int rowEnd;
	   private int pageStart;
	  
	   
	   public PageDTO() {
	      this.page = 1;
	      this.perPageNum = 10;
	   }
	   
	   public int getPage() {
	      return page;
	   }
	   
	   public void setPage(int page) {
	      if(page <= 0) {
	        this.page = 1;
	        return;
	      }
	      this.page = page;
	   }
	   
	   public void setPageStart() {
	      this.pageStart = (this.page -1) * perPageNum;
	   }
	   
	   public int getPageStart() {
	      return pageStart;
	   }
	   

	   public int getPerPageNum() {
	      return perPageNum;
	   }
	   
	   public void setPerPageNum(int perPageNum) {
	      if(perPageNum <=0 || perPageNum > 100) {
	         this.perPageNum = 10;
	         return;
	      }
	      this.perPageNum = perPageNum;
	   }
	   
	      
	   public int getRowStart() {
	      return rowStart;
	   }
	   
	   public void setRowStart() {
	      this.rowStart = ((page -1) * perPageNum) + 1;
	   }
	   
	   public int getRowEnd() {
	      return rowEnd;
	   }
	   
	   public void setRowEnd() {
	      this.rowEnd = rowStart + perPageNum - 1;
	   }


}
