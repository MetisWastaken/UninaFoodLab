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


    // Font Configuration
    private static final int TITLE_FONT_SIZE = 28;
    private static final int ERROR_LABEL_FONT_SIZE = 14;
    private static final int ERROR_BUTTON_FONT_SIZE = 18;

    // Color Configuration
    private static final int CLOSE_BUTTON_WIDTH = 20;
    private static final int CLOSE_BUTTON_HEIGHT = 20;

    private JPanel container_panel;
    private JPanel info_log_panel;
    private JLabel info_log_label;
    private JLabel close_info_log_btn;
    private JLayeredPane layered_pane;

    public MainFrame() {
        setTitle("UninaFoodLab");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel main_panel = buildMainPanel();
        add(main_panel);
        setVisible(true);
    }

    /** Costruisce il panel principale che racchiude tutto */
    public JPanel buildMainPanel() {
        JPanel main_panel = new JPanel(new BorderLayout(MAIN_PANEL_GAP, MAIN_PANEL_GAP));
        main_panel.setBackground(Color.WHITE);
        main_panel.setBorder(BorderFactory.createEmptyBorder(MAIN_PANEL_BORDER, MAIN_PANEL_BORDER, MAIN_PANEL_BORDER, MAIN_PANEL_BORDER));

        main_panel.add(buildTopPanel(), BorderLayout.NORTH);
        main_panel.add(buildLayeredPane(), BorderLayout.CENTER);

        return main_panel;
    }

    /** Costruisce il panel superiore con titolo e logo */
    public JPanel buildTopPanel() {
        JPanel top_panel = new JPanel(new BorderLayout());
        top_panel.setBackground(Color.WHITE);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, TOP_PANEL_GAP, 0));
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(buildLogoPanel());

        JLabel titleLabel = new JLabel("UninaFoodLab");
        titleLabel.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, TITLE_FONT_SIZE));
        centerPanel.add(titleLabel);
        top_panel.add(centerPanel, BorderLayout.CENTER);
        return top_panel;
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
        layered_pane = new JLayeredPane() {
            @Override
            public void doLayout() {
                int w = getWidth();
                int h = getHeight();
                if (container_panel != null) {
                    int hPad = (int)(w * CONTAINER_HORIZONTAL_MARGIN_PERCENT);
                    int vPad = (int)(h * CONTAINER_VERTICAL_MARGIN_PERCENT);
                    container_panel.setBounds(hPad, vPad, w - hPad * 2, h - vPad * 2);
                }
                if (info_log_panel != null) {
                    int hPad = (int)(w * INFO_LOG_HORIZONTAL_MARGIN_PERCENT);
                    int errH = info_log_panel.getPreferredSize().height;
                    info_log_panel.setBounds(hPad, h - errH - INFO_LOG_BOTTOM_OFFSET, w - hPad * 2, errH);
                }
            }
        };
        layered_pane.add(buildContainerPanel(), JLayeredPane.DEFAULT_LAYER);
        layered_pane.add(buildInfoLogPanel(), JLayeredPane.PALETTE_LAYER);
        return layered_pane;
    }

    /** Costruisce il container principale */
    private JPanel buildContainerPanel() {
        container_panel = new UiUtil.BorderedPanel(UiUtil.COLORE_PRIMARIO, CONTAINER_BORDER_THICKNESS, 1);
        container_panel.setLayout(new BorderLayout()); 
        container_panel.setBackground(UiUtil.COLORE_SFONDO);
        container_panel.add(buildContentPanel(), BorderLayout.CENTER);
        return container_panel;
    }

    /** Sostituisce il contenuto del container con il panel fornito */
    public void setContent(JPanel panel) {
        container_panel.removeAll();
        container_panel.add(panel, BorderLayout.CENTER); 
        container_panel.revalidate();
        container_panel.repaint();
    }

    /** Sostituisce il contenuto centrandolo alla sua dimensione preferita (LoginPage) */
    public void setContent(LoginPage panel) {
        JPanel centrpanel = new JPanel(new GridBagLayout());
        centrpanel.setOpaque(false);
        centrpanel.add(panel);
        setContent(centrpanel);
    }

    
    private JPanel buildContentPanel() {
        JPanel content_panel = new JPanel();
        content_panel.add(new JLabel("Contenuto principale - personalizza qui"));
        return content_panel;
    }

    /** Costruisce il panel degli errori (invisibile di default) */
    private JPanel buildInfoLogPanel() {
        info_log_panel = new JPanel(new BorderLayout());
        info_log_panel.setBackground(UiUtil.TRASPARENT_COLOR);
        info_log_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, INFO_LOG_BORDER_THICKNESS));

        // Header panel con la X in alto a destra
        JPanel header_panel = new JPanel(new BorderLayout());
        header_panel.setOpaque(false);
        header_panel.setPreferredSize(new Dimension(0, CLOSE_BUTTON_HEIGHT));

        close_info_log_btn = new JLabel("X");
        close_info_log_btn.setForeground(Color.BLACK);
        close_info_log_btn.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, ERROR_BUTTON_FONT_SIZE));
        close_info_log_btn.setPreferredSize(new Dimension(CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT));
        close_info_log_btn.setHorizontalAlignment(SwingConstants.CENTER);
        close_info_log_btn.setVerticalAlignment(SwingConstants.CENTER);
        close_info_log_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close_info_log_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { hideInfoLog(); }
        });
        header_panel.add(close_info_log_btn, BorderLayout.EAST);
        info_log_panel.add(header_panel, BorderLayout.NORTH);

        // Label del messaggio al centro
        info_log_label = new JLabel();
        info_log_label.setForeground(Color.BLACK);
        info_log_label.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, ERROR_LABEL_FONT_SIZE));
        info_log_label.setVerticalAlignment(SwingConstants.CENTER);
        info_log_label.setHorizontalAlignment(SwingConstants.LEFT);
        info_log_panel.add(info_log_label, BorderLayout.CENTER);

        info_log_panel.setVisible(false);
        return info_log_panel;
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
            backgroundColor = UiUtil.TRASPARENT_COLOR;
            textColor = Color.BLACK;
        }

        
        info_log_panel.setBackground(backgroundColor);
        info_log_panel.setBorder(BorderFactory.createLineBorder(textColor, INFO_LOG_BORDER_THICKNESS));
        info_log_label.setText("<html>" + tipo + " : " + message + "</html>");
        info_log_label.setForeground(textColor);
        close_info_log_btn.setForeground(textColor);
        
        info_log_panel.setVisible(true);
        layered_pane.revalidate();
        layered_pane.repaint();
    }

    /** Nasconde il panel infolog */
    public void hideInfoLog() {
        info_log_panel.setVisible(false);
        layered_pane.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}