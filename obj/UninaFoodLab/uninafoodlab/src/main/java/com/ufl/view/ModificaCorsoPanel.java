package com.ufl.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import com.ufl.dao.CorsoDAO;
import com.ufl.model.Corso;
import com.ufl.model.Pratica;
import com.ufl.model.Online;

public class ModificaCorsoPanel extends UiUtil.BlankPanel {
    private static final int TITLE_FONT_SIZE = 22;
    private static final int SUBTITLE_FONT_SIZE = 16;


    private List<Pratica> pratiche;
    private List<Online> online;
    private CorsoNotificaPanel corso_notifica_panel;
    private POBox po_box;
    
    public ModificaCorsoPanel(Corso corso) {
        super(UiUtil.TRASPARENT_COLOR);
        
        setLayout(new BorderLayout()); 

        this.pratiche = corso.getSessioniPratiche();
        this.online = corso.getSessioniOnline();

        this.corso_notifica_panel = new CorsoNotificaPanel(corso);
        add(this.corso_notifica_panel, BorderLayout.NORTH);
        this.po_box = new POBox(pratiche, online);
        add(this.po_box, BorderLayout.CENTER);
    }

    public class CorsoNotificaPanel extends UiUtil.BlankPanel {
        private static final int PANEL_HEIGHT = 110;
        private UiUtil.CreateButton aggiungi_notifiche_btn;

        public CorsoNotificaPanel(Corso corso) {
            super(UiUtil.TRASPARENT_COLOR);
            setLayout(new BorderLayout()); 
            this.aggiungi_notifiche_btn = new UiUtil.CreateButton("Aggiungi Notifica a questo Corso", 80);

            UiUtil.BlankPanel titolo_panel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            JLabel titolo = UiUtil.infoLabel(corso.getNome(), Font.BOLD, TITLE_FONT_SIZE);
            titolo_panel.add(titolo);

            add(aggiungi_notifiche_btn, BorderLayout.NORTH);
            add(Box.createVerticalStrut(10), BorderLayout.WEST); 
            add(titolo_panel, BorderLayout.CENTER);

            setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));
            setPreferredSize(new Dimension(0, PANEL_HEIGHT));
        }

        public void addAggiungiNotificaButtonListener(ActionListener listener){
            aggiungi_notifiche_btn.addActionListener(listener);
        }
    }

    public class POBox extends UiUtil.BlankPanel {
        private static final int SIDE_BOX_W = 520;
        private static final int SIDE_BOX_H = 350;
        private static final int CENTER_GAP = 20;
        
        private AMEPraticaPanel ame_pratiche_panel;
        private AMEOnlinePanel ame_online_panel;

        public POBox(List<Pratica> pratiche, List<Online> online) {
            super(UiUtil.TRASPARENT_COLOR);

            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 

            JPanel inner = new JPanel(new GridBagLayout());
            inner.setOpaque(false);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;

            this.ame_pratiche_panel = new AMEPraticaPanel(pratiche);
            ame_pratiche_panel.setPreferredSize(new Dimension(SIDE_BOX_W, SIDE_BOX_H));

            this.ame_online_panel = new AMEOnlinePanel(online);
            ame_online_panel.setPreferredSize(new Dimension(SIDE_BOX_W, SIDE_BOX_H));

            JLabel praticheLabel = UiUtil.infoLabel("Sessioni Pratiche", Font.BOLD, SUBTITLE_FONT_SIZE);
            JLabel onlineLabel = UiUtil.infoLabel("Sessioni Online", Font.BOLD, SUBTITLE_FONT_SIZE);

            UiUtil.BlankPanel leftColumn = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
            praticheLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            ame_pratiche_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftColumn.add(praticheLabel);
            leftColumn.add(Box.createVerticalStrut(5));
            leftColumn.add(ame_pratiche_panel);

            UiUtil.BlankPanel rightColumn = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
            onlineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            ame_online_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            rightColumn.add(onlineLabel);
            rightColumn.add(Box.createVerticalStrut(5));
            rightColumn.add(ame_online_panel);

            JPanel gap = new JPanel();
            gap.setOpaque(false);
            gap.setPreferredSize(new Dimension(CENTER_GAP, SIDE_BOX_H));

            gbc.gridx = 0;
            inner.add(leftColumn, gbc);

            gbc.gridx = 1;
            inner.add(gap, gbc);

            gbc.gridx = 2;
            inner.add(rightColumn, gbc);

            add(inner);
        }

        public AMEPraticaPanel getAMEPratichePanel() {
            return ame_pratiche_panel;
        }

        public AMEOnlinePanel getAMEOnlinePanel() {
            return ame_online_panel;
        }
    }
        
    public CorsoNotificaPanel getCorsoNotificaPanel() {
        return corso_notifica_panel;
    }

    public POBox getPoBox() {
        return po_box;
    }

    public static void main(String[] args) {
        Corso corso = CorsoDAO.get(1);
        corso.recChef();
        ModificaCorsoPanel panel = new ModificaCorsoPanel(corso);
        UiUtil.TestFrame frame = new UiUtil.TestFrame();
        
        frame.add(panel);
        frame.setVisible(true);
    
    }
}
