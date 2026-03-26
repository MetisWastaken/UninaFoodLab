package com.ufl.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

import com.ufl.model.Pratica;
import com.ufl.model.Ricetta;


public class AggiungiRicetteFrame extends UiUtil.PopUpFrame  {
    RicettePanel ricette_panel;
    Pratica pratica_attiva;
    public AggiungiRicetteFrame() {
        super(new Dimension(500,300));
       
    }

    public void setAggiungiRicetteFrame(Pratica pratica) {
        setTitle("Aggiungui ricetta per il giorno: "+ UiUtil.DATE_FORMATTER.format(pratica.getGiornoSessione()));
        pratica_attiva = pratica;
        ricette_panel = new RicettePanel(pratica_attiva);
        setContent(ricette_panel);
    }

    public class RicettePanel extends UiUtil.BlankPanel {
        private List<RicettaRow> lista_ricette_row;

        public RicettePanel(Pratica pratica) {
            super(UiUtil.COLORE_SFONDO);
            
            List<Ricetta> ricette_pratica = pratica.getRicette();
            List<Ricetta> ricette_totali = pratica.getAllRicette();

            lista_ricette_row = new java.util.ArrayList<>();

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
            for(Ricetta r : ricette_totali) {
                RicettaRow ricetta_row = new RicettaRow(r);
                
                if(ricette_pratica.contains(r)) {
                    ricetta_row.disableRicettaRow();
                }

                lista_ricette_row.add(ricetta_row);

                add(ricetta_row);
            }
            
        }

        public List<RicettaRow> getRicetteRows() {
            return lista_ricette_row;
        }

    }

    public class RicettaRow extends UiUtil.CreateButton {
        Ricetta ricetta;

        public RicettaRow(Ricetta ricetta) {
            super(ricetta.getNome(),20);
            this.ricetta = ricetta;
            
        }

        public Ricetta getRicetta() {
            return ricetta;
        }

        public void addRicettaRowListener(ActionListener actionListener) {
            super.addActionListener(actionListener);
        }
        
        public void disableRicettaRow() {
            super.setEnabled(false);
        }
    }

    public RicettePanel getRicettePanel(){
        return ricette_panel;
    }

    public Pratica getPraticaAttiva(){
        return pratica_attiva;
    }

}
