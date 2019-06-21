package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	
	private final String userName = "student";
	private final String password = "student";
	private static String url = "jdbc:mysql://localhost:3306/sdvid";
	
	
	public Film findFilmById(int filmId) {

		
		String sqltext;
		String sql = "SELECT id FROM film WHERE id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, filmId);
		Connection conn = DriverManager.getConnection(url, userName, password);
		ResultSet fs = pstmt.executeQuery();
		
		System.out.println(pstmt);
		
		if (filmResult.next()) {
			Film film = new Film(); // Create the object
			// Here is our mapping of query columns to our object fields:
			film.setId(fs.getInt(1));
			film.setFilms(findFilmById(filmId)); // An Actor has Films
		}
		fs.close;
		pstmt.close();
		conn.close;
		Film film = null;
	}
	

	@Override
	private final String userName = "student";
	private final String password = "student";
	private static String url = "jdbc:mysql://localhost:3306/sdvid";
	

	public Actor findActorById(int actorId) {
		Actor actor = null;
		// ...
		
		String sqltext;
		
		Connection conn = DriverManager.getConnection(url, userName, password);
		ResultSet fs = pstmt.executeQuery();
		
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor(); // Create the object
			// Here is our mapping of query columns to our object fields:
			actor.setId(actorResult.getInt(1));
			actor.setFirstName(actorResult.getString(2));
			actor.setLastName(actorResult.getString(3));
			actor.setFilms(findActorById(actorId)); // An Actor has Films
		}
		fs.close;
		pstmt.close();
		conn.close;
		Actor actor = null;

	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
			sql += " rental_rate, length, replacement_cost, rating, special_features "
					+ " FROM film JOIN film_actor ON film.id = film_actor.film_id " + " WHERE actor_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int filmId = rs.getInt(1);
				String title = rs.getString(2);
				String desc = rs.getString(3);
				short releaseYear = rs.getShort(4);
				int langId = rs.getInt(5);
				int rentDur = rs.getInt(6);
				double rate = rs.getDouble(7);
				int length = rs.getInt(8);
				double repCost = rs.getDouble(9);
				String rating = rs.getString(10);
				String features = rs.getString(11);
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features);
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
