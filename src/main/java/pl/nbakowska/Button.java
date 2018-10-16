package pl.nbakowska;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Button extends JFrame implements ActionListener {

    public Button() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(100, 100);
        setLocation(100, 100);

        JButton restartButton = new JButton("Restart Game");
        restartButton.addActionListener(this);
        add(restartButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(KeyEvent.VK_ENTER)) {
            new Snake();
        }

    }
}
