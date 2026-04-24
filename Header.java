import java.awt.*;
import javax.swing.*;

/**
 * La classe <code>Header</code> représente un composant d'en-tête pour l'interface.
 * 
 * Affiche un titre avec une mise en forme spécifique (police, couleur, dimensions).
 *
 * @version 1.0
 * @author SjujuS
 */
public class Header extends JPanel {

    /**
     * Constructeur qui crée un en-tête avec le titre spécifié.
     *
     * @param title le titre à afficher dans l'en-tête
     */
    public Header(String title) {
        setPreferredSize(new Dimension(600, 60));
        setBackground(new Color(0xA7C7E7));

        JLabel label = new JLabel(title);
        label.setFont(new Font("SansSerif", Font.BOLD, 26));
        label.setForeground(new Color(0x2A3F5F));
        

        add(label);
    }
}
