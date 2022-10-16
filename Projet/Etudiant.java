
/**
 * SAE 1.02
 *
 * @author Groupe n°28
 */
public class Etudiant{
    /*Variables*/
    String nom;
    String prenom;    
    String numgroupetd;
    String numgroupetp;

    /*Constructeur sans paramètres levant une exception*/
    Etudiant() throws Exception {
        throw new Exception("Faute de paramètres");        
    }    

    /*Constructeur avec paramètres*/
    Etudiant(String pfNom, String pfPrenom, String pfNumGroupeTD, String pfNumGroupeTP) {
        this.nom = pfNom;
        this.prenom = pfPrenom;
        this.numgroupetd = pfNumGroupeTD;
        this.numgroupetp = pfNumGroupeTP;
    }    
}

