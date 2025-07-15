package app.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "livres")
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(length = 100)
    private String editeur;

    @Column(name = "annee_publication")
    private Integer anneePublication;

    @Column(columnDefinition = "TEXT")
    private String resume;

    @Column(name = "nb_pages")
    private Integer nbPages;

    @Column(length = 50)
    private String langue = "français";

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @Column(length = 50)
    private String niveau;

    @ElementCollection
    @CollectionTable(name = "livre_mots_cles", joinColumns = @JoinColumn(name = "livre_id"))
    @Column(name = "mot_cle")
    private List<String> motsCles;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructeurs
    public Livre() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Livre(String titre, String isbn, String editeur, Integer anneePublication, 
                String resume, Integer nbPages, String langue, Categorie categorie, 
                String niveau, List<String> motsCles) {
        this.titre = titre;
        this.isbn = isbn;
        this.editeur = editeur;
        this.anneePublication = anneePublication;
        this.resume = resume;
        this.nbPages = nbPages;
        this.langue = langue != null ? langue : "français";
        this.categorie = categorie;
        this.niveau = niveau;
        this.motsCles = motsCles;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getEditeur() { return editeur; }
    public void setEditeur(String editeur) { this.editeur = editeur; }

    public Integer getAnneePublication() { return anneePublication; }
    public void setAnneePublication(Integer anneePublication) { 
        this.anneePublication = anneePublication; 
    }

    public String getResume() { return resume; }
    public void setResume(String resume) { this.resume = resume; }

    public Integer getNbPages() { return nbPages; }
    public void setNbPages(Integer nbPages) { this.nbPages = nbPages; }

    public String getLangue() { return langue; }
    public void setLangue(String langue) { 
        this.langue = langue != null ? langue : "français"; 
    }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }

    public List<String> getMotsCles() { return motsCles; }
    public void setMotsCles(List<String> motsCles) { this.motsCles = motsCles; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }


    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL, orphanRemoval = true)
private List<LivreAuteur> livreAuteurs = new ArrayList<>();

public void addAuteur(Auteur auteur, String role) {
    LivreAuteur livreAuteur = new LivreAuteur(this, auteur, role);
    livreAuteurs.add(livreAuteur);
}

public void removeAuteur(Auteur auteur) {
    livreAuteurs.removeIf(la -> la.getAuteur().equals(auteur));
}

// Dans Livre.java
@ManyToMany
@JoinTable(
    name = "livre_auteurs",
    joinColumns = @JoinColumn(name = "livre_id"),
    inverseJoinColumns = @JoinColumn(name = "auteur_id")
)
private List<Auteur> auteurs;

public List<Auteur> getAuteurs() {
    return auteurs;
}

public void setAuteurs(List<Auteur> auteurs) {
    this.auteurs = auteurs;
}

// Ajoutez ces méthodes
public String getNomsAuteurs() {
    if (auteurs == null || auteurs.isEmpty()) {
        return "Auteur inconnu";
    }
    return auteurs.stream()
            .map(a -> a.getPrenom() + " " + a.getNom())
            .collect(Collectors.joining(", "));
}

public String getPremierAuteur() {
    if (auteurs == null || auteurs.isEmpty()) {
        return "Auteur inconnu";
    }
    Auteur a = auteurs.get(0);
    return a.getPrenom() + " " + a.getNom();
}

public Boolean estDisponible(List<Exemplaire> exemplaires,List<Pret> prets){
    Boolean t=false;
    for (int i = 0; i < exemplaires.size(); i++) {
        if(this.id==exemplaires.get(i).getLivre().getId()){
            if(exemplaires.get(i).estDisponibleDate(prets)){
                t=true;
                break;
            }
        }
        
    }
    return t;
}
public static List<Livre> livredisponible(List<Livre> liv,List<Exemplaire> exemplaires,List<Pret> prets){
    List<Livre> livre=new ArrayList<Livre>();
    for (int i = 0; i < exemplaires.size(); i++) {
        for (int j = 0; j < liv.size(); j++) {
            if(liv.get(j).getId()==exemplaires.get(i).getLivre().getId()){
            if(exemplaires.get(i).estDisponibleDate(prets)){
               Livre resultat=liv.get(j);
               if(!livre.contains(resultat)){
                   livre.add(resultat);

               }
            }
        }
        }
    
        
    }

    return livre;
}
public Exemplaire getDispoExemplaire(List<Exemplaire> exemplaires,List<Pret> prets){
    Exemplaire result=null;
     for (int j = 0; j < exemplaires.size(); j++) {
            if(this.getId()==exemplaires.get(j).getLivre().getId()){
            if(exemplaires.get(j).estDisponibleDate(prets)){
               result=exemplaires.get(j);
               break;
            }
        }
    }
    return result;
}

public List<Exemplaire> getDispoExemplaireList(List<Exemplaire> exemplaires,List<Pret> prets){
    List<Exemplaire> result=new ArrayList<Exemplaire>();
     for (int j = 0; j < exemplaires.size(); j++) {
            if(this.getId()==exemplaires.get(j).getLivre().getId()){
            if(exemplaires.get(j).estDisponibleDate(prets)){
                Exemplaire tmp=exemplaires.get(j);
                result.add(tmp);
            }
        }
    }
    return result;
}
public List<Exemplaire> getExemplaireList(List<Exemplaire> exemplaires,List<Pret> prets){
    List<Exemplaire> result=new ArrayList<Exemplaire>();
     for (int j = 0; j < exemplaires.size(); j++) {
            if(this.getId()==exemplaires.get(j).getLivre().getId()){
                Exemplaire tmp=exemplaires.get(j);
                result.add(tmp);
        }
    }
    return result;
}

}