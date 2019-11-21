package com.docutools.matheus.footballmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You can't pass firs team and benched parameters if it isn't a player role")
public class FirstTeamAndBenchedOptionsNotAllowedException extends RuntimeException {
}
