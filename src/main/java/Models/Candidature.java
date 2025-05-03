package Models;

public class Candidature {
    private int id;
    private int idoffre;
    private int idcandidat;
    private String dateCandidature;
    private String statut;

    public Candidature(int id, int idoffre, int idcandidat, String dateCandidature, String statut) {
        this.id = id;
        this.idoffre = idoffre;
        this.idcandidat = idcandidat;
        this.dateCandidature = dateCandidature;
        this.statut = statut;
    }

    public Candidature() {}

    public String getStatut() {
        return statut;
    }

    public int getId() {
        return id;
    }

    public int getIdoffre() {
        return idoffre;
    }

    public int getIdCandidat() {
        return idcandidat;
    }

    public String getDateCandidature() {
        return dateCandidature;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdoffre(int idoffre) {
        this.idoffre = idoffre;
    }

    public void setIdCandidat(int idcandidat) {
        this.idcandidat = idcandidat;
    }

    public void setDateCandidature(String dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}

