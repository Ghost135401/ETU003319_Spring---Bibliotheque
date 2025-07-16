package app.Controllers;

import app.Models.Adherent;
import app.Models.Exemplaire;
import app.Models.Inscription;
import app.Models.Livre;
import app.Models.Penalite;
import app.Models.Pret;
import app.Models.Reservation;
import app.Models.StatutExemplaire;
import app.Models.StatutPret;
import app.Repository.ReservationRepository;
import app.Services.AdherentService;
import app.Services.ExemplaireService;
import app.Services.InscriptionService;
import app.Services.JoursFeriesService;
import app.Services.LivreService;
import app.Services.PenaliteService;
import app.Services.PretService;
import app.Services.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BibliotecaireController {
    
    @Autowired
    private PretService pretService;
    
    @Autowired
    private AdherentService adherentService;

    @Autowired
    private LivreService livreService;

    @Autowired
    private ExemplaireService exemplaireService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private JoursFeriesService joursFeriesService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    private InscriptionService inscriptionService;
   


    @GetMapping("/bibliotecaire/acceuil")
    public String acceuil(HttpSession session, Model model) {
    // if (session.getAttribute("userType") == null || !"bibliothecaire".equals(session.getAttribute("userType"))) {
    //     return "redirect:/";
    // }
    
    long nombrePretsActifs = pretService.countActivePrets();
    long nombreAdherentsActifs = adherentService.countAdherentsWithActiveInscription();
    List<Adherent> adherentsActifs = adherentService.getAll();
    List<Livre> livres = livreService.getAll();
    List<Pret> prets = pretService.findAll();
    List<Exemplaire> exemplaires = exemplaireService.findAll();
    
   List<Livre> livreDispo= Livre.livredisponible(livres, exemplaires, prets);

    
    model.addAttribute("nombrePretsActifs", nombrePretsActifs);
    model.addAttribute("nombreAdherentsActifs", nombreAdherentsActifs);
    model.addAttribute("adherentsActifs", adherentsActifs);
    model.addAttribute("livres", livreDispo);
    
    return "bibliotecaire/acceuil";
}
   @PostMapping("bibliotecaire/preter")
    public String PreterLivre(
         @RequestParam("adherent") int adherentId,
         @RequestParam("livre") int livreId,
         @RequestParam("dateDebut") String dateDebut,Model model,
         RedirectAttributes redirectAttributes) {
        Adherent personne=adherentService.getById(adherentId);
        Livre livre=livreService.getById(livreId);
        List<Pret> prets=pretService.findAll();
        List<Exemplaire> exemplaires=exemplaireService.findAll();
        Exemplaire dispo=livre.getDispoExemplaire(exemplaires, prets);
        int days=personne.getProfil().getDuree_emprunt_jours();
        LocalDate dateDebutDate=LocalDate.parse(dateDebut);
        LocalDate dateFin=calculerDateRetour(dateDebutDate, days);
        StatutPret stat=new StatutPret("en_cours");
        int mesPret=personne.nombrePret(prets);
        List<Penalite> penalite=penaliteService.findAll();
        List<Inscription> inscriptions=inscriptionService.findAll();
        
        stat.setId(1);

        if(personne.estPenalise(penalite)){
             redirectAttributes.addFlashAttribute("error", "Sanction non levée");
            return "redirect:/bibliotecaire/acceuil";
        }
          if(!personne.estAbonnee(inscriptions)){
             redirectAttributes.addFlashAttribute("error", "vous etes desabonné");
            return "redirect:/bibliotecaire/acceuil";
        }
        if(personne.getAge() < 18) {
        redirectAttributes.addFlashAttribute("error", "Vous avez moins de 18 ans");
        return "redirect:/bibliotecaire/acceuil";
    }
    if (personne.getProfil().getQuota_emprunt()-1 < mesPret) {
        redirectAttributes.addFlashAttribute("error", "Vous avez dépassé votre quota");
        return "redirect:/bibliotecaire/acceuil";
    }

    Pret nouveau = new Pret(personne, dispo, dateDebutDate, dateFin, stat, "standard");
    pretService.save(nouveau);
    redirectAttributes.addFlashAttribute("succes", "Prêt enregistré avec succès");
    return "redirect:/bibliotecaire/acceuil";
        
    }
    @GetMapping("bibliotecaire/listeReservation")
    public String listeReservations(Model model) {
        List<Reservation> reservations = reservationService.getAll();
        model.addAttribute("reservations", reservations);
        return "bibliotecaire/reservation";
    }
     @GetMapping("bibliotecaire/pret")
    public String listePrets(Model model) {
        List<Pret> prets = pretService.findAll();
        model.addAttribute("prets", prets);
        return "bibliotecaire/pret";
    }
    private LocalDate calculerDateRetour(LocalDate dateDebut, int joursOuvres) {
    LocalDate dateCalcul = dateDebut;
    int joursAjoutes = 0;
    
    while (joursAjoutes < joursOuvres) {
        dateCalcul = dateCalcul.plusDays(1);
    
        if (dateCalcul.getDayOfWeek().getValue() == 7 ) {
            continue;
        }
        
        if (joursFeriesService.estJourFerie(dateCalcul)) {
            continue;
        }
        
        joursAjoutes++;
    }
    
    return dateCalcul;
}

@PostMapping("bibliotecaire/annulerReservation")
public String annulerReservation(@RequestParam("id") Integer id,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {
    try {
    
    
        Reservation reservation = reservationService.findById(id);
        if (reservation == null) {
            redirectAttributes.addFlashAttribute("error", "Réservation introuvable");
            return "redirect:/bibliotecaire/listeReservation";
        }
        reservationService.delete(id);
        
        redirectAttributes.addFlashAttribute("succes", "Réservation #" + id + " annulée avec succès");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Erreur lors de l'annulation: " + e.getMessage());
    }
    return "redirect:/bibliotecaire/listeReservation";
}

@PostMapping("bibliotecaire/confirmer")
public String confirmerReservation(
    @RequestParam("id") Integer reservationId,
    RedirectAttributes redirectAttributes,
    HttpServletRequest request) {
    
    try {
        
        
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new Exception("Réservation non trouvée"));
        
        // 2. Récupérer les objets nécessaires
        Adherent personne = reservation.getAdherent();
        List<Inscription> inscriptions = inscriptionService.findAll();
        Boolean estAbonnee=personne.estAbonnee(inscriptions);
        Exemplaire dispo = reservation.getExemplaire();
        
        
      
        List<Pret> prets = pretService.findAll(); 
        int mesPret = personne.nombrePret(prets); 
        if(!dispo.estDisponibleDate(prets)){
            redirectAttributes.addFlashAttribute("error", "Pas d'exemplaire disponible");
            return "redirect:/bibliotecaire/listeReservation";
        }
        if(!estAbonnee){
            redirectAttributes.addFlashAttribute("error", "vous n'etes pas abonne");
            return "redirect:/bibliotecaire/listeReservation";
        }
        List<Penalite> penalite=penaliteService.findAll();
        if(personne.estPenalise(penalite)){
             redirectAttributes.addFlashAttribute("error", "Sanction non levée");
            return "redirect:/bibliotecaire/acceuil";
        }
        if(personne.getAge() < 18) {
            redirectAttributes.addFlashAttribute("error", "Vous avez moins de 18 ans");
            return "redirect:/bibliotecaire/listeReservation";
        }
        
        if (personne.getProfil().getQuota_emprunt() <= mesPret) {
            redirectAttributes.addFlashAttribute("error", "Vous avez dépassé votre quota");
            return "redirect:/bibliotecaire/listeReservation";
        }
        
        LocalDate dateDebut = reservation.getDateReservation();
        int days = personne.getProfil().getDuree_emprunt_jours();
        LocalDate dateFin = calculerDateRetour(dateDebut, days);
        
        StatutPret stat = new StatutPret("en_cours");
        stat.setId(1);
        
        Pret nouveau = new Pret(personne, dispo, dateDebut, dateFin, stat, "standard");
        pretService.save(nouveau);
        // StatutExemplaire statue=new StatutExemplaire("emprunte");
        // statue.setId(2);
        // dispo.setStatut(statue); 
        // exemplaireService.save(dispo);
    
        reservationRepository.delete(reservation);
        
        redirectAttributes.addFlashAttribute("succes", 
            "Réservation transformée en prêt. Retour avant le " + dateFin);
            
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", 
            "Erreur: " + e.getMessage());
    }
    
    return "redirect:/bibliotecaire/listeReservation";
}

