package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger les interfaces FXML (vérifier que les FXML sont sous src/main/resources)
            /*FXMLLoader candidatLoader = new FXMLLoader(getClass().getResource("/ajouterCandidat.fxml"));
            Parent candidatRoot = candidatLoader.load();

            FXMLLoader offreLoader = new FXMLLoader(getClass().getResource("/AdminOffres.fxml"));
            Parent offreRoot = offreLoader.load();

            // Créer un TabPane pour contenir les deux vues
            TabPane tabPane = new TabPane();

            Tab tabCandidats = new Tab("Candidats", candidatRoot);
            tabCandidats.setClosable(false);

            Tab tabOffres = new Tab("Offres", offreRoot);
            tabOffres.setClosable(false);

            tabPane.getTabs().addAll(tabCandidats, tabOffres);

            // Configurer la scène principale
            Scene scene = new Scene(tabPane);
            primaryStage.setTitle("Gestion des Candidats et des Offres");
            primaryStage.setScene(scene);
            primaryStage.show();*/

            // Dans AdminCandidatureController, lors du double-clic ou clic sur un bouton :
            // Charger la page d'accueil (Dashboard) avec les onglets ou boutons pour accéder aux offres et candidatures
            /*Parent root = FXMLLoader.load(getClass().getResource("/AdminCandidature.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Gestion des Candidatures");
            primaryStage.setScene(scene);
            primaryStage.show();*/

            /*Parent root = FXMLLoader.load(getClass().getResource("/PageAccueil.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Page Accueil");
            primaryStage.setScene(scene);
            primaryStage.show();*/

            /*Parent root = FXMLLoader.load(getClass().getResource("/listeOffres.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("listeOffres");
            primaryStage.setScene(scene);
            primaryStage.show();*/

            Parent root = FXMLLoader.load(getClass().getResource("/Guest.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Guest");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Erreur de chargement des fichiers FXML : assurez-vous que les chemins sont corrects."+ e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
