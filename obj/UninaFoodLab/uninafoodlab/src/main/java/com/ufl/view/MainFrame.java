package com.ufl.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel wrapperPanel;
    private JPanel containerPanel;
    private JPanel errorPanel;
    private JLabel errorLabel;
    private JLayeredPane layeredPane;

    public MainFrame() {
        setTitle("UninaFoodLab");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanel = buildMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    /** Costruisce il panel principale che racchiude tutto */
    public JPanel buildMainPanel() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(buildTopPanel(), BorderLayout.NORTH);
        mainPanel.add(buildLayeredPane(), BorderLayout.CENTER);

        return mainPanel;
    }

    /** Costruisce il panel superiore con titolo e logo */
    public JPanel buildTopPanel() {
        topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("UninaFoodLab");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        topPanel.add(titleLabel, BorderLayout.WEST);

        topPanel.add(buildLogoPanel(), BorderLayout.EAST);
        return topPanel;
    }

    /** Costruisce il panel del logo */
    public JPanel buildLogoPanel() {
        JPanel logoPanel = new JPanel();
        logoPanel.setPreferredSize(new Dimension(80, 70));
        logoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JLabel logoLabel = new JLabel("Logo\nImmagine");
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoPanel.add(logoLabel);
        return logoPanel;
    }

    /** JLayeredPane che contiene wrapper (sotto) ed errorPanel (overlay in primo piano) */
    public JLayeredPane buildLayeredPane() {
        layeredPane = new JLayeredPane() {
            @Override
            public void doLayout() {
                int w = getWidth();
                int h = getHeight();
                if (wrapperPanel != null)
                    wrapperPanel.setBounds(0, 0, w, h);
                if (errorPanel != null) {
                    int hPad = (int)(w * 0.15);
                    int errH = errorPanel.getPreferredSize().height;
                    errorPanel.setBounds(hPad, h - errH - 10, w - hPad * 2, errH);
                }
            }
        };
        layeredPane.add(buildWrapperPanel(), JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(buildErrorPanel(), JLayeredPane.PALETTE_LAYER);
        return layeredPane;
    }

    /** Wrapper trasparente che posiziona containerPanel con margini proporzionali */
    public JPanel buildWrapperPanel() {
        wrapperPanel = new JPanel(null) {
            @Override
            public void doLayout() {
                int hPad = (int)(getWidth()  * 0.10);
                int vPad = (int)(getHeight() * 0.05);
                for (Component c : getComponents()) {
                    c.setBounds(hPad, vPad, getWidth() - hPad * 2, getHeight() - vPad * 2);
                }
            }
        };
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.add(buildContainerPanel());
        return wrapperPanel;
    }

    /** Costruisce il container verde principale */
    public JPanel buildContainerPanel() {
        containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(UiUtil.COLORE_SFONDO);
        containerPanel.setBorder(BorderFactory.createLineBorder(UiUtil.COLORE_PRIMARIO, 3));
        containerPanel.add(buildContentPanel(), BorderLayout.CENTER);
        return containerPanel;
    }

    /** Sostituisce il contenuto del container con il panel fornito */
    public void setContent(JPanel panel) {
        containerPanel.removeAll();
        containerPanel.add(panel, BorderLayout.CENTER);
        containerPanel.revalidate();
        containerPanel.repaint();
    }

    
    public JPanel buildContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(UiUtil.COLORE_SFONDO);
        contentPanel.add(new JLabel("Contenuto principale - personalizza qui"));
        return contentPanel;
    }

    /** Costruisce il panel degli errori (invisibile di default) */
    public JPanel buildErrorPanel() {
        errorPanel = new JPanel(new BorderLayout());
        errorPanel.setBackground(new Color(255, 240, 240));
        errorPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        errorLabel = new JLabel("Errore");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        errorPanel.add(errorLabel, BorderLayout.CENTER);

        JLabel closeErrorBtn = new JLabel("X");
        closeErrorBtn.setForeground(Color.RED);
        closeErrorBtn.setFont(new Font("Arial", Font.BOLD, 18));
        closeErrorBtn.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        closeErrorBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeErrorBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { hideError(); }
        });
        errorPanel.add(closeErrorBtn, BorderLayout.EAST);

        errorPanel.setVisible(false);
        return errorPanel;
    }

    
    public void showError(String message) {
        errorLabel.setText("Errore : " + message);
        errorPanel.setVisible(true);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    /** Nasconde il panel errore */
    public void hideError() {
        errorPanel.setVisible(false);
        layeredPane.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}