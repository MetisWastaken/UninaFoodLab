package com.ufl.view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AggiungiNotificheFrame extends UiUtil.PopUpFrame {
    private AggiungiNotifichePanel aggiungi_notifiche_panel;

    public AggiungiNotificheFrame() {
        super(new Dimension(500, 300));
        aggiungi_notifiche_panel = new AggiungiNotifichePanel();
        setContent(aggiungi_notifiche_panel);
    }

    private class AggiungiNotifichePanel extends UiUtil.BlankPanel {
        private static final int TEXT_SIZE = 12;
        private static final Dimension INPUT_DIMENSION = new Dimension(260, 30);
        private static final int HORIZONTAL_STRUT = 6;
        private static final int VERTICAL_STRUT = 10;

        public JTextField titolo_field;
        public JTextArea messaggio_area;
        public JButton aggiungi_button;

        public AggiungiNotifichePanel() {
            super(UiUtil.COLORE_SFONDO);
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(VERTICAL_STRUT, HORIZONTAL_STRUT, VERTICAL_STRUT, HORIZONTAL_STRUT);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Titolo notifica
            JLabel titolo_label = UiUtil.infoLabel("Titolo notifica:", Font.PLAIN, TEXT_SIZE);
            titolo_field = UiUtil.createInputTextField("Titolo", INPUT_DIMENSION);

            gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST; gbc.weightx = 0;
            add(titolo_label, gbc);
            gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 1;
            add(titolo_field, gbc);


            // Messaggio notifica
            UiUtil.BorderedPanel messaggio_panel = new UiUtil.BorderedPanel(UiUtil.COLORE_PRIMARIO, 2, 0);
            messaggio_panel.setLayout(new BorderLayout());
            JLabel messaggio_label = UiUtil.infoLabel("Messaggio:", Font.PLAIN, TEXT_SIZE);
            messaggio_area = new JTextArea();
            messaggio_area.setLineWrap(true);
            messaggio_area.setWrapStyleWord(true);
            messaggio_area.setFont(new Font(UiUtil.FONT_FAMILY, Font.PLAIN, TEXT_SIZE));
            messaggio_area.setBackground(UiUtil.COLORE_SFONDO);
            messaggio_area.setForeground(UiUtil.COLORE_TESTO1);
            messaggio_area.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

            UiUtil.BorderedPanel messaggio_container = new UiUtil.BorderedPanel(UiUtil.TRASPARENT_COLOR, 0, 6);
            messaggio_container.setLayout(new BorderLayout());
            messaggio_container.add(messaggio_area, BorderLayout.CENTER);
            messaggio_container.setBackground(UiUtil.COLORE_SFONDO);

            UiUtil.ScrollablePanel messaggio_scroll = new UiUtil.ScrollablePanel(messaggio_container);
            messaggio_scroll.setBorder(null);
            messaggio_scroll.setPreferredSize(new Dimension(260, 100));
            messaggio_scroll.setMinimumSize(new Dimension(260, 100));
            messaggio_scroll.setMaximumSize(new Dimension(260, 100));

            messaggio_panel.add(messaggio_scroll, BorderLayout.CENTER);
            messaggio_panel.setPreferredSize(new Dimension(260, 100));
            messaggio_panel.setMinimumSize(new Dimension(260, 100));
            messaggio_panel.setMaximumSize(new Dimension(260, 100));

            gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.NORTHEAST; gbc.weightx = 0; gbc.weighty = 0;
            add(messaggio_label, gbc);
            gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 1;
            add(messaggio_panel, gbc);

            // Pulsanti (solo aggiungi)
            JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            buttons.setOpaque(false);
            aggiungi_button = UiUtil.createButton("Aggiungi notifica");
            buttons.add(aggiungi_button);

            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0; gbc.weighty = 1;
            add(buttons, gbc);

        }
    }

    public String getTitoloNotifica() {
        return aggiungi_notifiche_panel.titolo_field.getText().trim();
    }

    public String getMessaggioNotifica() {
        return aggiungi_notifiche_panel.messaggio_area.getText().trim();
    }

    public void addAggiungiNotificaListener(ActionListener listener) {
        aggiungi_notifiche_panel.aggiungi_button.addActionListener(listener);
    }

    public void pulisciCampi() {
        aggiungi_notifiche_panel.titolo_field.setText("");
        aggiungi_notifiche_panel.messaggio_area.setText("");
    }

    public void inserisciTesto(String a_chi_si_riferisce){
        setTitle("Aggiungi Notifica "+ a_chi_si_riferisce);
    }
}
