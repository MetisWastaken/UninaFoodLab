package com.ufl.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.ufl.model.Pratica;

import com.ufl.dao.CorsoDAO;
import com.ufl.model.Corso;

public class AMEPraticaPanel extends UiUtil.BorderedPanel {
    private static final int FONT_SIZE = 12;

    private UiUtil.CreateButton aggiungi_pratica_btn;
    private PraticaPanel pratica_panel;

    public AMEPraticaPanel(List<Pratica> sessioni_pratiche) {
        super(UiUtil.COLORE_PRIMARIO, 3, 0);
        setBackground(UiUtil.TRASPARENT_COLOR);
        setOpaque(false);
        setLayout(new BorderLayout(0, 10));

        this.aggiungi_pratica_btn = new UiUtil.CreateButton("Aggiungi Sessione", 35);
        this.pratica_panel = new PraticaPanel(sessioni_pratiche);

        add(aggiungi_pratica_btn, BorderLayout.NORTH);
        add(pratica_panel, BorderLayout.CENTER);
    }

    public class PraticaPanel extends UiUtil.BlankPanel {
        private List<PraticaRow> pratica_rows;

        public PraticaPanel(List<Pratica> sessioni_pratiche) {
            super(UiUtil.TRASPARENT_COLOR);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            this.pratica_rows = new ArrayList<>();

            UiUtil.BlankPanel pratica_rows_panel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            pratica_rows_panel.setLayout(new BoxLayout(pratica_rows_panel, BoxLayout.Y_AXIS));

            for (Pratica sessione : sessioni_pratiche) {
                pratica_rows_panel.add(Box.createVerticalStrut(10));

                PraticaRow row = new PraticaRow(sessione);
                pratica_rows.add(row);
                pratica_rows_panel.add(row);
            }

            UiUtil.ScrollablePanel scrollable_panel = new UiUtil.ScrollablePanel(pratica_rows_panel);
            add(scrollable_panel);
        }

        public List<PraticaRow> getPraticaRows() {
            return pratica_rows;
        }
    }

    public class PraticaRow extends UiUtil.BlankPanel {
        private static final int PANEL_HEIGHT = 35;
        private final Pratica sessione_pratica;

        private JButton modifica_btn;
        private JButton elimina_btn;
        private JButton agg_ricetta_btn; // rinominato

        public PraticaRow(Pratica sessione_pratica) {
            super(UiUtil.TRASPARENT_COLOR);
            this.sessione_pratica = sessione_pratica;

            setPreferredSize(new Dimension(0, PANEL_HEIGHT));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));
            setLayout(new BorderLayout(10, 0));

            JLabel data_label = UiUtil.infoLabel(
                "Sessione del: " + UiUtil.DATE_FORMATTER.format(sessione_pratica.getGiornoSessione()),
                Font.BOLD,
                FONT_SIZE
            );
            JLabel aula_label = UiUtil.infoLabel(
                "Aula: " + sessione_pratica.getAula(),
                Font.PLAIN,
                FONT_SIZE
            );

            JPanel textPanel = new JPanel();
            textPanel.setOpaque(false);
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.add(data_label);
            textPanel.add(Box.createVerticalStrut(5));
            textPanel.add(aula_label);

            this.modifica_btn = UiUtil.createButton("Modifica");
            this.elimina_btn = UiUtil.createButton("Elimina");
            this.agg_ricetta_btn = UiUtil.createButton("AggRicetta"); 

            JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            actions.setOpaque(false);
            actions.add(modifica_btn);
            actions.add(elimina_btn);
            actions.add(agg_ricetta_btn);

            add(textPanel, BorderLayout.CENTER);
            add(actions, BorderLayout.EAST);
        }

        public Pratica getSessionePratica() {
            return sessione_pratica;
        }

        public void addModificaButtonListener(ActionListener listener) {
            modifica_btn.addActionListener(listener);
        }

        public void addEliminaButtonListener(ActionListener listener) {
            elimina_btn.addActionListener(listener);
        }

        public void addAggRicettaButtonListener(ActionListener listener) { 
            agg_ricetta_btn.addActionListener(listener);
        }
    }

    public void addAggiungiButtonListener(ActionListener listener) {
        aggiungi_pratica_btn.addActionListener(listener);
    }

    public PraticaPanel getPraticaPanel() {
        return pratica_panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Corso corso = CorsoDAO.get(1);
            AMEPraticaPanel panel = new AMEPraticaPanel(corso.getSessioniPratiche());
            UiUtil.TestFrame frame = new UiUtil.TestFrame();
            frame.add(panel);
            frame.revalidate();
            frame.repaint();
        });
    }
}
