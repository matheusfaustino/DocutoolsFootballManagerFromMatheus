package com.docutools.matheus.footballmanager.role;

public enum CoachRoles {
    HEAD_COACH("HEAD COACH"),
	VICE_COACH("VICE COACH"),
	ATTACH_TRAINEER("ATTACH TRAINEER"),
	DEFENCE_TRAINEER("DEFENCE TRAINEER"),
	GOALKEEPER_TRAINEER("GOALKEEPER TRAINEER");

	private final String text;

    CoachRoles(final String text) {
    	this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
