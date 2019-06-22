package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private final String userName = "student";
	private final String password = "student";
	private static String url = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	public Film findFilmById(int filmId) {
		Film film = new Film(); 
		String sql = "SELECT film.id, film.title, film.description, film.release_year, film.language_id, film.rental_duration, film.rental_rate,"
				+ " film.length, film.replacement_cost, film.rating, film.special_features, l.name FROM film JOIN language l ON film.language_id = l.id WHERE film.id = ?";
		try {
			Connection conn = DriverManager.getConnection(url, userName, password);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, filmId);
			ResultSet fs = pstmt.executeQuery();

//			System.out.println(pstmt);

			if (fs.next()) {
				film.setId(fs.getInt("id"));			
				film.setTitle(fs.getString("title"));
				film.setDescription(fs.getString("description"));
				film.setReleaseYear(fs.getInt("release_year"));
				film.setLanguageId(fs.getString("language_id"));
				film.setRentalDuration(fs.getInt("rental_duration"));
				film.setRentalRate(fs.getDouble("rental_rate"));
				film.setLength(fs.getDouble("length"));
				film.setReplacementCost(fs.getDouble("replacement_cost"));
				film.setRating(fs.getString("rating"));
				film.setSpecialFeatures(fs.getString("special_features"));
				film.setLanguage(fs.getString("l.name"));
				film.setActors(findActorsByFilmId(filmId));
				
			}
			fs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return film;
	}

	
	public List<Film> findFilmByKeyword(String keyword) {
		List <Film> films = new ArrayList<Film>();
		try {
			Connection conn = DriverManager.getConnection(url, userName, password);
			String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration, rental_rate,"
					+ " length, replacement_cost, rating, special_features, l.name FROM film JOIN language l ON film.language_id = l.id WHERE film.title LIKE ? OR film.description LIKE ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			ResultSet ks = pstmt.executeQuery();

			while (ks.next()){
				Film film = new Film(); 
				film.setId(ks.getInt("id"));			
				film.setTitle(ks.getString("title"));
				film.setDescription(ks.getString("description"));
				film.setReleaseYear(ks.getInt("release_year"));
				film.setLanguageId(ks.getString("language_id"));
				film.setRentalDuration(ks.getInt("rental_duration"));
				film.setRentalRate(ks.getDouble("rental_rate"));
				film.setLength(ks.getDouble("length"));
				film.setReplacementCost(ks.getDouble("replacement_cost"));
				film.setRating(ks.getString("rating"));
				film.setSpecialFeatures(ks.getString("special_features"));
				film.setLanguage(ks.getString("l.name"));
				film.setActors(findActorsByFilmId(film.getId()));
				films.add(film);
				
			}
			ks.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return films;
	}
		
	public Actor findActorById(int actorId) {
		Actor actor = null;
		
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		try {
			Connection conn = DriverManager.getConnection(url, userName, password);
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, actorId);
			ResultSet as = pstmt.executeQuery();
			if (as.next()) {
				actor = new Actor(); // Create the object
				// Here is our mapping of query columns to our object fields:
				actor.setId(as.getInt(1));
				actor.setFirstName(as.getString(2));
				actor.setLastName(as.getString(3));
			}
			as.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return actor;

	}

	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();


		try {
			Connection conn = DriverManager.getConnection(url, userName, password);
			String sql = "select actor.id, actor.first_name, actor.last_name" 
					+ " from actor" + " join film_actor on actor.id = film_actor.actor_id"
					+ " join film on film.id = film_actor.film_id" + " where film.id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, filmId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("actor.id");
				String firstName = rs.getString("actor.first_name");
				String lastName = rs.getString("actor.last_name");
				
				
				Actor actor = new Actor(id, firstName, lastName);
				actors.add(actor);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

}
