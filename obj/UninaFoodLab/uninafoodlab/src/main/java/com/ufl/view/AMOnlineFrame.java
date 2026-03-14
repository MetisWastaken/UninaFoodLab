package com.ufl.view;

import com.ufl.model.Online;
import com.ufl.model.Chef;
import com.ufl.model.Corso;
import com.ufl.dao.ChefDAO;
import com.ufl.dao.CorsoDAO;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AMOnlineFrame extends UiUtil.BlankPanel {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Dimension DATE_DIMENSION = new Dimension(100, 30);
    private static final Dimension MEETING_DIMENSION = new Dimension(220, 30);

    private final UiUtil.CreateButton OnlineSessioneBtn;
    private final JPanel listaSessioniPanel;
    private final ArrayList<SessioneOnlineItemPanel> itemPanels;
    private final UiUtil.PopUpFrame activePopup;

    private Integer corsoId;
    private JFormattedTextField popupDataField;
    private JTextField popupMeetingField;

    public AMOnlineFrame(List<Online> sessioniOnline) {
        super(UiUtil.TRASPARENT_COLOR);
        setLayout(new BorderLayout());

        aggiornaCorsoId(sessioniOnline);

        JPanel externalBorderPanel = new UiUtil.BorderedPanel(UiUtil.COLORE_PRIMARIO, 3, 0);
        externalBorderPanel.setLayout(new BorderLayout());
        externalBorderPanel.setBackground(UiUtil.COLORE_SFONDO);

        JPanel topPanel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        OnlineSessioneBtn = new UiUtil.CreateButton("+", 80);
        topPanel.add(Box.createHorizontalStrut(7));
        topPanel.add(OnlineSessioneBtn);
        topPanel.add(Box.createHorizontalGlue());
        externalBorderPanel.add(topPanel, BorderLayout.NORTH);

        listaSessioniPanel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
        listaSessioniPanel.setLayout(new BoxLayout(listaSessioniPanel, BoxLayout.Y_AXIS));

        activePopup = new UiUtil.PopUpFrame(new Dimension(420, 220));

        itemPanels = new ArrayList<>();
        popolaSessioni(sessioniOnline);

        UiUtil.ScrollablePanel scrollPane = new UiUtil.ScrollablePanel(listaSessioniPanel);
        externalBorderPanel.add(scrollPane, BorderLayout.CENTER);

        add(externalBorderPanel, BorderLayout.CENTER);
    }

    public void setCorsoId(Integer corsoId) {
        this.corsoId = corsoId;
    }

    public void aggiornaSessioni(List<Online> nuoveSessioni) {
        aggiornaCorsoId(nuoveSessioni);
        popolaSessioni(nuoveSessioni);
    }

    public void apriPopup(String titolo, Online sessionePrefill) {
        if (corsoId == null && sessionePrefill == null) {
            JOptionPane.showMessageDialog(this, "Corso non disponibile");
            return;
        }

        configuraPopup(titolo, sessionePrefill);
    }

    private void aggiornaCorsoId(List<Online> sessioniOnline) {
        if (sessioniOnline != null && !sessioniOnline.isEmpty()) {
            this.corsoId = sessioniOnline.get(0).getCorsoId();
        }
    }

    private void popolaSessioni(List<Online> sessioniOnline) {
        listaSessioniPanel.removeAll();
        itemPanels.clear();

        for (Online sessione : sessioniOnline) {
            SessioneOnlineItemPanel item = new SessioneOnlineItemPanel(sessione);
            itemPanels.add(item);

            listaSessioniPanel.add(Box.createVerticalStrut(4));
            listaSessioniPanel.add(item);
        }

        listaSessioniPanel.revalidate();
        listaSessioniPanel.repaint();
    }

    private void configuraPopup(String titolo, Online prefill) {
        JPanel content = new UiUtil.BlankPanel(UiUtil.COLORE_SFONDO);
        content.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel dataLabel = UiUtil.infoLabel("Data:", Font.PLAIN, 13);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        content.add(dataLabel, gbc);

        popupDataField = UiUtil.createInputDateField(DATE_DIMENSION);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        content.add(popupDataField, gbc);

        JLabel meetingLabel = UiUtil.infoLabel("Codice meeting:", Font.PLAIN, 13);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        content.add(meetingLabel, gbc);

        popupMeetingField = UiUtil.createInputTextField("Es. ABC-123", MEETING_DIMENSION);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        content.add(popupMeetingField, gbc);

        if (prefill != null) {
            popupDataField.setText(prefill.getGiornoSessione().format(DATE_FORMATTER));
            popupMeetingField.setText(prefill.getCodiceMeeting());
        }

        JButton confermaBtn = UiUtil.createButton("Conferma");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        content.add(confermaBtn, gbc);

        activePopup.setTitle(titolo);
        activePopup.setContent(content);
        activePopup.setVisible(true);
    }

    public void chiudiPopup() {
        activePopup.setVisible(false);
    }

    public Integer getPopupCorsoId() {
        return corsoId;
    }

    public String getPopupDataText() {
        return popupDataField != null ? popupDataField.getText() : "";
    }

    public String getPopupMeetingText() {
        return popupMeetingField != null ? popupMeetingField.getText() : "";
    }

    private static class SessioneOnlineItemPanel extends UiUtil.BlankPanel {
        private final Online sessione;
        private final JButton modificaBtn;
        private final JButton eliminaBtn;

        public SessioneOnlineItemPanel(Online sessione) {
            super(UiUtil.TRASPARENT_COLOR);
            this.sessione = sessione;

            setLayout(new GridBagLayout());
            setBackground(UiUtil.TRASPARENT_COLOR);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.anchor = GridBagConstraints.WEST;

            JLabel dataLabel = UiUtil.infoLabel("Data: " + sessione.getGiornoSessione().format(DATE_FORMATTER), Font.BOLD, 14);
            add(dataLabel, gbc);

            JLabel codiceLabel = UiUtil.infoLabel("Meeting: " + sessione.getCodiceMeeting(), Font.PLAIN, 13);
            gbc.gridx = 1;
            add(codiceLabel, gbc);

            JPanel spacer = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            gbc.gridx = 2;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(spacer, gbc);

            modificaBtn = UiUtil.createButton("Modifica");
            eliminaBtn = UiUtil.createButton("Elimina");
            JPanel actionsPanel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            actionsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 6, 0));
            actionsPanel.add(modificaBtn);
            actionsPanel.add(eliminaBtn);

            gbc.gridx = 3;
            gbc.gridwidth = 2;
            gbc.weightx = 0.0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.EAST;
            add(actionsPanel, gbc);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Corso corso = CorsoDAO.get(1);
            AMOnlineFrame panel = new AMOnlineFrame(corso.getSessioniOnline());
            UiUtil.TestFrame frame = new UiUtil.TestFrame();
            frame.add(panel);
            frame.revalidate();
            frame.repaint();
        });
    }
}

