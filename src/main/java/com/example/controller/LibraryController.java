package com.example.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Library;
import com.example.entity.Log;
import com.example.repository.LibraryRepository;
import com.example.repository.LogRepository;
import com.example.service.LibraryService;
import com.example.service.LoginUser;

@Controller
@RequestMapping("library")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }
    @Autowired
    private LogRepository logRepository;
    
    @Autowired
    private LibraryRepository libraryRepository;

    @GetMapping
    public String index(Model model) {
        List<Library> libraries = this.libraryService.findAll();
        model.addAttribute("libraries", libraries);
        return "library/index";
    }
    @GetMapping("/borrow")
    public String borrowingForm(@RequestParam("id") Integer id, Model model) {
        // 書籍IDに該当する書籍情報を取得
        Library library = libraryService.getLibraryById(id);

        // モデルに書籍情報をセット
        model.addAttribute("library", library);

        // borrowingForm.html テンプレートを表示
        return "library/borrowingForm";
    }
    @PostMapping("/borrow")
    public String borrow(
            @RequestParam("id") Integer id,
            @RequestParam("return_due_date") String returnDueDate,
            @AuthenticationPrincipal LoginUser loginUser
            ) {

    	libraryService.borrow(id, loginUser, returnDueDate);


        // Redirect to the library page
       
        return "redirect:/library";
    }
    @PostMapping("/return")
    public String returnBook(@RequestParam("id") Integer id, 
                             @AuthenticationPrincipal LoginUser loginUser
                             ) {
       
        // 書籍IDを利用して書籍情報を取得
        Library library = libraryRepository.findById(id).orElse(null);
    
        if (library != null) {
        // 書籍情報のUSER_IDを0に設定して更新
        library.setUserId(0);
        libraryRepository.save(library);

        // Logsモデルを利用して更新処理を行う
        Log log = logRepository.findFirstByLibraryIdAndUserIdOrderByRentDateDesc(library.getId(), loginUser.getUserId());
        if (log != null) {
            log.setReturnDate(LocalDateTime.now());
            logRepository.save(log);
            
        }
    }
    return "redirect:/library";
    }
}
            
            
        
    
    

      

    
    
