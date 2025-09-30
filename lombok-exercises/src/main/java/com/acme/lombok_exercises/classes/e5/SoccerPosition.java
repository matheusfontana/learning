package com.acme.lombok_exercises.classes.e5;

public enum SoccerPosition implements Position {
    GK("Goalkeeper"),
    DF("Defender"),
    MF("Midfielder"),
    FW("Forward");

    private final String name;

    SoccerPosition(String name) {
        this.name = name;
    }

    @Override
    public String getName() { return name; }
}