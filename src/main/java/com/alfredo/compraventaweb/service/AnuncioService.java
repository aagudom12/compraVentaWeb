package com.alfredo.compraventaweb.service;

import com.alfredo.compraventaweb.entity.Anuncio;
import com.alfredo.compraventaweb.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnuncioService {
    @Autowired
    private AnuncioRepository anuncioRepository;

    public List<Anuncio> obtenerAnuncios() {
        return anuncioRepository.findAll();
    }

    public void guardarAnuncio(Anuncio anuncio) {
        anuncioRepository.save(anuncio);
    }

    public void eliminarAnuncio(Long id) {
        anuncioRepository.deleteById(id);
    }

    public Optional<Anuncio> obtenerAnuncioPorId(Long id) {
        return anuncioRepository.findById(id);
    }
}
