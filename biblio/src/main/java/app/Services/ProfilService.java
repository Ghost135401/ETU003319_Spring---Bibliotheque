package app.Services;

import app.Models.Profil;
import app.Repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfilService {
    @Autowired
    private ProfilRepository profilRepository;

    public List<Profil> getAll() {
        return profilRepository.findAll();
    }

    public Profil getById(Integer id) {
        return profilRepository.findById(id).orElse(null);
    }

    public Profil save(Profil profil) {
        return profilRepository.save(profil);
    }

    public void delete(Integer id) {
        profilRepository.deleteById(id);
    }
}