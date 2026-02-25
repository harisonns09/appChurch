package com.church.appChurch.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    TESOUREIRO("TESOUREIRO"),
    LIDER("LIDER"),
    MEMBRO("MEMBRO"),
    KIDS("KIDS");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
