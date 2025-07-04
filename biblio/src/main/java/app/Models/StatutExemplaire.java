package app.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "statut_exemplaire")
public class StatutExemplaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 20)
    private String nom;

    public StatutExemplaire() {}

    public StatutExemplaire(String nom) {
        this.nom = nom;
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}