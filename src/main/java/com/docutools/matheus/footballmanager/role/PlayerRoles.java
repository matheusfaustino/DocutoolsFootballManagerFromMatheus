package com.docutools.matheus.footballmanager.role;

public enum PlayerRoles {
    FORWARDS("FORWARDS"),
	MIDDLE_FIELDERS("MIDDLE FIELDERS"),
	DEFENDERS("DEFENDERS"),
	GOALKEEPER("GOALKEEPER");

	private final String text;

	PlayerRoles(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
