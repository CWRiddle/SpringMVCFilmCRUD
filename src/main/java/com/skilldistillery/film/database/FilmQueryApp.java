package com.skilldistillery.film.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		//Local Variables:
		FilmQueryApp app = new FilmQueryApp();
		
		//Launch App
		app.launch();
		
		//Verify program end.
		System.out.println("Program ended.");
	}

	//Launches App
	private void launch() {
		//Local Variables:
		Scanner input = new Scanner(System.in);
		
		startUserInterface(input);
		input.close();
	}

	//Navigation Road Map for Menu Display and Menu Actions
	private void startUserInterface(Scanner input) {
		boolean looping = true;
		while (looping) {
			// Display Menu Prompt and Execute Selected Method
			switch (menu(input)) {
			case 1:
				lookUpFilmById(input);
				break;
			case 2:
				lookUpFilmByKeyword(input);
				break;
			case 3:
				addNewFilm(input);
				break;
			case 4:
				deleteFilm(input);
				break;
			case 5:
				looping = false;
				//Program ends after this line is executed
				break;
			default:
			}
		}
	}
	
	//Displays menu and returns user selection as an int
	private int menu(Scanner input) {
		//Local Variables:
		int menuSelection;
		
		//USER INPUT:
		while (true) {
			System.out.println("+---------------------------------------+");
			System.out.println("|                  Menu                 |");
			System.out.println("+---------------------------------------+");
			System.out.println("| 1. Look up film by its id             |");
			System.out.println("| 2. Look up a film by a search keyword |");
			System.out.println("| 3. Add new film                       |");
			System.out.println("| 4. Delete a film                      |");
			System.out.println("| 5. Exit the application               |");
			System.out.println("+---------------------------------------+");
			System.out.print("+-> Enter a selection: ");
			menuSelection = input.nextInt();

			//VALIDATION:
			if (menuSelection < 1 || menuSelection > 5) {
				System.out.println("Invalid input. Please try again");
			}
			else {
				break;
			}
		}
		return menuSelection;
	}
	
	private void lookUpFilmById(Scanner input) {
		//Local Variables:
		Film film;
		
		while (true) {
			//USER INPUT:
			System.out.print("Enter film ID: ");
			int filmId = input.nextInt();
			
			//Checks to see if ID entered is one that belongs to original database
			if(filmId < 1000) {
				film = db.findFilmById(filmId);
			}
			//Calls different film ID entered is on
			else
				film = db.findUserCreatedFilmById(filmId);
			
			//VALIDATION:
			if (film == null) {
				System.out.println("No Film Found. Please try again.");
			} 
			else
				break;
		}
		System.out.println(film);
		System.out.println();
	}
	
	private void lookUpFilmByKeyword(Scanner input) {
		//Local Variables:
		List<Film> films;
		String keyword;
		
		//USER INPUT:
		System.out.print("Enter film search keyword: ");
		keyword = input.next();
		
		//Finds films by keyword
		films = db.findFilmByKeyword("%" + keyword + "%");
		
		//VALIDATION:
		if(films.size() == 0) {
			System.out.println("\nNo results found.\n");
		}
		
		//Prints out all found films
		for (Film film : films) {
			System.out.println(film.toString());
			System.out.println();
		}
	}
	
	
	private void addNewFilm(Scanner input) {
		//USER INPUT:
		//w/ Local Variables:
		System.out.print("Title: ");
		String title = input.next();
		
		System.out.print("Description: ");
		String description = input.next();
		
		System.out.print("Release Year: ");
		int releaseYear = input.nextInt();
		
		System.out.print("Language ID: ");
		int languageId = input.nextInt();
		
		System.out.print("Rental Rate: ");
		double rentalRate = input.nextDouble();
		
		System.out.print("Length: ");
		int length = input.nextInt();
		
		System.out.print("Replacement Cost: ");
		double replacementCost = input.nextDouble();
		
		System.out.print("Rating: ");
		String rating = input.next();
		
		//Special Features has caused problems in the past...
		//System.out.print("Special Features: ");
		//String specialFeatures = input.next();
		
		//Hard-coded values for problematic properties of films 
			// -> some of these values require data from associative tables (which do not exist for user created films)
		String specialFeatures = null;
		int filmId = 0;
		int rentalDuration = 0;
		List<Actor> cast = null;
		String language = "Blank Language";
		String category = "Blank Category";
		
		//Creates a new film object based on previously defined values
		Film newFilm = new Film(filmId, title, description, releaseYear, languageId, rentalDuration, rentalRate,
						        length, replacementCost, rating, specialFeatures, cast, language, category);
		
		//Creates newly instantiated film in the film database
		db.createFilm(newFilm);	//(comment this out if using debug message below)
		
		//Debug Message for newly created films
		//System.out.println(db.createFilm(newFilm).toString());
	}
	
	private void deleteFilm(Scanner input) {
		
		//USER INPUT:
		System.out.print("Enter film title: ");
		String title = input.next();
		
		//Populates a list of filmIds with any that match the title
		List<Integer> filmIds = db.findFilmIdsByFilmName(title);
		db.deleteFilmsById(filmIds);
		
		//DEBUG: Message that prints out all of the newly created film IDs
//		for(Integer filmId : filmIds) {
//			System.out.println("Film ID: " + filmId);
//		}
		
		//ALTERNATIVE: Method for deleting a film by its title
		//Film film = db.findIdByFilmTitle(title);
		//db.deleteFilm(title);
	}
}
