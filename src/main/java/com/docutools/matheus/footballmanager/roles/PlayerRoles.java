package com.docutools.matheus.footballmanager.roles;

public enum PlayerRoles {
    FORWARDS("FORWARDS"),
	MIDDLE_FIELDERS("MIDDLE FIELDERS"),
	DEFENDERS("DEFENDERS"),
	GOALKEEERS("GOALKEEERS");

	private final String text;

	PlayerRoles(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