@PostMapping("/bibliotecaire/prolonger")
public String prolongerPret(
    @RequestParam("id") Integer pretId,
    @RequestParam("jours") Integer jours,
    RedirectAttributes redirectAttributes) {
    
    try {
        Pret pret = pretService.findById(pretId);
        Adherent personne = pret.getAdherent();
        int nb_prolongations = pret.getNbProlongations();
        int quota = personne.getProfil().getNb_prolongations_max();
        
        int total = personne.getProfil().getDuree_emprunt_jours();
        List<Pret> prets = pretService.findAll();
        int valeur=personne.countPro(prets);

        
        
         if(quota <= valeur || pret.getNbrpro()==1) {
             redirectAttributes.addFlashAttribute("error", "Quota de prolongement dépassé");
             return "redirect:/bibliotecaire/pret";
         }

        LocalDate nouvelleDate =calculerDateRetour(pret.getDateRetourPrevue(), total);
        pret.setDateRetourPrevue(nouvelleDate);
        pret.setNbrpro(1);
        pretService.save(pret);
        
        redirectAttributes.addFlashAttribute("succes", 
            "Prolongation réussie! Nouvelle date de retour: " + nouvelleDate + 
            " | Jours utilisés: " + total + "/" + quota);
            
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Erreur technique: " + e.getMessage());
    }
    
    return "redirect:/bibliotecaire/pret";
}


