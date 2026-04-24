import java.awt.Color;
import javax.swing.*;

/**
 * La classe <code>Fenetre</code> représente la fenêtre principale de l'application.
 * 
 * Configure les propriétés de base de la fenêtre (taille, position, couleur de fond).
 *
 * @version 1.0
 * @author SjujuS
 */
public class Fenetre extends JFrame{
    
    /**
     * Constructeur qui initialise la fenêtre avec les paramètres par défaut.
     * 
     * La fenêtre est centrée sur l'écran avec une taille de 600x600 pixels.
     */
    public Fenetre(){
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0xC7DDF2));
    }
}