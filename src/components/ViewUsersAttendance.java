package src.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

import src.objects.User;
import src.services.TestData;
import src.services.UserDomain;

public class ViewUsersAttendance extends JPanel {

    public ViewUsersAttendance(User user) {

        setLayout(new BorderLayout());

        List<User> users = UserDomain.getUsers();

        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

        JPanel headers = new JPanel(new GridLayout(1, 4));
        headers.setBorder(border);
        headers.setPreferredSize(new Dimension(300, 30));

        headers.add(new JLabel("Student ID", JLabel.CENTER));
        headers.add(new JLabel("Name", JLabel.CENTER));
        headers.add(new JLabel("Role", JLabel.CENTER));
        headers.add(new JLabel("", JLabel.CENTER));

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        users.forEach(u -> {

            JPanel row = new JPanel(new GridLayout(1, 4));
            row.setPreferredSize(new Dimension(300, 30));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            JButton viewButton = new JButton("View Record");

            // TestData.dumpTestAttendanceRecord().forEach(e -> {
            // u.setRecord(e);
            // });

            viewButton.addActionListener(e -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

                if (!new ConfirmationDialog(
                        frame,
                        "View Records",
                        "View " + u.name + "'s record?").isConfirmed())
                    return;

                new RecordModal(frame, u);
            });

            row.add(new JLabel(u.studentId, JLabel.CENTER));
            row.add(new JLabel(u.name, JLabel.CENTER));
            row.add(new JLabel(String.valueOf(u.role), JLabel.CENTER));
            row.add(viewButton);

            body.add(row);
        });

        JScrollPane scrollPane = new JScrollPane(body);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

            if (!new ConfirmationDialog(
                    frame,
                    "Back",
                    "Go back to dashboard?").isConfirmed())
                return;

            frame.setContentPane(new Dashboard(user));
            frame.revalidate();
            frame.repaint();
        });

        footer.add(backButton);

        add(headers, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }
}