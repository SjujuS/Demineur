import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * La classe <code>EcranJeu</code> gère l'interface graphique du jeu de Démineur.
 * 
 * Elle crée et affiche la grille de cases cliquables, gère les interactions utilisateur
 * (clics gauche et droit), et met à jour la grille en fonction du moteur de jeu.
 *
 * @version 1.0
 * @author SjujuS
 */
public class EcranJeu {

    /**
     * Matrice de cases visuelles représentant la grille du jeu.
     */
    private Carre[][] visuel;

    /**
     * Constructeur qui crée et initialise l'écran de jeu.
     * 
     * Affiche la grille, initialise les interactions de souris, et restaure
     * l'état sauvegardé si une partie était en cours.
     *
     * @param moteur le moteur de jeu contenant l'état de la grille
     */
    public EcranJeu(Mines moteur) {

        int L = moteur.getNbLignes();
        int H = moteur.getNbColonnes();

        Fenetre f = new Fenetre();
        f.setLayout(new BorderLayout());

        // HEADER + COMPTEUR
        JPanel top = new JPanel(new GridLayout(2, 1));
        top.setOpaque(true);
        top.setBackground(new Color(0xA7C7E7));
        top.add(new Header("Démineur"));

        JLabel compteur = new JLabel("Mines restantes : " + moteur.getMinesRestantes(), SwingConstants.CENTER);
        compteur.setFont(new Font("SansSerif", Font.PLAIN, 18));
        compteur.setForeground(new Color(0x2A3F5F));
        top.add(compteur);
        f.add(top, BorderLayout.NORTH);

        // GRILLE
        JPanel grillePanel = new JPanel(new GridLayout(L, H));
        grillePanel.setBackground(new Color(0xE8F4FF));
        visuel = new Carre[L][H];

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < H; j++) {

                Carre c = new Carre();
                c.setMine(moteur.estMine(i, j));
                c.setMinesAdj(moteur.getAdj(i, j));

                // Restaurer l'état depuis la sauvegarde
                if (moteur.estRevelee(i, j)) {
                    c.reveler();
                }
                if (moteur.estFlag(i, j)) {
                    c.toggleRightClick(); // remet l'étoile
                } else if (moteur.estDoute(i, j)) {
                    c.toggleRightClick(); // remet étoile
                    c.toggleRightClick(); // puis passe en doute
                }

                final int x = i;
                final int y = j;

                c.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        // CLIC DROIT
                        if (SwingUtilities.isRightMouseButton(e)) {
                            if (c.isRevealed()) return;

                            boolean etaitFlag = c.isFlagged();
                            c.toggleRightClick();

                            // Synchroniser dans le modèle
                            moteur.setFlag(x, y, c.isFlagged());
                            moteur.setDoute(x, y, c.isDoute());

                            if (!etaitFlag && c.isFlagged()) {
                                moteur.incrementerMarqueur();
                            } else if (etaitFlag && !c.isFlagged()) {
                                moteur.decrementerMarqueur();
                            }

                            compteur.setText("Mines restantes : " + moteur.getMinesRestantes());
                            return;
                        }

                        // CLIC GAUCHE
                        if (!SwingUtilities.isLeftMouseButton(e)) return;
                        if (c.isFlagged() || c.isDoute()) return;
                        if (c.isRevealed()) return;

                        if (c.estMine()) {
                            c.reveler();
                            moteur.revelerCase(x, y);
                            afficherDefaite(f);
                            Sauvegarde.supprimerSauvegarde();
                            return;
                        }

                        propager(x, y, moteur);

                        if (verifierVictoire(moteur)) {
                            afficherVictoire(f);
                            Sauvegarde.supprimerSauvegarde();
                        }
                    }
                });

                visuel[i][j] = c;
                grillePanel.add(c);
            }
        }

        // BOUTON SAUVEGARDE
        CreateButton saveButton = new CreateButton("Sauvegarder et Quitter");
        saveButton.addActionListener(e -> {
            Sauvegarde.sauvegarder(moteur);
            System.exit(0);
        });

        f.add(grillePanel, BorderLayout.CENTER);
        f.add(saveButton, BorderLayout.SOUTH);
        f.setVisible(true);
    }

    /**
     * Propage la révélation des cases en cascades de manière récursive.
     * 
     * Révèle les cases adjacentes jusqu'à atteindre des cases avec des mines voisines.
     * Cette méthode est appelée après un clic gauche sur une case vide.
     *
     * @param x la ligne de la case
     * @param y la colonne de la case
     * @param moteur le moteur de jeu
     */
    private void propager(int x, int y, Mines moteur) {
        if (!moteur.estDansGrille(x, y)) return;

        Carre c = visuel[x][y];
        if (c.isRevealed() || c.isFlagged() || c.isDoute()) return;

        c.reveler();
        moteur.revelerCase(x, y); // synchroniser le modèle

        if (moteur.getAdj(x, y) > 0) return;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    propager(x + dx, y + dy, moteur);
                }
            }
        }
    }

    /**
     * Vérifie si le joueur a remporté la partie.
     * 
     * La victoire est atteinte quand toutes les cases non-mines sont révélées.
     *
     * @param moteur le moteur de jeu
     * @return true si la victoire est atteinte, false sinon
     */
    private boolean verifierVictoire(Mines moteur) {
        for (int i = 0; i < moteur.getNbLignes(); i++) {
            for (int j = 0; j < moteur.getNbColonnes(); j++) {
                if (!moteur.estMine(i, j) && !visuel[i][j].isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Affiche le message de victoire et retourne à l'écran d'accueil.
     *
     * @param f la fenêtre du jeu
     */
    private void afficherVictoire(Fenetre f) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane optionPane = new JOptionPane("🎉 Bravo ! Vous avez gagné !", JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = optionPane.createDialog(f, "Victoire");
            dialog.setLocationRelativeTo(f);
            dialog.setVisible(true);
            f.dispose();
            new Accueil();
        });
    }

    /**
     * Affiche le message de défaite et retourne à l'écran d'accueil.
     *
     * @param f la fenêtre du jeu
     */
    private void afficherDefaite(Fenetre f) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane optionPane = new JOptionPane("💥 Boom ! Perdu !", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog(f, "Défaite");
            dialog.setLocationRelativeTo(f);
            dialog.setVisible(true);
            f.dispose();
            new Accueil();
        });
    }
}