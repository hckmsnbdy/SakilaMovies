package com.pluralsight.models;

public class Actor {
    int actorId;
    String firstName;
    String lastName;

    public int getActorId() {
        return actorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Actor(int actorID, String firstName, String lastName) {
        this.actorId = actorID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("ID: %-4d Name: %s %s", actorId, firstName, lastName);
    }
}
