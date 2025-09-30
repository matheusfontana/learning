package com.acme.lombok_exercises.classes.e5;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

/**
 * Challenge Tasks:
 *  Implement all classes with proper Lombok annotations
 */
@Builder(toBuilder = true)
@AllArgsConstructor(staticName = "of")
@Getter
public class SportsTeam {
    private final String teamId;
    private final String teamName;
    private final Sport sport;
    private final List<Player> players;
    private final Coach coach;
    @Builder.Default
    private TeamStatus status = TeamStatus.ACTIVE;
    private String homeVenue;

    @Builder(toBuilder = true)
    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class Player {
        private final String playerId;
        private final String firstName;
        private final String lastName;
        private final Position position;
        private int jerseyNumber;
        private PlayerStats stats;
        @Builder.Default
        private boolean isActive = true;
    }

    @Builder(toBuilder = true)
    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class Coach {
        private final String coachId;
        private final String name;
        private final String specialty;
        private int yearsExperience;
    }

    @Builder(toBuilder = true)
    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class SoccerPlayerStats implements PlayerStats {
        @Builder.Default
        private final int season = LocalDate.now().getYear();
        @Builder.Default
        private final int gamesPlayed = 0;
        @Builder.Default
        private final double averageRating = 0.00;
        @Builder.Default
        private final int goals = 0;
        @Builder.Default
        private final int assists = 0;
        @Builder.Default
        private final int yellowCards = 0;
        @Builder.Default
        private final int redCards = 0;
    }

    @Builder(toBuilder = true)
    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class BasketballPlayerStats implements PlayerStats {
        @Builder.Default
        private final int season = LocalDate.now().getYear();
        @Builder.Default
        private final int gamesPlayed = 0;
        @Builder.Default
        private final double averageRating = 0.00;
        @Builder.Default
        private final int totalPoints = 0;
        @Builder.Default
        private final int threePointFieldGoals = 0;
        @Builder.Default
        private final int twoPointFieldGoals = 0;
        @Builder.Default
        private final int rebounds = 0;
        @Builder.Default
        private final int steals = 0;
    }
}
