package app.Services;

import app.Models.Exemplaire;
import app.Models.StatutExemplaire;
import app.Repository.ExemplaireRepository;
import app.Repository.StatutExemplaireRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExemplaireService {
    @Autowired
    private ExemplaireRepository exemplaireRepository;
    private StatutExemplaireRepository statutrep;

    public List<Exemplaire> findAll() {
        return exemplaireRepository.findAll();
    }

    public Exemplaire findById(Integer id) {
        Optional<Exemplaire> exemplaire = exemplaireRepository.findById(id);
        return exemplaire.orElse(null);
    }

    public Exemplaire findByCodeBarre(String codeBarre) {
        return exemplaireRepository.findByCodeBarre(codeBarre);
    }

    public Exemplaire save(Exemplaire exemplaire) {
        return exemplaireRepository.save(exemplaire);
    }

    public void delete(Integer id) {
        exemplaireRepository.deleteById(id);
    }

    public List<Exemplaire> findByLivreId(Integer livreId) {
        return exemplaireRepository.findByLivreId(livreId);
    }

    public List<Exemplaire> findByStatut(StatutExemplaire statut) {
        return exemplaireRepository.findByStatut(statut);
    }
    // @Autowired
    // public ExemplaireService(ExemplaireRepository exemplaireRepository) {
    //     this.exemplaireRepository = exemplaireRepository;
    // }

    // public long countByLivreIdAndStatut(Integer livreId, StatutExemplaire statut) {
    //     return exemplaireRepository.countByLivreIdAndStatut(livreId, statut);
    // }
    @Autowired
    public ExemplaireService(ExemplaireRepository exemplaireRepository) {
        this.exemplaireRepository = exemplaireRepository;
    }

    public long countByLivreIdAndStatut(Integer livreId, StatutExemplaire statut) {
        return exemplaireRepository.countByLivreIdAndStatut(livreId, statut);
    }
    public List<Exemplaire> findDisponiblesByLivreId(Integer livreId) {
    StatutExemplaire disponible = new StatutExemplaire("disponible");
    disponible.setId(1);
    return exemplaireRepository.findByLivreIdAndStatut(livreId, disponible);
}
}