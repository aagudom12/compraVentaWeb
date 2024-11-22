package com.alfredo.compraventaweb.controller;

import com.alfredo.compraventaweb.entity.Anuncio;
import com.alfredo.compraventaweb.service.AnuncioService;
import com.alfredo.compraventaweb.service.FotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class AnuncioController {

    private AnuncioService anuncioService;
    private FotoService fotoService;

    @Autowired
    public AnuncioController(AnuncioService anuncioService, FotoService fotoService) {
        this.anuncioService = anuncioService;
        this.fotoService = fotoService;
    }

    @GetMapping("/")
    public String listado(Model model) {
        model.addAttribute("anuncios", anuncioService.obtenerAnunciosPorFechaDesc());
        model.addAttribute("cantidadAnuncios", anuncioService.obtenerCantidadAnuncios());
        return "anuncio-list";
    }

    @GetMapping("/insertar")
    public String insertar(Model model) {
        model.addAttribute("anuncio", new Anuncio());
        return "anuncio-new";
    }

    @PostMapping("/insertar")
    public String procesarInsertar(@Valid Anuncio anuncio, BindingResult bindingResult, @RequestParam(value = "archivosFotos", required = false) List<MultipartFile> fotos, Model model) {
        if(bindingResult.hasErrors()){
            return "anuncio-new";
        }

        //Guardar fotos
        try {
            fotoService.guardarFotos(fotos, anuncio);
        }catch (IllegalArgumentException ex) {
            model.addAttribute("mensaje", ex.getMessage());
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

    @GetMapping("/edit/{id}")
    public String editar(Model model, @PathVariable Long id) {
        Optional<Anuncio> anuncio = anuncioService.obtenerAnuncioPorId(id);
        if(anuncio.isPresent()){
            model.addAttribute("anuncio", anuncio.get());
            return "anuncio-edit";
        }
        return "redirect:/";
    }

    @PostMapping("/edit/{id}")
    public String procesarEditar(@Valid Anuncio anuncio, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "anuncio-edit";
        }

        anuncioService.guardarAnuncio(anuncio);
        return "redirect:/";
    }
}
