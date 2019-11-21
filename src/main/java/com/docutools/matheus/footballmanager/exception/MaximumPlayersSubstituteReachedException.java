package com.docutools.matheus.footballmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Maximum of players on the bench reached")
public class MaximumPlayersSubstituteReachedException extends RuntimeException {
}
