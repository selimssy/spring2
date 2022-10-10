package com.spring.mvc.board.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.mvc.board.model.BoardVO;
import com.spring.mvc.board.repository.IboardMapper;
import com.spring.mvc.commons.PageVO;



@RunWith(SpringJUnit4ClassRunner.class)  // 이거 쓰려면 Spring Test 모듈 maven 주입해야
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/mvc-config.xml"})
public class BoardMapperTest {
	
	@Autowired
	private IboardMapper mapper;
	
	
	
	// 게시글 등록 테스트
	@Test
	public void insertTest() {
		
		for(int i=1; i<=320; i++) {
		
			BoardVO article = new BoardVO();
			article.setTitle("테스트 제목" + i);
			article.setWriter("홍길동" + i);
			article.setContent("테스트 게시물 컨텐츠" + i);
			mapper.insert(article);
		
		}
		
		System.out.println("게시물 등록 성공!");
	}
	
	
	
	
	
	// 게시글 목록 조회 테스트
	@Test
	public void getListTest() {
		
		List<BoardVO> list = mapper.getArticleList();
		
		for(BoardVO vo : list) {
			System.out.println(vo);
		}
		
		
		// 람다식 이욯해서 이렇게 위의 3줄을 1줄로 코딩할 수 있다 
		//mapper.getArticleList().forEach(vo -> System.out.println(vo));
		
	}
	
	
	
	
	// 게시글 단일 조회 테스트
	@Test
	public void getArticleTest() {
		
		BoardVO article = mapper.getArticle(45);
		System.out.println(article);
		
	}
	
	
	
	
	// 게시물 수정 테스트
	@Test
	public void update() {
		
		BoardVO article = new BoardVO();
		article.setBoardNo(45);
		article.setTitle("제목 수정");
		article.setContent("내용 수정");
		article.setWriter("박영희");
		mapper.update(article);
		
		System.out.println(mapper.getArticle(45)); 
	
	}
	
	
	
	
	// 게시물 삭제 테스트
	@Test
	public void delete() {
		
		mapper.delete(10);
		
		BoardVO vo = mapper.getArticle(10);
		if(vo == null) {
			System.out.println("게시물이 없습니다!");
		}else {
			System.out.println("게시물 : " + vo);
		}
		
	}
	
	
	
	
	
	
	// 페이징 이후 전체조회 테스트
	@Test
	public void pagingTest() {
		PageVO paging = new PageVO();
		paging.setPage(0);
		paging.setCountPerPage(20);
		mapper.getArticleListPaging(paging).forEach(vo -> System.out.println(vo));
	}
	
}
