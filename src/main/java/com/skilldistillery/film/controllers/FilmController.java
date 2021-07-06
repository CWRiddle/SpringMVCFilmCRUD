package com.skilldistillery.film.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.database.DatabaseAccessorObject;
import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

@Controller
public class FilmController {
	Integer globalFilmId;
	private DatabaseAccessorObject filmDAO = new DatabaseAccessorObject();

	@RequestMapping(path = { "/", "home.do" })
	public String index() {
		return "WEB-INF/home.jsp";
	}

	@RequestMapping(path = "GetFilmData.do", params = "filmId", method = RequestMethod.GET)
	public ModelAndView findFilmById(int filmId) {
		ModelAndView mv = new ModelAndView();
		Film film = filmDAO.findFilmById(filmId);
		if(film == null) {
			mv.setViewName("error.jsp");
			return mv;
		}
		mv.addObject("film", film);
		mv.setViewName("results.jsp");
		return mv;
	}

	@RequestMapping(path = "createFilm.do",
//			params={"filmTitle", "filmDesc"},
			params = { "filmTitle", "filmDesc", "releaseYear", "languageId", "language", "length", "rating", "category",
					"rentalRate", "replCost" }, method = RequestMethod.GET)
//	public ModelAndView createFilm(String filmTitle, String filmDesc) {
	public ModelAndView createFilm(String filmTitle, String filmDesc, String releaseYear, String language, String languageId,
			String length, String rating, String category, String rentalRate, String replCost) {
		ModelAndView mv = new ModelAndView();
		Film film;
		int id = 0;
		int rentalDuration = 0;
		List<Actor> cast = null;
		String specialFeatures = null;
		
		try {
		film = new Film(id, filmTitle, filmDesc, Integer.parseInt(releaseYear),Integer.parseInt(languageId), rentalDuration,Double.parseDouble(rentalRate),Integer.parseInt(length),
				Double.parseDouble(replCost), rating, specialFeatures, cast, language, category);
			film = filmDAO.createFilm(film);
		} catch (Exception e) {
			mv.setViewName("error.jsp");
			return mv;
		}
		for (int testId : filmDAO.findFilmIdsByFilmName(film.getTitle())) {
			System.out.println(testId);
		}
		System.out.println(film.getId());
		mv.addObject("film", film);
		mv.setViewName("results.jsp");
		return mv;
	}

	@RequestMapping(path = "delete.do", params = "filmId")
	public ModelAndView deleteFilm(int filmId) {
		ModelAndView mv = new ModelAndView();
		if (filmId <= 1000) {
			mv.setViewName("error.jsp");
		} else {
			filmDAO.deleteFilms(filmId);
			mv.setViewName("delete.jsp");
		}
		return mv;

	}

	@RequestMapping(path = "edit.do", params = { "filmTitle", "filmDesc", "releaseYear", "languageId", "language",
			"length", "rating", "category", "rentalRate", "replCost" })
	public ModelAndView updateFilm(String filmTitle, String filmDesc, String releaseYear, String language, String languageId,
			String length, String rating, String category, String rentalRate, String replCost) {
		ModelAndView mv = new ModelAndView();

		Film film = filmDAO.findFilmById(globalFilmId);
		try {
			film.setTitle(filmTitle);
			film.setDescription(filmDesc);
			film.setReleaseYear(Integer.parseInt(releaseYear));
			film.setLanguageId(Integer.parseInt(languageId));
			film.setLength(Integer.parseInt(length));
			film.setRating(rating);
			film.setRentalRate(Double.parseDouble(rentalRate));
			film.setReplacementCost(Double.parseDouble(replCost));
		} catch (Exception e) {
			mv.setViewName("error.jsp");
			e.printStackTrace();
			System.out.println("Exception Caught Here");
			return mv;
		}
		try {
			filmDAO.updateFilm(film);
		} catch (SQLException e) {
			mv.setViewName("error.jsp");
			return mv;
		}
		mv.addObject("film", film);
		mv.setViewName("updated.jsp");
		globalFilmId = null;
		return mv;

	}

	@RequestMapping(path = "editView.do")
	public ModelAndView editView(int filmId) {
		ModelAndView mv = new ModelAndView();
		Film film = filmDAO.findFilmById(filmId);
		mv.addObject("film", film);
		globalFilmId = filmId;
		if (globalFilmId <= 1000) {
			mv.setViewName("error.jsp");
		} else {
			mv.setViewName("editfilmform.jsp");

		}
		return mv;
	}

	@RequestMapping(path = "keyword.do", params = "keyword", method = RequestMethod.GET)
	public ModelAndView keywordSearch(String keyword) {
		ModelAndView mv = new ModelAndView();
		List<Film> films = filmDAO.findFilmByKeyword(keyword);
		mv.addObject("films", films);
		mv.setViewName("multiresult.jsp");
		return mv;

	}

}
