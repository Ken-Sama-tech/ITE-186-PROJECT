package src;

import javax.swing.JFrame;

import src.components.LoginPanel;

public class App extends JFrame {
    public App() {

        setTitle("AT 1.0");
        add(new LoginPanel());
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
