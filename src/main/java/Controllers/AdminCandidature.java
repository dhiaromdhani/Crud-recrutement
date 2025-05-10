package Controllers;

import Services.Admin;
import Utils.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminCandidature implements Initializable {

    @FXML private TreeView<String> treeViewCandidatures;
    @FXML private Button btnAfficher;
    @FXML private Button btnAccepter;
    @FXML private Button btnRefuser;

    private Admin adminService;
    private Connection con;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = Database.getInstance().getConnection();
        adminService = new Admin(con);
        configureTree();
        loadCandidatures();
    }

    private void configureTree() {
        treeViewCandidatures.setShowRoot(false);
        treeViewCandidatures.setCellFactory(tv -> new TreeCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
            }
        });
    }

    @FXML
    private void afficherCandidatures(ActionEvent event) {
        loadCandidatures();
    }

    private void loadCandidatures() {
        TreeItem<String> root = new TreeItem<>();
        root.setExpanded(true);
        String sql = "SELECT ca.idcandidat, ca.idoffre, c.nom, c.prenom, o.titre, ca.dateCandidature, ca.statut " +
                "FROM candidature ca " +
                "JOIN candidat c ON ca.idcandidat = c.idcandidat " +
                "JOIN offre o ON ca.idoffre = o.idoffre";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int idC = rs.getInt("idcandidat");
                int idO = rs.getInt("idoffre");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String titre = rs.getString("titre");
                String date = rs.getString("dateCandidature");
                String statut = rs.getString("statut");
                // Format: idC|idO|Nom Prenom - Titre (statut) | Date
                String label = idC + "|" + idO + "|" + nom + " " + prenom + " - " + titre + " (" + statut + ") [" + date + "]";
                TreeItem<String> node = new TreeItem<>(label);
                root.getChildren().add(node);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        treeViewCandidatures.setRoot(root);
    }

    @FXML
    private void accepterCandidature(ActionEvent event) {
        updateSelected("Acceptée");
    }

    @FXML
    private void refuserCandidature(ActionEvent event) {
        updateSelected("Refusée");
    }

    private void updateSelected(String nouveauStatut) {
        TreeItem<String> sel = treeViewCandidatures.getSelectionModel().getSelectedItem();
        if (sel != null && sel.getValue() != null) {
            String[] parts = sel.getValue().split("\\|");
            int idC = Integer.parseInt(parts[0]);
            int idO = Integer.parseInt(parts[1]);
            adminService.changerStatutCandidature(idC, idO, nouveauStatut);
            showAlert(AlertType.INFORMATION, "Succès", "Statut mis à jour : " + nouveauStatut);
            loadCandidatures();
        } else {
            showAlert(AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner une candidature.");
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

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
