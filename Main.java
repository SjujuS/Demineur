import java.awt.Font;
import javax.swing.UIManager;

/**
 * La classe <code>Main</code> est le point d'entrée de l'application Démineur.
 * 
 * Elle initialise les paramètres graphiques de l'application et lance l'écran d'accueil.
 *
 * @version 1.0
 * @author SjujuS
 */
public class Main {
    
    /**
     * Méthode principale de l'application.
     * 
     * Configure les polices de caractères par défaut et lance l'interface d'accueil.
     *
     * @param args les arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 18));
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 18));
        new Accueil();
    }
}
