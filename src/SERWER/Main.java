package SERWER;

import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
 
public class Main implements ActionListener {
 
        private static final JFrame frame = new JFrame("Klient czatu");
        private static final JTextArea textArea = new JTextArea();
        private static final JTextField textField = new JTextField(25);
        private static final JButton sendButton = new JButton("Wyœlij przeklêty draniu!");
       
        public Main() {
                frame.setSize(450, 375);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               
                Panel p = new Panel();
               
                sendButton.addActionListener(this);
                textArea.setEditable(false);
                JScrollPane areaScrollPane = new JScrollPane(textArea);
                areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                areaScrollPane.setPreferredSize(new Dimension(430, 275));
               
                p.add(areaScrollPane);
                p.add(textField);
                p.add(sendButton);
               
                frame.add(p);
                frame.setVisible(true);
        }
       
        public static void main(String[] args) {
                new Main();
                Serwer serwer = new Serwer();
        }
 
        @Override
        public void actionPerformed(ActionEvent arg0) {
                String message = textField.getText();
                textArea.append("==Ty==       " + message + "\n");
                textField.setText("");
        }
 
}