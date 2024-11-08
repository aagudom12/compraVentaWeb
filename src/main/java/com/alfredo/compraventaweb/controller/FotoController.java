package com.alfredo.compraventaweb.controller;

import com.alfredo.compraventaweb.repository.FotoRepository;
import com.alfredo.compraventaweb.service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FotoController {

    private FotoService fotoService;

    @Autowired
    public FotoController(FotoService fotoService) {
        this.fotoService = fotoService;
    }

}
