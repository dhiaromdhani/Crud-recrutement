import Dao.Service;
import Dao.ServiceCandidat;
import Dao.ServiceCandidature;
import Model.Offre;
import Model.Candidat;
import Model.Candidature;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Service offreDAO = new Service();
        ServiceCandidat candidatDAO = new ServiceCandidat();
        ServiceCandidature candidatureDAO = new ServiceCandidature();
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.println("\nMenu principal - Choisissez une entité à gérer:");
            System.out.println("1. Offres");
            System.out.println("2. Candidats");
            System.out.println("3. Candidatures (à faire)");
            System.out.println("4. Quitter");
            System.out.print("Votre choix: ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    menuOffres(scanner, offreDAO);
                    break;
                case 2:
                    menuCandidats(scanner, candidatDAO);
                    break;
                case 3:
                    menuCandidatures(scanner, candidatureDAO);
                    break;
                case 4:
                    continueLoop = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
        scanner.close();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void menuOffres(Scanner scanner, Service offreDAO) {
        boolean loop = true;
        while (loop) {
            System.out.println("\n--- GESTION DES OFFRES ---");
            System.out.println("1. Ajouter une offre");
            System.out.println("2. Lister les offres");
            System.out.println("3. Modifier une offre");
            System.out.println("4. Supprimer une offre");
            System.out.println("5. Retour");
            System.out.print("Choix: ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.print("Titre: ");
                    String titre = scanner.nextLine();
                    System.out.print("Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Localisation: ");
                    String localisation = scanner.nextLine();
                    System.out.print("Date de publication (YYYY-MM-DD): ");
                    String datePublication = scanner.nextLine();
                    Offre nouvelleOffre = new Offre(0, titre, description, localisation, datePublication);
                    offreDAO.add(nouvelleOffre);
                    break;

                case 2:
                    List<Offre> offres = offreDAO.getAll();
                    if (offres.isEmpty()) {
                        System.out.println("Aucune offre trouvée.");
                    } else {
                        for (Offre offre : offres) {
                            System.out.println("ID: " + offre.getIdoffre() + " | Titre: " + offre.getTitre() + " | Description: " + offre.getDescription());
                        }
                    }
                    break;

                case 3:
                    System.out.print("ID de l'offre à modifier: ");
                    int idModifier = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nouveau titre: ");
                    String nouveauTitre = scanner.nextLine();
                    System.out.print("Nouvelle description: ");
                    String nouvelleDescription = scanner.nextLine();
                    System.out.print("Nouvelle localisation: ");
                    String nouvelleLocalisation = scanner.nextLine();
                    System.out.print("Nouvelle date de publication: ");
                    String nouvelleDate = scanner.nextLine();
                    Offre offreModif = new Offre(idModifier, nouveauTitre, nouvelleDescription, nouvelleLocalisation, nouvelleDate);
                    offreDAO.update(offreModif);
                    break;

                case 4:
                    System.out.print("ID de l'offre à supprimer: ");
                    int idSuppr = scanner.nextInt();
                    Offre offreSupp = new Offre();
                    offreSupp.setIdoffre(idSuppr);
                    offreDAO.delete(offreSupp);

                    break;

                case 5:
                    loop = false;
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void menuCandidats(Scanner scanner, ServiceCandidat candidatDAO) {
        boolean loop = true;
        while (loop) {
            System.out.println("\n--- GESTION DES CANDIDATS ---");
            System.out.println("1. Ajouter un candidat");
            System.out.println("2. Lister les candidats");
            System.out.println("3. Modifier un candidat");
            System.out.println("4. Supprimer un candidat");
            System.out.println("5. Retour");
            System.out.print("Choix: ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.print("Nom: ");
                    String nom = scanner.nextLine();
                    System.out.print("Prénom: ");
                    String prenom = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Téléphone: ");
                    String tel = scanner.nextLine();
                    Candidat candidat = new Candidat(0, nom, prenom, email, tel);
                    candidatDAO.add(candidat);
                    break;

                case 2:
                    List<Candidat> candidats = candidatDAO.getAll();
                    if (candidats.isEmpty()) {
                        System.out.println("Aucun candidat trouvé.");
                    } else {
                        for (Candidat c : candidats) {
                            System.out.println("ID: " + c.getIdCandidat() + " | Nom: " + c.getNom() + " | Prénom: " + c.getPrenom());
                        }
                    }
                    break;

                case 3:
                    System.out.print("ID du candidat à modifier: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nouveau nom: ");
                    String nomMod = scanner.nextLine();
                    System.out.print("Nouveau prénom: ");
                    String prenomMod = scanner.nextLine();
                    System.out.print("Nouvel email: ");
                    String emailMod = scanner.nextLine();
                    System.out.print("Nouveau téléphone: ");
                    String telMod = scanner.nextLine();
                    Candidat cModif = new Candidat(id, nomMod, prenomMod, emailMod, telMod);
                    candidatDAO.update(cModif);
                    break;

                case 4:
                    System.out.print("ID de le candidat à supprimer: ");
                    int idSuppr = scanner.nextInt();
                    Candidat candidatSupp = new Candidat();
                    candidatSupp.setIdCandidat(idSuppr);
                    candidatDAO.delete(candidatSupp);

                    break;
                case 5:
                    loop = false;
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void menuCandidatures(Scanner scanner, ServiceCandidature candidatureDAO) {
        boolean loop = true;
        while (loop) {
            System.out.println("\n--- GESTION DES CANDIDATURES ---");
            System.out.println("1. Ajouter une candidature");
            System.out.println("2. Lister les candidatures");
            System.out.println("3. Supprimer une candidature");
            System.out.println("4. Retour");
            System.out.print("Choix: ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.print("ID du candidat: ");
                    int idcandidat = scanner.nextInt();
                    System.out.print("ID de l'offre: ");
                    int idoffre = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Date de candidature (YYYY-MM-DD): ");
                    String dateCandidature = scanner.nextLine();
                    System.out.print("Statut de la candidature (ex: En attente, Acceptée, Refusée): ");
                    String statut = scanner.nextLine();

                    Candidature candidature = new Candidature(0, idoffre, idcandidat, dateCandidature, statut);
                    candidatureDAO.add(candidature);
                    break;

                case 2:
                    List<Candidature> candidatures = candidatureDAO.getAll();
                    if (candidatures.isEmpty()) {
                        System.out.println("Aucune candidature trouvée.");
                    } else {
                        for (Candidature c : candidatures) {
                            System.out.println("ID: " + c.getId() +" | Offre: " + c.getIdoffre() + " | Candidat: " + c.getIdCandidat() + " | Date: " + c.getDateCandidature() + "| Status: " + c.getStatut());
                        }
                    }
                    break;

                case 3:
                    System.out.print("ID de la candidature à supprimer: ");
                    int idSuppr = scanner.nextInt();
                    Candidature candidatureSupp = new Candidature();
                    candidatureSupp.setId(idSuppr);
                    candidatureDAO.delete(candidatureSupp);
                    break;

                case 4:
                    loop = false;
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        }
    }

}
