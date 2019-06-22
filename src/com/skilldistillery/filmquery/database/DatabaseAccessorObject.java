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

		String sql = "SELECT id, title, description, release_year, language_id, rental_duration, rental_rate,"
				+ " length, replacement_cost, rating, special_features FROM film WHERE id = ?";
		try {
			Connection conn = DriverManager.getConnection(url, userName, password);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, filmId);
			ResultSet fs = pstmt.executeQuery();

			System.out.println(pstmt);

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
			
			}
			fs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return film;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return actor;

	}

	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();


		try {
			Connection conn = DriverManager.getConnection(url, userName, password);
			String sql = "select actor.id, actor.first_name, actor.last_name" 
					+ "from actor" + "join film_actor on actor.id = film_actor.actor"
					+ "join film on film.id = film_actor.film_id" + "where film.id = ?";
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
