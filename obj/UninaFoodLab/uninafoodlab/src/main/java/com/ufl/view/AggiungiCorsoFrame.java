package com.ufl.view;

import java.awt.*;
import java.time.LocalDate;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.ufl.view.UiUtil;

public class AggiungiCorsoFrame extends UiUtil.PopUpFrame {
    private AggiungiCorsoPanel aggiungi_corso_panel;

    public AggiungiCorsoFrame() {
        super("AggiungiCorso", new Dimension(450, 400));
        aggiungi_corso_panel = new AggiungiCorsoPanel();
        setContent(aggiungi_corso_panel);
        setVisible(false);
    }

    private class AggiungiCorsoPanel extends UiUtil.BlankPanel {
        private static final Dimension INPUT_DIMENSION = new Dimension(200, 30);
        private static final Dimension DATE_DIMENSION = new Dimension(80, 30);
        private static final int HORIZONTAL_STRUT = 5;
        private static final int VERTICAL_STRUT = 10;
        
        public JTextField nome_field;
        public JTextField categoria_field;
        public JFormattedTextField data_in_field;
        public JFormattedTextField data_fin_field;
        public JTextField frequenza_field;
        public JButton aggiungi_button;

        public AggiungiCorsoPanel() {
            super(UiUtil.COLORE_SFONDO);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(VERTICAL_STRUT, HORIZONTAL_STRUT, VERTICAL_STRUT, HORIZONTAL_STRUT);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Nome del corso
            JLabel nome_label = new JLabel("Nome del corso:");
            nome_field = UiUtil.createInputTextField("Nome del corso", INPUT_DIMENSION);
            gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
            add(nome_label, gbc);
            gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
            add(nome_field, gbc);

            // Categoria del corso
            JLabel categoria_label = new JLabel("Categoria del corso:");
            categoria_field = UiUtil.createInputTextField("Categoria del corso", INPUT_DIMENSION);
            gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
            add(categoria_label, gbc);
            gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
            add(categoria_field, gbc);

            // Data inizio del corso
            JLabel data_in_label = new JLabel("Data inizio del corso:");
            data_in_field = UiUtil.createInputDateField(DATE_DIMENSION);
            gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
            add(data_in_label, gbc);
            gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
            add(data_in_field, gbc);

            // Data fine del corso
            JLabel data_fin_label = new JLabel("Data fine del corso:");
            data_fin_field = UiUtil.createInputDateField(DATE_DIMENSION);
            gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
            add(data_fin_label, gbc);
            gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
            add(data_fin_field, gbc);

            // Frequenza settimanale del corso
            JLabel frequenza_label = new JLabel("Frequenza settimanale del corso:");
            frequenza_field = UiUtil.createInputTextField("Frequenza settimanale del corso", INPUT_DIMENSION);
            gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
            add(frequenza_label, gbc);
            gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
            add(frequenza_field, gbc);

            // Bottone centrato in basso
            aggiungi_button = UiUtil.createButton("Aggiungi corso");
            gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(20, HORIZONTAL_STRUT, VERTICAL_STRUT, HORIZONTAL_STRUT);
            add(aggiungi_button, gbc);
        }

    }

    public String getNomeCorso() {
        return aggiungi_corso_panel.nome_field.getText();
    }

    public String getCategoriaCorso() {
        return aggiungi_corso_panel.categoria_field.getText();
    }

    public LocalDate getDataInizioCorso() {
        return LocalDate.parse(aggiungi_corso_panel.data_in_field.getText());
    }

    public LocalDate getDataFineCorso() {
        return LocalDate.parse(aggiungi_corso_panel.data_fin_field.getText());
    }

    public String getFrequenzaCorso() {
        return aggiungi_corso_panel.frequenza_field.getText();
    }

    public void addAggiungiCorsoListener(ActionListener listener) {
        aggiungi_corso_panel.aggiungi_button.addActionListener(listener);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AggiungiCorsoFrame();
        });
    }
}
