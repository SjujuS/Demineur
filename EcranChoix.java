import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * La classe <code>EcranChoix</code> gère l'écran de sélection des paramètres de jeu.
 * 
 * Elle permet à l'utilisateur de définir la largeur, la hauteur et le nombre de mines
 * avant de lancer une nouvelle partie.
 *
 * @version 1.0
 * @author SjujuS
 */
public class EcranChoix implements ActionListener {

    /**
     * Champ de texte pour la largeur de la grille.
     */
    private static JTextField largeur;
    
    /**
     * Champ de texte pour la hauteur de la grille.
     */
    private static JTextField hauteur;
    
    /**
     * Champ de texte pour le nombre de mines.
     */
    private static JTextField mines;
    
    /**
     * La fenêtre de l'écran de choix.
     */
    protected Fenetre f = new Fenetre();
    
    /**
     * Bouton pour lancer une nouvelle partie.
     */
    private CreateButton boutonLancer;
    
    /**
     * Bouton pour annuler et retourner à l'accueil.
     */
    private CreateButton boutonAnnuler;

    /**
     * Constructeur qui crée et initialise l'écran de choix des paramètres.
     * 
     * Affiche un formulaire avec champs de saisie et boutons d'action.
     */
    public EcranChoix() {
        f.setLayout(new GridLayout(4, 2, 15, 15));
        f.add(new JLabel("Largeur :"));
        largeur = new JTextField("10");
        f.add(largeur);

        f.add(new JLabel("Hauteur :"));
        hauteur = new JTextField("10");
        f.add(hauteur);

        f.add(new JLabel("Mines :"));
        mines = new JTextField("10");
        f.add(mines);

        boutonLancer = new CreateButton("Lancer");
        boutonAnnuler = new CreateButton("Annuler");

        boutonLancer.addActionListener(this);
        boutonAnnuler.addActionListener(this);

        f.add(boutonLancer);
        f.add(boutonAnnuler);

        f.setVisible(true);
    }

    /**
     * Gère les événements des boutons de l'écran.
     * 
     * Si le bouton "Lancer" est cliqué, crée une nouvelle partie avec les paramètres spécifiés.
     * Si le bouton "Annuler" est cliqué, revient à l'écran d'accueil.
     *
     * @param e l'événement de clic bouton
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonLancer) {
            int L = Integer.parseInt(largeur.getText());
            int H = Integer.parseInt(hauteur.getText());
            int M = Integer.parseInt(mines.getText());

            Mines moteur = new Mines(M, L, H);
            new EcranJeu(moteur);
            f.dispose();

        } else if (e.getSource() == boutonAnnuler) {
            new Accueil();
            f.dispose();
        }
    }
    
    /**
     * Retourne le nombre de mines saisi par l'utilisateur.
     *
     * @return le nombre de mines
     */
    public static int getMines(){
        return Integer.parseInt(mines.getText());
    }
    
    /**
     * Retourne la largeur saisie par l'utilisateur.
     *
     * @return la largeur de la grille
     */
    public static int getLargeur(){
        return Integer.parseInt(largeur.getText());
    }
    
    /**
     * Retourne la hauteur saisie par l'utilisateur.
     *
     * @return la hauteur de la grille
     */
    public static int getHauteur(){
        return Integer.parseInt(hauteur.getText());
    }
}
