import java.awt.*;
import javax.swing.*;

/**
 * La classe <code>Accueil</code> représente l'écran d'accueil de l'application Démineur.
 * 
 * Affiche le titre du jeu et trois boutons : démarrer une nouvelle partie, reprendre une partie,
 * ou quitter l'application.
 *
 * @version 1.0
 * @author Julie Picart
 */
public class Accueil {
    
    /**
     * Constructeur qui crée et affiche l'écran d'accueil.
     * 
     * Initialise l'interface avec le titre et les boutons d'action.
     * Le bouton "Reprendre la partie" est désactivé si aucune sauvegarde n'existe.
     */
    public Accueil() {
        Fenetre f = new Fenetre();
        f.setLayout(new BorderLayout());
        // --- TITRE ---
        JLabel texteAccueil = new JLabel("Démineur", SwingConstants.CENTER);
        texteAccueil.setFont(new Font("SansSerif", Font.BOLD, 48));
        texteAccueil.setForeground(new Color(0x2A3F5F)); // bleu profond
        texteAccueil.setOpaque(true);
        texteAccueil.setBackground(new Color(0xC7DDF2)); // pastel bleu
        f.add(texteAccueil, BorderLayout.CENTER);

        // --- PANEL DES BOUTONS ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 0, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 80, 40, 80));

        // Fond pastel derrière les boutons
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(new Color(0xC7DDF2)); // pastel bleu

        // Boutons pastel
        CreateButton boutonJouer = new CreateButton("Nouvelle partie");
        CreateButton boutonContinuer = new CreateButton("Reprendre la partie");
        CreateButton boutonQuitter = new CreateButton("Quitter");

        buttonPanel.add(boutonJouer);
        buttonPanel.add(boutonContinuer);
        buttonPanel.add(boutonQuitter);

        // Désactiver continuer si pas de sauvegarde
        boolean partieSauvegarder = Sauvegarde.sauvegardeExiste();
        boutonContinuer.setEnabled(partieSauvegarder);

        // Listener
        ActionAccueil listener = new ActionAccueil(f);
        boutonJouer.addActionListener(listener);
        boutonContinuer.addActionListener(listener);
        boutonQuitter.addActionListener(listener);

        f.add(buttonPanel, BorderLayout.SOUTH);
        f.setVisible(true);
    }
}