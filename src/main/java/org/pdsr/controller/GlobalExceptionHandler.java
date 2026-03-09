package org.pdsr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleBindException(BindException ex, HttpServletRequest request) {
        // Log all binding errors to server console
        System.err.println("=== BINDING ERROR on URL: " + request.getRequestURL() + "?" + request.getQueryString() + " ===");
        ex.getBindingResult().getFieldErrors().forEach(fe ->
            System.err.println("  Field: [" + fe.getField() + "] rejected value: [" + fe.getRejectedValue() + "] reason: " + fe.getDefaultMessage())
        );
        ex.getBindingResult().getGlobalErrors().forEach(ge ->
            System.err.println("  Global error: " + ge.getDefaultMessage())
        );
        System.err.println("=== END BINDING ERROR ===");

        // Build a user-visible error message listing the problem fields
        StringBuilder msg = new StringBuilder("Data binding error on page submission. Problem fields:<br><ul>");
        ex.getBindingResult().getFieldErrors().forEach(fe ->
            msg.append("<li><b>").append(fe.getField()).append("</b>: rejected value = [")
               .append(fe.getRejectedValue()).append("] — ").append(fe.getDefaultMessage()).append("</li>")
        );
        msg.append("</ul><a href='javascript:window.history.back()'>Go Back</a>");

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", msg.toString());
        return mav;
    }
}
