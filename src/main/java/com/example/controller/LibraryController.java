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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Library;
import com.example.service.LibraryService;

@Controller
@RequestMapping("library")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

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
            @AuthenticationPrincipal Integer loginUser,
            RedirectAttributes redirectAttributes) {

    	this.libraryService.insert(id,loginUser,returnDueDate);

        // Redirect to the library page
        redirectAttributes.addFlashAttribute("message", "Book borrowed successfully!");
        return "redirect:/library";
    }
    
}