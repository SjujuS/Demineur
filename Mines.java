import java.io.Serializable;
import java.util.Random;

/**
 * La classe <code>Mines</code> représente le moteur du jeu de Démineur.
 * 
 * Elle gère la grille de jeu, le placement des mines, le calcul des adjacences,
 * et l'état des cases (révélées, marquées, en doute).
 *
 * @version 1.0
 * @author Lisa Zouba
 */
public class Mines implements Serializable {

    /**
     * Nombre de mines à placer sur la grille.
     */
    private int nbMines;
    
    /**
     * Nombre de lignes de la grille.
     */
    private int nbLignes;
    
    /**
     * Nombre de colonnes de la grille.
     */
    private int nbColonnes;

    /**
     * Matrice booléenne indiquant la position des mines.
     */
    private boolean[][] mines;
    
    /**
     * Matrice booléenne indiquant les cases révélées.
     */
    private boolean[][] revelee;
    
    /**
     * Matrice booléenne indiquant les cases marquées d'un drapeau.
     */
    private boolean[][] flag;
    
    /**
     * Matrice booléenne indiquant les cases marquées en doute.
     */
    private boolean[][] doute;
    
    /**
     * Matrice d'entiers indiquant le nombre de mines adjacentes pour chaque case.
     * -1 si la case contient une mine, sinon le nombre de mines voisines (0-8).
     */
    private int[][] adj;

    /**
     * Nombre de marqueurs (drapeaux) placés sur la grille.
     */
    private int nbMarqueurs = 0;

    /**
     * Constructeur qui initialise une grille de Démineur avec les paramètres spécifiés.
     *
     * @param nbMines le nombre de mines à placer sur la grille
     * @param nbLignes le nombre de lignes de la grille
     * @param nbColonnes le nombre de colonnes de la grille
     */
    public Mines(int nbMines, int nbLignes, int nbColonnes) {
        this.nbMines = nbMines;
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;

        mines   = new boolean[nbLignes][nbColonnes];
        revelee = new boolean[nbLignes][nbColonnes];
        flag    = new boolean[nbLignes][nbColonnes];
        doute   = new boolean[nbLignes][nbColonnes];
        adj     = new int[nbLignes][nbColonnes];

        placerMines();
        calculerAdjacences();
    }

    /**
     * Place aléatoirement les mines sur la grille.
     * 
     * Assure qu'aucune mine ne soit placée deux fois au même emplacement.
     */
    private void placerMines() {
        Random r = new Random();
        int placees = 0;

        while (placees < nbMines) {
            int x = r.nextInt(nbLignes);
            int y = r.nextInt(nbColonnes);

            if (!mines[x][y]) {
                mines[x][y] = true;
                placees++;
            }
        }
    }

    /**
     * Calcule le nombre de mines adjacentes pour chaque case de la grille.
     * 
     * Pour chaque case non-mine, compte le nombre de mines parmi ses 8 voisins.
     * Les mines elles-mêmes ont une valeur d'adjacence de -1.
     */
    private void calculerAdjacences() {
        for (int x = 0; x < nbLignes; x++) {
            for (int y = 0; y < nbColonnes; y++) {

                if (mines[x][y]) {
                    adj[x][y] = -1;
                    continue;
                }

                int c = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx == 0 && dy == 0) continue;
                        int nx = x + dx;
                        int ny = y + dy;
                        if (estDansGrille(nx, ny) && mines[nx][ny]) c++;
                    }
                }
                adj[x][y] = c;
            }
        }
    }

    /**
     * Retourne le nombre de lignes de la grille.
     *
     * @return le nombre de lignes
     */
    public int getNbLignes()        { return nbLignes; }
    
    /**
     * Retourne le nombre de colonnes de la grille.
     *
     * @return le nombre de colonnes
     */
    public int getNbColonnes()      { return nbColonnes; }
    
    /**
     * Retourne le nombre total de mines sur la grille.
     *
     * @return le nombre de mines
     */
    public int getNbMines()         { return nbMines; }
    
    /**
     * Vérifie si une case contient une mine.
     *
     * @param x la ligne de la case
     * @param y la colonne de la case
     * @return true si la case contient une mine, false sinon
     */
    public boolean estMine(int x, int y)        { return mines[x][y]; }
    
    /**
     * Vérifie si les coordonnées sont à l'intérieur de la grille.
     *
     * @param x la ligne
     * @param y la colonne
     * @return true si les coordonnées sont valides, false sinon
     */
    public boolean estDansGrille(int x, int y)  { return x >= 0 && y >= 0 && x < nbLignes && y < nbColonnes; }
    
    /**
     * Retourne le nombre de mines adjacentes à une case.
     *
     * @param x la ligne de la case
     * @param y la colonne de la case
     * @return le nombre de mines adjacentes (-1 si c'est une mine)
     */
    public int getAdj(int x, int y)             { return adj[x][y]; }
    
    /**
     * Incrémente le nombre de marqueurs (drapeaux) placés.
     */
    public void incrementerMarqueur()           { nbMarqueurs++; }
    
    /**
     * Décrémente le nombre de marqueurs (drapeaux) placés.
     */
    public void decrementerMarqueur()           { if (nbMarqueurs > 0) nbMarqueurs--; }
    
    /**
     * Retourne le nombre de mines restantes non marquées.
     *
     * @return le nombre de mines restantes (nbMines - nbMarqueurs)
     */
    public int getMinesRestantes()              { return nbMines - nbMarqueurs; }

    /**
     * Vérifie si une case est révélée.
     *
     * @param x la ligne de la case
     * @param y la colonne de la case
     * @return true si la case est révélée, false sinon
     */
    public boolean estRevelee(int x, int y)  { return revelee[x][y]; }
    
    /**
     * Vérifie si une case est marquée d'un drapeau.
     *
     * @param x la ligne de la case
     * @param y la colonne de la case
     * @return true si la case est marquée d'un drapeau, false sinon
     */
    public boolean estFlag(int x, int y)     { return flag[x][y]; }
    
    /**
     * Vérifie si une case est marquée en doute.
     *
     * @param x la ligne de la case
     * @param y la colonne de la case
     * @return true si la case est marquée en doute, false sinon
     */
    public boolean estDoute(int x, int y)    { return doute[x][y]; }

    /**
     * Révèle une case sur la grille.
     *
     * @param x la ligne de la case
     * @param y la colonne de la case
     */
    public void revelerCase(int x, int y) {
        revelee[x][y] = true;
    }

    /**
     * Définit ou retire le drapeau d'une case.
     *
     * @param x la ligne de la case
     * @param y la colonne de la case
     * @param val true pour ajouter le drapeau, false pour le retirer
     */
    public void setFlag(int x, int y, boolean val) {
        flag[x][y] = val;
    }

    /**
     * Définit ou retire la marque de doute d'une case.
     *
     * @param x la ligne de la case
     * @param y la colonne de la case
     * @param val true pour ajouter la marque de doute, false pour la retirer
     */
    public void setDoute(int x, int y, boolean val) {
        doute[x][y] = val;
    }
}