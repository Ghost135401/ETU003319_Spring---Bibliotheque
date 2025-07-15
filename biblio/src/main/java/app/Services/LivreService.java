package app.Services;

import app.Models.Livre;
import app.Repository.LivreRepository;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LivreService {
    private final LivreRepository repo;
    
    public LivreService(LivreRepository repo) {
        this.repo = repo;
    }

    public List<Livre> getAll() {
        return repo.findAll();
    }

    public Livre getById(int livreId) {
        return repo.findById(livreId);
    }
    public List<Livre> findByCategorieId(Integer categorieId) {
    return repo.findByCategorie_Id(categorieId);
}
    public Livre save(Livre livre) {
        if (livre.getLangue() == null) {
            livre.setLangue("français");
        }
        livre.setUpdatedAt(LocalDateTime.now());
        return repo.save(livre);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public List<Livre> searchByTitre(String titre) {
        return repo.findByTitreContainingIgnoreCase(titre);
    }

    public List<Livre> searchByEditeur(String editeur) {
        return repo.findByEditeur(editeur);
    }

    public List<Livre> searchByAnnee(Integer annee) {
        return repo.findByAnneePublication(annee);
    }

    public List<Livre> searchByLangue(String langue) {
        return repo.findByLangue(langue);
    }

    public List<Livre> searchByCategorie(Integer categorieId) {
        return repo.findByCategorie_Id(categorieId);
    }

    public List<Livre> searchByCategorieNom(String nomCategorie) {
        return repo.findByCategorie_Nom(nomCategorie);
    }

    public List<Livre> searchByNiveau(String niveau) {
        return repo.findByNiveau(niveau);
    }

    public List<Livre> searchByMotCle(String motCle) {
        return repo.findByMotCle(motCle);
    }

    public Livre getByIsbn(String isbn) {
        return repo.findByIsbn(isbn);
    }
    public List<Livre> getAllWithAuteurs() {
    List<Livre> livres = repo.findAll();
    // Chargez explicitement les auteurs pour chaque livre
    for (Livre livre : livres) {
        Hibernate.initialize(livre.getNomsAuteurs());
    }
    return livres;
}

// public List<Livre> findByCategorieIdWithAuteurs(Integer categorieId) {
//     List<Livre> livres = repo.findByCategorie_Id(categorieId);
//     for (Livre livre : livres) {
//         Hibernate.initialize(livre.getNomsAuteurs());
//     }
//     return livres;
// }


    public long countByCategorieId(Integer categorieId) {
        return repo.countByCategorieId(categorieId);
    }
public List<Livre> findAllWithAuteurs() {
    return repo.findAllWithAuteurs();
}

public List<Livre> findByCategorieIdWithAuteurs(Integer categorieId) {
    return repo.findByCategorieIdWithAuteurs(categorieId);
}
}