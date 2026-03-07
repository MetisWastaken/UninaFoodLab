package com.ufl.view;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;

public class HomepageContainer extends UiUtil.BlankPanel {


    JButton miei_corsi_btn;
    JButton altri_corsi_btn;
    JButton notifiche_btn;
    JButton report_btn;

    public HomepageContainer() {
        super(UiUtil.COLORE_ACCENTO);
        setLayout(new BorderLayout());
        add(new ButtonPanel(), BorderLayout.NORTH);
    }

    private class ButtonPanel extends UiUtil.BorderedPanel {
        public ButtonPanel() {
            super(UiUtil.COLORE_PRIMARIO, 3, 20);
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            miei_corsi_btn = UiUtil.createButton("I Miei Corsi");
            altri_corsi_btn = UiUtil.createButton("Altri Corsi");
            notifiche_btn = UiUtil.createButton("Notifiche");
            report_btn = UiUtil.createButton("Report");

            add(miei_corsi_btn);
            add(Box.createHorizontalStrut(20));
            add(altri_corsi_btn);
            add(Box.createHorizontalStrut(20));
            add(notifiche_btn);
            add(Box.createHorizontalStrut(20));
            add(report_btn);
            add(Box.createHorizontalGlue());


        }
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

    public static void main(String[] args) {
        UiUtil.TestFrame frame = new UiUtil.TestFrame();
        
        JPanel home_page_container = new HomepageContainer();
        frame.add(home_page_container);
        frame.revalidate();
    }
}
