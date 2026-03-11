package com.ufl.view;

import java.awt.*;

import javax.swing.*;



import java.util.List;

import com.ufl.model.Corso;
import com.ufl.model.Ingrediente;
import com.ufl.model.Online;
import com.ufl.model.Pratica;
import com.ufl.model.Ricetta;
import com.ufl.view.UiUtil.PopUpFrame;
import com.ufl.dao.CorsoDAO;

public class DettagliCorsoPanel extends UiUtil.BorderedPanel {
    private static final int TITLE_FONT_SIZE = 22;
    private static final int SUBTITLE_FONT_SIZE = 16;
    private static final int TEXT_FONT_SIZE = 14;
    private static UiUtil.PopUpFrame activePopup=null;

    private List<Pratica> pratiche=null;
    private List<Online> online=null;

    public DettagliCorsoPanel(Corso corso) {
        super(UiUtil.TRASPARENT_COLOR, 0, 10);
        setBackground(UiUtil.TRASPARENT_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        if(activePopup == null){
            activePopup = new PopUpFrame( new Dimension(400, 300));
        }

        this.pratiche = corso.getSessioniPratiche();
        this.online = corso.getSessioniOnline();

        add(new HeaderBigCorsoPanel(corso), BorderLayout.CENTER);
        add(Box.createVerticalStrut(10));
        add(new PraticheBox(pratiche), BorderLayout.CENTER);
        add(Box.createVerticalStrut(10));
        add(new OnlineBox(online), BorderLayout.CENTER);
        add(Box.createVerticalGlue());
    }

    private class HeaderBigCorsoPanel extends UiUtil.BlankPanel {
        private static final int PANEL_HEIGHT = 120;

        public HeaderBigCorsoPanel(Corso corso) {
            super(UiUtil.TRASPARENT_COLOR);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JLabel titolo = infoLabel(corso.getNome());
            titolo.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, TITLE_FONT_SIZE));

            add(titolo);
            add(Box.createVerticalStrut(5));
            add(infoLabel("Categoria: " + corso.getCategoria()));
            add(infoLabel("Periodo: " + corso.getDataIn() + " fino al " + corso.getDataFin()));
            add(infoLabel("Frequenza settimanale: " + corso.getFrequenzaSettimanale()));
            add(infoLabel("Chef: " + corso.getChef().getNome()));

            setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));
            setPreferredSize(new Dimension(getPreferredSize().width, PANEL_HEIGHT));
        }
    }
    
    private class PraticheBox extends UiUtil.BorderedPanel {
        private static final int PANEL_HEIGHT = 150;

        public PraticheBox(List<Pratica> pratiche) {
            super(UiUtil.COLORE_PRIMARIO, 2, 0);

            setLayout(new BorderLayout(0, 8));
            setBackground(UiUtil.TRASPARENT_COLOR);

            UiUtil.BlankPanel pratica_container = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            pratica_container.setLayout(new BoxLayout(pratica_container, BoxLayout.Y_AXIS));
            pratica_container.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            
            
            JLabel title_pratica = new JLabel("Sessioni Pratiche:");
            title_pratica.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, SUBTITLE_FONT_SIZE));
            title_pratica.setAlignmentX(Component.LEFT_ALIGNMENT);

            pratica_container.add(title_pratica);
            pratica_container.add(Box.createVerticalStrut(10));
        
            if(pratiche == null || pratiche.isEmpty()){
                JLabel emptyLabel = infoLabel("Nessuna sessione pratica disponibile");
                emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                pratica_container.add(emptyLabel);
            }
            else{
                for(Pratica pratica : pratiche){
                    PraticaRow row = new PraticaRow(pratica);
                    row.setAlignmentX(Component.LEFT_ALIGNMENT);
                    pratica_container.add(row);
                    pratica_container.add(Box.createVerticalStrut(10));
                }
            }
            add(new UiUtil.ScrollablePanel(pratica_container), BorderLayout.CENTER);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));
            setPreferredSize(new Dimension(getPreferredSize().width, PANEL_HEIGHT));
        }

        private class PraticaRow extends UiUtil.BlankPanel {
            private Pratica pratica;
            private JButton ricette_btn;
            private JButton iscritti_btn;
            private JButton ingredienti_btn;
            private static final int ROW_HEIGHT = 40;

            // Cache dati DB della pratica
            private List<Ricetta> ricetteCache;
            private String iscrittiCache;
            private List<Ingrediente> ingredientiCache;

            public PraticaRow(Pratica pratica) {
                super(UiUtil.TRASPARENT_COLOR);
                this.pratica = pratica;

                // Carica una sola volta i dati dal DB
                this.ricetteCache = pratica.getRicette();
                this.iscrittiCache = pratica.getStudentiIscritti();
                this.ingredientiCache = pratica.getIngredientiPratica();

                setLayout(new GridBagLayout());
                setBackground(UiUtil.COLORE_SFONDO);
                setAlignmentX(Component.LEFT_ALIGNMENT);
                setMaximumSize(new Dimension(Integer.MAX_VALUE, ROW_HEIGHT));
                setPreferredSize(new Dimension(getPreferredSize().width, ROW_HEIGHT));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 6, 0, 6);
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Data
                gbc.gridx = 0;
                gbc.weightx = 0.28;
                add(infoLabel("Data: " + pratica.getGiornoSessione()), gbc);

                // Aula
                gbc.gridx = 1;
                gbc.weightx = 0.18;
                add(infoLabel("Aula: " + pratica.getAula()), gbc);

                // Posti/Prenotati
                gbc.gridx = 2;
                gbc.weightx = 0.24;
                add(infoLabel("Posti: " + pratica.getPostiTotali() + " | Prenotati: " + pratica.getNStudentiIscritti()), gbc);

                // Spaziatore elastico
                gbc.gridx = 3;
                gbc.weightx = 1.0;
                add(Box.createHorizontalStrut(1), gbc);

                // Bottoni a destra
                gbc.weightx = 0;

                ricette_btn = UiUtil.createButton("Ricette");
                gbc.gridx = 4;
                add(ricette_btn, gbc);

                iscritti_btn = UiUtil.createButton("Iscritti");
                gbc.gridx = 5;
                add(iscritti_btn, gbc);

                ingredienti_btn = UiUtil.createButton("Ingredienti");
                gbc.gridx = 6;
                add(ingredienti_btn, gbc);

                updateButtonsState();
                listenerRicette();
                listenerIscritti();
                listenerIngredienti();

            }

            private void updateButtonsState() {
                boolean hasRicette = ricetteCache != null && !ricetteCache.isEmpty();
                ricette_btn.setEnabled(hasRicette);

                boolean hasIscritti = iscrittiCache != null && !iscrittiCache.isBlank();
                iscritti_btn.setEnabled(hasIscritti);

                boolean hasIngredienti = ingredientiCache != null && !ingredientiCache.isEmpty();
                ingredienti_btn.setEnabled(hasIngredienti);
            }

          private void listenerRicette(){
                ricette_btn.addActionListener(e -> showRicettePopup(pratica));
            }

            private void listenerIscritti(){
                iscritti_btn.addActionListener(e -> showIscrittiPopup(pratica));
            }

            

            private void listenerIngredienti(){
                ingredienti_btn.addActionListener(e -> showIngredientiPopup(pratica));
            }

            private void showRicettePopup(Pratica pratica) {
                JPanel content = new UiUtil.BlankPanel(UiUtil.COLORE_SFONDO);
                content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
                content.add(infoLabel("Pratica: " + pratica.getGiornoSessione() + " - " + pratica.getAula()));
                content.add(Box.createVerticalStrut(8));

                for (Ricetta ricetta : ricetteCache) {
                    content.add(infoLabel("Ricetta: " + ricetta.getNome()));
                    ricetta.recIngredienti();

                    List<Ingrediente> ingredienti = ricetta.getIngredienti();
                    if (ingredienti != null && !ingredienti.isEmpty()) {
                        for (Ingrediente ingrediente : ingredienti) {
                            content.add(infoLabel(
                                "   Ingrediente: " + ingrediente.getNome()
                                + " - Quantità: " + ingrediente.getQuantita()
                                + " - Tipo: " + ingrediente.getUnitMisura()
                            ));
                        }
                    }
                    content.add(Box.createVerticalStrut(6));
                }

                openPraticaPopup(pratica, content);
            }

            private void showIscrittiPopup(Pratica pratica) {
                JPanel content = new UiUtil.BlankPanel(UiUtil.COLORE_SFONDO);
                content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
                content.add(infoLabel("Pratica: " + pratica.getGiornoSessione() + " - " + pratica.getAula()));
                content.add(infoLabel(iscrittiCache));

                openPraticaPopup(pratica, content);
            }

            private void showIngredientiPopup(Pratica pratica){
                JPanel content = new UiUtil.BlankPanel(UiUtil.COLORE_SFONDO);
                content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
                content.add(infoLabel("Pratica: " + pratica.getGiornoSessione() + " - " + pratica.getAula()));
                content.add(Box.createVerticalStrut(8));

                for (Ingrediente ingrediente : ingredientiCache) {
                    content.add(infoLabel(
                        "Ingrediente: " + ingrediente.getNome()
                        + " - Quantità: " + ingrediente.getQuantita()
                        + " - Tipo: " + ingrediente.getUnitMisura()
                    ));
                    content.add(Box.createVerticalStrut(4));
                }

                openPraticaPopup(pratica, content);
            }
        
            private void openPraticaPopup(Pratica pratica, JPanel content) {
                activePopup.setTitle("Dettagli Pratica - " + pratica.getGiornoSessione());
                activePopup.setContent(content);
                activePopup.setHorizontalScrollTrue();
                activePopup.setVisible(true);
            }
        }
    }

    private class OnlineBox extends UiUtil.BorderedPanel {
        private static final int PANEL_HEIGHT = 150;

        public OnlineBox(List<Online> onlineList) {
            super(UiUtil.COLORE_PRIMARIO, 2, 0);
            setLayout(new BorderLayout(0, 8));
            setBackground(UiUtil.TRASPARENT_COLOR);

            UiUtil.BlankPanel onlineContainer = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
            onlineContainer.setLayout(new BoxLayout(onlineContainer, BoxLayout.Y_AXIS));
            onlineContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

            JLabel titleOnline = new JLabel("Sessioni Online:");
            titleOnline.setFont(new Font(UiUtil.FONT_FAMILY, Font.BOLD, SUBTITLE_FONT_SIZE));
            titleOnline.setAlignmentX(Component.LEFT_ALIGNMENT);

            onlineContainer.add(titleOnline);
            onlineContainer.add(Box.createVerticalStrut(10));

            if (onlineList == null || onlineList.isEmpty()) {
                JLabel emptyLabel = infoLabel("Nessuna sessione online disponibile");
                emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                onlineContainer.add(emptyLabel);
            } else {
                for (Online o : onlineList) {
                    OnlineRow row = new OnlineRow(o);
                    row.setAlignmentX(Component.LEFT_ALIGNMENT);
                    onlineContainer.add(row);
                    onlineContainer.add(Box.createVerticalStrut(10));
                }
            }

            add(new UiUtil.ScrollablePanel(onlineContainer), BorderLayout.CENTER);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_HEIGHT));
            setPreferredSize(new Dimension(getPreferredSize().width, PANEL_HEIGHT));
        }

        private class OnlineRow extends UiUtil.BlankPanel {
            private static final int ROW_HEIGHT = 40;

            public OnlineRow(Online online) {
                super(UiUtil.TRASPARENT_COLOR);
                setLayout(new GridBagLayout());
                setBackground(UiUtil.COLORE_SFONDO);
                setAlignmentX(Component.LEFT_ALIGNMENT);
                setMaximumSize(new Dimension(Integer.MAX_VALUE, ROW_HEIGHT));
                setPreferredSize(new Dimension(getPreferredSize().width, ROW_HEIGHT));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridy = 0;
                gbc.insets = new Insets(0, 6, 0, 6);
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Data
                gbc.gridx = 0;
                gbc.weightx = 0.35;
                add(infoLabel("Data: " + online.getGiornoSessione()), gbc);

                // Meeting
                gbc.gridx = 1;
                gbc.weightx = 0.65;
                add(infoLabel("Codice meeting: " + online.getCodiceMeeting()), gbc);

                // Spaziatore elastico
                gbc.gridx = 2;
                gbc.weightx = 1.0;
                add(Box.createHorizontalStrut(1), gbc);
            }
        }
    }
    

    
//Ausiliarie
    private JLabel infoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(UiUtil.FONT_FAMILY, Font.PLAIN, TEXT_FONT_SIZE));
        return label;
    }


    
    public static void main(String[] args) {
        Corso corso = CorsoDAO.get(1);
        corso.recChef();
        DettagliCorsoPanel panel = new DettagliCorsoPanel(corso);
        UiUtil.PopUpFrame frame = new UiUtil.PopUpFrame( "Dettagli Corso", new Dimension(800, 600));
        frame.setContent(panel);
        frame.setVisible(true);
    }
}
