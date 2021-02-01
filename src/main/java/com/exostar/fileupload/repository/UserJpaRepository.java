package com.exostar.fileupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exostar.fileupload.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {

	
}
