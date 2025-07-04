package app.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "statutsPret")
public class StatutPret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String nom;

    public StatutPret() {}

    public StatutPret(String nom) {
        this.nom = nom;
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}