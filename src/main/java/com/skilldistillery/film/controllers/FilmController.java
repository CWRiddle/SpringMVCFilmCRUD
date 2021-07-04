package com.skilldistillery.film.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FilmController {
//private FilmDAO filmDAO;

	@RequestMapping( path= { "/", "home.do" } )
	public String index() {
		return "WEB-INF/home.jsp";
	}
	
	@RequestMapping("GetFilmData.do")
	public ModelAndView findFilm(int filmId) {
		ModelAndView mv = new ModelAndView();
		//Don't forget to setView
		return mv;
	}
	
}
