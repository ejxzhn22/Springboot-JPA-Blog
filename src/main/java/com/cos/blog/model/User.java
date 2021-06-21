package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM : JAVA(다른언어) object -> 테이블로 매핑해주는 기술

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
@Entity //User 클래스가 Mysql에 테이블이 생성된다.
//@DynamicInsert  -> insert시에 null인 필드를 제외시켜준다.
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 db의 넘버링 전략을 따라간다. 
	private int id; // auto_increment
	
	@Column(nullable = false, length = 100)
	private String username; //아이디
	
	@Column(nullable = false, length = 100) // 비밀번호를 해쉬로 암호화
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//@ColumnDefault("user")
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum 을 쓰는게 좋다.
	
	private String oauth; // kakao, google
	
	@CreationTimestamp //시간이 자동 입력
	private Timestamp createDate;
	
	
	
}
