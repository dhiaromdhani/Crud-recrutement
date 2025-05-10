package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Guest {

    @FXML
    void EntrerGuest(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/listeOffres.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("liste offres");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
           System.out.println(e.getMessage());
        }
    }

    }


