package com.ufl.view;

import com.ufl.model.Report;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
    
import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class VisualizzaJFreeChartReport extends UiUtil.BlankPanel {

    public VisualizzaJFreeChartReport(Report report) {

        super(UiUtil.COLORE_SFONDO);

        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        String mese = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        JLabel titolo = new JLabel("Report del mese corrente: " + mese);
        titolo.setFont((new Font(UiUtil.FONT_FAMILY, Font.BOLD, 18)));
        titolo.setForeground(UiUtil.COLORE_TESTO1);

        add(titolo, BorderLayout.NORTH);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(report.getNumeroCorsiTotali(), "", "Corsi Totali");
        dataset.addValue(report.getNumeroSessioniOnline(), "", "Online");
        dataset.addValue(report.getNumeroSessioniPratiche(), "", "Pratiche");
        dataset.addValue(report.getMediaRicette(), "", "Media Ricette");
        dataset.addValue(report.getMinRicette(), "", "Min Ricette");
        dataset.addValue(report.getMaxRicette(), "", "Max Ricette");

        JFreeChart chart = ChartFactory.createBarChart(
            "Report Mensile",
            "",
            "Valori",
            dataset
        );
        chart.removeLegend();
         
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(UiUtil.COLORE_ACCENTO);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        add(chartPanel, BorderLayout.CENTER);

            JLabel footer = new JLabel(
            "Media Ricette: " + report.getMediaRicette() + " | Min: " + report.getMinRicette() + " | Max: " + report.getMaxRicette() + " | Corsi Totali: " + report.getNumeroCorsiTotali() + " | Online: " + report.getNumeroSessioniOnline() + " | Pratiche: " + report.getNumeroSessioniPratiche()
        
        );

        JButton salvaBtn = UiUtil.createButton("Salva Report");
        salvaBtn.addActionListener(e -> SalvaReport(report, mese));

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false );

        footerPanel.add(footer, BorderLayout.NORTH);
        footerPanel.add(salvaBtn, BorderLayout.SOUTH);

        add(footerPanel, BorderLayout.SOUTH);

    }

    private void SalvaReport(Report report, String mese) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salva Report");
        fileChooser.setSelectedFile(new java.io.File("Report_" + mese + "_" + report.getUsernameChef() + ".txt"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        java.io.File file = fileChooser.getSelectedFile();

        if(!file.getName().endsWith(".txt")) {
            file = new java.io.File(file.getPath() + ".txt");
        }

    if (file.exists()) {
        int scelta = JOptionPane.showConfirmDialog(
            this,
            "Il file esiste già. Vuoi sovrascriverlo?",
            "Sovrascrivere?",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (scelta != JOptionPane.YES_OPTION) {
            return;
        }
    }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                writer.write("### Report Mensile: " + mese + " ###");
                writer.newLine();
                writer.write("Corsi Totali:         " + report.getNumeroCorsiTotali());
                writer.newLine();
                writer.write("Sessioni Online:      " + report.getNumeroSessioniOnline());
                writer.newLine();
                writer.write("Sessioni Pratiche:    " + report.getNumeroSessioniPratiche());
                writer.newLine();
                writer.write("Media Ricette:        " + report.getMediaRicette());
                writer.newLine();
                writer.write("Min Ricette:          " + report.getMinRicette());
                writer.newLine();
                writer.write("Max Ricette:          " + report.getMaxRicette());
                writer.newLine();


                JOptionPane.showMessageDialog(this, "Report salvato");

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Errore durante il salvataggio:");
        }

    }
    
}
