package com.ufl.view;

import java.awt.*;
import java.lang.reflect.Array;

import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;

import com.ufl.view.UiUtil;

import com.ufl.model.Corso;
import com.ufl.model.Chef;
import com.ufl.dao.ChefDAO;

public class CorsiContainerPanel extends UiUtil.BlankPanel {
    private static final int NOME_CORSO_FONT_SIZE = 18;
    private final static int DATE_CORSO_FONT_SIZE = 14;

    private JButton search_cat_btn;
    private JTextField search_cat_field;
    private CorsiPanel corsi_panel;

    public CorsiContainerPanel(boolean miei_corsi, ArrayList<Corso> corsi) {
        super(UiUtil.COLORE_ACCENTO);
        setLayout(new BorderLayout());
        add(new SearchCatPanel(), BorderLayout.NORTH);

        this.corsi_panel = new CorsiPanel(corsi);
        add(this.corsi_panel);


    }

    private class SearchCatPanel extends UiUtil.BorderedPanel {
        public SearchCatPanel() {
            super(UiUtil.COLORE_PRIMARIO, 1, 0);
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBackground(UiUtil.COLORE_PRIMARIO);
            search_cat_btn = UiUtil.createButton("Cerca");
            search_cat_field = UiUtil.createInputTextField("Categoria", new Dimension(200, 30));

            add(Box.createHorizontalGlue());
            add(search_cat_btn);
            add(Box.createHorizontalStrut(10));
            add(search_cat_field);
            add(Box.createHorizontalStrut(10));
            
            
            
        }
    }

    private class CorsiPanel extends UiUtil.BlankPanel {
        ArrayList<CorsoPanel> corsi_panel;
        public CorsiPanel(ArrayList<Corso> corsi) {
            super(UiUtil.TRASPARENT_COLOR);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            corsi_panel = new ArrayList<>();
            for(Corso corso : corsi){
                CorsoPanel corso_panel = new CorsoPanel(corso);
                corsi_panel.add(corso_panel);
                add(corso_panel);
                add(Box.createVerticalStrut(10));
            }
        }
        public ArrayList<CorsoPanel> getCorsiPanel(){
            return corsi_panel;
        }
        
    }

    private class CorsoPanel extends UiUtil.BorderedPanel {
        private static final Dimension PANEL_DIMENSION = new Dimension(Integer.MAX_VALUE, 80);
        private Integer corso_id;
        private JButton dettagli_btn;
        private JButton modifica_btn;

        public CorsoPanel(Corso corso) {
            super(UiUtil.COLORE_PRIMARIO, 3, 2);
            this.corso_id = corso.getId();

            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBackground(UiUtil.TRASPARENT_COLOR);

            setPreferredSize(PANEL_DIMENSION);
            setMinimumSize(PANEL_DIMENSION);
            setMaximumSize(PANEL_DIMENSION);

            UiUtil.BlankPanel nome_panel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            JLabel nome_label = new JLabel(corso.getNome());
            nome_label.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, NOME_CORSO_FONT_SIZE));
            nome_panel.add(nome_label, BorderLayout.CENTER);

            UiUtil.BlankPanel categoria_panel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            JLabel categoria_label = new JLabel(corso.getCategoria());
            categoria_label.setFont(new Font(UiUtil.FONT_FAMILY, Font.PLAIN, DATE_CORSO_FONT_SIZE));
            categoria_panel.add(categoria_label, BorderLayout.CENTER);

            UiUtil.BlankPanel date_panel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            JLabel date_label = new JLabel(corso.getDataIn().toString() + " - " + corso.getDataFin().toString());
            date_label.setFont(new Font(UiUtil.FONT_FAMILY, Font.PLAIN, DATE_CORSO_FONT_SIZE));
            date_panel.add(date_label, BorderLayout.CENTER);

            this.dettagli_btn = UiUtil.createButton("Dettagli");
            this.modifica_btn = UiUtil.createButton("Modifica");
            modifica_btn.setEnabled(false);

            add(Box.createHorizontalStrut(10));
            add(nome_panel);
            add(Box.createHorizontalStrut(10));
            add(categoria_panel);
            add(Box.createHorizontalStrut(10));
            add(date_panel);
            add(Box.createHorizontalGlue());
            add(dettagli_btn);
            add(Box.createHorizontalStrut(10));
            add(modifica_btn);
        }

        public Integer getCorso_id() {
            return corso_id;
        }
        public void addDettagliButtonListener(ActionListener listener){
            dettagli_btn.addActionListener(listener);
        }


    }

    public static void main(String[] args) {
        UiUtil.TestFrame frame = new UiUtil.TestFrame();

        Chef chef = ChefDAO.get("GEsposito");
        CorsiContainerPanel corsi_panel = new CorsiContainerPanel(true, chef.getCorsi(true,""));
        
        frame.add(corsi_panel);
        frame.revalidate();
    }
}
