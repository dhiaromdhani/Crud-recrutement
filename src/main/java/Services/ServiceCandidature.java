package Services;

import Utils.Database;
import Interfaces.IService;
import Models.Candidature;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCandidature implements IService<Candidature> {
    private final Connection con;

    public ServiceCandidature() {
        con = Database.getInstance().getConnection();
    }

    @Override
    public void add(Candidature candidature) {
        String sql = "INSERT INTO candidature (idoffre, idcandidat, dateCandidature, statut) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, candidature.getIdoffre());
            ps.setInt(2, candidature.getIdCandidat());
            ps.setString(3, candidature.getDateCandidature());
            ps.setString(4, candidature.getStatut());

            ps.executeUpdate();
            System.out.println("Candidature ajoutée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur ajout candidature : " + e.getMessage());
        }
    }

    @Override
    public void update(Candidature candidature) {
        String sql = "UPDATE candidature SET idoffre = ?, idcandidat = ?, dateCandidature = ?, statut = ? WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, candidature.getIdoffre());
            ps.setInt(2, candidature.getIdCandidat());
            ps.setString(3, candidature.getDateCandidature());
            ps.setString(4, candidature.getStatut());
            ps.setInt(5, candidature.getId());

            ps.executeUpdate();
            System.out.println("Candidature modifiée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur modification candidature : " + e.getMessage());
        }
    }

    @Override
    public void delete(Candidature candidature) {
        String sql = "DELETE FROM candidature WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, candidature.getId());
            ps.executeUpdate();
            System.out.println("Candidature supprimée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur suppression candidature : " + e.getMessage());
        }
    }

    public List<Candidature> getAll() {
        List<Candidature> candidatures = new ArrayList<>();
        String sql = "SELECT * FROM candidature";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Candidature c = new Candidature();
                c.setId(rs.getInt("id"));
                c.setIdoffre(rs.getInt("idoffre"));
                c.setIdCandidat(rs.getInt("idcandidat"));
                c.setDateCandidature(rs.getString("dateCandidature"));
                c.setStatut(rs.getString("statut"));

                candidatures.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erreur récupération candidatures : " + e.getMessage());
        }
        return candidatures;
    }
}
