package app.Services;

import app.Models.StatutExemplaire;
import app.Repository.StatutExemplaireRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatutExemplaireService {

    private final StatutExemplaireRepository statutExemplaireRepository;

    @Autowired
    public StatutExemplaireService(StatutExemplaireRepository statutExemplaireRepository) {
        this.statutExemplaireRepository = statutExemplaireRepository;
    }

    public StatutExemplaire findByNom(String nom) {
        return statutExemplaireRepository.findByNom(nom);
    }

    public List<StatutExemplaire> findAll() {
        return statutExemplaireRepository.findAll();
    }
}