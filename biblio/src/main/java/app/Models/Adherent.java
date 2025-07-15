package app.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "adherents")
public class Adherent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;

    private LocalDate date_naissance;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "profil_id")
    private Profil profil;

    @ManyToOne
    @JoinColumn(name = "statut_id")
    private Statut statut;

    public int getAge(){
        return Period.between(date_naissance, LocalDate.now()).getYears();
    }

    public int nombrePret(List<Pret> pret){
        int result=0;
        for (int i = 0; i < pret.size(); i++) {
            if(this.id==pret.get(i).getAdherent().getId()){
                if(pret.get(i).getStatut().getId()==1){
                    result++;
                }
            }
        }
        return result;
    }

    public Boolean estPenalise(List<Penalite> penalites){
        Boolean t=false;
         for (int i = 0; i < penalites.size(); i++) {
            if(penalites.get(i).getAdherent().getId()==this.getId() && penalites.get(i).getStatut().equals("ACTIVE")){
                LocalDate maintenant=LocalDate.now();
                LocalDate avant=penalites.get(i).getDateApplication();
                LocalDate apres=penalites.get(i).getDateLevee();
                if((maintenant.isAfter(avant) || maintenant.equals(avant)) && (maintenant.isBefore(apres) || maintenant.equals(apres))){
                    t=true;
                }
            }
        }
        return t;
    }



    public Adherent() {}

    public Adherent(String nom, String prenom, String email, String telephone, String adresse,
                    LocalDate date_naissance, Profil profil, String motDePasse, Statut statut) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.date_naissance = date_naissance;
        this.profil = profil;
        this.motDePasse = motDePasse;
        this.statut = statut;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public LocalDate getDate_naissance() { return date_naissance; }
    public void setDate_naissance(LocalDate date_naissance) { this.date_naissance = date_naissance; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Profil getProfil() { return profil; }
    public void setProfil(Profil profil) { this.profil = profil; }

    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }
    @OneToMany(mappedBy = "adherent")
    private List<Reservation> reservations;
} 