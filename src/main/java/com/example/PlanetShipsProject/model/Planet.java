package com.example.PlanetShipsProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private PlanetResource planetResource;
    private Double fuelPrice;
    @OneToMany
    List<SpaceShip> listOfShips;
}


