import javax.swing.*;
import java.awt.*;

/**
 * La classe <code>Carre</code> représente une case du jeu de Démineur.
 * 
 * Affiche une case avec différents états : révélée, cachée, marquée avec un drapeau ou un doute.
 *
 * @version 1.0
 * @author SjujuS
 */
public class Carre extends JComponent {

    /**
     * Indique si la case est révélée.
     */
    private boolean revelee = false;
    
    /**
     * Indique si la case contient une mine.
     */
    private boolean mine = false;
    // private boolean mine = false;
    private int minesAdj = 0;

    private boolean flag = false;   // étoile
    private boolean doute = false;  // ?

    /**
     * Couleur d'une case cachée (non révélée).
     */
    private static final Color HIDDEN = new Color(0xA7C7E7);
    
    /**
     * Couleur d'une case révélée.
     */
    private static final Color REVEALED = new Color(0xDFF2FF);
    
    /**
     * Couleur d'une case révélée qui contient une mine.
     */
    private static final Color MINE = new Color(0xFFB3C6);
    
    /**
     * Couleur de la bordure de la case.
     */
    private static final Color BORDER = new Color(0x6B90B6);

    /**
     * Constructeur qui crée une nouvelle case.
     */
    public Carre() {
        setOpaque(false);
        setPreferredSize(new Dimension(40, 40));
    }

    /**
     * Définit si cette case contient une mine.
     *
     * @param m true si la case contient une mine
     */
    public void setMine(boolean m) { this.mine = m; }
    
    /**
     * Vérifie si cette case contient une mine.
     *
     * @return true si la case contient une mine
     */
    public boolean estMine() { return mine; }

    /**
     * Définit le nombre de mines adjacentes à cette case.
     *
     * @param n le nombre de mines adjacentes
     */
    public void setMinesAdj(int n) { this.minesAdj = n; }
    
    /**
     * Vérifie si cette case est révélée.
     *
     * @return true si la case est révélée
     */
    public boolean isRevealed() { return revelee; }

    /**
     * Vérifie si cette case est marquée d'un drapeau.
     *
     * @return true si la case a un drapeau
     */
    public boolean isFlagged() { return flag; }
    
    /**
     * Vérifie si cette case est marquée en doute.
     *
     * @return true si la case est en doute
     */
    public boolean isDoute() { return doute; }

    /**
     * Fait cycler le marqueur : drapeau → doute → vide.
     * 
     * Cette méthode est appelée lors d'un clic droit sur la case.
     */
    public void toggleRightClick() {
        if (!flag && !doute) {
            flag = true;
        } else if (flag) {
            flag = false;
            doute = true;
        } else {
            doute = false;
        }
        repaint();
    }

    /**
     * Révèle cette case.
     * 
     * Retire les marqueurs (drapeau et doute) et affiche le contenu de la case.
     */
    public void reveler() {
        this.revelee = true;
        flag = false;
        doute = false;
        repaint();
    }

    /**
     * Peint le composant case avec son état actuel.
     * 
     * Affiche le fond, les gémétrique avec les symboles (chiffre, drapeau ou doute).
     *
     * @param g le contexte graphique pour le rendu
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 12;

        // fond
        g2.setColor(!revelee ? HIDDEN : (mine ? MINE : REVEALED));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // chiffre
        if (revelee && !mine && minesAdj > 0) {
            g2.setColor(new Color(0x2A3F5F));
            g2.setFont(new Font("SansSerif", Font.BOLD, 20));

            String txt = String.valueOf(minesAdj);
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(txt)) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 4;

            g2.drawString(txt, x, y);
        }

        // étoile
        if (!revelee && flag) {
            g2.setColor(new Color(0x2A3F5F));
            g2.setFont(new Font("SansSerif", Font.BOLD, 26));
            g2.drawString("★", getWidth()/2 - 8, getHeight()/2 + 10);
        }

        // doute
        if (!revelee && doute) {
            g2.setColor(new Color(0x2A3F5F));
            g2.setFont(new Font("SansSerif", Font.BOLD, 26));
            g2.drawString("?", getWidth()/2 - 5, getHeight()/2 + 10);
        }

        // bordure
        g2.setColor(BORDER);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.dispose();
    }
}
