package com.alfredo.compraventaweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull(message = "El email es obligatorio")
    @Min(4)
    private String password;

    private String nombre;

    private String telefono;

    private String poblacion;

    @OneToMany(targetEntity = Anuncio.class, cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Anuncio> anuncios = new ArrayList<>();

}
