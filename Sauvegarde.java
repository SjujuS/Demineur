import java.io.*;

/**
 * La classe <code>Sauvegarde</code> gère la sérialisation et la désérialisation
 * de l'état du jeu (grille de mines).
 * 
 * Elle permet de sauvegarder la partie en cours et de la reprendre ultérieurement.
 *
 * @version 1.0
 * @author SjujuS
 */
public class Sauvegarde {

    /**
     * Nom du fichier de sauvegarde utilisé pour la sérialisation.
     */
    private static final String FICHIER_SAUVEGARDE = "sauvegarde.ser";

    /**
     * Sauvegarde l'état du jeu dans un fichier binaire.</n
     *
     * @param mines l'objet Mines contenant l'état du jeu à sauvegarder
     */
    public static void sauvegarder(Mines mines) {
    try (ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream(FICHIER_SAUVEGARDE))) {
        oos.writeObject(mines);
    } catch (IOException e) {
        System.err.println("Erreur : " + e.getMessage());
    }
}

    // Charger la grille depuis le fichier
    public static Mines charger() {
    try (ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream(FICHIER_SAUVEGARDE))) {
        Mines mines = (Mines) ois.readObject();
        return mines;
    } catch (IOException | ClassNotFoundException e) {
        System.err.println("Erreur chargement : " + e.getMessage());
        return null;
    }
}

    // Vérifier si une sauvegarde existe
    public static boolean sauvegardeExiste() {
        return new File(FICHIER_SAUVEGARDE).exists();
    }

    /**
     * Supprime le fichier de sauvegarde.
     * 
     * Cette méthode est appelée généralement en fin de partie (victoire ou défaite).
     */
    public static void supprimerSauvegarde() {
        new File(FICHIER_SAUVEGARDE).delete();
    }
}
