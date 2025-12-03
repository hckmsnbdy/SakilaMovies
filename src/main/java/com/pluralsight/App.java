package com.pluralsight;

import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        DataSource dataSource = DataManager.getDataSource(args[0], args[1]);

        try (Scanner scanner = new Scanner(System.in);
             Connection connection = dataSource.getConnection()) {

            DataManager dataManager = new DataManager(connection);

            System.out.print("Search actor by name (first or last): ");
            String nameSearch = scanner.nextLine().trim();

            List<Actor> actors = dataManager.findActorsByName(nameSearch);

            if (actors.isEmpty()) {
                System.out.println("No actors found for that search.");
                return;
            }

            System.out.println("\nActors found:\n");
            for (Actor actor : actors) {
                System.out.printf("ID: %-4d Name: %s %s%n",
                        actor.getActorId(),
                        actor.getFirstName(),
                        actor.getLastName());
            }

            System.out.print("\nEnter an actor ID to see their films: ");
            String idInput = scanner.nextLine().trim();

            int actorId;
            try {
                actorId = Integer.parseInt(idInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid actor ID.");
                return;
            }

            List<Film> films = dataManager.findFilmsByActorId(actorId);

            if (films.isEmpty()) {
                System.out.println("No films found for that actor.");
            } else {
                System.out.println("\nFilms:\n");
                for (Film film : films) {
                    System.out.printf("ID: %-4d Title: %s (%d, %d min)%n",
                            film.getFilmId(),
                            film.getTitle(),
                            film.getReleaseYear(),
                            film.getLength());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}