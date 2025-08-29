package com.tutorial.ia.domain.model;

public enum GoalType {
    SHORT_TERM("Short term"),
    MEDIUM_TERM("Medium term"),
    LONG_TERM("Long term");

    private final String displayName;

    GoalType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
