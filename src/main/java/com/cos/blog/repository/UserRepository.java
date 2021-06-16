package com.cos.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

//DAO
// 자동으로 BEAN등록
//@Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {
	//SELECT * FROM user WHERE username =1?;
	Optional<User> findByUsername(String username);
	
}


//JPA Naming전략
	//SELECT * FROM user WHERE username=? AND password = ? 
	//User findByUsernameAndPassword(String username,String password);
	
	//	@Query(value="SELECT * FROM user WHERE username=?1 AND password = ?2" , nativeQuery = true)
	//	User login(String username, String password);
