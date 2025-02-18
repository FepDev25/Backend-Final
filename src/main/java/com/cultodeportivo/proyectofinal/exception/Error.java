package com.cultodeportivo.proyectofinal.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Error {

    private int status;
    private Date date;
    private String message;
    private String error;

    public Error(int status, Date date, String message, String error) {
        this.status = status;
        this.date = date;
        this.message = message;
        this.error = error;
    }

}
