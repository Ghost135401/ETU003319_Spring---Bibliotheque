package app.Repository;

import app.Models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
    Categorie findByNom(String nom);
}