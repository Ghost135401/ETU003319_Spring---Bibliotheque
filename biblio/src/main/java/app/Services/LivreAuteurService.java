package app.Services;

import app.Models.Livre;
import app.Models.Auteur;
import app.Models.LivreAuteur;
import app.Repository.LivreAuteurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreAuteurService {
    private final LivreAuteurRepository livreAuteurRepository;

    public LivreAuteurService(LivreAuteurRepository livreAuteurRepository) {
        this.livreAuteurRepository = livreAuteurRepository;
    }

    public LivreAuteur createAssociation(Livre livre, Auteur auteur, String role) {
        if (livreAuteurRepository.existsByLivreIdAndAuteurId(livre.getId(), auteur.getId())) {
            throw new IllegalStateException("Cette association existe déjà");
        }
        
        LivreAuteur livreAuteur = new LivreAuteur(livre, auteur, role);
        return livreAuteurRepository.save(livreAuteur);
    }

    public void removeAssociation(Integer livreId, Integer auteurId) {
        livreAuteurRepository.deleteByLivreAndAuteur(livreId, auteurId);
    }

    public List<LivreAuteur> getAuteursForLivre(Integer livreId) {
        return livreAuteurRepository.findByLivreId(livreId);
    }

    public List<LivreAuteur> getLivresForAuteur(Integer auteurId) {
        return livreAuteurRepository.findByAuteurId(auteurId);
    }

    public List<LivreAuteur> getByRole(String role) {
        return livreAuteurRepository.findByRole(role);
    }

    public LivreAuteur updateRole(Integer livreId, Integer auteurId, String newRole) {
        LivreAuteur association = livreAuteurRepository.findByLivreAndAuteur(livreId, auteurId);
        if (association == null) {
            throw new IllegalArgumentException("Association non trouvée");
        }
        association.setRole(newRole);
        return livreAuteurRepository.save(association);
    }
}