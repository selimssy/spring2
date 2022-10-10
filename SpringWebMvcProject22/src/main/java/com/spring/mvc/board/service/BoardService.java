package com.spring.mvc.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mvc.board.model.BoardVO;
import com.spring.mvc.board.repository.IboardMapper;
import com.spring.mvc.commons.PageVO;


@Service
public class BoardService implements IBoardService {
	
	@Autowired
	private IboardMapper mapper;

	
	
	@Override
	public void insert(BoardVO article) {
		mapper.insert(article);
	}

	
	@Override
	public List<BoardVO> getArticleList() {
		return mapper.getArticleList();
	} 
	@Override
	public List<BoardVO> getArticleListPaging(PageVO paging) {
		//page = (page - 1) *10; // 객체에서 수행!
		return mapper.getArticleListPaging(paging);
	}
	
	
	
	@Override
	public Integer countArticles() {
		return mapper.countArticles();
	}
	

	@Override
	public BoardVO getArticle(Integer boardNo) {
		return mapper.getArticle(boardNo);
	}

	@Override
	public void update(BoardVO article) {
		mapper.update(article);
	}

	@Override
	public void delete(Integer boardNo) {
		mapper.delete(boardNo);
	}

}
