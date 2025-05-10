package Services;
import Interfaces.IService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Models.Candidat;
import Models.Offre;
import Utils.Database;

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




    public List<Candidat> getCandidatsByOffre(int idOffre) {
        List<Candidat> candidats = new ArrayList<>();
        String sql = ""
                + "SELECT c.* "
                + "  FROM candidat c "
                + "  JOIN candidature ca ON c.idcandidat = ca.idcandidat "
                + " WHERE ca.idoffre = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idOffre);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Candidat c = new Candidat();
                    c.setIdCandidat(rs.getInt("idcandidat"));
                    c.setNom(rs.getString("nom"));
                    c.setPrenom(rs.getString("prenom"));
                    c.setEmail(rs.getString("email"));
                    c.setTel(rs.getString("tel"));
                    candidats.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL getCandidatsByOffre : " + e.getMessage());
        }
        return candidats;
    }




    public List<Offre> getOffresByCandidat(int idCandidat) {
        List<Offre> offres = new ArrayList<>();
        String sql = "SELECT o.* FROM offre o " + "JOIN candidature ca ON o.idoffre = ca.idoffre " + "WHERE ca.idcandidat = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idCandidat);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Offre offre = new Offre();
                offre.setIdoffre(rs.getInt("idoffre"));
                offre.setTitre(rs.getString("titre"));
                offre.setDescription(rs.getString("description"));
                offre.setLocalisation(rs.getString("localisation"));
                offre.setDatePublication(rs.getString("datePublication"));

                offres.add(offre);
            }

        } catch (SQLException e)
        {
            System.out.println("Erreur SQL : " + e.getMessage());
        }

        return offres;
    }


    public int postuler(int idCandidat, int idOffre, String dateCandidature) {
        String sql = "INSERT INTO candidature (idoffre, idcandidat, dateCandidature, statut) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOffre);
            ps.setInt(2, idCandidat);
            ps.setString(3,dateCandidature);
            ps.setString(4, "En attente");
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int generatedId = keys.getInt(1);
                    // Tu peux logger cet ID ou le stocker si tu as besoin de le re‑utiliser
                    System.out.println("Candidature enregistrée (id interne " + generatedId + ")");
                    return generatedId;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la candidature : " + e.getMessage());
        }
        return -1;
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
