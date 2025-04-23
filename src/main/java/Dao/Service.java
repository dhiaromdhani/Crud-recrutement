package Dao;
import Model.Offre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Service {

    // Ajouter une offre
    public void ajouterOffre(Offre offre) {
        String sql = "INSERT INTO offre (titre, description, localisation, datePublication) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, offre.getTitre());
            ps.setString(2, offre.getDescription());
            ps.setString(3, offre.getLocalisation());
            ps.setString(4, offre.getDatePublication());
            ps.executeUpdate();
            System.out.println("Offre ajoutée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lister toutes les offres
    public List<Offre> listerOffres() {
        List<Offre> offres = new ArrayList<>();
        String sql = "SELECT * FROM offres";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Offre offre = new Offre();
                offre.setId(rs.getInt("id"));
                offre.setTitre(rs.getString("titre"));
                offre.setDescription(rs.getString("description"));
                offre.setLocalisation(rs.getString("localisation"));
                offre.setDatePublication(rs.getString("datePublication"));
                offres.add(offre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offres;
    }

    // Modifier une offre
    public void modifierOffre(Offre offre) {
        String sql = "UPDATE offres SET titre = ?, description = ?, localisation = ?, datePublication = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, offre.getTitre());
            ps.setString(2, offre.getDescription());
            ps.setString(3, offre.getLocalisation());
            ps.setString(4, offre.getDatePublication());
            ps.setInt(5, offre.getId());
            ps.executeUpdate();
            System.out.println("Offre modifiée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer une offre
    public void supprimerOffre(int id) {
        String sql = "DELETE FROM offre WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Offre supprimée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
