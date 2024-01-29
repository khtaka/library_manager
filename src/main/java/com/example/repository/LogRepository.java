package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

    // 必要に応じて追加のクエリメソッドを記述
}
