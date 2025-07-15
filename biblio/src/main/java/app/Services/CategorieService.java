package app.Services;

import app.Models.Categorie;
import app.Repository.CategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {
    private final CategorieRepository repo;
    
    public CategorieService(CategorieRepository repo) {
        this.repo = repo;
    }

    public List<Categorie> getAll() {
        return repo.findAll();
    }

    public Optional<Categorie> getById(int id) {
        return repo.findById(id);
    }

    public Categorie save(Categorie c) {
        return repo.save(c);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public Categorie getByNom(String nom) {
        return repo.findByNom(nom);
    }
}