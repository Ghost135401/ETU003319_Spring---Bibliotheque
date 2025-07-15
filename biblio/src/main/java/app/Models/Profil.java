package app.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profils")
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String nom;
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    private int quota_emprunt;
    public int getQuota_emprunt() {
        return quota_emprunt;
    }

    public void setQuota_emprunt(int quota_emprunt) {
        this.quota_emprunt = quota_emprunt;
    }

    private int duree_emprunt_jours;
    public int getDuree_emprunt_jours() {
        return duree_emprunt_jours;
    }

    public void setDuree_emprunt_jours(int duree_emprunt_jours) {
        this.duree_emprunt_jours = duree_emprunt_jours;
    }

    private Integer nb_prolongations_max;
    public Integer getNb_prolongations_max() {
        return nb_prolongations_max;
    }

    public void setNb_prolongations_max(Integer nb_prolongations_max) {
        this.nb_prolongations_max = nb_prolongations_max;
    }

    private Integer quota_reservations;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Profil() {}

    // Getters and setters
    // ... (similaires à Adherent)
}