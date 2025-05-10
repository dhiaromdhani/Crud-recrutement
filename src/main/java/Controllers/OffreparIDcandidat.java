package Controllers;

import Models.Offre;
import Services.ServiceOffre;
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

public class OffreparIDcandidat implements Initializable {

    @FXML
    private TextField idCandidatField;
    @FXML
    private TableView<Offre> tableOffres;
    @FXML
    private TableColumn<Offre, Integer> idColumn;
    @FXML
    private TableColumn<Offre, String> titreColumn;
    @FXML
    private TableColumn<Offre, String> descColumn;
    @FXML
    private TableColumn<Offre, String> locColumn;
    @FXML
    private TableColumn<Offre, String> dateColumn;

    private ObservableList<Offre> offres = FXCollections.observableArrayList();
    private ServiceOffre serviceOffre = new ServiceOffre();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdoffre()).asObject());
        titreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitre()));
        descColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        locColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocalisation()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatePublication()));
    }

    @FXML
    private void handleAfficherOffres() {
        try {
            int idCandidat = Integer.parseInt(idCandidatField.getText().trim());
            List<Offre> list = serviceOffre.getOffresByCandidat(idCandidat);
            offres.setAll(list);
            tableOffres.setItems(offres);

            if (list.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Aucune offre trouvée pour le candidat ID: " + idCandidat);
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
