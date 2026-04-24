import javax.swing.*;
import java.awt.*;

/**
 * La classe <code>CreateButton</code> représente un bouton personnalisé avec un design
 * métier spécifique.
 * 
 * Le bouton offre un effet de survol et s'affiche avec des angles arrondis.
 *
 * @version 1.0
 * @author SjujuS
 */
public class CreateButton extends JButton {

    /**
     * Couleur de fond du bouton.
     */
    private static final Color BG = new Color(0xA7C7E7);
    
    /**
     * Couleur de fond du bouton au survol.
     */
    private static final Color BG_HOVER = new Color(0xBBD9F5);
    
    /**
     * Couleur de la bordure du bouton.
     */
    private static final Color BORDER = new Color(0x6B90B6);
    
    /**
     * Couleur du texte du bouton.
     */
    private static final Color TEXT = new Color(0x2A3F5F);

    /**
     * Constructeur qui crée un bouton avec le texte spécifié.
     *
     * @param text le texte à afficher sur le bouton
     */
    public CreateButton(String text) {
        super(text);

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setForeground(TEXT);
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Peint le composant bouton avec un rendu personnalisé.
     * 
     * Affiche le bouton avec un fond, une bordure arrondie et gère l'effet de survol.
     *
     * @param g le contexte graphique pour le rendu
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 20;

        g2.setColor(getModel().isRollover() ? BG_HOVER : BG);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.setColor(BORDER);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // Texte centré
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent()) / 2 - 3;

        g2.setColor(TEXT);
        g2.drawString(getText(), x, y);

        g2.dispose();
    }
}
