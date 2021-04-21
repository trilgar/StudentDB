package com.stdb.helpers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Trying to delete entity related to other by foreign key")
public class ForeignKeyViolationException extends RuntimeException{

}
