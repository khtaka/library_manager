package com.example.controller;

import java.time.LocalDate;
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
        // 変数 library を定義し、リクエストパラメータで渡された書籍IDに該当する書籍情報を1件取得し代入する
        Library library = libraryService.getLibraryById(id);

        // 取得した書籍情報の USER_ID を現在ログインしているユーザーのIDで上書きし LIBRARIES テーブルの情報を更新する
        library.setUserId(loginUser.getId());
        libraryService.updateLibrary(library);

        // Log モデルを利用して、INSERT処理を行い、新しいログを生成する
        Log log = new Log();
        log.setLibraryId(library.getId());
        log.setUserId(loginUser.getId());
        log.setRentDate(LocalDateTime.now());
        
        // RETURN_DUE_DATE はフォームから送られてきた返却予定日とする
        LocalDate returnDueLocalDate = LocalDate.parse(returnDueDate);
        LocalDateTime returnDueLocalDateTime = returnDueLocalDate.atStartOfDay();
        log.setReturnDueDate(returnDueLocalDateTime);

        // 引数には、返却予定日と T00:00:00を文字連結する事
        log.setReturnDate(null); // return_dateにはNULLを設定する

        // save メソッドを利用して生成する
        libraryService.saveLog(log);

        // redirect の機能を利用して http://localhost:8080/library にリダイレクトを行う
        return "redirect:/library";
    }
}