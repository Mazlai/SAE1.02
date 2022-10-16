/**
 * SAE 1.02
 *
 * @author Groupe n°28
 **/
import java.io.*;
import java.util.Scanner;

public class ListeEtudiants {
    /** Donne le nombre d’étudiants de la liste pfListe
     *  @param   pfListe IN tableau contenant la liste d'étudiants nom, prenom
     *  @return le nombre d’étudiants de la liste
     **/
    public static int nbEtudiant(TNPEtudiants pfListe){
        return pfListe.nbEtu;
    }

    /** Charge dans un tableau la liste des étudiants
     * 
     *  @param   pfFileName IN le nom du fichier à lire
     *  @param   pfDelimiter IN le délimiteur de champs dans le fichier csv
     *  
     *  @return le tableau
     *  
     *  Précondition : le fichier .csv doit être valide
     **/
    public static TNPEtudiants getListe(String pfFileName, String pfDelimiter) throws FileNotFoundException{         
        //Ouvre un fichier et compte le nombre  de lignes du fichier.
        //Ce nombre de lignes correspond au nombre d'étudiants
        BufferedReader read = new BufferedReader(new FileReader(pfFileName));
        int nbElt = 0;

        //le try catch est la pour recuperer des erreurs eventuelles de lecture
        //dans le fichier. 
        //Si une erreur se produit, ce sont les instructions du catch qui seront executees.

        try {
            while (read.readLine() != null) {
                nbElt++;
            }
            read.close(); 

        } catch (IOException e) {
            e.printStackTrace();
        } 

        System.out.println("Nombre de lignes des etudiants : " + nbElt);

        TNPEtudiants res = new TNPEtudiants(nbElt);

        //lecture du fichier pour récupérer les noms et prénoms

        String line = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(pfFileName));
            int cpt = 0; // numero de l'etudiant en lecture

            //loops through every line until null found
            while ((line = reader.readLine()) != null) {   

                //separate every token by comma
                String[] token = line.split(pfDelimiter);    

                res.tabEtu[cpt] = new Etudiant(token[0], token[1], token[2], token[3]);

                cpt++;
            }
            reader.close(); 
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    /** Permet de trier les étudiants d'une liste pfListe
     *
     * @param pfListe IN/OUT : le tableau d'étudiants
     *
     */
    public static void trierEtudiant(TNPEtudiants pfListe) {    
        boolean hasperm = true;
        Etudiant ech;

        while (hasperm) {
            hasperm = false;
            for(int i = 0; i <= pfListe.nbEtu-2; i++) {            
                if((pfListe.tabEtu[i].nom.compareTo(pfListe.tabEtu[i+1].nom)) > 0  ||
                ((pfListe.tabEtu[i].nom.compareTo(pfListe.tabEtu[i+1].nom) == 0 ) &&
                    (pfListe.tabEtu[i].prenom.compareTo(pfListe.tabEtu[i+1].prenom) > 0))){
                    ech = pfListe.tabEtu[i];
                    pfListe.tabEtu[i] = pfListe.tabEtu[i+1];
                    pfListe.tabEtu[i+1] = ech;
                    hasperm = true;
                }
            }
        }    
    }

    /** Permet de rechercher un étudiant en utilisant la recherche dichotomique 
     *
     * @param pfListe IN : le tableau d'étudiants
     * 
     * @param pfEtudiant IN : un étudiant du tableau
     *
     * @return si l'étudiant recherché est présent ou non dans la promo
     */

    public static boolean rechercheDicho(Etudiant pfEtudiant, TNPEtudiants pfListe) {        
        System.out.println("\n" + "--> Recherche par dichotomie <--");

        // Variables

        boolean estPresent = false;
        int cpttotal = 0;

        int indiceMin = 0;
        int indiceMax = pfListe.nbEtu-1;
        int indiceMedian = 0;

        while(indiceMin <= indiceMax && !estPresent) {
            indiceMedian = indiceMin + (indiceMax - indiceMin)/2;
            cpttotal++;
            if(pfListe.tabEtu[indiceMedian].nom.compareTo(pfEtudiant.nom) ==0 && pfListe.tabEtu[indiceMedian].prenom.compareTo(pfEtudiant.prenom)==0){
                System.out.println("Le nombre d'occurences total est : " + cpttotal);
                System.out.println("L'étudiant recherché est bien dans la promo.");
                estPresent = true;
                return estPresent;
            } else if((pfListe.tabEtu[indiceMedian].nom.compareTo(pfEtudiant.nom)) > 0  ||
            ((pfListe.tabEtu[indiceMedian].nom.compareTo(pfEtudiant.nom) == 0 ) &&
                (pfListe.tabEtu[indiceMedian].prenom.compareTo(pfEtudiant.prenom) > 0))){
                indiceMax = indiceMedian-1;                
            } else{
                indiceMin = indiceMedian+1;
            }
        }
        System.out.println("L'étudiant n'est pas dans la promo.");
        return estPresent;
    }        

