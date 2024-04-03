package hkmu.comps380f.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public class BookNotFound extends Exception {
    public BookNotFound(long id) {
        super("Book " + id + " does not exist.");
    }
}
