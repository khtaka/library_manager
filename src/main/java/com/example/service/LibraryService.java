package com.example.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Library;
import com.example.entity.Log;
import com.example.repository.LibraryRepository;
import com.example.repository.LogRepository;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final LogRepository logRepository;
    

    @Autowired
    public LibraryService(LibraryRepository libraryRepository, LogRepository logRepository) {
        this.libraryRepository = libraryRepository;
        this.logRepository = logRepository;
    }

    public List<Library> findAll() {
        return this.libraryRepository.findAll();
    }
    public Library getLibraryById(Integer id) {
        // 書籍IDに該当する書籍情報を取得
    	Optional<Library> optionalLibrary = libraryRepository.findById(id);
        return optionalLibrary.orElse(null);
        
    }
    public void borrow(Integer id, LoginUser loginUser, String returnDueDate) {
        // 書籍情報を取得
        Library library = libraryRepository.findById(id).orElse(null);
        if (library != null) {
            // ユーザーIDを上書き
            library.setUserId(loginUser);
            // 書籍情報を更新
            libraryRepository.save(library);

            // 新しいログを生成
            Log log = new Log();
            log.setLibraryId(library.getId());
            log.setUserId(loginUser);
            log.setRentDate(LocalDateTime.now());
            // 返却予定日のフォーマットを適用してパース
            log.setReturnDueDate(LocalDateTime.parse(returnDueDate + "T00:00:00"));
            log.setReturnDate(null); // NULLを設定
            // ログを保存
            logRepository.save(log);
        }
    }
}




