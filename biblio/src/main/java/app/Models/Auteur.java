package app.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

@Entity
@Table(name = "auteurs")
public class Auteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 100)
    private String prenom;

    @Column(columnDefinition = "TEXT")
    private String biographie;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Constructeurs
    public Auteur() {
        this.createdAt = LocalDateTime.now();
    }

    public Auteur(String nom, String prenom, String biographie) {
        this.nom = nom;
        this.prenom = prenom;
        this.biographie = biographie;
        this.createdAt = LocalDateTime.now();
    }

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getBiographie() { return biographie; }
    public void setBiographie(String biographie) { this.biographie = biographie; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Méthode utilitaire
    public String getNomComplet() {
        return (prenom != null ? prenom + " " : "") + nom;
    }


    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL, orphanRemoval = true)
private List<LivreAuteur> livreAuteurs = new ArrayList<>();

public List<Livre> getLivres() {
    return livreAuteurs.stream()
            .map(LivreAuteur::getLivre)
            .collect(Collectors.toList());
}
}