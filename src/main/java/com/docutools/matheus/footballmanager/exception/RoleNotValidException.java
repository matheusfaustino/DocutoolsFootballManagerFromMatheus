package com.docutools.matheus.footballmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You should pass a specialized role for this member")
public class RoleNotValidException extends RuntimeException  {
}
