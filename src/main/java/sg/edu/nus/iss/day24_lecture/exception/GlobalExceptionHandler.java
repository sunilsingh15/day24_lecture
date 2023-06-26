package sg.edu.nus.iss.day24_lecture.exception;

import java.util.Date;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BankAccountNotFoundException.class)
    public ModelAndView handleBankAccountNotFoundException(BankAccountNotFoundException ex, HttpServletRequest request) {
        // forming the custom error message
        ErrorMessage msg = new ErrorMessage();
        msg.setStatusCode(404);
        msg.setTimeStamp(new Date());
        msg.setMessage("Bank account doesn't exist or has not been created!");
        msg.setDescription(request.getRequestURL().toString());

        // return the error page with injected error message
        ModelAndView mav = new ModelAndView("error.html");
        mav.addObject("errorMessage", msg);
        return mav;
    }

    @ExceptionHandler(AccountBlockedAndDisabledException.class)
    public ModelAndView handleBankAccountBlockedAndDisabledException(AccountBlockedAndDisabledException ex, HttpServletRequest request) {
        // forming the custom error message
        ErrorMessage msg = new ErrorMessage();
        msg.setStatusCode(404);
        msg.setTimeStamp(new Date());
        msg.setMessage("Bank account has been disabled or blocked.");
        msg.setDescription(request.getRequestURL().toString());

        // return the error page with injected error message
        ModelAndView mav = new ModelAndView("error.html");
        mav.addObject("errorMessage", msg);
        return mav;
    }

    @ExceptionHandler(AmountNotSufficientException.class)
    public ModelAndView handleAmountNotSufficientException(AmountNotSufficientException ex, HttpServletRequest request) {
        // forming the custom error message
        ErrorMessage msg = new ErrorMessage();
        msg.setStatusCode(404);
        msg.setTimeStamp(new Date());
        msg.setMessage("Amount insufficient for transfer!");
        msg.setDescription(request.getRequestURL().toString());

        // return the error page with injected error message
        ModelAndView mav = new ModelAndView("error.html");
        mav.addObject("errorMessage", msg);
        return mav;
    }
    
}