    /** Permet de rechercher un étudiant en utilisant la recherche avec rupture 
     *
     * @param pfPromo IN : le tableau d'étudiants
     * 
     * @param pfEtudiant IN : un étudiant du tableau
     *
     * @return si l'étudiant recherché est présent ou non dans la promo
     */
    public static boolean rechercheAvecRupture(Etudiant pfEtudiant, TNPEtudiants pfPromo){
        System.out.println("\n" + "--> Recherche avec rupture <--");

        boolean estPresent= false;
        int i;
        int compteur=0;
        for(i=0; i<pfPromo.nbEtu && !estPresent; i++){
            compteur++;
            if (pfPromo.tabEtu[i].nom.compareTo(pfEtudiant.nom)==0 && (pfPromo.tabEtu[i].prenom.compareTo(pfEtudiant.prenom)==0 ) ){
                System.out.println("L'étudiant recherché est bien dans la promo.");
                System.out.println("L'étudiant a été trouvé en "+ (compteur) +" tours de boucle");
                estPresent = true;
                return estPresent;
            }
        }
        System.out.println("L'étudiant ne figure pas dans la promo.");

        return estPresent;
    }

    /** Permet de rechercher un étudiant en utilisant la recherche sans rupture 
     *
     * @param pfPromo IN : le tableau d'étudiants
     * 
     * @param pfEtudiant IN : un étudiant du tableau
     *
     * @return si l'étudiant recherché est présent ou non dans la promo
     */
    public static boolean rechercheSansRupture(Etudiant pfEtudiant, TNPEtudiants pfPromo){
        System.out.println("\n" + "--> Recherche sans rupture <--");

        boolean estPresent=false;
        int compteur = 0;

        for(int i=0; i<pfPromo.nbEtu; i++){
            compteur++;
            if (pfPromo.tabEtu[i].nom.compareTo(pfEtudiant.nom)==0 && (pfPromo.tabEtu[i].prenom.compareTo(pfEtudiant.prenom)==0)){
                System.out.println("L'étudiant recherché est bien dans la promo.");
                estPresent = true;
            }              
        }
        if (estPresent == false) {
            System.out.println("L'étudiant n'est pas dans la promo.");
        }
        System.out.println("Le programme a été réalisé en " + compteur + " tours de boucle.");

        return estPresent;
    }

    /** Permet de remplir le tableau d'étudiants
     * 
     * @param pfTableauVide OUT : le tableau vide à remplir, d'étudiants
     * @param pfTableauPlein IN : le tableau plein d'étudiants
     * 
     */
    public static void remplirTableau(TNPEtudiants pfTableauVide, TNPEtudiants pfTableauPlein){
        for(int i = 0; i < pfTableauVide.nbEtu; i++){
            pfTableauVide.tabEtu[i] = pfTableauPlein.tabEtu[i];
        }
    }

    // 5 fonctions de test avec des tableaux de tailles différentes

    /** Fonction de test n°1, avec un tableau de 199 étudiants.
     *
     * @param pfTableau IN : le tableau d'étudiants
     * 
     */
    public static void test1(TNPEtudiants pfTableau){
        System.out.println("\n―--------------------------\nPremière expérience avec un tableau de 199 étudiants.\n―--------------------------");

        Etudiant debut = new Etudiant("Ade", "Jeremy", "1","1B");
        Etudiant milieu = new Etudiant("Hibulaire", "Pat", "3", "3B");
        Etudiant fin = new Etudiant("Zole", "Camille", "1", "1B");
        Etudiant absent = new Etudiant("Mecque", "Muhammad", "666", "666F");

        System.out.println("\nPour une recherche dichotomique, nous avons : ");
        rechercheDicho(debut, pfTableau);
        rechercheDicho(milieu, pfTableau);
        rechercheDicho(fin, pfTableau);
        rechercheDicho(absent, pfTableau);

        System.out.println("\nPour une recherche avec rupture, nous avons : ");
        rechercheAvecRupture(debut, pfTableau);
        rechercheAvecRupture(milieu, pfTableau);
        rechercheAvecRupture(fin, pfTableau);
        rechercheAvecRupture(absent, pfTableau);

        System.out.println("\nPour une recherche sans rupture, nous avons : ");
        rechercheSansRupture(debut, pfTableau);
        rechercheSansRupture(milieu, pfTableau);
        rechercheSansRupture(fin, pfTableau);
        rechercheSansRupture(absent, pfTableau);
    }

