package Dao;
import Interfaces.IService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Candidat;
import Model.Offre;

public class ServiceCandidat implements IService<Candidat> {
    private final Connection con;

    public ServiceCandidat() {
        con = Database.getInstance().getConnection();
    }

    @Override
    public void add(Candidat candidat) {

        String sql = "INSERT INTO candidat(nom,prenom,email,tel) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, candidat.getNom());
            ps.setString(2, candidat.getPrenom());
            ps.setString(3, candidat.getEmail());
            ps.setString(4, candidat.getTel());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        candidat.setIdCandidat(generatedId);  // Met à jour l'ID généré
                        System.out.println("Candidat ajouté avec succès. ID: " + generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public List<Candidat> getAll() {
        List<Candidat> candidats = new ArrayList<>();
        String sql = "SELECT * FROM candidat";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Candidat candidat= new Candidat();
                candidat.setIdCandidat(rs.getInt("idcandidat"));
                candidat.setNom(rs.getString("nom"));
                candidat.setPrenom(rs.getString("prenom"));
                candidat.setEmail(rs.getString("email"));
                candidat.setTel(rs.getString("tel"));

                candidats.add(candidat);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return candidats;
    }

    @Override

    public void update(Candidat candidat) {
        String sql = "UPDATE candidat SET nom = ?, prenom = ?, email = ?, tel = ? WHERE idCandidat = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, candidat.getNom());
            ps.setString(2, candidat.getPrenom());
            ps.setString(3, candidat.getEmail());
            ps.setString(4, candidat.getTel());
            ps.setInt(5, candidat.getIdCandidat());
            ps.executeUpdate();
            System.out.println("Candidat modifiée avec succès.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void delete(Candidat candidat ) {
        String sql = "DELETE FROM candidat WHERE idcandidat = ?";
        try (
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, candidat.getIdCandidat());
            ps.executeUpdate();
            System.out.println("Candidat supprimée avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
