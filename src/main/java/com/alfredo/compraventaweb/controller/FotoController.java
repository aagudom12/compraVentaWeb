package com.alfredo.compraventaweb.controller;

import ch.qos.logback.core.model.Model;
import com.alfredo.compraventaweb.entity.Anuncio;
import com.alfredo.compraventaweb.repository.AnuncioRepository;
import com.alfredo.compraventaweb.repository.FotoRepository;
import com.alfredo.compraventaweb.service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
public class FotoController {

    @Autowired
    private FotoRepository fotoRepository;
    @Autowired
    private AnuncioRepository anuncioRepository;
    @Autowired
    private FotoService fotoService;

    @GetMapping("/{id1}/fotos/{id2}/delete")
    public String deleteFoto(@PathVariable("id1") Long idAnuncio,
                             @PathVariable("id2") Long idFoto) {
        fotoService.deleteFoto(idFoto);
        return "redirect:/edit/" + idAnuncio ;
    }


    @PostMapping("/edit/{id}/addfoto")
    public String addFoto(@PathVariable("id") Long idFoto, Model model,
                          @RequestParam(value = "archivoFoto") MultipartFile archivoFoto) {
        Optional<Anuncio> anuncioOptional = anuncioRepository.findById(idFoto);
        if(anuncioOptional.isPresent()){
            fotoService.addFoto(archivoFoto, anuncioOptional.get());
        }
        return "redirect:/edit/" + idFoto;
    }

}
