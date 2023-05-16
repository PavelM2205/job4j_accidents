package ru.job4j.accidents.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "types")
public class AccidentType {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

}
