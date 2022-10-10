package com.spring.mvc.board.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.mvc.board.repository.IboardMapper;
import com.spring.mvc.commons.PageVO;

@RunWith(SpringJUnit4ClassRunner.class)  // 이거 쓰려면 Spring Test 모듈 maven 주입해야
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/mvc-config.xml"})
public class PagingAlgorithmTest {
	
	
	/*
	  
	  	***  페이징 알고리즘 만들기  ***
	  	사용자가 보게 될 페이지 화면
	  	
	  	ex) 총 게시물 수가 67개
	  	    1 2 3 4 5 6 7  // 이전, 다음 비활성화
	  	    
	  	ex) 총 게시물 수 142개, 현재 12페이지에 머물러있다면
	  	    11 12 13 14 15
	  	    
	  	        
	   
	    1. 우선 총 게시물의 수를 조회히야
	     - DB로부터 총 게시물 수를 조회하는 SQL을 작성 	        
	  
	    
	    2. 사용자가 현재 위치한 페이지를 기준으로 끝 페이지 번호를 구하는 로직 작성
	      ex) 현재 3페이지, 한 화면에 10페이지씩 -> 끝페이지는 10페이지
	      ex) 현재 37페이지, 판 화면에 20페이지씩 -> 끝페이지는 40페이지
	      
	     공식 : Math.ceil(현재 위치한 페이지 / 한 화면당 보여질 페이지 수) * 한 화면당 보여질 페이지 수 
	    
	    
	   	    
	    3. 시작 페이지 번호 계산하기
	      ex) 현재 15페이지, 한 화면에 10페이지씩 -> 시작페이지는 11페이지
	      ex) 현재 73페이지, 한 화면에 20페이지씩 -> 시작페이지는 61페이지
	      
	     공식 : (끝페이지 번호 - 한 화면에 보여줄 페이지 수) + 1   
	    
	    
	    
	    4. 끝 페이지 보정
	      - 총 게시물 수가 324개, 한 페이지당 10개의 게시물 출력, 그리고 한 화면당 10개의 페이지씩 보여주는데
	        현재 31페이지에 위치할 때, 위 공식에 의한 끝페이지 : 40
	      - BUT 실제는 33페이지까지 나와야!!
	      
	      
	    # 4-1) 이전 버튼 활성 여부 설정
	       - 언제 이전 버튼을 비활성화 할 것인가? -> 시작페이지가 1인 부분에서 비활성
	       - 공식 : 시작페이지 번호가 1로 구해진 시점에서 비활성처리, 나머지는 활성
	               boolean isPrev = (beginPage == 1) ? false : true;
	         
	    # 4-2) 다음 버튼 활성 여부 설정
	       - 언제 다음 버튼을 비활성화 할 것인가? -> 
	         총 게시물 수 <= 보정 전 끝페이지 번호 x 한 페이지당 들어갈 게시물 수
	         일 때 비활성
	       - boolean isNext = (mapper.countArticles() <= endPage * paging.getCountPerPage()) ? false : true;
	    
	    # 4-3) 끝 페이지 값 보정
	       - 다음 버튼이 비활성화 되었을 때 총 게시물 수에 맞춰 끝페이지번호 보정!
	       - 공식 : Math.ceil(총 게시물의 수 / 한 페이지에 보여줄 게시물 수)
	              if(!isNext) {
				     endPage = (int)Math.ceil(mapper.countArticles() / (double)paging.getCountPerPage());
		            }
	    
	  */
	
	
	@Autowired
	private IboardMapper mapper;
	
	@Test
	public void pagingAlgorithmTest() {
		
		// 총 게시물 수 구하는 테스트
		System.out.println("--------------------------------");
		System.out.println("총 게시물 수 : " + mapper.countArticles() + "개");
		System.out.println("--------------------------------");
		
		
		// 끝 페이지 번호 계산 테스트
		PageVO paging = new PageVO();
		paging.setPage(31);
		int displayPage = 10;
		
		int endPage = (int)Math.ceil(paging.getPage() / (double)displayPage) * displayPage;
		System.out.println("끝 페이지 번호 : " + endPage);
		
		
		// 시작 페이지 번호 계산 테스트
		int beginPage = (endPage - displayPage) + 1;
		System.out.println("시작 페이지 번호 : " + beginPage);
		
		
		// 이전 버튼 활성 여부
		boolean isPrev = (beginPage == 1) ? false : true;
		System.out.println("이전 버튼 활성 여부 : " + isPrev);
		
		
		// 다음 버튼 활성 여부
		boolean isNext = (mapper.countArticles() <= endPage * paging.getCountPerPage()) ? false : true;
		System.out.println("다음 버튼 활성 여부 : " + isNext);
		
		
		// 끝 페이지 값 보정
		if(!isNext) {
			endPage = (int)Math.ceil(mapper.countArticles() / (double)paging.getCountPerPage());
		}
		System.out.println("보정 후 끝페이지 번호 : " + endPage);
		
		System.out.println("--------------------------------");
	}
	
	
	
	
	
	
	
	
	
}
