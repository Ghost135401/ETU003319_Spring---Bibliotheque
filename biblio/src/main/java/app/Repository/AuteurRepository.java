package app.Repository;

import app.Models.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuteurRepository extends JpaRepository<Auteur, Integer> {
    List<Auteur> findByNomContainingIgnoreCase(String nom);
    List<Auteur> findByPrenomContainingIgnoreCase(String prenom);
    List<Auteur> findByNomContainingOrPrenomContainingAllIgnoreCase(String nom, String prenom);
}