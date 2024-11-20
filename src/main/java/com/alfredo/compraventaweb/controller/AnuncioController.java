package com.alfredo.compraventaweb.controller;

import com.alfredo.compraventaweb.entity.Anuncio;
import com.alfredo.compraventaweb.service.AnuncioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AnuncioController {

    private AnuncioService anuncioService;

    @Autowired
    public AnuncioController(AnuncioService anuncioService) {
        this.anuncioService = anuncioService;
    }

    @GetMapping("/")
    public String listado(Model model) {
        model.addAttribute("anuncios", anuncioService.obtenerAnuncios());
        return "anuncio-list";
    }

    @GetMapping("/insertar")
    public String insertar(Model model) {
        model.addAttribute("anuncio", new Anuncio());
        return "anuncio-new";
    }

    @PostMapping("/insertar")
    public String procesarInsertar(@Valid Anuncio anuncio, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "anuncio-new";
        }

        anuncioService.guardarAnuncio(anuncio);
        return "redirect:/";
    }

    @GetMapping("/del/{id}")
    public String eliminar(@PathVariable Long id) {
        anuncioService.eliminarAnuncio(id);
        return "redirect:/";
    }

    @GetMapping("/view/{id}")
    public String ver(@PathVariable Long id, Model model) {
        Optional<Anuncio> anuncio = anuncioService.obtenerAnuncioPorId(id);
        if(anuncio.isPresent()){
            model.addAttribute("anuncio", anuncio.get());
            return "anuncio-view";
        }
        return "anuncio-view";
    }

}
