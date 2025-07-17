import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Esempio Swing");
        JButton button = new JButton("Cliccami!");

        button.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Hai cliccato!"));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.getContentPane().add(button);
        frame.setVisible(true);
    }
}
