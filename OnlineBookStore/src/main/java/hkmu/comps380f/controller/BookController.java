package hkmu.comps380f.controller;

import hkmu.comps380f.dao.BookService;
import hkmu.comps380f.exception.BookNotFound;
import hkmu.comps380f.exception.CoverPhotoNotFound;
import hkmu.comps380f.model.Book;
import hkmu.comps380f.model.CoverPhoto;
import hkmu.comps380f.view.DownloadingView;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/book")
public class BookController {
    @Resource
    private BookService bService;

    @GetMapping(value = {"", "/catalog"})
    public String catalog(ModelMap model) {
        model.addAttribute("bookDatabase", bService.getBooks());
        return "catalog";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("add", "bookForm", new Form());
    }
    public static class Form {
        private String bookName;
        private String author;
        private int price;
        private String description;
        private List<MultipartFile> coverPhotos;

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<MultipartFile> getCoverPhotos() {
            return coverPhotos;
        }

        public void setCoverPhotos(List<MultipartFile> coverPhotos) {
            this.coverPhotos = coverPhotos;
        }
    }

    @PostMapping("/create")
    public View create(Form form, Principal principal) throws IOException {
        long bookId = bService.createBook(form.getBookName(),
                form.getAuthor(), form.getPrice(), form.getDescription(), form.getCoverPhotos());
        return new RedirectView("/book/view/" + bookId, true);
    }

    @GetMapping("/view/{bookId}")
    public String view(@PathVariable("bookId") long bookId,
                       ModelMap model)
            throws BookNotFound {
        Book book = bService.getBook(bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("book", book);
        return "viewBook";
    }

    @GetMapping("/{bookId}/coverPhoto/{coverPhoto:.+}")
    public View download(@PathVariable("bookId") long bookId,
                         @PathVariable("coverPhoto") UUID coverPhotoId)
            throws BookNotFound, CoverPhotoNotFound {
        CoverPhoto coverPhoto = bService.getCoverPhoto(bookId, coverPhotoId);
        return new DownloadingView(coverPhoto.getName(),
                coverPhoto.getMimeContentType(), coverPhoto.getContents());
    }

    @GetMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") long bookId)
            throws BookNotFound {
        bService.delete(bookId);
        return "redirect:/book/catalog";
    }

    @GetMapping("/{bookId}/delete/{coverPhoto:.+}")
    public String deleteCoverPhoto(@PathVariable("bookId") long bookId,
                                   @PathVariable("coverPhoto") UUID coverPhotoId)
            throws BookNotFound, CoverPhotoNotFound {
        bService.deleteCoverPhoto(bookId, coverPhotoId);
        return "redirect:/book/view/" + bookId;
    }

    @GetMapping("/edit/{bookId}")
    public ModelAndView showEdit(@PathVariable("bookId") long bookId,
                                 Principal principal, HttpServletRequest request)
            throws BookNotFound {
        Book book = bService.getBook(bookId);
        if (book == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(book.getBookName()))) {
            return new ModelAndView(new RedirectView("/book/catalog", true));
        }
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("book", book);
        Form bookForm = new Form();
        bookForm.setBookName(book.getBookName());
        bookForm.setAuthor(book.getAuthor());
        bookForm.setPrice(book.getPrice());
        bookForm.setDescription(book.getDescription());
        modelAndView.addObject("bookForm", bookForm);
        return modelAndView;
    }
    @PostMapping("/edit/{bookId}")
    public String edit(@PathVariable("bookId") long bookId, Form form,
                       Principal principal, HttpServletRequest request)
            throws IOException, BookNotFound {
        Book book = bService.getBook(bookId);
        if (book == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(book.getBookName()))) {
            return "redirect:/book/catalog";
        }
        bService.updateBook(bookId, form.getBookName(), form.getAuthor(),
                form.getPrice(), form.getDescription(), form.getCoverPhotos());
        return "redirect:/book/view/" + bookId;
    }


    @ExceptionHandler({BookNotFound.class, CoverPhotoNotFound.class})
    public ModelAndView error(Exception e) {
        return new ModelAndView("error", "message", e.getMessage());
    }
}

