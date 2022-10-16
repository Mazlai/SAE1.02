
/**
 * SAE 1.02
 *
 * @author Groupe n°28
 */
public class TNPEtudiants{    
    /*Variables*/
    Etudiant[] tabEtu;
    int nbEtu;

    /*Constructeur sans paramètres levant une exception*/
    TNPEtudiants() throws Exception {
        throw new Exception("Faute de paramètres");        
    }

    /*Constructeur avec paramètres*/
    TNPEtudiants(int pfNbEtuMax) {
        this.nbEtu = pfNbEtuMax;
        this.tabEtu = new Etudiant[pfNbEtuMax];
    }    

}

