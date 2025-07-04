package app.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "livre_auteurs")
public class LivreAuteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    @ManyToOne
    @JoinColumn(name = "auteur_id", nullable = false)
    private Auteur auteur;

    @Column(length = 50)
    private String role = "auteur";

    // Constructeurs
    public LivreAuteur() {}

    public LivreAuteur(Livre livre, Auteur auteur, String role) {
        this.livre = livre;
        this.auteur = auteur;
        this.role = role != null ? role : "auteur";
    }

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }

    public Auteur getAuteur() { return auteur; }
    public void setAuteur(Auteur auteur) { this.auteur = auteur; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}