package com.alfredo.compraventaweb.service;

import com.alfredo.compraventaweb.entity.Anuncio;
import com.alfredo.compraventaweb.entity.Foto;
import com.alfredo.compraventaweb.entity.Usuario;
import com.alfredo.compraventaweb.repository.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnuncioService {
    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private FotoService fotoService;

    public List<Anuncio> obtenerAnuncios() {
        return anuncioRepository.findAll();
    }

    public List<Anuncio> obtenerAnunciosPorFechaDesc() {
        return anuncioRepository.findAllByOrderByFechaCreacionDesc();
    }

    public void guardarAnuncio(Anuncio anuncio) {
        anuncioRepository.save(anuncio);
    }

    public void eliminarAnuncio(Long id) {
        //anuncioRepository.deleteById(id);
        // Obtener el anuncio con sus fotos
        Anuncio anuncio = anuncioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anuncio no encontrado"));

        // Eliminar las fotos asociadas
        List<Foto> fotos = anuncio.getFotos();
        for (Foto foto : fotos) {
            fotoService.deleteFoto(foto.getId());
        }

        // Eliminar el anuncio de la base de datos
        anuncioRepository.deleteById(id);
    }

    public Optional<Anuncio> obtenerAnuncioPorId(Long id) {
        return anuncioRepository.findById(id);
    }

    public long obtenerCantidadAnuncios() {
        return anuncioRepository.count();
    }

    public List<Anuncio> obtenerAnunciosPorUsuario(Usuario usuario) {
        return anuncioRepository.findByUsuario(usuario);
    }

}
