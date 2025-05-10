package Controllers;

import Models.Offre;
import Services.ServiceOffre;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AdminOffresController implements Initializable {

    @FXML private TableView<Offre> tableOffres;
    @FXML private TableColumn<Offre, Integer> colId;
    @FXML private TableColumn<Offre, String> colTitre;
    @FXML private TableColumn<Offre, String> colDescription;
    @FXML private TableColumn<Offre, String> colLocalisation;
    @FXML private TableColumn<Offre, String> colDate;

    @FXML private TextField titreField;
    @FXML private TextArea descriptionField;
    @FXML private TextField localisationField;
    @FXML private DatePicker datePublicationPicker;

    private ServiceOffre serviceOffre;
    private ObservableList<Offre> offreList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceOffre = new ServiceOffre();

        // Configurer les colonnes du tableau
        colId.setCellValueFactory(new PropertyValueFactory<>("idoffre"));
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLocalisation.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        colDate.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDatePublication())
        );

        loadOffres();


        tableOffres.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateForm(newSel);
            }
        });
    }

    private void loadOffres() {
        List<Offre> list = serviceOffre.getAll();
        offreList = FXCollections.observableArrayList(list);
        tableOffres.setItems(offreList);
    }

    @FXML
    private void ajouterOffre(ActionEvent event) {
        Offre o = new Offre(0,
                titreField.getText(),
                descriptionField.getText(),
                localisationField.getText(),
                datePublicationPicker.getValue() != null ? datePublicationPicker.getValue().toString() : ""
        );
        serviceOffre.add(o);
        loadOffres();
        resetForm(event);
    }

    @FXML
    private void modifierOffre(ActionEvent event) {
        Offre selected = tableOffres.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setTitre(titreField.getText());
            selected.setDescription(descriptionField.getText());
            selected.setLocalisation(localisationField.getText());
            selected.setDatePublication(
                    datePublicationPicker.getValue() != null ? datePublicationPicker.getValue().toString() : ""
            );
            serviceOffre.update(selected);
            loadOffres();
            resetForm(event);
        }
    }

    @FXML
    private void supprimerOffre(ActionEvent event) {
        Offre selected = tableOffres.getSelectionModel().getSelectedItem();
        if (selected != null) {
            serviceOffre.delete(selected);
            loadOffres();
            resetForm(event);
        }
    }

    @FXML
    void RetourpageAcceuil(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/PageAccueil.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Page Acceuil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
           System.out.println(e.getMessage());
        }

    }


    @FXML
    private void resetForm(ActionEvent event) {
        titreField.clear();
        descriptionField.clear();
        localisationField.clear();
        datePublicationPicker.setValue(null);
        tableOffres.getSelectionModel().clearSelection();
    }

    private void populateForm(Offre o) {
        titreField.setText(o.getTitre());
        descriptionField.setText(o.getDescription());
        localisationField.setText(o.getLocalisation());
        if (o.getDatePublication() != null && !o.getDatePublication().isEmpty()) {
            datePublicationPicker.setValue(LocalDate.parse(o.getDatePublication()));
        } else {
            datePublicationPicker.setValue(null);
        }
    }
}
