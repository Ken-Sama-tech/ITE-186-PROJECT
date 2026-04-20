package src.components;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

public class ConfirmationDialog {
    private boolean isConfirmed = false;
    private JDialog dialog;

    public ConfirmationDialog(Frame frame, String title, String text) {
        dialog = new JDialog(frame, title, true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(new Dimension(400, 200));
        dialog.setLocationRelativeTo(frame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(e -> {
            isConfirmed = true;
            dialog.dispose();
        });

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> {
            isConfirmed = false;
            dialog.dispose();
        });

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(1, 2, 5, 0));

        gbc.gridy = 0;
        gbc.gridx = 0;

        dialog.add(new JLabel(text, JLabel.CENTER), gbc);

        gbc.gridy++;
        dialog.add(buttonContainer, gbc);

        buttonContainer.add(confirm);
        buttonContainer.add(cancel);

        dialog.setVisible(true);
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

}
