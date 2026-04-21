package src;

import javax.swing.JFrame;

import src.components.LoginPanel;
import src.services.TestData;
import src.services.UserDomain;

public class App extends JFrame {
    public App() {

        // TestData.dumpTestUsers().forEach((k, v) -> {
        // new UserDomain().createUser(k, v.name, v.password, v.role);
        // });

        setTitle("AT-1.0");
        add(new LoginPanel());
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