    /** Fonction de test n°2, avec un tableau de 100 étudiants.
     *
     * @param pfTableau IN : le tableau d'étudiants
     * 
     */
    public static void test2(TNPEtudiants pfTableau){
        System.out.println("\n―--------------------------\nDeuxième expérience avec un tableau de 100 étudiants.\n―--------------------------");

        Etudiant debut = new Etudiant("Ade", "Jeremy", "1","1B");
        Etudiant milieu = new Etudiant("Desmuguets", "Ivan", "2", "2B");
        Etudiant fin = new Etudiant("Hibulaire", "Pat", "3", "3B");
        Etudiant absent = new Etudiant("Mecque", "Muhammad", "666", "666F");

        System.out.println("\nPour une recherche dichotomique, nous avons : ");
        rechercheDicho(debut, pfTableau);
        rechercheDicho(milieu, pfTableau);
        rechercheDicho(fin, pfTableau);
        rechercheDicho(absent, pfTableau);

        System.out.println("\nPour une recherche avec rupture, nous avons : ");
        rechercheAvecRupture(debut, pfTableau);
        rechercheAvecRupture(milieu, pfTableau);
        rechercheAvecRupture(fin, pfTableau);
        rechercheAvecRupture(absent, pfTableau);

        System.out.println("\nPour une recherche sans rupture, nous avons : ");
        rechercheSansRupture(debut, pfTableau);
        rechercheSansRupture(milieu, pfTableau);
        rechercheSansRupture(fin, pfTableau);
        rechercheSansRupture(absent, pfTableau);
    }

    /** Fonction de test n°3, avec un tableau de 50 étudiants.
     *
     * @param pfTableau IN : le tableau d'étudiants
     * 
     */
    public static void test3(TNPEtudiants pfTableau){
        System.out.println("\n―--------------------------\nTroisième expérience avec un tableau de 50 étudiants.\n―--------------------------");

        Etudiant debut = new Etudiant("Ade", "Jeremy", "1","1B");
        Etudiant milieu = new Etudiant("Cekilepamor", "Sylvie", "2", "2A");
        Etudiant fin = new Etudiant("Desmuguets", "Ivan", "2", "2B");
        Etudiant absent = new Etudiant("Mecque", "Muhammad", "666", "666F");

        System.out.println("\nPour une recherche dichotomique, nous avons : ");
        rechercheDicho(debut, pfTableau);
        rechercheDicho(milieu, pfTableau);
        rechercheDicho(fin, pfTableau);
        rechercheDicho(absent, pfTableau);

        System.out.println("\nPour une recherche avec rupture, nous avons : ");
        rechercheAvecRupture(debut, pfTableau);
        rechercheAvecRupture(milieu, pfTableau);
        rechercheAvecRupture(fin, pfTableau);
        rechercheAvecRupture(absent, pfTableau);

        System.out.println("\nPour une recherche sans rupture, nous avons : ");
        rechercheSansRupture(debut, pfTableau);
        rechercheSansRupture(milieu, pfTableau);
        rechercheSansRupture(fin, pfTableau);
        rechercheSansRupture(absent, pfTableau);
    }

