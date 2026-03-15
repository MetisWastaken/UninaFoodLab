package com.ufl.view;

import com.ufl.model.Online;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.ufl.dao.OnlineDAO;

public class AMOnlineFrame extends UiUtil.PopUpFrame {
    private AMOnlinePanel am_online_panel;
    private Online online= null;

    public AMOnlineFrame() {
        super(new Dimension(450, 250));
    }

    public void setAMOnlineFrame(Online online){
        this.online = online;

        if(online == null){
            setTitle("Aggiungi Sessione Online");
        } else {
            setTitle("Modifica Sessione Online");
        }

        this.am_online_panel = new AMOnlinePanel(online);
        setContent(am_online_panel);
    }

    private class AMOnlinePanel extends UiUtil.BlankPanel {
        private static final int TEXT_SIZE = 12;
        private static final Dimension INPUT_DIMENSION = new Dimension(200, 30);
        private static final Dimension DATE_DIMENSION = new Dimension(100, 30);
        private static final int HORIZONTAL_STRUT = 5;
        private static final int VERTICAL_STRUT = 10;

        private JFormattedTextField giorno_sessione_field;
        private JTextField codice_meeting_field;
        private JButton conferma_button;

        public AMOnlinePanel(Online online) {
            super(UiUtil.COLORE_SFONDO);
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(VERTICAL_STRUT, HORIZONTAL_STRUT, VERTICAL_STRUT, HORIZONTAL_STRUT);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel giorno_sessione_label = UiUtil.infoLabel("Giorno sessione:", Font.PLAIN, TEXT_SIZE);
            giorno_sessione_field = UiUtil.createInputDateField(
                DATE_DIMENSION,
                online == null ? null : online.getGiornoSessione()
            );

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            add(giorno_sessione_label, gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            add(giorno_sessione_field, gbc);

            JLabel codice_meeting_label = UiUtil.infoLabel("Codice meeting:", Font.PLAIN, TEXT_SIZE);
            codice_meeting_field = UiUtil.createInputTextField(
                "Codice meeting",
                INPUT_DIMENSION
            );

            if (online != null) {
                codice_meeting_field.setText(online.getCodiceMeeting());
            }

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.EAST;
            add(codice_meeting_label, gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            add(codice_meeting_field, gbc);

            conferma_button = UiUtil.createButton(online == null ? "Aggiungi sessione" : "Modifica sessione");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(20, HORIZONTAL_STRUT, VERTICAL_STRUT, HORIZONTAL_STRUT);
            add(conferma_button, gbc);
        }
    }

    public Online getOnlineOriginale() {
        return online;
    }

    public LocalDate getGiornoSessione() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateText = am_online_panel.giorno_sessione_field.getText();
            return LocalDate.parse(dateText, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public String getCodiceMeeting() {
        return am_online_panel.codice_meeting_field.getText();
    }

    public void addConfermaListener(ActionListener listener) {
        if (am_online_panel == null) {
            setAMOnlineFrame(online); // inizializza se non ancora creato
        }
        am_online_panel.conferma_button.addActionListener(listener);
    }

    public void pulisciCampi() {
        am_online_panel.giorno_sessione_field.setValue(null);
        am_online_panel.codice_meeting_field.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AMOnlineFrame frameAggiunta = new AMOnlineFrame();
            frameAggiunta.setAMOnlineFrame(OnlineDAO.get(1));
            frameAggiunta.setVisible(true);
        });
    }
}

