package app.Services;

import app.Models.Pret;
import app.Models.StatutPret;
import app.Repository.PretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PretService {
    @Autowired
    private PretRepository pretRepository;

    public List<Pret> findAll() {
        return pretRepository.findAll();
    }

    public Pret findById(Integer id) {
        return pretRepository.findById(id).orElse(null);
    }

    public long countActivePrets() {
    return pretRepository.countActivePrets();
}

    public List<Pret> findByAdherent(Integer adherentId) {
        return pretRepository.findByAdherentId(adherentId);
    }

    public List<Pret> findByExemplaire(Integer exemplaireId) {
        return pretRepository.findByExemplaireId(exemplaireId);
    }

    public List<Pret> findByStatut(StatutPret statut) {
        return pretRepository.findByStatutId(statut.getId());
    }

    public List<Pret> findLateReturns() {
        return pretRepository.findLateReturns(LocalDate.now());
    }

    public Pret save(Pret pret) {
        return pretRepository.save(pret);
    }

    public void delete(Integer id) {
        pretRepository.deleteById(id);
    }

    public Pret prolongerPret(Integer pretId, LocalDate nouvelleDate) {
        Pret pret = findById(pretId);
        if (pret != null) {
            pret.setDateRetourPrevue(nouvelleDate);
            pret.setNbProlongations(pret.getNbProlongations() + 1);
            return save(pret);
        }
        return null;
    }

    public Pret retournerPret(Integer pretId) {
        Pret pret = findById(pretId);
        if (pret != null) {
            pret.setDateRetourEffective(LocalDate.now());
            return save(pret);
        }
        return null;
    }

    public List<Pret> findBetweenDates(LocalDate start, LocalDate end) {
        return pretRepository.findByDateEmpruntBetween(start, end);
    }

       public List<Pret> getHistorique(int id) {
        return pretRepository.getHistorique(id);
    }

}