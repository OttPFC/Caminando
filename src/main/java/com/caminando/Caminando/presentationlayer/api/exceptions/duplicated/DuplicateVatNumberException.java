package com.caminando.Caminando.presentationlayer.api.exceptions.duplicated;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class DuplicateVatNumberException extends DuplicateKeyException{

    @Serial
    private static final long serialVersionUID = 1L;


    public DuplicateVatNumberException(String key) {
        super(key, HttpStatus.BAD_REQUEST, "VatNumber duplicato");
    }
}
