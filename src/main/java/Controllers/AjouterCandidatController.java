package Controllers;

import Services.ServiceCandidat;
import Models.Candidat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AjouterCandidatController {

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
    private TableView<Candidat> tableCandidats;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTéléphone;

    private final ServiceCandidat serviceCandidat = new ServiceCandidat();
    private final ObservableList<Candidat> candidats = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getIdCandidat()).asObject());
        nomColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNom()));
        prenomColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPrenom()));
        emailColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail()));
        telColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTel()));

        candidats.setAll(serviceCandidat.getAll());
        tableCandidats.setItems(candidats);
    }

    @FXML
    private void Ajouter(ActionEvent event) {
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String email = txtEmail.getText();
        String tel = txtTéléphone.getText();

        Candidat nouveau = new Candidat(0, nom, prenom, email, tel);
        serviceCandidat.add(nouveau);

        candidats.setAll(serviceCandidat.getAll());
        viderChamps();
    }

    @FXML
    private void Supprimer(ActionEvent event) {
        Candidat selected = tableCandidats.getSelectionModel().getSelectedItem();
        if (selected != null) {
            serviceCandidat.delete(selected);
            candidats.setAll(serviceCandidat.getAll());
            viderChamps();
        }
    }

    @FXML
    private void Modifier(ActionEvent event) {
        Candidat selected = tableCandidats.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNom(txtNom.getText());
            selected.setPrenom(txtPrenom.getText());
            selected.setEmail(txtEmail.getText());
            selected.setTel(txtTéléphone.getText());

            serviceCandidat.update(selected);
            candidats.setAll(serviceCandidat.getAll());
            viderChamps();
        }
    }

    @FXML
    private void Lire(ActionEvent event) {
        Candidat selected = tableCandidats.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtNom.setText(selected.getNom());
            txtPrenom.setText(selected.getPrenom());
            txtEmail.setText(selected.getEmail());
            txtTéléphone.setText(selected.getTel());
        }
    }

    private void viderChamps() {
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
        txtTéléphone.clear();
    }
}
