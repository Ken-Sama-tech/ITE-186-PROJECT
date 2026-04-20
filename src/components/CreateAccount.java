package src.components;

import java.awt.*;

import javax.swing.*;

import src.enums.*;

import src.objects.*;

import src.services.UserDomain;

public class CreateAccount extends JPanel {
    public CreateAccount(User user) {

        setLayout(new GridBagLayout());

        JLabel title = new JLabel("Create Account", JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        JLabel studentIdLabel = new JLabel("Student ID");
        JTextField studentIdField = new JTextField(15);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);

        JLabel userRoleLabel = new JLabel("Role:");
        JComboBox<UserRole> userRoleField = new JComboBox<>(UserRole.values());

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);

        JButton create = new JButton("Create");
        create.addActionListener(e -> {

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

            if (!new ConfirmationDialog(frame, "Create User", "Create this user?").isConfirmed())
                return;

            String name = usernameField.getText();
            char[] password = passwordField.getPassword();
            UserRole role = (UserRole) userRoleField.getSelectedItem();
            String id = studentIdField.getText();

            UserDomain userDomain = new UserDomain();

            ResponseObject result = userDomain.createUser(id, name, password, role);

            if (result.success) {
                JOptionPane.showMessageDialog(null, result.message);
                frame.setContentPane(new ManageAccount(user));
                frame.revalidate();
                frame.repaint();
                return;
            }

            JOptionPane.showMessageDialog(null, result.message, "Error", JOptionPane.ERROR_MESSAGE);
        });

        JButton dashboard = new JButton("Dashboard");

        dashboard.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new Dashboard(user));
            frame.revalidate();
            frame.repaint();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JPanel form = new JPanel();
        form.setPreferredSize(new Dimension(300, 350));
        form.setLayout(new GridBagLayout());

        form.add(title, gbc);

        gbc.gridy++;
        form.add(studentIdLabel, gbc);

        gbc.gridy++;
        form.add(studentIdField, gbc);

        gbc.gridy++;
        form.add(usernameLabel, gbc);

        gbc.gridy++;
        form.add(usernameField, gbc);

        gbc.gridy++;
        form.add(userRoleLabel, gbc);

        gbc.gridy++;
        form.add(userRoleField, gbc);

        gbc.gridy++;
        form.add(passwordLabel, gbc);

        gbc.gridy++;
        form.add(passwordField, gbc);

        gbc.gridy++;
        form.add(create, gbc);

        gbc.gridy++;
        form.add(dashboard, gbc);

        gbc.gridx = 0;
        add(form, gbc);

    }
}
