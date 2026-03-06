package com.ufl.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainFrame extends JFrame {

    // Window Configuration
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;
    

    // Border and Padding
    private static final int MAIN_PANEL_BORDER = 10;
    private static final int MAIN_PANEL_GAP = 10;
    private static final int TOP_PANEL_GAP = 5;

    // Logo Configuration
    private static final int LOGO_WIDTH = 80;
    private static final int LOGO_HEIGHT = 70;

    // Container Margins (Percentages)
    private static final double CONTAINER_HORIZONTAL_MARGIN_PERCENT = 0.10;
    private static final double CONTAINER_VERTICAL_MARGIN_PERCENT = 0.05;
    private static final int CONTAINER_BORDER_THICKNESS = 3;

    // InfoLog Margins Configuration
    private static final double INFO_LOG_HORIZONTAL_MARGIN_PERCENT = 0.15;
    private static final int INFO_LOG_BOTTOM_OFFSET = 10;
    private static final int INFO_LOG_BORDER_THICKNESS = 2;

    // InfoLog Configuration
    private static final String INFO_LOG_TYPE_ERROR = "ERR";
    private static final String INFO_LOG_TYPE_SUCCESS = "SUCC";
    private static final Color INFO_LOG_SUCCESS_COLOR = new Color(240, 255, 240);
    private static final Color INFO_LOG_ERROR_COLOR = new Color(255, 240, 240);
    private static final Color INFO_LOG_VOID_COLOR = new Color(0, 0, 0, 0); // Trasparente

    // Font Configuration
    private static final int TITLE_FONT_SIZE = 28;
    private static final int ERROR_LABEL_FONT_SIZE = 14;
    private static final int ERROR_BUTTON_FONT_SIZE = 18;

    // Color Configuration
    private static final int CLOSE_BUTTON_WIDTH = 20;
    private static final int CLOSE_BUTTON_HEIGHT = 20;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel containerPanel;
    private JPanel infoLogPanel;
    private JLabel infoLogLabel;
    private JLabel closeInfoLogBtn;
    private JLayeredPane layeredPane;

    public MainFrame() {
        setTitle("UninaFoodLab");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanel = buildMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    /** Costruisce il panel principale che racchiude tutto */
    public JPanel buildMainPanel() {
        mainPanel = new JPanel(new BorderLayout(MAIN_PANEL_GAP, MAIN_PANEL_GAP));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(MAIN_PANEL_BORDER, MAIN_PANEL_BORDER, MAIN_PANEL_BORDER, MAIN_PANEL_BORDER));

        mainPanel.add(buildTopPanel(), BorderLayout.NORTH);
        mainPanel.add(buildLayeredPane(), BorderLayout.CENTER);

        return mainPanel;
    }

    /** Costruisce il panel superiore con titolo e logo */
    public JPanel buildTopPanel() {
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, TOP_PANEL_GAP, 0));
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(buildLogoPanel());

        JLabel titleLabel = new JLabel("UninaFoodLab");
        titleLabel.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, TITLE_FONT_SIZE));
        centerPanel.add(titleLabel);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        return topPanel;
    }

    /** Costruisce il panel del logo */
    public JPanel buildLogoPanel() {
        JPanel logoPanel = new JPanel();
        logoPanel.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));
        JLabel logoLabel = new JLabel();
        logoPanel.setOpaque(false); // Rende il pannello trasparente per mostrare solo il logo in png
        logoLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/Logo.png")).getImage().getScaledInstance(LOGO_WIDTH, LOGO_HEIGHT, java.awt.Image.SCALE_SMOOTH))); //Inserimento del logo ridimensionato
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoPanel.add(logoLabel);
        return logoPanel;
    }

    /** JLayeredPane che contiene containerPanel (sotto) ed errorPanel (overlay in primo piano) */
    public JLayeredPane buildLayeredPane() {
        layeredPane = new JLayeredPane() {
            @Override
            public void doLayout() {
                int w = getWidth();
                int h = getHeight();
                if (containerPanel != null) {
                    int hPad = (int)(w * CONTAINER_HORIZONTAL_MARGIN_PERCENT);
                    int vPad = (int)(h * CONTAINER_VERTICAL_MARGIN_PERCENT);
                    containerPanel.setBounds(hPad, vPad, w - hPad * 2, h - vPad * 2);
                }
                if (infoLogPanel != null) {
                    int hPad = (int)(w * INFO_LOG_HORIZONTAL_MARGIN_PERCENT);
                    int errH = infoLogPanel.getPreferredSize().height;
                    infoLogPanel.setBounds(hPad, h - errH - INFO_LOG_BOTTOM_OFFSET, w - hPad * 2, errH);
                }
            }
        };
        layeredPane.add(buildContainerPanel(), JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(buildInfoLogPanel(), JLayeredPane.PALETTE_LAYER);
        return layeredPane;
    }

    /** Costruisce il container principale */
    public JPanel buildContainerPanel() {
        containerPanel = new UiUtil.BorderedPanel(UiUtil.COLORE_PRIMARIO, CONTAINER_BORDER_THICKNESS, 30);
        containerPanel.setBackground(UiUtil.COLORE_SFONDO);
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
        contentPanel.add(new JLabel("Contenuto principale - personalizza qui"));
        return contentPanel;
    }

    /** Costruisce il panel degli errori (invisibile di default) */
    public JPanel buildInfoLogPanel() {
        infoLogPanel = new JPanel(new BorderLayout());
        infoLogPanel.setBackground(INFO_LOG_VOID_COLOR);
        infoLogPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, INFO_LOG_BORDER_THICKNESS));

        // Header panel con la X in alto a destra
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(0, CLOSE_BUTTON_HEIGHT));

        closeInfoLogBtn = new JLabel("X");
        closeInfoLogBtn.setForeground(Color.BLACK);
        closeInfoLogBtn.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, ERROR_BUTTON_FONT_SIZE));
        closeInfoLogBtn.setPreferredSize(new Dimension(CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT));
        closeInfoLogBtn.setHorizontalAlignment(SwingConstants.CENTER);
        closeInfoLogBtn.setVerticalAlignment(SwingConstants.CENTER);
        closeInfoLogBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeInfoLogBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { hideInfoLog(); }
        });
        headerPanel.add(closeInfoLogBtn, BorderLayout.EAST);
        infoLogPanel.add(headerPanel, BorderLayout.NORTH);

        // Label del messaggio al centro
        infoLogLabel = new JLabel();
        infoLogLabel.setForeground(Color.BLACK);
        infoLogLabel.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, ERROR_LABEL_FONT_SIZE));
        infoLogLabel.setVerticalAlignment(SwingConstants.CENTER);
        infoLogLabel.setHorizontalAlignment(SwingConstants.LEFT);
        infoLogPanel.add(infoLogLabel, BorderLayout.CENTER);

        infoLogPanel.setVisible(false);
        return infoLogPanel;
    }

    
    public void showInfoLog(String tipo, String message) {
        Color backgroundColor, textColor;
        
        if (INFO_LOG_TYPE_SUCCESS.equals(tipo)) {
            backgroundColor = INFO_LOG_SUCCESS_COLOR;
            textColor = Color.GREEN;
        } else if(INFO_LOG_TYPE_ERROR.equals(tipo)) {
            backgroundColor = INFO_LOG_ERROR_COLOR;
            textColor = Color.RED;
        }else{
            backgroundColor = INFO_LOG_VOID_COLOR;
            textColor = Color.BLACK;
        }

        
        infoLogPanel.setBackground(backgroundColor);
        infoLogPanel.setBorder(BorderFactory.createLineBorder(textColor, INFO_LOG_BORDER_THICKNESS));
        infoLogLabel.setText("<html>" + tipo + " : " + message + "</html>");
        infoLogLabel.setForeground(textColor);
        closeInfoLogBtn.setForeground(textColor);
        
        infoLogPanel.setVisible(true);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    /** Nasconde il panel infolog */
    public void hideInfoLog() {
        infoLogPanel.setVisible(false);
        layeredPane.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}