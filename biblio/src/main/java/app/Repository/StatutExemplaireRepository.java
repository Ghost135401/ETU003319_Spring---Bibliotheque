package app.Repository;

import app.Models.StatutExemplaire;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutExemplaireRepository extends JpaRepository<StatutExemplaire, Integer> {
    StatutExemplaire findByNom(String nom);
    List<StatutExemplaire> findAll();
    

}