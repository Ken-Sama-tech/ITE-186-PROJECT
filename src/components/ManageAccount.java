package src.components;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

import src.objects.User;

public class ManageAccount extends JPanel {
    public ManageAccount(User user) {
        setLayout(new GridBagLayout());

        JPanel container = new JPanel();
        container.add(new CreateAccount(user));
        container.setLayout(new GridBagLayout());

        JLabel title = new JLabel("Manage Account", JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JButton createAccount = new JButton("Create Account");
        createAccount.addActionListener(e -> {
            container.removeAll();
            container.add(new CreateAccount(user));
            revalidate();
            repaint();
        });

        JButton editAccount = new JButton("Edit Account");
        editAccount.addActionListener(e -> {
            container.removeAll();
            container.add(new EditAccount(user));
            revalidate();
            repaint();
        });

        add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridy++;
        add(createAccount, gbc);

        gbc.gridy++;
        add(editAccount, gbc);

        gbc.gridy++;
        add(container, gbc);
    }
}
