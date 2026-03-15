package com.ufl.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import com.ufl.model.Online;

import com.ufl.dao.CorsoDAO;
import com.ufl.model.Corso;

public class AMEOnlinePanel extends UiUtil.BorderedPanel {
    private static final int FONT_SIZE = 12;

    private UiUtil.CreateButton aggiungi_online_btn;
    private OnlinePanel online_panel;
 

    public AMEOnlinePanel(List<Online> sessioni_online) {
        super(UiUtil.COLORE_PRIMARIO, 3, 0);
        setBackground(UiUtil.TRASPARENT_COLOR);
        setOpaque(false);
        setLayout(new BorderLayout(0, 10)); 

        this.aggiungi_online_btn = new UiUtil.CreateButton("Aggiungi Sessione", 35);
        this.online_panel = new OnlinePanel(sessioni_online);

        add(aggiungi_online_btn, BorderLayout.NORTH); 
        add(online_panel, BorderLayout.CENTER);       
    }

    public class OnlinePanel extends UiUtil.BlankPanel {
        private List<OnlineRow> online_rows;
        

        public OnlinePanel(List<Online> sessioni_online) {
            super(UiUtil.TRASPARENT_COLOR);


            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            
            this.online_rows = new ArrayList<>();

            UiUtil.BlankPanel online_rows_panel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            online_rows_panel.setLayout(new BoxLayout(online_rows_panel, BoxLayout.Y_AXIS));
            
            for(Online sessione : sessioni_online){

                online_rows_panel.add(Box.createVerticalStrut(10));

                OnlineRow row = new OnlineRow(sessione);
                online_rows.add(row);
                online_rows_panel.add(row);
                
            }
            UiUtil.ScrollablePanel scrollable_panel = new UiUtil.ScrollablePanel(online_rows_panel);
            add(scrollable_panel);
        }
        
        public List<OnlineRow> getOnlineRows(){
            return online_rows;
        }
    }

    public class OnlineRow extends UiUtil.BlankPanel{
        private static final int PANEL_HEIGHT = 35;
        private final Online sessione_online;
        

        private JButton modifica_btn;
        private JButton elimina_btn;

        public OnlineRow(Online sessione_online) {
            super(UiUtil.TRASPARENT_COLOR);
            this.sessione_online = sessione_online;

            setPreferredSize(new Dimension(0, PANEL_HEIGHT));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));
            setLayout(new BorderLayout(10, 0));

            JLabel data_label = UiUtil.infoLabel("Sessione del: " + UiUtil.DATE_FORMATTER.format(sessione_online.getGiornoSessione()), Font.BOLD, FONT_SIZE);
            JLabel codice_label = UiUtil.infoLabel("Codice Meeting: " + sessione_online.getCodiceMeeting(), Font.PLAIN, FONT_SIZE);

            JPanel textPanel = new JPanel();
            textPanel.setOpaque(false);
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.add(data_label);
            textPanel.add(Box.createVerticalStrut(5));
            textPanel.add(codice_label);

            this.modifica_btn = UiUtil.createButton("Modifica");
            this.elimina_btn = UiUtil.createButton("Elimina");

            JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            actions.setOpaque(false);
            actions.add(modifica_btn);
            actions.add(elimina_btn);

            add(textPanel, BorderLayout.CENTER); 
            add(actions, BorderLayout.EAST);     
        }

        public Online getSessioneOnline(){
            return sessione_online;
        }

        public void addModificaButtonListener(ActionListener listener){
            modifica_btn.addActionListener(listener);
        }

        public void addEliminaButtonListener(ActionListener listener){
            elimina_btn.addActionListener(listener);
        }
    }

    public void addAggiungiButtonListener(ActionListener listener){
        aggiungi_online_btn.addActionListener(listener);
    }

    public OnlinePanel getOnlinePanel(){
        return online_panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Corso corso = CorsoDAO.get(1);
            AMEOnlinePanel panel = new AMEOnlinePanel(corso.getSessioniOnline());
            UiUtil.TestFrame frame = new UiUtil.TestFrame();
            frame.add(panel);
            frame.revalidate();
            frame.repaint();
            
        });
    }
}
