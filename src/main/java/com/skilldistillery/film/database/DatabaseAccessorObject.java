package com.skilldistillery.film.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	// SQL Database Login Info
	public static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private String user = "student";
	private String pass = "student";

	// Setup for SQL Driver
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////
	// FINDS A SINGLE FILM BASED OFF OF A FILM ID
	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT * FROM film WHERE film.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				// Retrieve Fields From Film Database
				int id = rs.getInt("film.id");
				String title = rs.getString("film.title");
				String description = rs.getString("film.description");
				int releaseYear = rs.getInt("film.release_year");
				int languageId = rs.getInt("film.language_id");
				int rentalDuration = rs.getInt("film.rental_duration");
				double rentalRate = rs.getDouble("film.rental_rate");
				int length = rs.getInt("film.length");
				double replacementCost = rs.getDouble("film.replacement_cost");
				String rating = rs.getString("film.rating");
				String specialFeatures = rs.getString("film.special_features");

				film = new Film(id, title, description, releaseYear, languageId, rentalDuration, rentalRate, length,
						replacementCost, rating, specialFeatures, findActorsByFilmId(filmId), findFilmLanguage(filmId),
						findFilmCategory(filmId));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return film;
	}

	/////////////////////
	// FIND FILM LANGUAGE
	private String findFilmLanguage(int filmId) {
		String language = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT language.name FROM language JOIN film ON language.id=film.language_id WHERE film.id=?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, filmId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				language = rs.getString("language.name");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return language;
	}

	/////////////////////
	// FIND FILM CATEGORY
	private String findFilmCategory(int filmId) {
		String category = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT category.name " + "FROM category " + "JOIN film_category "
					+ "ON category.id=film_category.category_id " + "JOIN film "
					+ "ON film_category.film_id=film.id WHERE film.id=?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, filmId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				category = rs.getString("category.name");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;

	}

	//////////////////////////////////
	// RETURNS ACTOR GIVEN AN ACTOR ID
	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT * FROM actor WHERE actor.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("actor.id");
				String firstName = rs.getString("actor.first_name");
				String lastName = rs.getString("actor.last_name");

				actor = new Actor(id, firstName, lastName);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	////////////////////////////////////////////
	// RETURNS A LIST OF ACTORS GIVEN A FILM ID
	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		Actor actor = null;

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT DISTINCT actor.id, actor.first_name, actor.last_name, film.title " + "FROM actor "
					+ "JOIN film_actor " + "ON actor.id=film_actor.actor_id " + "JOIN film "
					+ "ON film_actor.film_id=film.id WHERE film.id=?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("actor.id");
				String firstName = rs.getString("actor.first_name");
				String lastName = rs.getString("actor.last_name");

				actor = new Actor(id, firstName, lastName);
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	/////////////////////////////////////////////
	// RETURNS A LIST OF FILMS - GIVEN A KEYWORD
	@Override
	public List<Film> findFilmByKeyword(String keyword) {
		Film film;
		List<Film> films = new ArrayList<>();
		// Format keyword for sql statement
		keyword = "%" + keyword + "%";
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT * FROM film WHERE title LIKE ? OR description LIKE ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, keyword);
			stmt.setString(2, keyword);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int filmId = rs.getInt("film.id");
				film = findFilmById(filmId);
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	///////////////////////////////////////////
	// RETUNS A LIST OF FILM IDs GIVEN A TITLE
	private List<Integer> findFilmIdsByFilmName(String filmTitle) {
		List<Integer> filmIds = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT film.id FROM film WHERE film.title=?";

			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, filmTitle);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int filmId = rs.getInt("film.id");
				filmIds.add(filmId);
			}

			rs.close();
			stmt.close();
			conn.close();

			return filmIds;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////
	// CREATES AND RETURNS A NEW FILM IN THE FILM DATABASE GIVEN A FILM OBJECT
	@Override
	public Film createFilm(Film newFilm) {
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "INSERT INTO film (film.title, film.description, film.release_year, film.language_id, "
					+ "film.rental_duration, film.rental_rate, film.length, film.replacement_cost, "
					+ "film.rating, film.special_features) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			// Sets ? at indexes to appropriate field from newFilm
			stmt.setString(1, newFilm.getTitle());
			stmt.setString(2, newFilm.getDescription());
			stmt.setInt(3, newFilm.getReleaseYear());
			stmt.setInt(4, newFilm.getLanguageId());
			stmt.setInt(5, newFilm.getRentalDuration());
			stmt.setDouble(6, newFilm.getRentalRate());
			stmt.setInt(7, newFilm.getLength());
			stmt.setDouble(8, newFilm.getReplacementCost());
			stmt.setString(9, newFilm.getRating());
			stmt.setString(10, newFilm.getSpecialFeatures());

			stmt.executeUpdate();

			ResultSet key = stmt.getGeneratedKeys();

			// Sets newly created film's ID field to the ID in the Database
			if (key.next()) {
				newFilm.setId(1);
				return newFilm;
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/////////////////////////////////////////////////////////
	// DELETES FILMS FROM DATABASED GIVEN A LIST OF FILM IDs
	public void deleteFilms(String title) {
		List<Integer> filmIds = findFilmIdsByFilmName(title);
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			for (Integer filmId : filmIds) {
				// VALIDATION: (only deletes if filmID is a user created filmID)
				if (filmId > 1000) {
					String sql = "DELETE FROM film WHERE film.id=?";

					PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					stmt.setInt(1, filmId);

					stmt.executeUpdate();

					stmt.close();
				} else {
					System.out.println("Film cannot be deleted.");
				}
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////////
	// DELETES A SINGLE FILM FROM DATABASED GIVEN A FILM ID
	public void deleteFilms(int filmId) {
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			// VALIDATION: (only deletes if filmID is a user created filmID)
			if (filmId > 1000) {
				String sql = "DELETE FROM film WHERE film.id=?";

				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				stmt.setInt(1, filmId);
				stmt.executeUpdate();
				stmt.close();
			} else {
				System.out.println("Film cannot be deleted.");
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////
	// DELETES FILMS FROM DATABASE BASED OFF OF A TITLE
	// ->This is an unused method
	@Override
	public void deleteFilmsByTitle(String filmTitle) {

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "DELETE FROM film WHERE film.id=?";

			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, filmTitle);

			int numFilmsDeleted = stmt.executeUpdate();

			System.out.println(numFilmsDeleted + " film(s) deleted from table");

			// rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// return null;
	}

}
