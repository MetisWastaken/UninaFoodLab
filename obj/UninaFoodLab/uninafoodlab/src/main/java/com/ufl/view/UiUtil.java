package com.ufl.view;

import java.awt.*;

import javax.swing.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class UiUtil {
    
    // Color Configuration
    public static final Color COLORE_SFONDO = new Color(250, 248, 240); // Crema chiaro
    public static final Color COLORE_PRIMARIO = new Color(183, 115, 82); // Terracotta/Marrone
    public static final Color COLORE_ACCENTO = new Color(213, 213, 213); // Platino
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

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static class TestFrame extends JFrame {
        public TestFrame() {
            setTitle("TestFrame");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(500, 500);
            setLocationRelativeTo(null);
            setResizable(true);
            setVisible(true);
        }
    }

    public static class PopUpFrame extends JFrame {
        private ScrollablePanel scrollablePanel;

        public PopUpFrame(Dimension dimension){
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(dimension);
            setLocationRelativeTo(null);
            setResizable(false);
            setType(Window.Type.UTILITY); 

            setVisible(false);
        }

        public PopUpFrame(String title, Dimension dimension) {
            this(dimension);
            setTitle(title);
            
        }

        public PopUpFrame(String title, Dimension dimension, JPanel contentPanel) {
            this(title, dimension);
            setContent(contentPanel);
        }

        public void setContent(JPanel contentPanel) {
            if (scrollablePanel != null) {
                remove(scrollablePanel);
            }
            scrollablePanel = new ScrollablePanel(contentPanel);
            add(scrollablePanel);
            revalidate();
            repaint();
        }

        public void setHorizontalScrollTrue(){
            scrollablePanel.setHorizontalScrollTrue();
        }
    }

    public static class BorderedPanel extends JPanel {
        public BorderedPanel(Color borderColor, int borderThickness, int padding) {
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, borderThickness, true),
                BorderFactory.createEmptyBorder(padding, padding, padding, padding)
            ));
        }
    }

    public static class BlankPanel extends JPanel
    {
        public BlankPanel(Color backgroundColor){
            // Se il colore è trasparente, il panel NON deve essere opaque
            boolean isTransparent = backgroundColor.getAlpha() < 255;
            setOpaque(!isTransparent);
            setBackground(backgroundColor);
        }

        public BlankPanel(Dimension dimension)
        {
            setOpaque(false);
            setPreferredSize(dimension);
            setMinimumSize(dimension);
            setMaximumSize(dimension);
        }
    }

    public static class ScrollablePanel extends JScrollPane {

        public ScrollablePanel(JPanel container_panel) {
            this(container_panel, container_panel.getBackground(), container_panel.isOpaque());
        }

        public ScrollablePanel(JPanel container_panel, Color background, boolean opaqueContent) {
            super(container_panel);

            // prima: SIMPLE_SCROLL_MODE
            getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            setBorder(null);
            getVerticalScrollBar().setUnitIncrement(16);

            if (opaqueContent) {
                setOpaque(true);
                getViewport().setOpaque(true);
                setBackground(background);
                getViewport().setBackground(background);
                container_panel.setOpaque(true);
                container_panel.setBackground(background);
            } else {
                setOpaque(false);
                getViewport().setOpaque(false);
                container_panel.setOpaque(false);
            }

            getVerticalScrollBar().setBackground(COLORE_SFONDO);
            getVerticalScrollBar().setForeground(COLORE_PRIMARIO);
            getHorizontalScrollBar().setBackground(COLORE_SFONDO);
            getHorizontalScrollBar().setForeground(COLORE_PRIMARIO);
            
            // Imposta un UI personalizzato per colorare completamente la scrollbar
            getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = COLORE_PRIMARIO;
                    this.trackColor = TRASPARENT_COLOR; 
                }
                
                @Override
                protected JButton createIncreaseButton(int orientation) {
                    JButton button = super.createIncreaseButton(orientation);
                    button.setBackground(TRASPARENT_COLOR);
                    button.setForeground(COLORE_PRIMARIO);
                    return button;
                }

                @Override
                protected JButton createDecreaseButton(int orientation) {
                    JButton button = super.createDecreaseButton(orientation);
                    button.setBackground(TRASPARENT_COLOR);
                    button.setForeground(COLORE_PRIMARIO);
                    return button;
                }
            });
            
            getHorizontalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = COLORE_PRIMARIO;
                    this.trackColor = TRASPARENT_COLOR;
                }
                
                @Override
                protected JButton createIncreaseButton(int orientation) {
                    JButton button = super.createIncreaseButton(orientation);
                    button.setBackground(TRASPARENT_COLOR);
                    button.setForeground(COLORE_PRIMARIO);
                    return button;
                }

                @Override
                protected JButton createDecreaseButton(int orientation) {
                    JButton button = super.createDecreaseButton(orientation);
                    button.setBackground(TRASPARENT_COLOR);
                    button.setForeground(COLORE_PRIMARIO);
                    return button;
                }
            });
            
        }
        public void setHorizontalScrollTrue(){
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }
    }

    public static class CreateButton extends JButton {
        
        public CreateButton(String text, int height) {
            super(text);
            // Imposta solo l'altezza, lascia che la larghezza si adatti
            setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
            setPreferredSize(new Dimension(getPreferredSize().width, height));
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setBackground(UiUtil.COLORE_PRIMARIO);
            setForeground(UiUtil.COLORE_ACCENTO);
            setFocusPainted(false);
            setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, (int)(height/2)));
        }

        
        public CreateButton(String src,  Dimension size, Color background_color) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setBackground(background_color); 
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(true);
            setIcon(new ImageIcon(new ImageIcon(getClass().getResource(src)).getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH)));
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
    public static JLabel infoLabel(String text, int font_style, int font_size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(UiUtil.FONT_FAMILY, font_style, font_size));
        return label;
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
    
    public static JFormattedTextField createInputDateField(Dimension dimension, LocalDate date) {
        JFormattedTextField dateField;
        DateTimeFormatter outFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');

            dateField = new JFormattedTextField(dateFormatter);
            dateField.setFocusLostBehavior(JFormattedTextField.PERSIST);

            if (date == null) {
                dateField.setValue(null); // mostra __/__/____
            } else {
                dateField.setText(date.format(outFmt)); // sostituisce i _ con la data
            }

            dateField.setBackground(COLORE_SFONDO);
            dateField.setForeground(COLORE_TESTO1);
            dateField.setPreferredSize(dimension);
            dateField.setMinimumSize(dimension);
            dateField.setMaximumSize(dimension);
            dateField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLORE_PRIMARIO, TEXT_FIELD_BORDER_THICKNESS, true),
                BorderFactory.createEmptyBorder(TEXT_FIELD_PADDING_TOP, TEXT_FIELD_PADDING_LEFT,
                    TEXT_FIELD_PADDING_BOTTOM, TEXT_FIELD_PADDING_RIGHT)
            ));
        } catch (ParseException e) {
            e.printStackTrace();
            dateField = new JFormattedTextField(DATE_FORMATTER);
            dateField.setPreferredSize(dimension);
            dateField.setMinimumSize(dimension);
            dateField.setMaximumSize(dimension);
            dateField.setText(date == null ? "__/__/____" : date.format(outFmt));
        }

        return dateField;
    }
    
    public static JSpinner createInputNumberField(Dimension dimension, Integer default_value) {
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(
            default_value == null ? 1 : default_value,
            1,
            99999,
            1
        );

        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setPreferredSize(dimension);
        spinner.setMinimumSize(dimension);
        spinner.setMaximumSize(dimension);
        spinner.setBackground(COLORE_SFONDO); // FIX: sfondo spinner

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
        editor.setBackground(COLORE_SFONDO);  // FIX: sfondo editor
        spinner.setEditor(editor);

        // Sfondo e testo del campo numerico
        JFormattedTextField textField = editor.getTextField();
        textField.setBackground(COLORE_SFONDO);
        textField.setForeground(COLORE_TESTO1);
        textField.setFont(new Font(FONT_FAMILY, Font.PLAIN, HINT_FONT_SIZE));
        textField.setHorizontalAlignment(JTextField.LEFT);
        textField.setBorder(BorderFactory.createEmptyBorder()); // FIX: rimuove bordo interno del textField

        // Colore bottoni freccia su/giù
        for (Component c : spinner.getComponents()) {
            if (c instanceof JButton btn) {
                btn.setBackground(COLORE_ACCENTO);
                btn.setForeground(COLORE_TESTO1);
                btn.setFocusPainted(false);
                btn.setBorderPainted(false);
            }
        }

        spinner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLORE_PRIMARIO, TEXT_FIELD_BORDER_THICKNESS, true),
            BorderFactory.createEmptyBorder(TEXT_FIELD_PADDING_TOP, TEXT_FIELD_PADDING_LEFT,
                TEXT_FIELD_PADDING_BOTTOM, TEXT_FIELD_PADDING_RIGHT)
        ));

        return spinner;
    }

    public static void main(String[] args){
        
        BlankPanel blankPanel = new BlankPanel(COLORE_SFONDO);
        blankPanel.add(Box.createVerticalStrut(500));
        blankPanel.add(createInputDateField(new Dimension(200, 40), LocalDate.now()));
        blankPanel.add(Box.createHorizontalStrut(500));
        PopUpFrame frame = new PopUpFrame( new Dimension(400, 300));
        frame.setContent(blankPanel);
        frame.setHorizontalScrollTrue();
        frame.revalidate();     
        frame.setVisible(true);
        
    }
}

