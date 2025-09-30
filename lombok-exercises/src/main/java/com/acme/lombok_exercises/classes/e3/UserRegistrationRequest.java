package com.acme.lombok_exercises.classes.e3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder(toBuilder = true)
@AllArgsConstructor(staticName = "of")
@Getter
public class UserRegistrationRequest {
    private final List<UserProfile> userProfiles;

    @Builder(toBuilder = true)
    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class UserProfile {
        private final String username;
        private final PersonalInfo personalInfo;
        private List<Permission> permissions;
    }

    @Builder(toBuilder = true)
    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class PersonalInfo {
        private final String firstName;
        private final String lastName;
        private String email;
        private String phoneNumber;
    }

    @Builder(toBuilder = true)
    @AllArgsConstructor(staticName = "of")
    @Getter
    public static class Permission {
        private final String permissionId;
        private final String scope;
        private boolean active;
    }
}
