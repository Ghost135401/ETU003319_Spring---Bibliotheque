package app.Repository;

import app.Models.LivreAuteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LivreAuteurRepository extends JpaRepository<LivreAuteur, Integer> {
    List<LivreAuteur> findByLivreId(Integer livreId);
    List<LivreAuteur> findByAuteurId(Integer auteurId);
    List<LivreAuteur> findByRole(String role);
    boolean existsByLivreIdAndAuteurId(Integer livreId, Integer auteurId);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM LivreAuteur la WHERE la.livre.id = :livreId AND la.auteur.id = :auteurId")
    void deleteByLivreAndAuteur(Integer livreId, Integer auteurId);
    
    @Query("SELECT la FROM LivreAuteur la WHERE la.livre.id = :livreId AND la.auteur.id = :auteurId")
    LivreAuteur findByLivreAndAuteur(Integer livreId, Integer auteurId);
}