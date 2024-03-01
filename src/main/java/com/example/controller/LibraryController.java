package com.example.controller;

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

    	this.libraryService.borrow(id, loginUser, returnDueDate);


        // Redirect to the library page
       
        return "redirect:/library";
    }
    @PostMapping("/return")
    public String returnBook(@RequestParam("id") Integer id, 
                             @AuthenticationPrincipal LoginUser loginUser
                             ) {
       
    	this.libraryService.returnBook(id,loginUser);
    
   
    	return "redirect:/library";
    }
    @GetMapping("/history")
    public String history(Model model, @AuthenticationPrincipal LoginUser loginUser) {
        // ログインユーザーのIDを取得
        Integer userId = loginUser.getUserId();
        
        // ユーザーIDに紐づいた貸し出し履歴を取得
        List<Log> borrowHistory = logRepository.findByUserId(userId);

        // モデルに貸し出し履歴をセット
        model.addAttribute("borrowHistory", borrowHistory);

        // borrowHistory.html テンプレートを表示
        return "library/borrowHistory";
    }
}
            
            
        
    
    

      

    
    
