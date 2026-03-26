package com.ufl.view;

import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


import com.ufl.model.Corso;
import com.ufl.model.Notifica;



public class NotificheContainerPanel extends UiUtil.BlankPanel {
    private static final int TEXT_FONT_SIZE = 14;
    private static UiUtil.PopUpFrame activePopup=null;
    private UiUtil.CreateButton aggiungi_notifiche_btn;
    private NotifichePanel notifiche_panel;

    public NotificheContainerPanel(List<Notifica> notifiche) {
        super(UiUtil.TRASPARENT_COLOR);

        setLayout(new BorderLayout());
        
        if(activePopup == null){
            activePopup = new UiUtil.PopUpFrame( new Dimension(400, 300));
        }

        this.aggiungi_notifiche_btn = new UiUtil.CreateButton("+", 80);

        this.notifiche_panel = new NotifichePanel(notifiche);

        UiUtil.ScrollablePanel scrollPane = new UiUtil.ScrollablePanel(this.notifiche_panel);
        
        add(aggiungi_notifiche_btn, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        
    }

    private class NotifichePanel extends UiUtil.BlankPanel {
        private List<NotificaRowPanel> notifiche_row_panel;
        public NotifichePanel(List<Notifica> notifiche) {
            super(UiUtil.TRASPARENT_COLOR);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            notifiche_row_panel = new ArrayList<>();
            for(Notifica notifica : notifiche){
                NotificaRowPanel notifica_row_panel = new NotificaRowPanel(notifica);
                this.notifiche_row_panel.add(notifica_row_panel);
                add(notifica_row_panel);
                add(Box.createVerticalStrut(10));
            }
        }

        public List<NotificaRowPanel> getNotificheRowPanel() {
            return notifiche_row_panel;
        }
    }

    public class NotificaRowPanel extends UiUtil.BorderedPanel {
        private static final int ROW_HEIGHT = 40;

        private Notifica notifica;
        private JButton dettagli_notifica_btn;
        private JButton elimina_notifica_btn;

        public NotificaRowPanel(Notifica notifica) {
            super(UiUtil.COLORE_PRIMARIO, 2, 1);
            this.notifica = notifica;

            setLayout(new GridBagLayout());
            setBackground(UiUtil.TRASPARENT_COLOR);
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, ROW_HEIGHT));
            setPreferredSize(new Dimension(getPreferredSize().width, ROW_HEIGHT));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Testo a sinistra
            JLabel titolo_label = UiUtil.infoLabel(notifica.getTitolo(), Font.BOLD, TEXT_FONT_SIZE);
            gbc.gridx = 0;
            gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            add(titolo_label, gbc);

            // Bottone Dettagli a destra
            dettagli_notifica_btn = UiUtil.createButton("Dettagli");
            gbc.gridx = 1;
            gbc.weightx = 0.0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.EAST;
            add(dettagli_notifica_btn, gbc);

            // Bottone Elimina a destra
            elimina_notifica_btn = UiUtil.createButton("Elimina");
            gbc.gridx = 2;
            add(elimina_notifica_btn, gbc);

            ListenerDettagli();
        }
        
        public Notifica getNotifica() {
            return notifica;
        }

        public void addEliminaNotificaButtonListener(ActionListener listener){
            elimina_notifica_btn.addActionListener(listener);
        }

        private void ListenerDettagli(){
            CostruisciNotifica();
            dettagli_notifica_btn.addActionListener(e -> {
                UiUtil.BlankPanel content = new UiUtil.BlankPanel(UiUtil.COLORE_SFONDO);
                content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS)); // FIX: era "this"

                content.add(UiUtil.infoLabel("Dettagli notifica: ", Font.BOLD, TEXT_FONT_SIZE));
                content.add(Box.createVerticalStrut(10));
                content.add(UiUtil.infoLabel("Titolo: " + notifica.getTitolo(), Font.PLAIN, TEXT_FONT_SIZE));
                content.add(UiUtil.infoLabel("Messaggio: " + notifica.getMessaggio(), Font.PLAIN, TEXT_FONT_SIZE));
                content.add(UiUtil.infoLabel("Data creazione: " + notifica.getDataCreazione().format(UiUtil.DATE_FORMATTER), Font.PLAIN, TEXT_FONT_SIZE));
                if (notifica.isSoloIscritti()) {
                    content.add(UiUtil.infoLabel("Corso associato: " + new Corso(notifica.getCorsoId()).getNomeById(), Font.PLAIN, TEXT_FONT_SIZE));
                }
                String username_riceventi = notifica.getUsernameRiceventi();
                String destinatari = (username_riceventi == null || username_riceventi.isBlank())
                    ? "Nessuno"
                    : username_riceventi;
                content.add(UiUtil.infoLabel("Chi può vederla (Username): " + destinatari, Font.PLAIN, TEXT_FONT_SIZE));

                activePopup.setTitle("Notifica: " + notifica.getTitolo());
                activePopup.setContent(content);
                activePopup.setHorizontalScrollTrue();
                activePopup.setVisible(true);
            });
        }

        private void CostruisciNotifica(){
            if(notifica.getDataCreazione() == null){
                notifica.recDataCreazione();
                notifica.recMessagio();
                notifica.recSoloIscritti();
            }
        }
        
    }

    public void addAggiungiNotificaButtonListener(ActionListener listener){
            aggiungi_notifiche_btn.addActionListener(listener);
    }
    

    public List<NotificaRowPanel> getNotificheRowPanel() {
        return notifiche_panel.getNotificheRowPanel();
    }

}
