package org.pdsr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BindException.class, org.springframework.web.bind.MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleBindException(Exception ex, HttpServletRequest request) {
        org.springframework.validation.BindingResult br = null;
        if (ex instanceof BindException) {
            br = ((BindException) ex).getBindingResult();
        } else if (ex instanceof org.springframework.web.bind.MethodArgumentNotValidException) {
            br = ((org.springframework.web.bind.MethodArgumentNotValidException) ex).getBindingResult();
        }

        // Log all binding errors to server console
        System.err.println("=== BINDING ERROR on URL: " + request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "") + " ===");
        if (br != null) {
            br.getFieldErrors().forEach(fe ->
                System.err.println("  Field: [" + fe.getField() + "] rejected value: [" + fe.getRejectedValue() + "] reason: " + fe.getDefaultMessage())
            );
            br.getGlobalErrors().forEach(ge ->
                System.err.println("  Global error: " + ge.getDefaultMessage())
            );
        } else {
            System.err.println("  Exception: " + ex.getMessage());
        }
        System.err.println("=== END BINDING ERROR ===");

        // Build a user-visible error message listing the problem fields
        StringBuilder msg = new StringBuilder("Data binding error on page submission. Problem fields:<br><ul>");
        if (br != null) {
            br.getFieldErrors().forEach(fe ->
                msg.append("<li><b>").append(fe.getField()).append("</b>: rejected value = [")
                   .append(fe.getRejectedValue()).append("] — ").append(fe.getDefaultMessage()).append("</li>")
            );
        } else {
            msg.append("<li>An unexpected error occurred: ").append(ex.getMessage()).append("</li>");
        }
        msg.append("</ul><a href='javascript:window.history.back()'>Go Back</a>");

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", msg.toString());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleAllExceptions(Exception ex, HttpServletRequest request) {
        System.err.println("=== UNEXPECTED ERROR on URL: " + request.getRequestURL() + " ===");
        ex.printStackTrace();
        System.err.println("=== END UNEXPECTED ERROR ===");

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "An unexpected error occurred: " + ex.getMessage());
        return mav;
    }
}
