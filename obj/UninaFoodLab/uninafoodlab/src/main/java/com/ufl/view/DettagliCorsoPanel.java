package com.ufl.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;


import com.ufl.model.Chef;
import com.ufl.model.Corso;
import com.ufl.model.Online;
import com.ufl.model.Pratica;
import com.ufl.view.UiUtil.BorderedPanel;

public class DettagliCorsoPanel extends UiUtil.BlankPanel {
    private static final int TITLE_FONT_SIZE = 22;
    private static final int SUBTITLE_FONT_SIZE = 16;
    private static final int TEXT_FONT_SIZE = 14;

    private final Corso corso;

    public DettagliCorsoPanel(Corso corso) {
        super(UiUtil.TRASPARENT_COLOR);
        this.corso = corso;
        setLayout(new BorderLayout());

        if (corso.getChef() == null) {
            corso.recChef();
        }
        if (corso.getSessioniPratiche() == null) {
            corso.recSessioniPratiche();
        }
        if (corso.getSessioniOnline() == null) {
            corso.recSessioniOnline();
        }
        
        JPanel content = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(buildHeaderBigCorso());
        content.add(Box.createVerticalStrut(10));
        content.add(buildPraticheBox());
        content.add(Box.createVerticalStrut(10));
        content.add(buildOnlineBox());
        content.add(Box.createVerticalGlue());

        add(new UiUtil.ScrollablePanel(content), BorderLayout.CENTER);
    }

    private JPanel buildHeaderBigCorso(){
        UiUtil.BorderedPanel box = new BorderedPanel(UiUtil.TRASPARENT_COLOR, 2, 12);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(UiUtil.COLORE_SFONDO);

        JLabel titolo = new JLabel(corso.getNome());
        titolo.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, TITLE_FONT_SIZE));
        
        box.add(titolo);
        box.add(Box.createVerticalStrut(5));
        box.add(infoLabel("Categoria: " + corso.getCategoria()));
        box.add(infoLabel("Periodo: " + corso.getDataIn() + " fino al " + corso.getDataFin()));
        box.add(infoLabel("Frequenza settimanale: " + corso.getFrequenzaSettimanale()));
        box.add(infoLabel("Chef: " + corso.getChef().getNome()));
        
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.setMaximumSize(new Dimension(Integer.MAX_VALUE, box.getPreferredSize().height));

        return box;
    }

    private JPanel buildPraticheBox(){
        JPanel rows = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));

        List<Pratica> pratiche = corso.getSessioniPratiche() == null
            ? new ArrayList<>()
            : corso.getSessioniPratiche();

        if(pratiche.isEmpty()){
            rows.add(infoLabel("Nessuna sessione pratica disponibile"));
        }else{
            for(Pratica p : pratiche){
                rows.add(sessionRow(
                        "Data: " + p.getGiornoSessione() + " | Aula: " + p.getAula() + " | Posti: " + p.getPostiTotali()+ " | Prenotati: " + p.getNStudentiIscritti()));
            }
        }
        return buildBox("Sessioni Pratiche: ", rows);
    }

    private JPanel buildOnlineBox(){
        JPanel rows = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));
        List<Online> online = corso.getSessioniOnline() == null
            ? new ArrayList<>()
            : new ArrayList<>(corso.getSessioniOnline());
        
        if(online.isEmpty()){
            rows.add(infoLabel("Nessuna sessione online disponibile"));
        }else{
            for (Online o : online) {
                rows.add(sessionRow(
                        "Data: " + o.getGiornoSessione() + " | Meeting: " + o.getCodiceMeeting()));
                rows.add(Box.createVerticalStrut(6));
            }
        }
        return buildBox("Sessioni online:", rows);
    }
//Ausiliarie
    private JLabel infoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(UiUtil.FONT_FAMILY, Font.PLAIN, TEXT_FONT_SIZE));
        return label;
    }

    private JPanel sessionRow(String text) {
        JPanel row = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
        row.setLayout(new FlowLayout(FlowLayout.LEFT));
        row.add(infoLabel(text));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        return row;
    }

    private JPanel buildBox(String title, JPanel body) {
        UiUtil.BorderedPanel box= new UiUtil.BorderedPanel(UiUtil.COLORE_PRIMARIO, 2, 10);
        box.setLayout(new BorderLayout(0, 8));
        box.setBackground(UiUtil.COLORE_SFONDO);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, SUBTITLE_FONT_SIZE));

        box.add(titleLabel, BorderLayout.NORTH);
        box.add(body, BorderLayout.CENTER);

        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.setMaximumSize(new Dimension(Integer.MAX_VALUE, box.getPreferredSize().height));
        return box;
    }

}
