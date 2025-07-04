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

    private Integer quota_emprunt;
    private Integer duree_emprunt_jours;
    private Integer nb_prolongations_max;
    private Integer quota_reservations;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Profil() {}

 
}