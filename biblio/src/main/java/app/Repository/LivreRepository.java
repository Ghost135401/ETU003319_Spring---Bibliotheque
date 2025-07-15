package app.Repository;

import app.Models.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface LivreRepository extends JpaRepository<Livre, Integer> {
    List<Livre> findByTitreContainingIgnoreCase(String titre);
    
    List<Livre> findByEditeur(String editeur);
    
    List<Livre> findByAnneePublication(Integer annee);

    
    Livre findById(int id);
    List<Livre> findByLangue(String langue);
    
    List<Livre> findByCategorie_Id(Integer categorieId);
    
    List<Livre> findAll();
    List<Livre> findByNiveau(String niveau);
    
    @Query("SELECT l FROM Livre l WHERE :motCle MEMBER OF l.motsCles")
    List<Livre> findByMotCle(@Param("motCle") String motCle);
    
    Livre findByIsbn(String isbn);
    
    List<Livre> findByCategorie_Nom(String nomCategorie);
    // Dans LivreRepository.java
@Query("SELECT DISTINCT l FROM Livre l LEFT JOIN FETCH l.auteurs WHERE l.id = :id")
Optional<Livre> findByIdWithAuteurs(@Param("id") Integer id);

@Query("SELECT DISTINCT l FROM Livre l LEFT JOIN FETCH l.auteurs")
List<Livre> findAllWithAuteurs();
 
    @Query("SELECT COUNT(l) FROM Livre l WHERE l.categorie.id = :categorieId")
    long countByCategorieId(@Param("categorieId") Integer categorieId);
    
 @Query("SELECT DISTINCT l FROM Livre l LEFT JOIN FETCH l.auteurs WHERE l.categorie.id = :categorieId")
List<Livre> findByCategorieIdWithAuteurs(@Param("categorieId") Integer categorieId);

}