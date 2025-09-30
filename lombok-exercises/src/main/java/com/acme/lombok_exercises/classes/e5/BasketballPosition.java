package com.acme.lombok_exercises.classes.e5;

public enum BasketballPosition implements Position {
    PG("Point Guard"),
    SG("Shooting Guard"),
    SF("Small Forward"),
    PF("Power Forward"),
    C("Center");

    private final String name;

    BasketballPosition(String name) {
        this.name = name;
    }

    @Override
    public String getName() { return name; }
}
