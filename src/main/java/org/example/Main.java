package org.example;

import org.example.constants.Constants;
import org.example.screens.FlappyBird;

import javax.swing.*;

public class Main extends JPanel {


    public static void main(String[] args) {


        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);

    }
}