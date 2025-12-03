package com.pluralsight.models;

public class Film {
    public Film(int filmId, String title, String description, int releaseYear, int length) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
    }

    int filmId;
    String title;
    String description;
    int releaseYear;
    int length;

    public int getFilmId() {
        return filmId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return String.format("ID: %-4d Title: %s (%d) [%d min]",
                filmId, title, releaseYear, length);
    }
}
