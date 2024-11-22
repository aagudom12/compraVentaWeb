package com.alfredo.compraventaweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "anuncios")
public class Anuncio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El titulo es obligatorio")
    private String titulo;

    @NotNull(message = "El precio es obligatorio")
    private Double precio;

    private String descripcion;

    @Column(nullable = false,updatable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(targetEntity = Foto.class, cascade = CascadeType.ALL, mappedBy = "anuncio")
    private List<Foto> fotos = new ArrayList<>();

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now(); // Asigna la fecha actual al momento de persistir
    }
}
