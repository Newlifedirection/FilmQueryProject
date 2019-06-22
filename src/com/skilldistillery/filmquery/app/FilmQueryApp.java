package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		
		FilmQueryApp app = new FilmQueryApp();
	    app.launch();
//	    app.test();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);
		displayUserMenu(input);
		startUserInterface(input);
	}

	public void displayUserMenu(Scanner input) {
		boolean stayInFilmQuery = true;
		do {
    System.out.println("  *******************************************************  ");
    System.out.println("**                                                       **");
    System.out.println("Welcome to New Generation Films. Please make a selection:");
    System.out.println("1. Film ID");
    System.out.println("2. Film by keyword");
    System.out.println("3. Exit");
    System.out.println("**                                                       **");
    System.out.println("  *******************************************************");
    
    int choice = input.nextInt();
    	input.nextLine();
    	userAction(choice, input);
   
  
	}while(stayInFilmQuery);
		
//    System.out.println("");
  }

	public void userAction(int choice, Scanner input) {
		
	
		switch (choice) {
		
		case 1:
			try {
				System.out.println("1");
				System.out.println("Enter film Id: ");
				int userChoice = input.nextInt();
				input.nextLine();
				Film findFilmById = db.findFilmById(userChoice);
					System.out.println(findFilmById);
					
			} catch (Exception e) {
				input.nextLine();
				System.out.println("Invalid Id");
				
			}
			
			break;
		case 2:
			System.out.println("2");
			System.out.println("Enter Keyword: ");
			String enter = input.nextLine();
			for (Film result : db.findFilmByKeyword(enter)) {
				System.out.println(result);
			}
			if (db.findFilmByKeyword(enter).size()==0)
			System.out.println("Sorry, no matches found ");
			break;
		case 3:
			System.out.println("Good Bye");
			System.exit(0);
			break;
		default:
			System.out.println("Try Again");
			break;
		}
}
private static void Quit() {
		
	}


	private void test() {
		Film film = db.findFilmById(1);
		Actor actor = db.findActorById(1);
		List<Actor> actors = db.findActorsByFilmId(1);
		System.out.println(film.getActors().size());
		System.out.println(actors);
		System.out.println(actor);
	}

	private void startUserInterface(Scanner input) {
		input.close();

	}

}
