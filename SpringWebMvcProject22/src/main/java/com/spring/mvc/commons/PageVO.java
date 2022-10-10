package com.spring.mvc.commons;

public class PageVO {
	
	private Integer page; // 사용자가 요청한 페이지 번호
	private Integer countPerPage; // 한 페이지당 들어갈 게시물의 수
	
	
	public PageVO() {
		this.page = 1;
		this.countPerPage = 10;
	}
	
	
	// 클라이언트가 전달한 페이지번호를 데이터베이스의 LIMIT절에 맞는 숫자로 변환
	public Integer getPageStart() { 
		// ★getter 메서드가 꼭 필드에만 한정되는 메서드가 아니다!!★
		// 즉, BoardMapper에서 #{pageStart}를 입력하면 getPageStart()를 호출한다!!★★
		return (this.page - 1) * this.countPerPage;
	}
	
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		// page에 0 이하의 값 대입 방지
		if(page <= 0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}
	public Integer getCountPerPage() {
		return countPerPage;
	}
	public void setCountPerPage(Integer countPerPage) {
		// 0이하나 30초과의 값 대입 방지
		if(countPerPage <= 0 || countPerPage > 30) {
			this.countPerPage = 10;
			return;
		}
		this.countPerPage = countPerPage;
	}
	
	
	@Override
	public String toString() {
		return "pageVO [page=" + page + ", countPerPage=" + countPerPage + "]";
	}
	
}
