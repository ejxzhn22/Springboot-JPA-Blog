package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void boardWrite(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
		
	}
	@Transactional(readOnly = true)
	public Page<Board> boardList(Pageable pageable){
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board boardDetail(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void boardDelete(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void boardUpdate(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
				}); //영속화 완료
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수로 종료시 (service가 종료될때) 트랜잭션이 종료, 이때 더티체킹 -> 자동 업데이트 flush
	}
	
	
}
