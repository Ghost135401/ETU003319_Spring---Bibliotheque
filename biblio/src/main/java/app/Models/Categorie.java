package app.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "categories")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean reservable = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructeurs
    public Categorie() {
        this.createdAt = LocalDateTime.now();
    }

    public Categorie(String nom, String description, Boolean reservable) {
        this.nom = nom;
        this.description = description;
        this.reservable = reservable;
        this.createdAt = LocalDateTime.now();
    }

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getReservable() { return reservable; }
    public void setReservable(Boolean reservable) { this.reservable = reservable; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}