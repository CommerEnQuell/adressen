package nl.commerquell.adressen.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception x) {
		ModelAndView retval = new ModelAndView("exception");
		retval.addObject("error", x.getClass().getName());
		retval.addObject("message", x.getMessage());
		return retval;
	}

}
