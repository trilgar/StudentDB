package com.stdb.helpers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Trying to assign aspirant status=true to not proper teacher category ")
public class AspStatusException extends RuntimeException{
}
