package app.Services;

import app.Models.Adherent;
import app.Repository.AdherentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdherentService {
    private final AdherentRepository repo;
    
    public AdherentService(AdherentRepository repo) {
        this.repo = repo;
    }
    public long countActiveAdherents() {
    return repo.countActiveAdherents();
}
    public List<Adherent> getAll() {
        return repo.findAll();
    }

    public Optional<Adherent> getById(int id) {
        return repo.findById(id);
    }

    public Adherent save(Adherent a) {
        return repo.save(a);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public Adherent getByEmail(String email) {
        return repo.findByEmail(email);
    }
}
