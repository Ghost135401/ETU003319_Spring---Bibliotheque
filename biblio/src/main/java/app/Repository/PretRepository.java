package app.Repository;

import app.Models.Pret;
import app.Models.StatutPret;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
    List<Pret> findByAdherentId(Integer adherentId);
    List<Pret> findByExemplaireId(Integer exemplaireId);
    List<Pret> findByStatutId(Integer statutId);
    
    @Query("SELECT p FROM Pret p WHERE p.dateRetourPrevue < ?1 AND p.dateRetourEffective IS NULL")
    List<Pret> findLateReturns(LocalDate currentDate);
    
    List<Pret> findByDateEmpruntBetween(LocalDate startDate, LocalDate endDate);
    List<Pret> findByDateRetourEffectiveBetween(LocalDate startDate, LocalDate endDate);
   
    List<Pret> findByAdherentIdAndDateRetourEffectiveIsNull(Integer adherentId);
    List<Pret> findByAdherentIdAndDateRetourEffectiveIsNotNullOrderByDateEmpruntDesc(Integer adherentId);
    @Query("SELECT COUNT(p) FROM Pret p WHERE p.dateRetourEffective IS NULL")
    long countActivePrets();
      @Query("SELECT DISTINCT p FROM Pret p " +
               "JOIN FETCH p.exemplaire e " +
               "JOIN FETCH e.livre l " +
               "LEFT JOIN FETCH l.auteurs a " +
               "WHERE p.adherent.id = :adherentId AND p.dateRetourEffective IS NULL")
        List<Pret> findByAdherentWithDetails(@Param("adherentId") Integer adherentId);

}