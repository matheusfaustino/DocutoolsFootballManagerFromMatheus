package com.docutools.matheus.footballmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Maximum of Head Coach reached on the team")
public class MaximumHeadCoachReachedException extends RuntimeException {
}
