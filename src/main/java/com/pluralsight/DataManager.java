package com.pluralsight;

import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;
import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private final Connection connection;

    public DataManager(Connection connection) {
        this.connection = connection;
    }

    public static DataSource getDataSource(String user, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
    public List<Actor> findActorsByName(String name) {
        String query = "SELECT actor_id, first_name, last_name " +
                "FROM actor " +
                "WHERE first_name LIKE ? OR last_name LIKE ? " +
                "ORDER BY last_name, first_name";

        List<Actor> actors = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            String pattern = "%" + name + "%";
            statement.setString(1, pattern);
            statement.setString(2, pattern);

            try (ResultSet results = statement.executeQuery()) {

                while (results.next()) {
                    int actorId = results.getInt("actor_id");
                    String firstName = results.getString("first_name");
                    String lastName = results.getString("last_name");

                    Actor actor = new Actor(actorId, firstName, lastName);
                    actors.add(actor);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return actors;
    }
    public List<Film> findFilmsByActorId(int actorId) {
        String query = "SELECT f.film_id, f.title, f.description, f.release_year, f.length " +
                "FROM film f " +
                "JOIN film_actor fa ON f.film_id = fa.film_id " +
                "JOIN actor a ON a.actor_id = fa.actor_id " +
                "WHERE a.actor_id = ? " +
                "ORDER BY f.title";

        List<Film> films = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, actorId);

            try (ResultSet results = statement.executeQuery()) {

                while (results.next()) {
                    int filmId = results.getInt("film_id");
                    String title = results.getString("title");
                    String description = results.getString("description");
                    int releaseYear = results.getInt("release_year");
                    int length = results.getInt("length");

                    Film film = new Film(filmId, title, description, releaseYear, length);
                    films.add(film);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return films;
    }



}
