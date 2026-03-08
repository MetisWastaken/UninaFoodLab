package com.ufl.view;

import java.awt.*;

import javax.swing.*;

public class UiUtil {
    
    // Color Configuration
    public static final Color COLORE_SFONDO = new Color(250, 248, 240); // Crema chiaro
    public static final Color COLORE_PRIMARIO = new Color(160, 82, 45); // Terracotta/Marrone
    public static final Color COLORE_ACCENTO = new Color(218, 165, 32); // Oro scuro
    public static final Color COLORE_TESTO1 = new Color(60, 40, 30);     // Marrone scuro
    public static final Color COLORE_TESTO2 = new Color(46, 69, 63);    // Verde scuro
    public static final Color HINT_COLOR = new Color(180, 160, 140);    // Grigio caldo per hint
    public static final Color TRASPARENT_COLOR = new Color(0, 0, 0, 0); // Trasparente
    
    // Font Configuration
    public static final String FONT_FAMILY = "Arial";
    private static final int BUTTON_FONT_SIZE = 14;
    private static final int HINT_FONT_SIZE = 12;
    
    // Border Configuration
    public static final int TEXT_FIELD_BORDER_THICKNESS = 2;
    public static final int TEXT_FIELD_PADDING_TOP = 4;
    public static final int TEXT_FIELD_PADDING_LEFT = 8;
    public static final int TEXT_FIELD_PADDING_RIGHT = 8;
    public static final int TEXT_FIELD_PADDING_BOTTOM = 4;
    public static final int TEXT_FIELD_HINT_OFFSET_X = 2;


    public static class TestFrame extends JFrame {
        public TestFrame() {
            setTitle("TestFrame");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(200, 200);
            setLocationRelativeTo(null);
            setResizable(true);
            setVisible(true);
        }
    }

    public static class BorderedPanel extends JPanel {
        public BorderedPanel(Color borderColor, int borderThickness, int padding) {
            setOpaque(true);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, borderThickness, true),
                BorderFactory.createEmptyBorder(padding, padding, padding, padding)
            ));
        }
    }

    public static class BlankPanel extends JPanel
    {
        public BlankPanel(Color backgroundColor){
            setOpaque(true);
            setBackground(backgroundColor);
        }

        public BlankPanel(Dimension dimension)
        {
            setOpaque(false);
            setPreferredSize(dimension);
        }
    }

        public static class ScrollablePanel extends JScrollPane {
        public ScrollablePanel(JPanel container_panel) {
            super(container_panel);
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            setBorder(null);
            getVerticalScrollBar().setUnitIncrement(16);
            
            // Personalizza il colore della scrollbar
            getVerticalScrollBar().setBackground(COLORE_SFONDO);
            getVerticalScrollBar().setForeground(COLORE_PRIMARIO);
            getHorizontalScrollBar().setBackground(COLORE_SFONDO);
            getHorizontalScrollBar().setForeground(COLORE_PRIMARIO);
            
            // Imposta un UI personalizzato per colorare completamente la scrollbar
            getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = COLORE_PRIMARIO;
                    this.trackColor = COLORE_ACCENTO;
                }
                
                @Override
                protected JButton createIncreaseButton(int orientation) {
                    JButton button = super.createIncreaseButton(orientation);
                    button.setBackground(COLORE_ACCENTO);
                    button.setForeground(COLORE_PRIMARIO);
                    return button;
                }

                @Override
                protected JButton createDecreaseButton(int orientation) {
                    JButton button = super.createDecreaseButton(orientation);
                    button.setBackground(COLORE_ACCENTO);
                    button.setForeground(COLORE_PRIMARIO);
                    return button;
                }
            });
            
            getHorizontalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = COLORE_PRIMARIO;
                    this.trackColor = COLORE_ACCENTO;
                }
                
                @Override
                protected JButton createDecreaseButton(int orientation) {
                    JButton button = super.createDecreaseButton(orientation);
                    button.setBackground(COLORE_ACCENTO);
                    button.setForeground(COLORE_PRIMARIO);
                    return button;
                }
                
                @Override
                protected JButton createIncreaseButton(int orientation) {
                    JButton button = super.createIncreaseButton(orientation);
                    button.setBackground(COLORE_ACCENTO);
                    button.setForeground(COLORE_PRIMARIO);
                    return button;
                }
            });
        }
    }

    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(COLORE_PRIMARIO);
        button.setForeground(COLORE_ACCENTO);
        button.setFocusPainted(false);

        button.setFont(new Font(FONT_FAMILY, Font.BOLD, BUTTON_FONT_SIZE));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                button.setBackground(COLORE_PRIMARIO.darker());
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                button.setBackground(COLORE_PRIMARIO);
            }
        });

        return button;
    }
    
    public static JTextField createInputTextField(String hint, Dimension dimension) {
        JTextField textField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Disegna il placeholder solo se il campo è vuoto e non ha focus
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(HINT_COLOR);
                    g2.setFont(new Font(FONT_FAMILY, Font.ITALIC, HINT_FONT_SIZE));
                    Insets ins = getInsets();
                    g2.drawString(hint, ins.left + TEXT_FIELD_HINT_OFFSET_X, getHeight() / 2 + getFont().getSize() / 2 - 2);
                    g2.dispose();
                }
            }
        };
        textField.setBackground(COLORE_SFONDO);
        textField.setForeground(COLORE_TESTO1);
        textField.setPreferredSize(dimension);
        textField.setMinimumSize(dimension);
        textField.setMaximumSize(dimension);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLORE_PRIMARIO, TEXT_FIELD_BORDER_THICKNESS, true),
            BorderFactory.createEmptyBorder(TEXT_FIELD_PADDING_TOP, TEXT_FIELD_PADDING_LEFT, TEXT_FIELD_PADDING_BOTTOM, TEXT_FIELD_PADDING_RIGHT)
        ));
        // Ridisegna quando il focus cambia
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) { textField.repaint(); }
            @Override public void focusLost(java.awt.event.FocusEvent e)   { textField.repaint(); }
        });
        return textField;
    }

    

    public static void main(String[] args){
        TestFrame frame = new TestFrame();
        
        BlankPanel blankPanel = new BlankPanel(COLORE_ACCENTO);
        blankPanel.add(Box.createVerticalStrut(500));
        blankPanel.add(createButton("Test"));
        blankPanel.add(Box.createHorizontalStrut(500));
        ScrollablePanel scrollablePanel = new ScrollablePanel(blankPanel);
        frame.add(scrollablePanel);
        frame.revalidate();     
        
    }
}

