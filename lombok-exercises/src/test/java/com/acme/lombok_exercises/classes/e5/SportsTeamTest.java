package com.acme.lombok_exercises.classes.e5;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/* Challenge task:
 *  Create a complete soccer team using mixed patterns
 *  Update team home venue using toBuilder
 *  Modify player stats using toBuilder after a game
 *  Add new players to existing team roster
 *  Update coach information using toBuilder
 */
class SportsTeamTest {
    private static SportsTeam.Coach coach;
    private static List<SportsTeam.Player> players;

    @Nested
    class SoccerTeamTests {
        private static final SportsTeam soccerTeam = setUpClubAmerica();
        private static SportsTeam updatedAmericaRoster;

        @Test
        void shouldCreateCompleteSoccerTeamUsingMixedPatterns() {
            assertThat(soccerTeam).isNotNull();
            assertThat(soccerTeam.getSport()).isEqualTo(Sport.SOCCER);
            assertThat(soccerTeam.getStatus()).isEqualTo(TeamStatus.ACTIVE);
            assertThat(soccerTeam.getHomeVenue()).isEqualTo("Estadio Azteca");
            assertThat(soccerTeam.getCoach()).isNotNull();
            assertThat(soccerTeam.getCoach().getName()).isEqualTo("André Jardine");
            assertThat(soccerTeam.getPlayers()).isNotNull();
            assertThat(soccerTeam.getPlayers()).hasSize(18);
        }

        @Test
        void shouldUpdateSoccerTeamHomeVenueUsingToBuilder() {
            SportsTeam updatedAmerica = soccerTeam.toBuilder()
                    .homeVenue("Estadio Ciudad de los Deportes")
                    .build();

            assertThat(updatedAmerica).isNotNull();
            assertThat(updatedAmerica).usingRecursiveComparison()
                    .ignoringFields("homeVenue")
                    .isEqualTo(soccerTeam);
        }

        @Test
        void shouldAddNewSoccerPlayersToExistingTeamRoster() {
            List<SportsTeam.Player> americaWithExtraPlayers = new ArrayList<>(
                    soccerTeam.getPlayers()
            );
            americaWithExtraPlayers.addAll(getAdditionalAmericaPlayers());

            updatedAmericaRoster = soccerTeam.toBuilder().players(
                    americaWithExtraPlayers
            ).build();

            assertThat(updatedAmericaRoster).isNotNull();
            assertThat(updatedAmericaRoster.getPlayers()).isNotNull();
            assertThat(updatedAmericaRoster.getPlayers()).hasSize(
                    soccerTeam.getPlayers().size() +
                            getAdditionalAmericaPlayers().size());
        }

        @Test
        void shouldModifySoccerPlayerStatsUsingToBuilderAfterGame() {
            List<SportsTeam.Player> sortedPlayers = updatedAmericaRoster.getPlayers().stream()
                    .sorted((p1, p2) -> ((SoccerPosition) p1.getPosition()).compareTo((SoccerPosition) p2.getPosition()))
                    .toList();

            Map<String, Double> ratingsList = setUpClubAmericaRatings();
            /* my previous code
            List<SportsTeam.Player> modifiedPlayers = new ArrayList<>();
            sortedPlayers.forEach(player -> {
                Optional<Double> rating = Optional.ofNullable(ratingsList.get(player.getLastName()));
                SportsTeam.SoccerPlayerStats playerStats = (SportsTeam.SoccerPlayerStats) player.getStats();
                SportsTeam.SoccerPlayerStats modPlayerStats = playerStats.toBuilder()
                        .averageRating(rating.orElse(0.00))
                        .build();
                SportsTeam.Player modPlayer = player.toBuilder().stats(modPlayerStats).build();
                modifiedPlayers.add(modPlayer);
            }); */
            //lambda simplified - amazon q
            List<SportsTeam.Player> modifiedPlayers = sortedPlayers.stream()
                    .map(player -> updatePlayerRating(player, ratingsList))
                    .toList();

            assertThat(modifiedPlayers).isNotNull();
            assertThat(modifiedPlayers).hasSize(23);
            assertThat(modifiedPlayers.getFirst().getStats().getAverageRating()).isGreaterThan(0.00);
        }

