package com.ufl.view;

import java.awt.*;


import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.time.LocalDate;


import com.ufl.model.Corso;
import com.ufl.model.Chef;
import com.ufl.dao.ChefDAO;

public class CorsiContainerPanel extends UiUtil.BlankPanel {
    private JButton search_cat_btn;
    private JTextField search_cat_field;
    private CorsiPanel corsi_panel;

    public CorsiContainerPanel(boolean miei_corsi, ArrayList<Corso> corsi) {
        super(UiUtil.TRASPARENT_COLOR);
        setLayout(new BorderLayout());
        add(new SearchCatPanel(), BorderLayout.NORTH);


        this.corsi_panel = new CorsiPanel(miei_corsi, corsi);
        UiUtil.ScrollablePanel scrollPane = new UiUtil.ScrollablePanel(this.corsi_panel);
        add(scrollPane);


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

    public class CorsiPanel extends UiUtil.BlankPanel {
        private ArrayList<CorsoRow> corsi_rows;
        private UiUtil.CreateButton aggiungi_corso_btn;
        public CorsiPanel(boolean miei_corsi, ArrayList<Corso> corsi) {
            super(UiUtil.TRASPARENT_COLOR);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            if(miei_corsi){
                aggiungi_corso_btn = new UiUtil.CreateButton("+", 80);
                add(Box.createVerticalStrut(10));
                add(aggiungi_corso_btn);
            }
            corsi_rows = new ArrayList<>();
            for(Corso corso : corsi){
                CorsoRow corso_row = new CorsoRow(corso, miei_corsi);
                corsi_rows.add(corso_row);
                add(Box.createVerticalStrut(10));
                add(corso_row);
            }
        }
        public ArrayList<CorsoRow> getCorsiRows(){
            return corsi_rows;
        }
        
        public void addAggiungiCorsoButtonListener(ActionListener listener){
            if(aggiungi_corso_btn != null){
                aggiungi_corso_btn.addActionListener(listener);
            }
        }
        
    }

    

    public class CorsoRow extends UiUtil.BorderedPanel {
        private static final int NOME_CORSO_FONT_SIZE = 16;
        private final static int DATE_CORSO_FONT_SIZE = 12;
        private static final int PANEL_HEIGHT = 80;
        private static final int NOME_WIDTH = 200;      
        private static final int CATEGORIA_WIDTH = 110; 
        private static final int DATE_WIDTH = 150;      
        
        private JButton dettagli_btn;
        private JButton modifica_btn;
        private final Corso corso;
        private final boolean mieiCorsi;

        public CorsoRow(Corso corso, boolean miei_corsi) {
            super(UiUtil.COLORE_PRIMARIO, 3, 2);
            this.corso = corso;
            this.mieiCorsi = miei_corsi;

            setLayout(new GridBagLayout());
            setBackground(UiUtil.TRASPARENT_COLOR);
            setAlignmentX(Component.LEFT_ALIGNMENT);

            setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));
            setPreferredSize(new Dimension(getPreferredSize().width, PANEL_HEIGHT));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.WEST;

            // Nome (colonna 0)
            JLabel nome_label = new JLabel(corso.getNome());
            nome_label.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, NOME_CORSO_FONT_SIZE));
            nome_label.setPreferredSize(new Dimension(NOME_WIDTH, 20));
            nome_label.setMinimumSize(new Dimension(NOME_WIDTH, 20));
            gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
            add(nome_label, gbc);

            // Categoria (colonna 1)
            JLabel categoria_label = new JLabel(corso.getCategoria());
            categoria_label.setFont(new Font(UiUtil.FONT_FAMILY, Font.PLAIN, DATE_CORSO_FONT_SIZE));
            categoria_label.setPreferredSize(new Dimension(CATEGORIA_WIDTH, 20));
            categoria_label.setMinimumSize(new Dimension(CATEGORIA_WIDTH, 20));
            gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.0;
            add(categoria_label, gbc);

            // Date (colonna 2)
            String dataInFormattata = corso.getDataIn().format(UiUtil.DATE_FORMATTER);
            String dataFinFormattata = corso.getDataFin().format(UiUtil.DATE_FORMATTER);
            JLabel date_label = new JLabel(dataInFormattata+ " - " + dataFinFormattata);
            date_label.setFont(new Font(UiUtil.FONT_FAMILY, Font.PLAIN, DATE_CORSO_FONT_SIZE));
            date_label.setPreferredSize(new Dimension(DATE_WIDTH, 20));
            date_label.setMinimumSize(new Dimension(DATE_WIDTH, 20));
            gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0.0;
            add(date_label, gbc);

            // Spazio vuoto per spingere i bottoni a destra
            JPanel spacer = new JPanel();
            spacer.setOpaque(false);
            gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(spacer, gbc);

            // Bottone Dettagli (colonna 4)
            this.dettagli_btn = UiUtil.createButton("Dettagli");
            gbc.gridx = 4; gbc.gridy = 0; gbc.weightx = 0.0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.EAST;
            add(dettagli_btn, gbc);

            // Bottone Modifica (colonna 5)
            this.modifica_btn = UiUtil.createButton("Modifica");
            gbc.gridx = 5; gbc.gridy = 0;
            add(modifica_btn, gbc);

            updateButtonsState();
        }

        private void updateButtonsState() {
            boolean corsoNonScaduto = !corso.getDataFin().isBefore(LocalDate.now());
            modifica_btn.setEnabled(mieiCorsi && corsoNonScaduto);
        }

        public Corso getCorso() {
            return corso;
        }

        public void addDettagliButtonListener(ActionListener listener){
            dettagli_btn.addActionListener(listener);
        }

        public void addModificaButtonListener(ActionListener listener){
            modifica_btn.addActionListener(listener);
        }

    }

    public String getSearchCatText() {
        return search_cat_field.getText();
    }

    public void addSearchCatButtonListener(ActionListener listener) {
        search_cat_btn.addActionListener(listener);
    }
    public CorsiPanel getCorsiPanel(){
        return corsi_panel;
    }

    public ArrayList<CorsoRow> getArrayCorsiRows(){
        return corsi_panel.getCorsiRows();
    }

    public static void main(String[] args) {
        UiUtil.TestFrame frame = new UiUtil.TestFrame();

        Chef chef = ChefDAO.get("GEsposito");
        CorsiContainerPanel corsi_panel = new CorsiContainerPanel(true, chef.getCorsi(true,""));
        
        frame.add(corsi_panel);
        frame.revalidate();
    }
}
