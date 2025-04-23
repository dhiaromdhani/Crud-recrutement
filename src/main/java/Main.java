import Dao.Service;
import Model.Offre;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Service offreDAO = new Service();
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.println("\nChoisissez une option:");
            System.out.println("1. Ajouter une offre");
            System.out.println("2. Lister les offres");
            System.out.println("3. Modifier une offre");
            System.out.println("4. Supprimer une offre");
            System.out.println("5. Quitter");
            System.out.print("Votre choix: ");

            int choix = scanner.nextInt();
            scanner.nextLine();  // Consomme le \n restant après nextInt()

            switch (choix) {
                case 1:
                    // Ajouter une offre
                    System.out.print("Titre: ");
                    String titre = scanner.nextLine();
                    System.out.print("Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Localisation: ");
                    String localisation = scanner.nextLine();
                    System.out.print("Date de publication (YYYY-MM-DD): ");
                    String datePublication = scanner.nextLine();

                    Offre nouvelleOffre = new Offre(0, titre, description, localisation, datePublication);
                    offreDAO.ajouterOffre(nouvelleOffre);
                    break;

                case 2:
                    // Lister les offres
                    List<Offre> offres = offreDAO.listerOffres();
                    if (offres.isEmpty()) {
                        System.out.println("Aucune offre trouvée.");
                    } else {
                        for (Offre offre : offres) {
                            System.out.println("ID: " + offre.getId() + " | Titre: " + offre.getTitre() + " | Description: " + offre.getDescription());
                        }
                    }
                    break;

                case 3:
                    // Modifier une offre
                    System.out.print("ID de l'offre à modifier: ");
                    int idModifier = scanner.nextInt();
                    scanner.nextLine();  // Consomme le \n restant

                    System.out.print("Nouveau titre: ");
                    String nouveauTitre = scanner.nextLine();
                    System.out.print("Nouvelle description: ");
                    String nouvelleDescription = scanner.nextLine();
                    System.out.print("Nouvelle localisation: ");
                    String nouvelleLocalisation = scanner.nextLine();
                    System.out.print("Nouvelle date de publication (YYYY-MM-DD): ");
                    String nouvelleDate = scanner.nextLine();

                    Offre offreAModifier = new Offre(idModifier, nouveauTitre, nouvelleDescription, nouvelleLocalisation, nouvelleDate);
                    offreDAO.modifierOffre(offreAModifier);
                    break;

                case 4:
                    // Supprimer une offre
                    System.out.print("ID de l'offre à supprimer: ");
                    int idSupprimer = scanner.nextInt();
                    offreDAO.supprimerOffre(idSupprimer);
                    break;

                case 5:
                    // Quitter
                    continueLoop = false;
                    break;

                default:
                    System.out.println("Option invalide.");
                    break;
            }
        }

        scanner.close();
    }
}
