package src.components;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import src.objects.User;

import java.awt.Frame;

public class RecordModal {
    public RecordModal(Frame frame, User user) {
        JDialog dialog = new JDialog(
                frame,
                "Attendance Record",
                true);

        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(frame);

        String[] columns = { user.name + "'s Attendance Record" };

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
}
