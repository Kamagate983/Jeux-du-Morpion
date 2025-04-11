import javax.swing.*;
import java.awt.*;

public class morpionGUI extends JFrame {
    private JButton[][] boutons = new JButton[3][3];
    private String joueurActuel = "X";
    private JLabel labelStatus = new JLabel("Tour du joueur X");
    private JLabel labelScore = new JLabel("Score - X: 0 | O: 0");
    private int scoreX = 0, scoreO = 0;

    public morpionGUI() {
        setTitle("Jeu du Morpion");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelGrille = new JPanel(new GridLayout(3, 3));
        JPanel panelHaut = new JPanel(new GridLayout(3, 1));

        initJeu(panelGrille);

        labelStatus.setHorizontalAlignment(JLabel.CENTER);
        labelStatus.setFont(new Font("Arial", Font.BOLD, 18));

        labelScore.setHorizontalAlignment(JLabel.CENTER);
        labelScore.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel titre = new JLabel("Bienvenue dans le jeu du Morpion !", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setForeground(new Color(0, 102, 204));

        JButton btnRejouer = new JButton("Rejouer la partie");
        btnRejouer.setFont(new Font("Arial", Font.BOLD, 16));
        btnRejouer.setBackground(new Color(255, 204, 102));
        btnRejouer.addActionListener(e -> resetJeu());

        panelHaut.add(titre);
        panelHaut.add(labelStatus);
        panelHaut.add(labelScore);

        add(panelHaut, BorderLayout.NORTH);
        add(panelGrille, BorderLayout.CENTER);
        add(btnRejouer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initJeu(JPanel panel) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boutons[i][j] = new JButton("");
                boutons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                boutons[i][j].setFocusPainted(false);
                boutons[i][j].setBackground(Color.WHITE);
                final int ligne = i;
                final int colonne = j;
                boutons[i][j].addActionListener(e -> {
                    if (boutons[ligne][colonne].getText().equals("")) {
                        boutons[ligne][colonne].setText(joueurActuel);
                        boutons[ligne][colonne].setForeground(joueurActuel.equals("X") ? Color.BLUE : Color.RED);
                        if (verifierVictoire()) {
                            JOptionPane.showMessageDialog(this, "🎉 Félicitations ! Le joueur " + joueurActuel + " a gagné ! 🎉");
                            if (joueurActuel.equals("X")) scoreX++; else scoreO++;
                            updateScore();
                            afficherMeilleurJoueur();
                            resetJeu();
                        } else if (matchNul()) {
                            JOptionPane.showMessageDialog(this, "Match nul ! 😕 Aucun gagnant cette fois.");
                            afficherMeilleurJoueur();
                            resetJeu();
                        } else {
                            joueurActuel = joueurActuel.equals("X") ? "O" : "X";
                            labelStatus.setText("Tour du joueur " + joueurActuel);
                        }
                    }
                });
                panel.add(boutons[i][j]);
            }
        }
    }

    private boolean verifierVictoire() {
        for (int i = 0; i < 3; i++) {
            if (boutons[i][0].getText().equals(joueurActuel) &&
                    boutons[i][1].getText().equals(joueurActuel) &&
                    boutons[i][2].getText().equals(joueurActuel)) return true;
            if (boutons[0][i].getText().equals(joueurActuel) &&
                    boutons[1][i].getText().equals(joueurActuel) &&
                    boutons[2][i].getText().equals(joueurActuel)) return true;
        }
        if (boutons[0][0].getText().equals(joueurActuel) &&
                boutons[1][1].getText().equals(joueurActuel) &&
                boutons[2][2].getText().equals(joueurActuel)) return true;
        if (boutons[0][2].getText().equals(joueurActuel) &&
                boutons[1][1].getText().equals(joueurActuel) &&
                boutons[2][0].getText().equals(joueurActuel)) return true;
        return false;
    }

    private boolean matchNul() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (boutons[i][j].getText().equals("")) return false;
        return true;
    }

    private void resetJeu() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                boutons[i][j].setText("");
                boutons[i][j].setBackground(Color.WHITE);
            }
        joueurActuel = "X";
        labelStatus.setText("Tour du joueur " + joueurActuel);
    }

    private void updateScore() {
        labelScore.setText("Score - X: " + scoreX + " | O: " + scoreO);
    }

    private void afficherMeilleurJoueur() {
        String meilleur;
        if (scoreX > scoreO) {
            meilleur = "🏆 Le meilleur joueur est X avec " + scoreX + " victoire(s) !";
        } else if (scoreO > scoreX) {
            meilleur = "🏆 Le meilleur joueur est O avec " + scoreO + " victoire(s) !";
        } else {
            meilleur = "⚔ Égalité parfaite entre X et O !";
        }
        JOptionPane.showMessageDialog(this, meilleur);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(morpionGUI::new);
    }
}