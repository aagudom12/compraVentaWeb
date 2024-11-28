package com.alfredo.compraventaweb.controller;

import com.alfredo.compraventaweb.entity.Anuncio;
import com.alfredo.compraventaweb.entity.Usuario;
import com.alfredo.compraventaweb.service.AnuncioService;
import com.alfredo.compraventaweb.service.CustomUserDetailsService;
import com.alfredo.compraventaweb.service.FotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AnuncioController {

    private AnuncioService anuncioService;
    private FotoService fotoService;
    private CustomUserDetailsService usuarioService;

    @Autowired
    public AnuncioController(AnuncioService anuncioService, FotoService fotoService, CustomUserDetailsService usuarioService) {
        this.anuncioService = anuncioService;
        this.fotoService = fotoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String listado(Model model, Principal principal) {
        model.addAttribute("anuncios", anuncioService.obtenerAnunciosPorFechaDesc());
        model.addAttribute("cantidadAnuncios", anuncioService.obtenerCantidadAnuncios());

        // Si el usuario está autenticado, agrega información adicional al modelo
        if (principal != null) {
            model.addAttribute("usuario", principal.getName());
        }
        return "anuncio-list";
    }

    @GetMapping("/mis-anuncios")
    public String listarMisAnuncios(Model model, Principal principal) {
        String emailUsuario = principal.getName();
        Usuario usuario = usuarioService.comprobarUsuarioPorEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("anuncios", anuncioService.obtenerAnunciosPorUsuario(usuario));
        return "anuncio-list";
    }

    @GetMapping("/insertar")
    public String insertar(Model model) {
        model.addAttribute("anuncio", new Anuncio());
        return "anuncio-new";
    }

    @PostMapping("/insertar")
    public String procesarInsertar(@Valid Anuncio anuncio, BindingResult bindingResult, @RequestParam(value = "archivosFotos", required = false) List<MultipartFile> fotos, Model model, Principal principal) {
        if(bindingResult.hasErrors()){
            return "anuncio-new";
        }

        // Obtener el usuario autenticado
        String emailUsuario = principal.getName();
        Usuario usuario = usuarioService.comprobarUsuarioPorEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Asignar el usuario al anuncio
        anuncio.setUsuario(usuario);
        usuario.getAnuncios().add(anuncio);

        if (usuario.getId() == null) {
            throw new RuntimeException("El usuario autenticado no tiene un ID válido.");
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
    public String eliminar(@PathVariable Long id, Principal principal) {
        // Obtener el usuario autenticado
        String emailUsuario = principal.getName();
        Usuario usuario = usuarioService.comprobarUsuarioPorEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que el anuncio pertenece al usuario
        Anuncio anuncio = anuncioService.obtenerAnuncioPorId(id)
                .orElseThrow(() -> new RuntimeException("Anuncio no encontrado"));

        if (!anuncio.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No tienes permiso para eliminar este anuncio");
        }

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
        return "redirect:/error";
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
    public String procesarEditar(@PathVariable Long id, @Valid Anuncio anuncio, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "anuncio-edit";
        }

        // Recuperar el anuncio original
        Anuncio anuncioOriginal = anuncioService.obtenerAnuncioPorId(id)
                .orElseThrow(() -> new RuntimeException("Anuncio no encontrado"));

        // Verificar que el usuario autenticado es el dueño del anuncio
        String emailUsuario = principal.getName();
        if (!anuncioOriginal.getUsuario().getEmail().equals(emailUsuario)) {
            throw new RuntimeException("No tienes permiso para editar este anuncio");
        }

        // Mantener el usuario original
        anuncio.setUsuario(anuncioOriginal.getUsuario());

        // Guardar el anuncio editado
        anuncioService.guardarAnuncio(anuncio);

        return "redirect:/";
    }

    @GetMapping("/misAnuncios")
    public String verMisAnuncios(Model model, Principal principal) {
        String emailUsuario = principal.getName();
        Usuario usuario = usuarioService.comprobarUsuarioPorEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Anuncio> anuncios = anuncioService.obtenerAnunciosPorUsuario(usuario);
        model.addAttribute("anuncios", anuncios);

        return "anuncio-mios"; // vista para los anuncios del usuario
    }


}
