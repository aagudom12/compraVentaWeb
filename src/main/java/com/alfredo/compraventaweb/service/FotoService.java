package com.alfredo.compraventaweb.service;

import com.alfredo.compraventaweb.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FotoService {
    @Autowired
    private FotoRepository repository;
}
