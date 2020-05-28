package com.ryoeiken.bms.controller;

import com.ryoeiken.bms.pojo.BookInfo;
import com.ryoeiken.bms.pojo.LendList;
import com.ryoeiken.bms.pojo.PageResult;
import com.ryoeiken.bms.pojo.ReaderCard;
import com.ryoeiken.bms.service.BookService;
import com.ryoeiken.bms.service.LendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LendController {
    @Autowired
    private LendService lendService;

    @Autowired
    private BookService bookService;

    @RequestMapping("/mylend.action")
    public String myLend(HttpSession session, Model model) {
        ReaderCard readerCard = (ReaderCard) session.getAttribute("readercard");
        List<LendList> list = this.lendService.myLendList(readerCard.getReaderId());
        model.addAttribute("list", list);
        return "reader_lend_list";
    }

    @RequestMapping("/lendlist.action")
    public String lendList(Model model,@RequestParam(defaultValue="1")Integer pageNum,
                           @RequestParam(defaultValue="5")Integer pageSize) {
        PageResult<LendList> list = this.lendService.lendList(pageNum, pageSize);
        model.addAttribute("list", list.getList());
        model.addAttribute("page",list);
        return "admin_lend_list";
    }

    @RequestMapping("/deletelog.action")
    public String deleteLog(Long sernum, RedirectAttributes redirectAttributes) {
        boolean success = this.lendService.deleteLog(sernum);
        if (success) {
            redirectAttributes.addFlashAttribute("succ", "删除成功！");
            return "redirect:/lendlist.action";
        } else {
            redirectAttributes.addFlashAttribute("error", "删除失败！");
            return "redirect:/lendlist.action";
        }
    }

    @RequestMapping("/querylog.action")
    public String queryLog(Integer searchWord, Model model) {
        boolean exist = this.lendService.matchLog(searchWord);
        if (exist) {
            List<LendList> list = this.lendService.myLendList(searchWord);
            model.addAttribute("list", list);
            return "admin_lend_list";
        } else {
            model.addAttribute("error", "该读者没有借还信息");
            return "admin_lend_list";
        }
    }

    @RequestMapping("/lendbook.action")
    public String bookLend(Long bookId, Model model) {
        BookInfo book = this.bookService.getBook(bookId);
        model.addAttribute("book", book);
        return "admin_book_lend";
    }

    @RequestMapping("/lendAndReturnBook.action")
    public String lendAndReturnBook(Long bookId, Model model) {
        BookInfo book = this.bookService.getBook(bookId);
        model.addAttribute("book", book);
        return "admin_book_lendOrReturn";
    }

    @RequestMapping("/lendbookdo.action")
    public String bookLendDo(Long bookId, Integer readerId, RedirectAttributes redirectAttributes) {
        boolean lendsucc = this.lendService.bookLend(bookId, readerId);
        if (lendsucc) {
            redirectAttributes.addFlashAttribute("succ", "图书借阅成功！");
            return "redirect:/allbooks.action";
        } else {
            redirectAttributes.addFlashAttribute("succ", "图书借阅成功！");
            return "redirect:/allbooks.action";
        }

    }

    @RequestMapping("/returnbook.action")
    public String bookReturn(Long bookId, RedirectAttributes redirectAttributes) {
        boolean retSucc = lendService.bookReturn(bookId);
        if (retSucc) {
            redirectAttributes.addFlashAttribute("succ", "图书归还成功！");
            return "redirect:/allbooks.action";
        } else {
            redirectAttributes.addFlashAttribute("error", "图书归还失败！");
            return "redirect:/allbooks.action";
        }
    }

    @RequestMapping("/lendbookdoNew.action")
    public String bookLendDoNew(Long bookId, Integer readerId, RedirectAttributes redirectAttributes) {
        boolean lendsucc = this.lendService.bookLendNew(bookId, readerId);
        if (lendsucc) {
            redirectAttributes.addFlashAttribute("succ", "图书借阅成功！");
            return "redirect:/allbooks.action";
        } else {
            redirectAttributes.addFlashAttribute("error", "图书借阅失败！");
            return "redirect:/allbooks.action";
        }

    }

    @RequestMapping("/returnbookNew.action")
    public String bookReturnNew(Long bookId, Long sernum,RedirectAttributes redirectAttributes) {
        boolean retSucc = lendService.bookReturnNew(bookId,sernum);
        if (retSucc) {
            redirectAttributes.addFlashAttribute("succ", "图书归还成功！");
            return "redirect:/lendlist.action";
        } else {
            redirectAttributes.addFlashAttribute("error", "图书归还失败！");
            return "redirect:/lendlist.action";
        }
    }
}

