package Controllers;

import Models.Offre;
import Services.ServiceOffre;
import Services.ServiceCandidat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListeOffres implements Initializable {

    @FXML private TableView<Offre> tableoffre;
    @FXML private TableColumn<Offre, String> titre;
    @FXML private TableColumn<Offre, String> description;
    @FXML private TableColumn<Offre, String> localisation;
    @FXML private TableColumn<Offre, String> datePublication;

    private ServiceOffre serviceOffre;
    private ServiceCandidat serviceCandidat;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceOffre = new ServiceOffre();
        serviceCandidat = new ServiceCandidat();

        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        localisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        datePublication.setCellValueFactory(new PropertyValueFactory<>("datePublication"));

        loadOffres();
    }

    private void loadOffres() {
        List<Offre> offres = serviceOffre.getAll();
        ObservableList<Offre> data = FXCollections.observableArrayList(offres);
        tableoffre.setItems(data);
    }
    @FXML
    private void Postuler(ActionEvent event) {
        Offre selected = tableoffre.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterCandidat.fxml"));
                Parent root = loader.load();

                // Récupérer le contrôleur et lui passer l'offre sélectionnée
                Controllers.AjouterCandidatController ctrl = loader.getController();
                ctrl.setSelectedOfferId(selected.getIdoffre());

                // Afficher la fenêtre
                Stage stage = new Stage();
                stage.setTitle("Formulaire de candidature");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                System.err.println("Erreur" + e.getMessage());
            }

    }


}

    }