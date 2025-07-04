package app.Repository;

import app.Models.Adherent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdherentRepository extends JpaRepository<Adherent, Integer> {
    
    @Query(value = "SELECT * FROM adherents WHERE email = ?1", nativeQuery = true)
    Adherent findByEmailNative(String email);
    
    Adherent findByEmail(String email);
    
    // Corrected JPQL query (uses entity name, not table name)
    @Query("SELECT COUNT(a) FROM Adherent a WHERE a.statut.id = 1")
    long countActiveAdherents();
    
    // Alternative version if you prefer to join with statut
    @Query("SELECT COUNT(a) FROM Adherent a JOIN a.statut s WHERE s.id = 1")
    long countActiveAdherentsAlternative();
}