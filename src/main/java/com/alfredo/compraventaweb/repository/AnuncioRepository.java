package com.alfredo.compraventaweb.repository;

import com.alfredo.compraventaweb.entity.Anuncio;
import com.alfredo.compraventaweb.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    List<Anuncio> findAllByOrderByFechaCreacionDesc();

    List<Anuncio> findByUsuario(Usuario usuario);
}
