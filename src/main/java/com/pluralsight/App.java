package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        DataSource dataSource = getDataSource(args[0], args[1]);

        try (Scanner scanner = new Scanner(System.in) ;
             Connection connection = dataSource.getConnection()){

            System.out.print("Enter a last name of an actor you like: ");
            String lastName = scanner.nextLine().trim();

            listActorsByLastName(connection, lastName);

            System.out.print("\nEnter FIRST name of the actor to see their movies: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Enter LAST name of the actor to see their movies: ");
            String filmLastName = scanner.nextLine().trim();

            listFilmsByActor(connection, firstName, filmLastName);
        }
             catch (SQLException e) {
                e.printStackTrace();
            }


    }

    private static DataSource getDataSource(String user, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
    private static void listActorsByLastName(Connection connection, String lastName) {

        String query = "SELECT actor_id, first_name, last_name " +
                "FROM actor " +
                "WHERE last_name = ? " +
                "ORDER BY first_name, last_name";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, lastName);

            try (ResultSet results = statement.executeQuery()) {

                if (results.next()) {
                    System.out.println("\nYour matches are:\n");

                    do {
                        int actorId = results.getInt("actor_id");
                        String first = results.getString("first_name");
                        String last = results.getString("last_name");

                        System.out.printf("ID: %-4d  Name: %s %s%n", actorId, first, last);

                    } while (results.next());
                } else {
                    System.out.println("\nNo matches!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void listFilmsByActor(Connection connection, String firstName, String lastName) {

        String query = "SELECT f.film_id, f.title " +
                "FROM film f " +
                "JOIN film_actor fa ON f.film_id = fa.film_id " +
                "JOIN actor a ON a.actor_id = fa.actor_id " +
                "WHERE a.first_name = ? AND a.last_name = ? " +
                "ORDER BY f.title";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);

            try (ResultSet results = statement.executeQuery()) {

                if (results.next()) {
                    System.out.printf("%nFilms for actor %s %s:%n%n", firstName, lastName);

                    do {
                        int filmId = results.getInt("film_id");
                        String title = results.getString("title");

                        System.out.printf("ID: %-4d  Title: %s%n", filmId, title);

                    } while (results.next());
                } else {
                    System.out.println("\nNo films found for that actor!");
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}