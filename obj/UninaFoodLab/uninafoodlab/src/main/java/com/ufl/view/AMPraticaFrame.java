package com.ufl.view;

import com.ufl.model.Pratica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class AMPraticaFrame extends UiUtil.PopUpFrame {
    private AMPraticaPanel am_pratica_panel;
    private Pratica pratica = null;

    public AMPraticaFrame() {
        super(new Dimension(450, 300));
    }

    public void setAMPraticaFrame(Pratica pratica) {
        this.pratica = pratica;

        if (pratica == null) {
            setTitle("Aggiungi Sessione Pratica");
        } else {
            setTitle("Modifica Sessione Pratica");
        }

        this.am_pratica_panel = new AMPraticaPanel(pratica);
        setContent(am_pratica_panel);
    }

    private class AMPraticaPanel extends UiUtil.BlankPanel {
        private static final int TEXT_SIZE = 12;
        private static final Dimension INPUT_DIMENSION = new Dimension(200, 30);
        private static final Dimension DATE_DIMENSION = new Dimension(100, 30);
        private static final int HORIZONTAL_STRUT = 5;
        private static final int VERTICAL_STRUT = 10;

        private JFormattedTextField giorno_sessione_field;
        private JTextField aula_field;
        private JSpinner posti_totali_spinner;
        private JButton conferma_button;

        public AMPraticaPanel(Pratica pratica) {
            super(UiUtil.COLORE_SFONDO);
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(VERTICAL_STRUT, HORIZONTAL_STRUT, VERTICAL_STRUT, HORIZONTAL_STRUT);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // --- Giorno sessione ---
            JLabel giorno_sessione_label = UiUtil.infoLabel("Giorno sessione:", Font.PLAIN, TEXT_SIZE);
            giorno_sessione_field = UiUtil.createInputDateField(
                DATE_DIMENSION,
                pratica == null ? null : pratica.getGiornoSessione()
            );

            gbc.gridx = 0; gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            add(giorno_sessione_label, gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            add(giorno_sessione_field, gbc);

            // --- Aula ---
            JLabel aula_label = UiUtil.infoLabel("Aula:", Font.PLAIN, TEXT_SIZE);
            aula_field = UiUtil.createInputTextField("Aula", INPUT_DIMENSION);

            if (pratica != null) {
                aula_field.setText(pratica.getAula());
            }

            gbc.gridx = 0; gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.EAST;
            add(aula_label, gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            add(aula_field, gbc);

            // --- Posti totali ---
            JLabel posti_totali_label = UiUtil.infoLabel("Posti totali:", Font.PLAIN, TEXT_SIZE);
            posti_totali_spinner = UiUtil.createInputNumberField(
                INPUT_DIMENSION,
                pratica == null ? null : pratica.getPostiTotali()
            );

            gbc.gridx = 0; gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.EAST;
            add(posti_totali_label, gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            add(posti_totali_spinner, gbc);

            // --- Bottone conferma ---
            conferma_button = UiUtil.createButton(pratica == null ? "Aggiungi sessione" : "Modifica sessione");
            gbc.gridx = 0; gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(20, HORIZONTAL_STRUT, VERTICAL_STRUT, HORIZONTAL_STRUT);
            add(conferma_button, gbc);
        }
    }

    public Pratica getPraticaOriginale() {
        return pratica;
    }

    public LocalDate getGiornoSessione() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateText = am_pratica_panel.giorno_sessione_field.getText();
            return LocalDate.parse(dateText, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public String getAula() {
        return am_pratica_panel.aula_field.getText();
    }

    public int getPostiTotali() {
        return (int) am_pratica_panel.posti_totali_spinner.getValue();
    }

    public void addConfermaListener(ActionListener listener) {
        if (am_pratica_panel == null) {
            setAMPraticaFrame(pratica);
        }
        am_pratica_panel.conferma_button.addActionListener(listener);
    }

    public void pulisciCampi() {
        am_pratica_panel.giorno_sessione_field.setValue(null);
        am_pratica_panel.aula_field.setText("");
        am_pratica_panel.posti_totali_spinner.setValue(1);
    }
}
