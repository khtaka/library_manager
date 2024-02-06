package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Library;
import com.example.repository.LibraryRepository;
import com.example.repository.LogRepository;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final LogRepository logRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
        this.logRepository = logRepository;
    }

    public List<Library> findAll() {
        return this.libraryRepository.findAll();
    }
 // LibraryService クラス
    public Library getLibraryById(Integer id) {
        Optional<Library> libraryOptional = this.libraryRepository.findById(id);
        return libraryOptional.orElse(null);
    }
    public void borrowBook (Integer bookId, String returnDueDate, Integer userId) {
    	
    }



}