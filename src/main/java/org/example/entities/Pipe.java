package org.example.entities;

import org.example.constants.Constants;
import org.example.utils.Number;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pipe implements Serializable {
    public static final int PIPE_ROW_GAP = 350;
    public static final int PIPE_COL_GAP = 200;
    public static final int PIPE_IMAGE_HEIGHT = 300;
    public static final int DISTANCE_FROM_BIRD_TO_PIPE_ON_START = 500;
    public static final int NUMBER_OF_PIPE_FOR_SCENE = 10;
    Constants.PipeType pipeType;
    private transient Image image;
    private int x;
    private int y;
    private int width;
    private int height;

    public Pipe(Constants.PipeType pipeType, int x) {
        this.pipeType = pipeType;
        this.height = Number.generateRandomNumber(Constants.BOARD_HEIGHT / 4, Constants.BOARD_HEIGHT / 5 * 4);

        if (pipeType.equals(Constants.PipeType.TOP)) {
            this.image = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("imgs/toppipe.png"))).getImage();
            this.y = 0;

        } else {
            this.image = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("imgs/bottompipe.png"))).getImage();
            this.y = Constants.BOARD_HEIGHT;
        }
        this.x = x;
        this.width = Constants.PIPE_WIDTH;
    }

    public static List<Pipe> generateInitPipes(int timeFromStart) {
        List<Pipe> pipes = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PIPE_FOR_SCENE; i++) {
            int initialXPosition = (Constants.BOARD_WIDTH - Constants.PIPE_WIDTH + DISTANCE_FROM_BIRD_TO_PIPE_ON_START) + i * PIPE_ROW_GAP + timeFromStart * 4;
            Pipe topPipe = new Pipe(Constants.PipeType.TOP, initialXPosition);
            Pipe bottomPipe = new Pipe(Constants.PipeType.BOTTOM, initialXPosition);
            bottomPipe.setHeight(Constants.BOARD_HEIGHT - topPipe.getHeight() - PIPE_COL_GAP);
            bottomPipe.setY(Constants.BOARD_HEIGHT - bottomPipe.getHeight());
            pipes.add(topPipe);
            pipes.add(bottomPipe);
        }
        return pipes;
    }

    public static boolean isDisplayedOnFrame(int xPosition) {
        return xPosition >= -Constants.PIPE_WIDTH && xPosition <= Constants.BOARD_WIDTH;
    }

    public static boolean isPassed(int xPosition) {
        return xPosition < -Constants.PIPE_WIDTH;
    }

    public int getCurrentX(int timeFromStart) {
        return this.x - timeFromStart * 4;
    }

    public void draw(Graphics g, int timeFromStart) {
        int xPosition = this.getCurrentX(timeFromStart);
        if (isDisplayedOnFrame(xPosition)) {
            switch (this.pipeType) {
                case TOP:
                    g.drawImage(this.image, xPosition, this.y, Constants.PIPE_WIDTH, this.height, null);
                    break;
                case BOTTOM:
                    g.drawImage(this.image, xPosition, this.y, Constants.PIPE_WIDTH, PIPE_IMAGE_HEIGHT, null);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + this.pipeType);
            }
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Constants.PipeType getPipeType() {
        return pipeType;
    }

    public void setPipeType(Constants.PipeType pipeType) {
        this.pipeType = pipeType;
    }
}
