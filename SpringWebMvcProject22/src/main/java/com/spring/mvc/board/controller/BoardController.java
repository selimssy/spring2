package com.spring.mvc.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.mvc.board.model.BoardVO;
import com.spring.mvc.board.service.IBoardService;
import com.spring.mvc.commons.PageVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private IBoardService service;
	
	
	
	// 게시글 목록 불러오기 요청(페이징처리 이전)
	/*@GetMapping("/list")
	public String list(Model model) {
		
		List<BoardVO> list = service.getArticleList();
		System.out.println("URL: /board/list GET -> result" + list.size());
		//list.forEach(vo -> System.out.println(vo));
		model.addAttribute("articles", list);
		
		return "board/list";
	}  */
	
	// 페이징처리 이후 게시글 목록 불러오기 요청
	@GetMapping("/list")
	public String list(PageVO paging, Model model) { // PageVO paging 일 때 PageVO 객체가 생성되는거다...
		
		List<BoardVO> list = service.getArticleListPaging(paging);
		System.out.println("URL: /board/list GET -> result" + list.size());
		System.out.println("페이지 번호 : " + paging.getPage() + "페이지, " + paging.getCountPerPage() + "개씩 출력");
		model.addAttribute("articles", list);
		
		return "board/list";
	}  
	
	
	// 글쓰기 화면 띄우기 요청
	@GetMapping("/write")
	public void write() {
		
	}
	
	// 글쓰기 처리 요청
	@PostMapping("/write")
	public String write(BoardVO article, RedirectAttributes ra) {
		
		System.out.println("parameter : " + article);  // t) 이런 파라미터 잘 들어오는지 확인하는습관 들이는게 좋다!
		service.insert(article);	
		ra.addFlashAttribute("msg", "regSuccess");
		return "redirect:/board/list";
	}
	
	
	
	
	// 게시물 상세보기 요청
	@GetMapping("/content/{boardNo}")
	public String content(@PathVariable Integer boardNo, Model model) {
		// @PathVariable : 경로에서 파라미터variable을 읽어서 Integer boardNo 에 집어넣겠다 (이게 요즘 추세다!)
		// 원형은 @PathVariable("boardNo") Integer boardNo  인데 이름이 같으면 생략 가능하다
		
		System.out.println(boardNo + "번 게시물 조회 요청");
		model.addAttribute("article", service.getArticle(boardNo));
		
		return "board/content";
	}
	
	
	
	
	// 게시물 삭제 요청
	@PostMapping("/delete")
	public String delete(Integer boardNo, RedirectAttributes ra) {  // input String이지만 그냥 Integer boardNo로 받아도 된다!(커맨드?)
		
		System.out.println(boardNo + "번 게시물 삭제 요청");
		service.delete(boardNo);
		ra.addFlashAttribute("msg", "delSuccess");
		
		return "redirect:/board/list";
	}
	
	
	
	
	// 게시물 수정페이지 요청
	@GetMapping("/modify")
	public String modify(Integer boardNo, Model model) {
		System.out.println(boardNo + "번 게시물 수정 요청 : GET");
		model.addAttribute("article", service.getArticle(boardNo));
		return "board/modify";
	}
	
	// 게시물 수정 요청
	@PostMapping("/modify")
	public String modify(BoardVO article, RedirectAttributes ra) { 
		// 원래는 boardNo를 hidden으로 줘야되는데 spring에선 같은url 이면 get에서 썼던걸 그대로 넘겨준다! 근데 확실하게 하려면 hidden 넣어주자
		System.out.println("번 게시물 수정 요청 : POST");
		service.update(article);
		ra.addFlashAttribute("msg", "modSuccess");
		return "redirect:/board/content/" + article.getBoardNo();
	}

}
