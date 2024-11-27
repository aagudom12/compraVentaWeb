package com.alfredo.compraventaweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotNull(message = "El email es obligatorio")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull(message = "La contraseña es obligatoria")
    @Column(length = 500)
    @Size(min = 4, message = "Debe contener al menos 4 caracteres")
    private String password;

    @Size(max = 255, message = "El nombre no puede exceder los 255 caracteres")
    private String nombre;


    @Column(length = 20) // Ejemplo de teléfono con longitud limitada
    private String telefono;

    @Column(length = 100) // Ajusta según las restricciones
    private String poblacion;

    @OneToMany(targetEntity = Anuncio.class, cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Anuncio> anuncios = new ArrayList<>();

}
