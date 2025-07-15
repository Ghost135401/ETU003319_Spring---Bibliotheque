package app.Services;

import app.Models.Auteur;
import app.Repository.AuteurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuteurService {
    private final AuteurRepository auteurRepository;

    public AuteurService(AuteurRepository auteurRepository) {
        this.auteurRepository = auteurRepository;
    }

    public List<Auteur> getAllAuteurs() {
        return auteurRepository.findAll();
    }

    public Optional<Auteur> getAuteurById(Integer id) {
        return auteurRepository.findById(id);
    }

    public Auteur saveAuteur(Auteur auteur) {
        return auteurRepository.save(auteur);
    }

    public void deleteAuteur(Integer id) {
        auteurRepository.deleteById(id);
    }

    public List<Auteur> searchByNom(String nom) {
        return auteurRepository.findByNomContainingIgnoreCase(nom);
    }

    public List<Auteur> searchByPrenom(String prenom) {
        return auteurRepository.findByPrenomContainingIgnoreCase(prenom);
    }

    public List<Auteur> searchByNomOrPrenom(String terme) {
        return auteurRepository.findByNomContainingOrPrenomContainingAllIgnoreCase(terme, terme);
    }
}