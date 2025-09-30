package com.acme.lombok_exercises.classes.e3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tasks:
 * Create a registration request with 2 users using mixed patterns
 * Update one user’s email using toBuilder
 * Add a new permission to a user using toBuilder
 * Create a copy of the entire request with different permissions
 */
class UserRegistrationRequestTest {

    @Test
    void shouldCreateRegistrationRequestWithTwoUsers_mixingPatterns() {
        final UserRegistrationRequest registrations = createBaseRegistrationRequest();

        assertThat(registrations).isNotNull();
        assertThat(registrations.getUserProfiles().size()).isEqualTo(2);

        UserRegistrationRequest.UserProfile jonny = registrations.getUserProfiles().getFirst();
        assertThat(jonny.getUsername()).isEqualTo("jonny");
        assertThat(jonny.getPersonalInfo().getEmail()).isEqualTo("jcats@cats.com");

        UserRegistrationRequest.UserProfile matt = registrations.getUserProfiles().get(1);
        assertThat(matt.getUsername()).isEqualTo("matt");
        assertThat(matt.getPersonalInfo().getEmail()).isEqualTo("mdogs@dogs.com");
    }

    @Test
    void updateOneUserEmailUsingToBuilder() {
        final UserRegistrationRequest registrations = createBaseRegistrationRequest();

        UserRegistrationRequest updatedRegistrations = updateUserData(registrations, 0, user -> {
            final UserRegistrationRequest.PersonalInfo personalInfo = user.getPersonalInfo();
            return user.toBuilder().personalInfo(personalInfo.toBuilder().email("jonnycats@cats.com").build()).build();
        });

        assertThat(updatedRegistrations.getUserProfiles().getFirst().getPersonalInfo().getEmail())
                .isEqualTo("jonnycats@cats.com");
    }

    @Test
    void addNewPermissionToUserUsingToBuilder() {
        final UserRegistrationRequest registrations = createBaseRegistrationRequest();
        final UserRegistrationRequest.Permission permissionNew = UserRegistrationRequest.Permission.builder()
                .active(true)
                .permissionId("MATT_ADM01")
                .scope("ROOT_USER")
                .build();

        UserRegistrationRequest updatedPermission = updateUserData(registrations, 1, user -> {
            final List<UserRegistrationRequest.Permission> permissionList = new ArrayList<>(user.getPermissions());
            permissionList.add(permissionNew);
            return user.toBuilder().permissions(permissionList).build();
        });

        assertThat(updatedPermission.getUserProfiles().get(1).getPermissions()).contains(permissionNew);
    }

    @Test
    void shouldCreateCopyEntireRequestDifferentPermissionsForBothUsers() {
        final UserRegistrationRequest registrationRequest = createBaseRegistrationRequest();
        final UserRegistrationRequest.Permission newPermissionJonny = UserRegistrationRequest.Permission.of(
                "JONNY_CEO1",
                "CEO_RULESET",
                true
        );
        final UserRegistrationRequest.Permission newPermissionMatt = UserRegistrationRequest.Permission.builder()
                .permissionId("MATT_TEAM_PRINCIPAL")
                .scope("TEAM_PRINCIPAL")
                .active(true).build();

        UserRegistrationRequest updatedPermissions = registrationRequest.toBuilder().userProfiles(
                List.of(
                        registrationRequest.getUserProfiles().get(0).toBuilder().permissions(
                                List.of(newPermissionJonny)).build(),
                        registrationRequest.getUserProfiles().get(1).toBuilder().permissions(
                                List.of(newPermissionMatt)
                        ).build()
                )
        ).build();

        assertThat(updatedPermissions.getUserProfiles().get(0).getPermissions()).contains(newPermissionJonny);
        assertThat(updatedPermissions.getUserProfiles().get(1).getPermissions()).contains(newPermissionMatt);

        assertThat(updatedPermissions.getUserProfiles().get(0).getPermissions()).hasSize(1);
        assertThat(updatedPermissions.getUserProfiles().get(1).getPermissions()).hasSize(1);

    }

    private UserRegistrationRequest createBaseRegistrationRequest() {
        final UserRegistrationRequest.UserProfile user1 = UserRegistrationRequest.UserProfile.builder()
                .username("jonny")
                .personalInfo(UserRegistrationRequest.PersonalInfo.builder()
                        .firstName("Jonny")
                        .lastName("Cats")
                        .email("jcats@cats.com")
                        .phoneNumber("+1999999999999")
                        .build()
                )
                .permissions(List.of(UserRegistrationRequest.Permission.builder()
                        .permissionId("JONNY_1")
                        .scope("ADMIN")
                        .active(true)
                        .build()
                ))
                .build();

        final UserRegistrationRequest.UserProfile user2 = UserRegistrationRequest.UserProfile.of(
                "matt",
                UserRegistrationRequest.PersonalInfo.of(
                        "Matt",
                        "Dogs",
                        "mdogs@dogs.com",
                        "+17777272727"),
                List.of(UserRegistrationRequest.Permission.of(
                        "MATT_1",
                        "MANAGER",
                        true
                ))
        );

        return UserRegistrationRequest.builder()
                .userProfiles(List.of(user1, user2))
                .build();
    }

    private UserRegistrationRequest updateUserData(UserRegistrationRequest request,
                                                   int userIndex,
                                                   Function<UserRegistrationRequest.UserProfile, UserRegistrationRequest.UserProfile> userProfileFunction) {
        UserRegistrationRequest.UserProfile user = request.getUserProfiles().get(userIndex);
        UserRegistrationRequest.UserProfile updatedUser = userProfileFunction.apply(user);

        List<UserRegistrationRequest.UserProfile> updatedUsers = new ArrayList<>(request.getUserProfiles());
        updatedUsers.set(userIndex, updatedUser);

        return request.toBuilder().userProfiles(updatedUsers).build();
    }
}