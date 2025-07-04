package app.Services;

import app.Models.Statut;
import app.Repository.StatutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatutService {
    @Autowired
    private StatutRepository statutRepository;

    public List<Statut> getAll() {
        return statutRepository.findAll();
    }

    public Statut getById(Integer id) {
        return statutRepository.findById(id).orElse(null);
    }

    public Statut save(Statut statut) {
        return statutRepository.save(statut);
    }

    public void delete(Integer id) {
        statutRepository.deleteById(id);
    }
}