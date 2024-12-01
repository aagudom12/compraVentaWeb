package com.alfredo.compraventaweb.service;

import com.alfredo.compraventaweb.entity.Usuario;
import com.alfredo.compraventaweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .build();

    }

    public Optional<Usuario> comprobarUsuario(Usuario usuario) {
        return usuarioRepository.findByEmail(usuario.getEmail());
    }

    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> comprobarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
