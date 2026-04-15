package src.components;

import javax.swing.table.DefaultTableModel;

import java.awt.*;

import javax.swing.*;

import src.enums.UserRole;
import src.objects.User;

public class Dashboard extends JPanel {

    public Dashboard(User user) {
        setLayout(new GridBagLayout());

        JLabel title = new JLabel("Dashboard", JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

        JLabel displayName = new JLabel("Welcome " + user.name, JLabel.CENTER);
        displayName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(5, 1, 5, 5));
        // actionPanel.setBackground(Color.GRAY);
        actionPanel.setPreferredSize(new Dimension(200, 200));

        JButton mark = new JButton("Mark Attendance");
        mark.addActionListener(e -> {
            user.markAttendance();
            JOptionPane.showMessageDialog(null, "Attendance Marked");
        });

        JButton view = new JButton("View Attendance");
        view.addActionListener(e -> openRecordModal(user));

        JButton manage = new JButton("Manage Users");
        manage.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new ManageAccount(user));
            frame.revalidate();
            frame.repaint();
        });

        JButton report = new JButton("Generate Report");
        report.addActionListener(e -> generateReport(user));

        JButton logout = new JButton("Logout");

        logout.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new LoginPanel());
            frame.revalidate();
            frame.repaint();
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        gbc.gridy++;
        add(displayName, gbc);

        gbc.gridy++;
        add(actionPanel, gbc);

        actionPanel.add(mark);
        actionPanel.add(view);
        actionPanel.add(report);

        if (user.role == UserRole.ADMIN)
            actionPanel.add(manage);

        actionPanel.add(logout);
    }

    private void openRecordModal(User user) {
        JDialog dialog = new JDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                "Attendance Record",
                true);

        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(null);

        String[] columns = { "Attendance Record" };

        Object[][] data = user.getRecords()
                .stream()
                .map(r -> new Object[] { r })
                .toArray(Object[][]::new);

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        dialog.setContentPane(scrollPane);

        dialog.setVisible(true);
    }

    private void generateReport(User user) {
        StringBuilder reportText = new StringBuilder();
        reportText.append("Attendance Report for ").append(user.name).append("\n\n");

        if (user.getRecords().isEmpty()) {
            reportText.append("No attendance records available.");
        } else {
            reportText.append("Total days marked: ").append(user.getRecords().size()).append("\n\n");
            reportText.append("Records:\n");
            user.getRecords().forEach(record -> reportText.append(" - ").append(record).append("\n"));
        }

        JTextArea reportArea = new JTextArea(reportText.toString());
        reportArea.setEditable(false);
        reportArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setPreferredSize(new Dimension(360, 260));

        JOptionPane.showMessageDialog(
                SwingUtilities.getWindowAncestor(this),
                scrollPane,
                "Attendance Report",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
