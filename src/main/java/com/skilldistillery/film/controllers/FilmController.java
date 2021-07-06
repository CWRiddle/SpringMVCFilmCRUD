package com.skilldistillery.film.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.database.DatabaseAccessorObject;
import com.skilldistillery.film.entities.*;

@Controller
public class FilmController {
private DatabaseAccessorObject filmDAO;

	@RequestMapping( path= { "/", "home.do" } )
	public String index() {
		return "WEB-INF/home.jsp";
	}

	@RequestMapping(path="GetFilmData.do",
					params="filmId",
					method=RequestMethod.GET)
	public ModelAndView findFilmById(int filmId) {
		ModelAndView mv = new ModelAndView();
		Film film = filmDAO.findFilmById(filmId);
		mv.addObject(film);
		mv.setViewName("webapp/results.jsp");
		return mv;
	}

}