        @Test
        void shouldUpdateSoccerCoachInformationUsingToBuilder() {
            final SportsTeam.Coach jardineUpdated = soccerTeam.getCoach().toBuilder()
                    .name("André Soares Jardine")
                    .yearsExperience(20)
                    .build();
            final SportsTeam ameUpdated = soccerTeam.toBuilder()
                    .coach(jardineUpdated)
                    .build();

            assertThat(ameUpdated).isNotNull();
            assertThat(ameUpdated.getCoach().getName()).isEqualTo("André Soares Jardine");
            assertThat(ameUpdated.getCoach().getYearsExperience()).isEqualTo(20);
            assertThat(ameUpdated.getCoach().getCoachId()).isEqualTo(soccerTeam.getCoach().getCoachId());
            assertThat(ameUpdated.getCoach().getSpecialty()).isEqualTo(soccerTeam.getCoach().getSpecialty());

        }

        static SportsTeam setUpClubAmerica() {
            return SportsTeam.builder()
                    .teamId("AME_MEX")
                    .sport(Sport.SOCCER)
                    .teamName("Club America")
                    .homeVenue("Estadio Azteca")
                    .coach(SportsTeam.Coach.of(
                            "BRA_A_JARDINE",
                            "André Jardine",
                            "Manager",
                            18)
                    )
                    .status(TeamStatus.ACTIVE)
                    .players(getClubAmericaRoster())
                    .build();
        }

