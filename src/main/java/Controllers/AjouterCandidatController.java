package Controllers;

import Models.Candidat;
import Services.ServiceCandidat;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterCandidatController implements Initializable {

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelephone;
    @FXML
    private DatePicker dateCandidaturePicker;

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

    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnLire;
    @FXML
    private Button btnValiderDemande;

    private ServiceCandidat serviceCandidat;
    private ObservableList<Candidat> candidats;
    private int selectedOfferId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceCandidat = new ServiceCandidat();
        candidats = FXCollections.observableArrayList();

        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdCandidat()).asObject());
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        telColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTel()));

        dateCandidaturePicker.setValue(LocalDate.now());
        reloadTable();
    }

    private void reloadTable() {
        List<Candidat> list = serviceCandidat.getAll();
        candidats.setAll(list);
        tableCandidats.setItems(candidats);
    }

    @FXML
    private void Ajouter(ActionEvent event) {
        Candidat c = new Candidat(0, txtNom.getText(), txtPrenom.getText(), txtEmail.getText(), txtTelephone.getText());
        serviceCandidat.add(c);
        reloadTable();
        clearFields();
    }

    @FXML
    private void Supprimer(ActionEvent event) {
        Candidat sel = tableCandidats.getSelectionModel().getSelectedItem();
        if (sel != null) {
            serviceCandidat.delete(sel);
            reloadTable();
            clearFields();
        }
    }

    @FXML
    private void Modifier(ActionEvent event) {
        Candidat sel = tableCandidats.getSelectionModel().getSelectedItem();
        if (sel != null) {
            sel.setNom(txtNom.getText());
            sel.setPrenom(txtPrenom.getText());
            sel.setEmail(txtEmail.getText());
            sel.setTel(txtTelephone.getText());
            serviceCandidat.update(sel);
            reloadTable();
            clearFields();
        }
    }

    @FXML
    private void Lire(ActionEvent event) {
        Candidat sel = tableCandidats.getSelectionModel().getSelectedItem();
        if (sel != null) {
            txtNom.setText(sel.getNom());
            txtPrenom.setText(sel.getPrenom());
            txtEmail.setText(sel.getEmail());
            txtTelephone.setText(sel.getTel());
        }
    }

    @FXML
    private void ValiderDemande(ActionEvent event) {
        Candidat c = new Candidat(0, txtNom.getText(), txtPrenom.getText(), txtEmail.getText(), txtTelephone.getText());
        serviceCandidat.add(c);
        int idC = c.getIdCandidat();
        String date = dateCandidaturePicker.getValue().toString();
        serviceCandidat.postuler(idC, selectedOfferId, date);
        new Alert(Alert.AlertType.INFORMATION, "Candidature enregistrée !").showAndWait();
        // Fermer ou reset
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setSelectedOfferId(int offerId) {
        this.selectedOfferId = offerId;
    }

    private void clearFields() {
        txtNom.clear(); txtPrenom.clear(); txtEmail.clear(); txtTelephone.clear();
    }
}