package com.ufl.view;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

import com.ufl.view.UiUtil;

import com.ufl.model.Corso;
import com.ufl.model.Chef;
import com.ufl.dao.ChefDAO;

public class CorsiContainerPanel extends UiUtil.BlankPanel {
    private static final int NOME_CORSO_FONT_SIZE = 18;
    private final static int DATE_CORSO_FONT_SIZE = 14;

    private JButton search_cat_btn;
    private JTextField search_cat_field;

    public CorsiContainerPanel(boolean miei_corsi, ArrayList<Corso> corsi) {
        super(UiUtil.COLORE_ACCENTO);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new SearchCatPanel(), BorderLayout.NORTH);
        add(Box.createVerticalGlue());
        add(new CorsiPanel(corsi.get(0)), BorderLayout.CENTER);
        add(Box.createVerticalStrut(10));
        add(new CorsiPanel(corsi.get(1)), BorderLayout.CENTER);
        add(Box.createVerticalStrut(10));


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
    private class CorsiPanel extends UiUtil.BorderedPanel {
        private Integer corso_id;
        public CorsiPanel(Corso corso) {
            super(UiUtil.COLORE_PRIMARIO, 3, 2);
            this.corso_id = corso.getId();

            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBackground(UiUtil.TRASPARENT_COLOR);

            UiUtil.BlankPanel nome_panel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            JLabel nome_label = new JLabel(corso.getNome());
            nome_label.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, NOME_CORSO_FONT_SIZE));
            nome_panel.add(nome_label);

            UiUtil.BlankPanel categoria_panel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            JLabel categoria_label = new JLabel(corso.getCategoria());
            categoria_label.setFont(new Font(UiUtil.FONT_FAMILY, Font.PLAIN, DATE_CORSO_FONT_SIZE));
            categoria_panel.add(categoria_label);

            UiUtil.BlankPanel date_panel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            JLabel date_label = new JLabel(corso.getDataIn().toString() + " - " + corso.getDataFin().toString());
            date_label.setFont(new Font(UiUtil.FONT_FAMILY, Font.PLAIN, DATE_CORSO_FONT_SIZE));
            date_panel.add(date_label);

            add(Box.createHorizontalStrut(10));
            add(nome_panel);
            add(Box.createHorizontalStrut(10));
            add(categoria_panel);
            add(Box.createHorizontalStrut(10));
            add(date_panel);
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