        static List<SportsTeam.Player> getClubAmericaRoster() {
            final SportsTeam.Player malagon = SportsTeam.Player.builder()
                    .playerId("MEX_L_MALAGON_1")
                    .firstName("Luis Ángel")
                    .lastName("Malagón")
                    .jerseyNumber(1)
                    .position(SoccerPosition.GK)
                    .stats(SportsTeam.SoccerPlayerStats.builder().build())
                    .build();

            final SportsTeam.Player cota = SportsTeam.Player.of(
                    "MEX_R_COTA_30", "Rodolfo", "Cota", SoccerPosition.GK, 30, SportsTeam.SoccerPlayerStats.builder().build(), true);

            final SportsTeam.Player israelReyes = SportsTeam.Player.builder()
                    .playerId("MEX_I_REYES_3")
                    .firstName("Israel")
                    .lastName("Reyes")
                    .jerseyNumber(3)
                    .position(SoccerPosition.DF)
                    .stats(SportsTeam.SoccerPlayerStats.builder().build())
                    .build();

            final SportsTeam.Player lichnovsky = SportsTeam.Player.of(
                    "CHI_I_LICHNOVSKY_31", "Igor", "Lichnovsky", SoccerPosition.DF, 31, SportsTeam.SoccerPlayerStats.builder().build(), true);

            final SportsTeam.Player caceres = SportsTeam.Player.builder()
                    .playerId("URU_S_CACERES_4")
                    .firstName("Sebastián")
                    .lastName("Cáceres")
                    .jerseyNumber(4)
                    .position(SoccerPosition.DF)
                    .stats(SportsTeam.SoccerPlayerStats.builder().build())
                    .build();

            final SportsTeam.Player nestorAraujo = SportsTeam.Player.of(
                    "MEX_N_ARAUJO_14", "Néstor", "Araújo", SoccerPosition.DF, 14, SportsTeam.SoccerPlayerStats.builder().build(), true);

            final SportsTeam.Player kevinAlvarez = SportsTeam.Player.builder()
                    .playerId("MEX_K_ALVAREZ_5")
                    .firstName("Kevin")
                    .lastName("Álvarez")
                    .jerseyNumber(5)
                    .position(SoccerPosition.DF)
                    .stats(SportsTeam.SoccerPlayerStats.builder().build())
                    .build();

            final SportsTeam.Player ramonJuarez = SportsTeam.Player.of(
                    "MEX_R_JUAREZ_29", "Ramón", "Juárez", SoccerPosition.DF, 29, SportsTeam.SoccerPlayerStats.builder().build(), true);

            final SportsTeam.Player cristianBorja = SportsTeam.Player.builder()
                    .playerId("COL_C_BORJA_26")
                    .firstName("Cristian")
                    .lastName("Borja")
                    .jerseyNumber(26)
                    .position(SoccerPosition.DF)
                    .stats(SportsTeam.SoccerPlayerStats.builder().build())
                    .build();

            final SportsTeam.Player alvaroFidalgo = SportsTeam.Player.of(
                    "ESP_A_FIDALGO_8", "Álvaro", "Fidalgo", SoccerPosition.MF, 8, SportsTeam.SoccerPlayerStats.builder().build(), true);

            final SportsTeam.Player jonathanDosSantos = SportsTeam.Player.builder()
                    .playerId("MEX_J_DOS_SANTOS_6")
                    .firstName("Jonathan")
                    .lastName("dos Santos")
                    .jerseyNumber(6)
                    .position(SoccerPosition.MF)
                    .stats(SportsTeam.SoccerPlayerStats.builder().build())
                    .build();

            final SportsTeam.Player zendejas = SportsTeam.Player.of(
                    "USA_A_ZENDEJAS_10", "Alejandro", "Zendejas", SoccerPosition.MF, 10, SportsTeam.SoccerPlayerStats.builder().build(), true);

            final SportsTeam.Player brianRodriguez = SportsTeam.Player.builder()
                    .playerId("URU_B_RODRIGUEZ_7")
                    .firstName("Brian")
                    .lastName("Rodríguez")
                    .jerseyNumber(7)
                    .position(SoccerPosition.MF)
                    .stats(SportsTeam.SoccerPlayerStats.builder().build())
                    .build();

            final SportsTeam.Player erickSanchez = SportsTeam.Player.of(
                    "MEX_E_SANCHEZ_28", "Érick", "Sánchez", SoccerPosition.MF, 28, SportsTeam.SoccerPlayerStats.builder().build(), true);

            final SportsTeam.Player saintMaximin = SportsTeam.Player.builder()
                    .playerId("FRA_A_SAINT_MAXIMIN_97")
                    .firstName("Allan")
                    .lastName("Saint-Maximin")
                    .jerseyNumber(97)
                    .position(SoccerPosition.MF)
                    .stats(SportsTeam.SoccerPlayerStats.builder().build())
                    .build();

            final SportsTeam.Player henryMartin = SportsTeam.Player.of(
                    "MEX_H_MARTIN_9", "Henry", "Martín", SoccerPosition.FW, 9, SportsTeam.SoccerPlayerStats.builder().build(), true);

            final SportsTeam.Player rodrigoAguirre = SportsTeam.Player.builder()
                    .playerId("URU_R_AGUIRRE_27")
                    .firstName("Rodrigo")
                    .lastName("Aguirre")
                    .jerseyNumber(27)
                    .position(SoccerPosition.FW)
                    .stats(SportsTeam.SoccerPlayerStats.builder().build())
                    .build();

            final SportsTeam.Player victorDavila = SportsTeam.Player.of(
                    "CHI_V_DAVILA_11", "Víctor", "Dávila", SoccerPosition.FW, 11, SportsTeam.SoccerPlayerStats.builder().build(), true);

            return java.util.List.of(malagon, cota, israelReyes, lichnovsky, caceres, nestorAraujo,
                    kevinAlvarez, ramonJuarez, cristianBorja, alvaroFidalgo,
                    jonathanDosSantos, zendejas, brianRodriguez, erickSanchez,
                    saintMaximin, henryMartin, rodrigoAguirre, victorDavila);

        }

