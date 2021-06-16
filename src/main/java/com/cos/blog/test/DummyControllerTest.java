package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired //의존성주입
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다. " ;
		}
		
		return "삭제되었습니다 id: " + id;
	}
	
	//save함수는 id를 전달하지 않으면 insert
	//id를 전달하면 해당 id가 있으면update
	//id가 없으면 insert
	//이메일 비밀번호 수정
	@Transactional //함수 종료 시에 자동 commit 됨
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(() ->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		requestUser.setPassword(requestUser.getPassword());
		requestUser.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		
		//더티 체킹
		return user;
	}
	
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=1, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pagingUser = userRepository.findAll(pageable);
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	//{id}주소로 파라미터를 전달받을 수 있음
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//user/4을 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null
		//그럼 return null이 됨  그럼 프로그램에 문제
		//Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 리턴
		
		// 없으면 빈객체를 넣어준다
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//			@Override
//			public User get() {
//				// TODO Auto-generated method stub
//				return new User();
//			}
//		});
//		return user;
		
		//람다식
//		User user = userRepository.findById(id).orElseThrow(()->{
//				return new IllegalArgumentException("해당 유저는 없습니다. id: "+ id);
//			
//		});
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id: "+ id);
			}
		});
		
		//user 객체 = 자바 오브젝트
		//변환(웹브라우저가 이해할 수 있는 데이터) -> json
		//스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		//자바 오브젝트를 리턴하게 되면 MassageConverter가 Jackson라이브러리를 호출해서 
		//user 오브젝트를 json으로 변환에서 브라우저에게 던져준다.
		return user;
		
	}
	
	//http://localhost:8000/blog/dummy/join (요청)
	//http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) { //key = value
		System.out.println("userID: " + user.getId());
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		System.out.println("role: " + user.getRole());
		System.out.println("createDate: " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다";
	}
}
