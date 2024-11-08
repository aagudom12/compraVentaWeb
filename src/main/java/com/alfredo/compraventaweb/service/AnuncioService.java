package com.alfredo.compraventaweb.service;

import com.alfredo.compraventaweb.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnuncioService {
    @Autowired
    private AnuncioRepository anuncioRepository;
}
