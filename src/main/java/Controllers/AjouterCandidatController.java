package Controllers;

import Models.Candidat;
import Services.ServiceCandidat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterCandidatController implements Initializable {

    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelephone;
    @FXML private DatePicker dateCandidaturePicker;

    @FXML private Button btnAjouter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private Button btnLire;
    @FXML private Button btnValiderDemande;

    @FXML private VBox cardContainer;

    private ServiceCandidat serviceCandidat;
    private Candidat selectedCandidat;
    private int selectedOfferId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceCandidat = new ServiceCandidat();
        dateCandidaturePicker.setValue(LocalDate.now());
        reloadTable();
    }

    /** Reconstruit toutes les cartes à partir de la base. */
    private void reloadTable() {
        cardContainer.getChildren().clear();
        List<Candidat> list = serviceCandidat.getAll();

        for (Candidat c : list) {
            HBox card = new HBox(10);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-background-color:#f2f2f2; -fx-border-color:#ccc;");

            VBox info = new VBox(4);
            info.getChildren().addAll(
                    new Label("ID: "    + c.getIdCandidat()),
                    new Label("Nom: "   + c.getNom() + " " + c.getPrenom()),
                    new Label("Email: " + c.getEmail()),
                    new Label("Tél: "   + c.getTel())
            );
            card.getChildren().add(info);

            // Sélection de la carte
            card.setOnMouseClicked(e -> {
                // Enlever la surbrillance de toutes
                cardContainer.getChildren().forEach(node ->
                        node.setStyle("-fx-background-color:#f2f2f2; -fx-border-color:#ccc;")
                );
                // Mettre en surbrillance la carte cliquée
                card.setStyle("-fx-background-color:#d0f0d0; -fx-border-color:#00aa00;");

                // Mémoriser et préremplir le formulaire
                selectedCandidat = c;
                txtNom.setText(c.getNom());
                txtPrenom.setText(c.getPrenom());
                txtEmail.setText(c.getEmail());
                txtTelephone.setText(c.getTel());
                dateCandidaturePicker.setValue(LocalDate.now());
            });

            cardContainer.getChildren().add(card);
        }
    }

    @FXML
    private void Ajouter(ActionEvent event) {
        Candidat c = new Candidat(
                0,
                txtNom.getText(),
                txtPrenom.getText(),
                txtEmail.getText(),
                txtTelephone.getText()
        );
        serviceCandidat.add(c);
        reloadTable();
        clearFields();
    }

    @FXML
    private void Supprimer(ActionEvent event) {
        if (selectedCandidat != null) {
            serviceCandidat.delete(selectedCandidat);
            reloadTable();
            clearFields();
        }
    }

    @FXML
    private void Modifier(ActionEvent event) {
        if (selectedCandidat != null) {
            selectedCandidat.setNom(txtNom.getText());
            selectedCandidat.setPrenom(txtPrenom.getText());
            selectedCandidat.setEmail(txtEmail.getText());
            selectedCandidat.setTel(txtTelephone.getText());
            serviceCandidat.update(selectedCandidat);
            reloadTable();
            clearFields();
        }
    }

    @FXML
    private void Lire(ActionEvent event) {
        // La lecture est déjà gérée par la sélection dans reloadTable().
    }

    @FXML
    private void ValiderDemande(ActionEvent event) {
        String nom    = txtNom.getText();
        String prenom = txtPrenom.getText();
        String email  = txtEmail.getText();
        String tel    = txtTelephone.getText();
        String date   = dateCandidaturePicker.getValue().toString();

        // 1) On cherche d’abord un candidat avec cet email
        Candidat existant = serviceCandidat.findByEmail(email);
        int idCandidat;
        if (existant != null) {
            idCandidat = existant.getIdCandidat();
        } else {
            // sinon on le crée
            Candidat nouveau = new Candidat(0, nom, prenom, email, tel);
            serviceCandidat.add(nouveau);
            idCandidat = nouveau.getIdCandidat();
        }

        // 2) On crée la candidature en réutilisant idCandidat et l'id de l'offre
        serviceCandidat.postuler(idCandidat, selectedOfferId, date);

        new Alert(Alert.AlertType.INFORMATION, "Candidature enregistrée !").showAndWait();
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    /** Vide le formulaire et réinitialise la surbrillance. */
    private void clearFields() {
        txtNom.clear();
        txtPrenom.clear();
        txtEmail.clear();
        txtTelephone.clear();
        dateCandidaturePicker.setValue(LocalDate.now());
        selectedCandidat = null;
        cardContainer.getChildren().forEach(node ->
                node.setStyle("-fx-background-color:#f2f2f2; -fx-border-color:#ccc;")
        );
    }

    public void setSelectedOfferId(int offerId) {
        this.selectedOfferId = offerId;
    }
}