    /** Fonction de test n°4, avec un tableau de 10 étudiants.
     *
     * @param pfTableau IN : le tableau d'étudiants
     * 
     */
    public static void test4(TNPEtudiants pfTableau){
        System.out.println("\n―--------------------------\nQuatrième expérience avec un tableau de 10 étudiants.\n―--------------------------");

        Etudiant debut = new Etudiant("Ade", "Jeremy", "1","1B");
        Etudiant milieu = new Etudiant("Akepourlui", "Yann", "1", "1B");
        Etudiant fin = new Etudiant("Amo", "Nadine", "1", "1B");
        Etudiant absent = new Etudiant("Mecque", "Muhammad", "666", "666F");

        System.out.println("\nPour une recherche dichotomique, nous avons : ");
        rechercheDicho(debut, pfTableau);
        rechercheDicho(milieu, pfTableau);
        rechercheDicho(fin, pfTableau);
        rechercheDicho(absent, pfTableau);

        System.out.println("\nPour une recherche avec rupture, nous avons : ");
        rechercheAvecRupture(debut, pfTableau);
        rechercheAvecRupture(milieu, pfTableau);
        rechercheAvecRupture(fin, pfTableau);
        rechercheAvecRupture(absent, pfTableau);

        System.out.println("\nPour une recherche sans rupture, nous avons : ");
        rechercheSansRupture(debut, pfTableau);
        rechercheSansRupture(milieu, pfTableau);
        rechercheSansRupture(fin, pfTableau);
        rechercheSansRupture(absent, pfTableau);
    }

    /** Fonction de test n°5, avec un tableau de 3 étudiants.
     *
     * @param pfTableau IN : le tableau d'étudiants
     * 
     */
    public static void test5(TNPEtudiants pfTableau){
        System.out.println("\n―--------------------------\nDernière expérience avec un tableau de 3 étudiants.\n―--------------------------");

        Etudiant debut = new Etudiant("Ade", "Jeremy", "1","1B");
        Etudiant milieu = new Etudiant("Afritt", "Barack", "1", "1A");
        Etudiant fin = new Etudiant("Age", "Marie", "1", "1B");
        Etudiant absent = new Etudiant("Mecque", "Muhammad", "666", "666F");

        System.out.println("\nPour une recherche dichotomique, nous avons : ");
        rechercheDicho(debut, pfTableau);
        rechercheDicho(milieu, pfTableau);
        rechercheDicho(fin, pfTableau);
        rechercheDicho(absent, pfTableau);

        System.out.println("\nPour une recherche avec rupture, nous avons : ");
        rechercheAvecRupture(debut, pfTableau);
        rechercheAvecRupture(milieu, pfTableau);
        rechercheAvecRupture(fin, pfTableau);
        rechercheAvecRupture(absent, pfTableau);

        System.out.println("\nPour une recherche sans rupture, nous avons : ");
        rechercheSansRupture(debut, pfTableau);
        rechercheSansRupture(milieu, pfTableau);
        rechercheSansRupture(fin, pfTableau);
        rechercheSansRupture(absent, pfTableau);
    }    

    // Sous programme appelant les différentes fonctions crées précédemment, try catch récupérant les éventuelles erreurs. 
    public static void testPromo() {
        try {
            TNPEtudiants promoE = getListe("listenomssansaccent.csv", ",");
            TNPEtudiants promo1 = new TNPEtudiants(100);
            TNPEtudiants promo2 = new TNPEtudiants(50);
            TNPEtudiants promo3 = new TNPEtudiants(10);
            TNPEtudiants promo4 = new TNPEtudiants(3);

            trierEtudiant(promoE);
            remplirTableau(promo1, promoE);
            remplirTableau(promo2, promoE);
            remplirTableau(promo3, promoE);
            remplirTableau(promo4, promoE);

            test1(promoE);
            test2(promo1);
            test3(promo2);
            test4(promo3);
            test5(promo4);
        } catch (Exception e) {  
            System.out.println("Erreur : " + e.getMessage());
        }       
    }

    // Programme principal
    public static void main() {
        try {
            // Appel de la fonction de lecture du fichier avec le nom du fichier et le séparateur choisi conforme au contenu du csv
            TNPEtudiants promo = getListe("listenomssansaccent.csv", ",");
            trierEtudiant(promo);

            int numEtu = 1;
            // Cette boucle retourne un tableau contenant les noms et prenoms des etudiants de la promotion. Ce tableau est stocké dans promo.
            for (int i = 0 ; i < nbEtudiant(promo) ; i++){   
                System.out.println("Etudiant n°" + numEtu + " : " + promo.tabEtu[i].nom + " " + promo.tabEtu[i].prenom + " " + promo.tabEtu[i].numgroupetd + " " + promo.tabEtu[i].numgroupetp);   
                numEtu++;
            }
            System.out.println("Il y a : " + nbEtudiant(promo) + " personnes.\n");

            testPromo();
        } catch (Exception e) {  
            System.out.println("Erreur : " + e.getMessage());
        } 
    }
}