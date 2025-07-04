package app.Repository;

import app.Models.StatutPret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutPretRepository extends JpaRepository<StatutPret, Integer> {
    StatutPret findByNom(String nom);
}
