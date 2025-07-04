CREATE DATABASE web_dyn;
use web_dyn;


CREATE TABLE spring_mvc_p1_crud_categorie_produit(
    id_categorie_produit INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(25),
    descri TEXT 
);

CREATE TABLE spring_mvc_p1_crud_produit(
    id_produit INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(25),
    prix DECIMAL(10,2),
    descri TEXT,
    id_categorie_produit INT ,
    FOREIGN KEY (id_categorie_produit) REFERENCES spring_mvc_p1_crud_categorie_produit(id_categorie_produit)
);