@PostMapping("/bibliotecaire/retourner")
public String retournerPret(
    @RequestParam("id") Integer pretId,
    @RequestParam("dateRetour") String dateRetour,
    RedirectAttributes redirectAttributes) {
    
    try {
        Pret pret = pretService.findById(pretId);
        LocalDate rendu=LocalDate.parse(dateRetour);
        pret.setDateRetourEffective(rendu);
        pretService.save(pret);
        Adherent adherent=adherentService.getById(pret.getAdherent().getId());
        int penal=adherent.getProfil().getpenalites();
        LocalDate dateFin=rendu.plusDays(penal);
        if(rendu.isAfter(pret.getDateRetourPrevue())){
            StatutPret statup=new StatutPret("en_retard");
            statup.setId(3);
            pret.setStatut(statup);
            pretService.save(pret);
            Penalite penalite=new Penalite(adherent,pret,rendu,dateFin,"ACTIVE");
            penaliteService.createPenalite(penalite);
            redirectAttributes.addFlashAttribute("succes", 
            "Merci de votre confiance");
            redirectAttributes.addFlashAttribute("error", 
            "Rendu en retard,penalite appliqué");

        }else{
        StatutPret statup=new StatutPret("rendu");
        statup.setId(2);
        pret.setStatut(statup);
        pretService.save(pret);
        
        redirectAttributes.addFlashAttribute("succes", 
            "Merci de votre confiance");
        }
            
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Erreur technique: " + e.getMessage());
    }
    
    return "redirect:/bibliotecaire/pret";
}


  @PostMapping("bibliotecaire/penaliser")
    public String penaliserPret(
        @RequestParam("id") Integer pretId,
        @RequestParam("idA") Integer adherentId,
        @RequestParam("dateDebut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
        @RequestParam("DateFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
        RedirectAttributes redirectAttributes) {

        try {
            Adherent personne=adherentService.getById(adherentId);
            Pret pret=pretService.findById(pretId);
            Penalite penalite=new Penalite(personne,pret,dateDebut,dateFin,"ACTIVE");
            penaliteService.createPenalite(penalite);
            redirectAttributes.addFlashAttribute("error", 
            "Penalite appliqué");
            
           
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'application de la pénalité: " + e.getMessage());
        }

        return "redirect:/bibliotecaire/pret";
    }
    @GetMapping("bibliotecaire/penalite")
    public String listePenalites(Model model) {
        List<Penalite> penalites = penaliteService.findAll();
        model.addAttribute("penalites", penalites);
        return "bibliotecaire/penalite";
    }

    @PostMapping("bibliotecaire/payerPenalite")
    public String payerPenalite(@RequestParam("id") Integer penaliteId,
                               RedirectAttributes redirectAttributes) {
        try {
            Penalite penalite = penaliteService.findById(penaliteId);
            Pret pret=pretService.findById(penalite.getPret().getId());
            StatutPret statut=new StatutPret("rendu");
            statut.setId(2);
            pret.setStatut(statut);
            pretService.save(pret);
            penalite.setStatut("PAID");
            penalite.setDateLevee(LocalDate.now());
            penaliteService.updatePenalite(penalite);
            
            redirectAttributes.addFlashAttribute("succes", "Pénalité payée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur: " + e.getMessage());
        }
        return "redirect:/bibliotecaire/penalite";
    }
}
