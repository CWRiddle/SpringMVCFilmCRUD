package com.skilldistillery.film.database;

import java.util.List;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

public interface DatabaseAccessor {
  public Film findFilmById(int filmId);
  public Actor findActorById(int actorId);
  public List<Actor> findActorsByFilmId(int filmId);
  public List<Film> findFilmByKeyword(String keyword);
  
  public Film createFilm(Film newFilm);
  public void deleteFilmsByTitle(String filmTitle);	//Alternative method
  
  //public List<Integer> findFilmIdsByFilmName(String filmTitle);
  public void deleteFilms(String title);
}
