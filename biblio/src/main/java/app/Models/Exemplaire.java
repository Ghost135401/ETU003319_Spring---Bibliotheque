package app.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exemplaires")
public class Exemplaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "livre_id")
    private Livre livre;

    @Column(name = "code_barre", unique = true, nullable = false)
    private String codeBarre;

    private String localisation;

    @ManyToOne
    @JoinColumn(name = "statut_id", nullable = false)
    private StatutExemplaire statut;

    @Column(name = "type_pret")
    private String typePret = "standard";

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Exemplaire() {}

    public Exemplaire(Livre livre, String codeBarre, String localisation, 
                     StatutExemplaire statut, String typePret) {
        this.livre = livre;
        this.codeBarre = codeBarre;
        this.localisation = localisation;
        this.statut = statut;
        this.typePret = typePret;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }

    public String getCodeBarre() { return codeBarre; }
    public void setCodeBarre(String codeBarre) { this.codeBarre = codeBarre; }

    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }

    public StatutExemplaire getStatut() { return statut; }
    public void setStatut(StatutExemplaire statut) { this.statut = statut; }

    public String getTypePret() { return typePret; }
    public void setTypePret(String typePret) { this.typePret = typePret; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}