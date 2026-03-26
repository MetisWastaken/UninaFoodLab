package com.ufl.view;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;

public class HomepageContainer extends UiUtil.BlankPanel {

    private UiUtil.CreateButton homepage_exit_btn;
    private JButton miei_corsi_btn;
    private JButton altri_corsi_btn;
    private JButton notifiche_btn;
    private JButton report_btn;
    private UiUtil.BlankPanel container_panel;

    public HomepageContainer() {
        super(UiUtil.TRASPARENT_COLOR);
        setLayout(new BorderLayout());
        add(new ButtonPanel(), BorderLayout.NORTH);
        add(buildContainerPanel(), BorderLayout.CENTER);
        
    }

   

    private class ButtonPanel extends UiUtil.BorderedPanel {
        public ButtonPanel() {
            super(UiUtil.COLORE_PRIMARIO, 3, 20);
            setBackground(UiUtil.COLORE_PRIMARIO);
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            homepage_exit_btn = new UiUtil.CreateButton("/ExitMan.png", new Dimension(26, 26), UiUtil.COLORE_PRIMARIO);
            miei_corsi_btn = UiUtil.createButton("I Miei Corsi");
            altri_corsi_btn = UiUtil.createButton("Altri Corsi");
            notifiche_btn = UiUtil.createButton("Notifiche");
            report_btn = UiUtil.createButton("Report");

            add(homepage_exit_btn);
            add(Box.createHorizontalStrut(10));
            add(miei_corsi_btn);
            add(Box.createHorizontalStrut(10));
            add(altri_corsi_btn);
            add(Box.createHorizontalStrut(10));
            add(notifiche_btn);
            add(Box.createHorizontalStrut(10));
            add(report_btn);
            add(Box.createHorizontalGlue());
        }
    }

    public void addHomepageExitButtonListener(ActionListener listener) {
        homepage_exit_btn.addActionListener(listener);
    }

    public void addMieiCorsiButtonListener(ActionListener listener) {
        miei_corsi_btn.addActionListener(listener);
    }

    public void addAltriCorsiButtonListener(ActionListener listener) {
        altri_corsi_btn.addActionListener(listener);
    }

    public void addNotificheButtonListener(ActionListener listener) {
        notifiche_btn.addActionListener(listener);
    }

    public void addReportButtonListener(ActionListener listener) {
        report_btn.addActionListener(listener);
    }

    private JPanel buildContainerPanel() {
        container_panel = new UiUtil.BlankPanel(UiUtil.TRASPARENT_COLOR);
        container_panel.setLayout(new BorderLayout()); 
        return container_panel;
    }

    /** Sostituisce il contenuto del container con il panel fornito */
    public void setContent(JPanel panel) {
        container_panel.removeAll();
        container_panel.add(panel, BorderLayout.CENTER); 
        container_panel.revalidate();
        container_panel.repaint();
    }

}
