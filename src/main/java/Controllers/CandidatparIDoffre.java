package Controllers;

import Models.Candidat;
import Services.ServiceCandidat;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CandidatparIDoffre implements Initializable {

    @FXML
    private TextField idOffreField;
    @FXML
    private TableView<Candidat> tableCandidats;
    @FXML
    private TableColumn<Candidat, Integer> idColumn;
    @FXML
    private TableColumn<Candidat, String> nomColumn;
    @FXML
    private TableColumn<Candidat, String> prenomColumn;
    @FXML
    private TableColumn<Candidat, String> emailColumn;
    @FXML
    private TableColumn<Candidat, String> telColumn;

    private ObservableList<Candidat> candidats = FXCollections.observableArrayList();
    private ServiceCandidat serviceCandidat = new ServiceCandidat();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdCandidat()).asObject());
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        telColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTel()));
    }

    @FXML
    private void handleAfficherCandidats() {
        try {
            int idOffre = Integer.parseInt(idOffreField.getText().trim());
            List<Candidat> list = serviceCandidat.getCandidatsByOffre(idOffre);
            candidats.setAll(list);
            tableCandidats.setItems(candidats);

            if (list.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Aucun candidat trouvé pour l'offre ID: " + idOffre);
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Veuillez saisir un ID valide (nombre entier).");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
