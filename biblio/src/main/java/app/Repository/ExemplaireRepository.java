package app.Repository;

import app.Models.Exemplaire;
import app.Models.StatutExemplaire;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    Exemplaire findByCodeBarre(String codeBarre);
    List<Exemplaire> findByLivreId(Integer livreId);
    List<Exemplaire> findByStatut(StatutExemplaire statut);
    Exemplaire findById(int id); // Ajoutez cette méthode
    @Query("SELECT COUNT(e) FROM Exemplaire e WHERE e.livre.id = :livreId AND e.statut = :statut")
    long countByLivreIdAndStatut(Integer livreId, StatutExemplaire statut);
     List<Exemplaire> findByLivreIdAndStatut(Integer livreId,StatutExemplaire statut);
}