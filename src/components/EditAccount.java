package src.components;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;

import src.enums.UserRole;
import src.objects.User;
import src.objects.ResponseObject;
import src.services.UserDomain;

public class EditAccount extends JPanel {
    private int currentPage = 1;
    private int prevStartIndex = 0;

    private JPanel container;
    private JPanel header;
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);

    public EditAccount(User user) {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel title = new JLabel("Delete Account", JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        header = new JPanel(new GridLayout(1, 4));
        header.setBorder(border);

        container = new JPanel(new GridLayout(10, 1));
        container.setPreferredSize(new Dimension(200, 300));

        JPanel paginationContainer = new JPanel(new GridBagLayout());

        JButton dashboard = new JButton("Dashboard");
        dashboard.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new Dashboard(user));
            frame.revalidate();
            frame.repaint();
        });

        JButton next = new JButton("Next");
        next.addActionListener(e -> handleNext());

        JButton prev = new JButton("Prev");
        prev.addActionListener(e -> handlePrev());

        gbc.gridx = 0;
        gbc.gridy = 0;
        paginationContainer.add(prev, gbc);

        gbc.gridx++;
        paginationContainer.add(next, gbc);

        refreshTable(getTenUsers(prevStartIndex));

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        gbc.gridy++;
        add(header, gbc);

        gbc.gridy++;
        add(container, gbc);

        gbc.gridy++;
        add(paginationContainer, gbc);

        gbc.gridy++;
        add(dashboard, gbc);
    }

    private void updateHeader() {
        header.removeAll();
        header.add(new JLabel("Student ID", JLabel.CENTER));
        header.add(new JLabel("Username", JLabel.CENTER));
        header.add(new JLabel("Role", JLabel.CENTER));
        header.add(new JLabel("", JLabel.CENTER));
        header.add(new JLabel("Page: " + currentPage, JLabel.CENTER));
        header.revalidate();
        header.repaint();
    }

    private JPanel createRow(User user) {
        JPanel wrapper = new JPanel(new GridLayout(1, 5));
        JButton edit = new JButton("Edit");
        edit.addActionListener(e -> {
            openEditDialog(user);
        });

        JButton delete = new JButton("Delete");
        delete.addActionListener(e -> {
            handleDelete(user);
        });

        wrapper.setBorder(border);

        wrapper.add(new JLabel(user.studentId, JLabel.CENTER));
        wrapper.add(new JLabel(user.name, JLabel.CENTER));
        wrapper.add(new JLabel(String.valueOf(user.role), JLabel.CENTER));
        wrapper.add(edit);
        wrapper.add(delete);

        return wrapper;
    }

    private void refreshTable(List<User> users) {
        container.removeAll();
        updateHeader();

        for (User u : users) {
            container.add(createRow(u));
        }

        container.revalidate();
        container.repaint();
    }

    private void handleNext() {
        int startIndex = (prevStartIndex == 0) ? 11 : prevStartIndex + 10;
        List<User> newUsers = getTenUsers(startIndex);

        if (newUsers.size() == 0) {
            JOptionPane.showMessageDialog(null, "No next page");
            return;
        }

        prevStartIndex = startIndex;
        currentPage++;

        refreshTable(newUsers);
    }

    private void handlePrev() {
        int startIndex = (prevStartIndex == 11) ? 0 : prevStartIndex - 10;
        List<User> prevUsers = getTenUsers(startIndex);

        if (prevUsers.size() == 0) {
            JOptionPane.showMessageDialog(null, "No previous page");
            return;
        }

        prevStartIndex = startIndex;
        currentPage--;

        refreshTable(prevUsers);
    }

    private void handleDelete(User user) {

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (!new ConfirmationDialog(frame, "Delete user", "Are you sure you want to delete this user?").isConfirmed())
            return;

        ResponseObject response = UserDomain.deleteUser(user.studentId);

        if (response.success) {
            JOptionPane.showMessageDialog(null, response.message);
            refreshTable(getTenUsers(prevStartIndex));
            return;
        }

        JOptionPane.showMessageDialog(null, response.message, "Erorr", JOptionPane.ERROR_MESSAGE);
    }

    private void openEditDialog(User user) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit User", true);
        dialog.setSize(300, 250);
        dialog.setLayout(new GridLayout(5, 2, 5, 5));
        dialog.setLocationRelativeTo(this);

        JTextField nameField = new JTextField(user.name);
        JPasswordField passwordField = new JPasswordField(String.valueOf(user.password));
        JComboBox<UserRole> roleBox = new JComboBox<>(UserRole.values());
        roleBox.setSelectedItem(user.role);

        roleBox.setSelectedItem(user.role.toString());

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        dialog.add(new JLabel("Student ID:"));
        dialog.add(new JLabel(user.studentId));

        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);

        dialog.add(new JLabel("Password:"));
        dialog.add(passwordField);

        dialog.add(new JLabel("Role:"));
        dialog.add(roleBox);

        dialog.add(save);
        dialog.add(cancel);

        save.addActionListener(e -> {
            String newName = nameField.getText();
            char[] newPassword = passwordField.getPassword();
            UserRole role = (UserRole) roleBox.getSelectedItem();

            ResponseObject response = UserDomain.editUser(
                    user.studentId,
                    newName,
                    newPassword,
                    role);

            JOptionPane.showMessageDialog(dialog, response.message);

            if (response.success) {
                dialog.dispose();
                refreshTable(getTenUsers(prevStartIndex));
            }
        });

        cancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private List<User> getTenUsers(int startIndex) {
        List<User> users = UserDomain.getUsers();
        List<User> selected = new LinkedList<>();

        if (startIndex > users.size())
            return selected;

        for (int i = startIndex; i < startIndex + 10; i++) {
            try {
                selected.add(users.get(i));
            } catch (Exception e) {
                break;
            }
        }

        return selected;
    }
}