        static List<SportsTeam.Player> getAdditionalAmericaPlayers() {
            final SportsTeam.Player violante = SportsTeam.Player.builder()
                    .playerId("MEX_I_VIOLANTE_12")
                    .firstName("Illian")
                    .lastName("Violante")
                    .jerseyNumber(12)
                    .position(SoccerPosition.FW)
                    .stats(SportsTeam.SoccerPlayerStats.builder().build())
                    .build();

            final SportsTeam.Player espinoza = SportsTeam.Player.of(
                    "MEX_D_ESPINOZA_34", "Diego", "Espinoza", SoccerPosition.DF, 34, SportsTeam.SoccerPlayerStats.builder().build(), true);

            final SportsTeam.Player gutierrez = SportsTeam.Player.builder()
                    .playerId("MEX_A_GUTIERREZ_20")
                    .firstName("Álex")
                    .lastName("Gutiérrez")
                    .jerseyNumber(20)
                    .position(SoccerPosition.MF)
                    .stats(SportsTeam.SoccerPlayerStats.builder().build())
                    .build();

            final SportsTeam.Player zuniga = SportsTeam.Player.of(
                    "MEX_R_ZUNIGA_19", "Rodrigo", "Zúñiga", SoccerPosition.FW, 19, SportsTeam.SoccerPlayerStats.builder().build(), true);

            final SportsTeam.Player cervantes = SportsTeam.Player.builder()
                    .playerId("MEX_A_CERVANTES_13")
                    .firstName("Alan")
                    .lastName("Cervantes")
                    .jerseyNumber(13)
                    .position(SoccerPosition.MF)
                    .stats(SportsTeam.SoccerPlayerStats.builder().build())
                    .build();

            return List.of(violante, espinoza, gutierrez, zuniga, cervantes);
        }

        static Map<String, Double> setUpClubAmericaRatings() {
            final Map<String, Double> playerRatings = new HashMap<>();
            playerRatings.put("Malagón", 6.91);
            playerRatings.put("Cota", 6.50);
            playerRatings.put("Reyes", 6.98);
            playerRatings.put("Lichnovsky", 6.83);
            playerRatings.put("Cáceres", 6.94);
            playerRatings.put("Álvarez", 6.75);
            playerRatings.put("Juárez", 6.67);
            playerRatings.put("Borja", 6.50);
            playerRatings.put("Fidalgo", 7.14);
            playerRatings.put("dos Santos", 6.83);
            playerRatings.put("Zendejas", 7.42);
            playerRatings.put("Rodríguez", 6.83);
            playerRatings.put("Sánchez", 6.83);
            playerRatings.put("Saint-Maximin", 6.50);
            playerRatings.put("Martín", 6.83);
            playerRatings.put("Aguirre", 6.50);
            playerRatings.put("Dávila", 6.50);
            playerRatings.put("Violante", 6.50);
            playerRatings.put("Espinoza", 6.50);
            playerRatings.put("Gutiérrez", 6.50);
            playerRatings.put("Zúñiga", 6.50);
            playerRatings.put("Cervantes", 6.50);
            return playerRatings;
        }

        private static SportsTeam.Player updatePlayerRating(SportsTeam.Player player, Map<String, Double> ratings) {
            Double rating = ratings.getOrDefault(player.getLastName(), 0.0);
            SportsTeam.SoccerPlayerStats updatedStats = ((SportsTeam.SoccerPlayerStats) player.getStats())
                    .toBuilder()
                    .averageRating(rating)
                    .build();
            return player.toBuilder().stats(updatedStats).build();
        }
    }

    /**
     @Nested class BasketballTeamTests {

     @Test void shouldCreateCompleteBasketballTeamUsingMixedPatterns() {
     }

     @Test void shouldUpdateBasketballTeamHomeVenueUsingToBuilder() {
     }

     @Test void shouldModifyBasketballPlayerStatsUsingToBuilderAfterGame() {
     }

     @Test void shouldAddNewBasketballPlayersToExistingTeamRoster() {
     }

     @Test void shouldUpdateBasketballCoachInformationUsingToBuilder() {
     }
     }
     */
}