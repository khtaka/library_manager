package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Log;

public interface LogRepository extends JpaRepository<Log, Integer> {

	Log findFirstByLibraryIdAndUserIdOrderByRentDateDesc(Integer libraryId, Integer userId);

	List<Log> findByUserId(Integer userId);
	
    // 必要に応じて追加のクエリメソッドを記述
}
