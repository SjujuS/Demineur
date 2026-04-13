import javax.swing.*;
// import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe <code>ActionAccueil</code> gère les événements des boutons de l'écran d'accueil.
 * 
 * Implémente les actions pour démarrer une nouvelle partie, reprendre une sauvegarde ou quitter l'application.
 *
 * @version 1.0
 * @author Julie Picart
 */
public class ActionAccueil implements ActionListener {

    /**
     * La fenêtre d'accueil liée à ce gestionnaire d'événements.
     */
    private final Fenetre fenetreAccueil; 

    /**
     * Constructeur qui crée un gestionnaire d'événements pour l'accueil.
     *
     * @param fa la fenêtre d'accueil
     */
    public ActionAccueil(Fenetre fa) {
        this.fenetreAccueil = fa;
    }

    /**
     * Gère l'événement d'action d'un bouton.
     * 
     * Selon le bouton cliqué, lance une nouvelle partie, reprend une sauvegarde ou quitte l'application.
     *
     * @param e l'événement d'action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = ((JButton) e.getSource()).getText();
        if (action == "Nouvelle partie") {
            new EcranChoix();
            fenetreAccueil.dispose();
        }
        if (action == "Reprendre la partie" ) {
            Mines grille = Sauvegarde.charger();
            new EcranJeu(grille);
            fenetreAccueil.dispose();
        }
        if (action == "Quitter") {
            System.exit(0);
        }

    }

    
}