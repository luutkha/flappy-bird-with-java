package org.example.screens;

import org.example.constants.Constants;
import org.example.entities.Pipe;
import org.example.utils.Gravity;
import org.example.utils.Number;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    private final java.util.List<String> bgPaths = java.util.List.of("imgs/bg-1.png", "imgs/bg-2.jpg");
    private final transient Image birdImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("imgs/flappybird.png"))).getImage();
    private final transient Image gameOverImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("imgs/game-over.png"))).getImage();
    int boardWidth = Constants.BOARD_WIDTH;
    int boardHeight = Constants.BOARD_HEIGHT;
    int gravityTimeDrop = 0;
    int gravityTimeJump = 0;
    int birdY = boardHeight / 2;
    Timer gameLoop;
    int timeFromStart = 0;
    boolean isStarted = false;
    boolean isGameOver = false;
    private transient Image bgImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(bgPaths.getFirst()))).getImage();
    private transient Image startImg = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("imgs/space-start.png"))).getImage();
    private java.util.List<Pipe> pipes = Pipe.generateInitPipes(this.timeFromStart);

    public FlappyBird() {
        addKeyListener(this);
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        startImg = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("imgs/space-start.png"))).getImage();
        gameLoop = new Timer(1000 / 60, e -> {
            if (isStarted) {
                timeFromStart += 1;
                gravityTimeDrop += 1;
                if (gravityTimeJump > 0) {
                    gravityTimeJump -= 1;
                    birdY -= Gravity.getGravityUp(gravityTimeJump);
                }
            }
            repaint();
        }); //how long it takes to start timer, milliseconds gone between frames
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {

        drawBackGroundOfGame(g);

        drawStartAnnual(g);

        birdY = calculateBirdY();
        boolean isLastPipeInArraysPassed = Pipe.isPassed(pipes.getLast().getCurrentX(this.timeFromStart));
        if (isLastPipeInArraysPassed) {
            this.pipes.clear();
            this.pipes.addAll(Pipe.generateInitPipes(this.timeFromStart));
            int bgIndex = Number.generateRandomNumber(0, bgPaths.size() - 1);
            this.bgImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(bgPaths.get(bgIndex)))).getImage();
        }

        pipes.forEach(pipe -> {
            pipe.draw(g, this.timeFromStart);
            int currentX = pipe.getCurrentX(this.timeFromStart);
            if (birdY > Constants.BOARD_HEIGHT || isTouchedTopPipe(pipe, currentX) || isTouchedBottomPipe(pipe, currentX)) {
                triggerGameOver();
            }
        });
        drawScore(g);


        if (this.isGameOver) {
            g.drawImage(gameOverImage, 0, 0, boardWidth, boardHeight, null);
            gameLoop.stop();
        }
        g.drawImage(birdImage, 2, birdY, Constants.BIRD_WIDTH, Constants.BIRD_HEIGHT, null);

    }

    private boolean isTouchedBottomPipe(Pipe pipe, int currentX) {
        return currentX < Constants.BIRD_WIDTH && birdY >= pipe.getY() && Pipe.isDisplayedOnFrame(currentX) && pipe.getPipeType().equals(Constants.PipeType.BOTTOM);
    }

    private boolean isTouchedTopPipe(Pipe pipe, int currentX) {
        return currentX < Constants.BIRD_WIDTH && birdY <= pipe.getY() + pipe.getHeight() && Pipe.isDisplayedOnFrame(currentX) && pipe.getPipeType().equals(Constants.PipeType.TOP);
    }

    private void drawStartAnnual(Graphics g) {
        if (!isStarted) {
            g.drawImage(startImg, boardWidth / 3 / 2, boardHeight / 3 / 2, boardWidth / 3 * 2, boardHeight / 3 * 2, null);
        }
    }

    private void drawBackGroundOfGame(Graphics g) {
        g.drawImage(bgImage, 0, 0, boardWidth, boardHeight, null);
    }


    private void triggerGameOver() {
        this.isGameOver = true;
    }

    private int calculateBirdY() {
        if (!isStarted) {
            return boardHeight / 2;
        }
        return Math.max(birdY + Gravity.getGravity(this.gravityTimeDrop), 0);
    }

    private void drawScore(Graphics g) {
        g.drawString("Score: " + this.timeFromStart, 10, 20);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (isGameOver) {
                resetTheGame();
            } else {
                if (isStarted) {
                    this.gravityTimeDrop = 0;
                    this.gravityTimeJump = 20;
                } else {
                    this.isStarted = true;
                    birdY = Math.max(birdY - Gravity.JUMP_FORCE, 0);
                    if (birdY == 0) {
                        this.gravityTimeDrop = 20;
                        birdY -= 20;
                    } else {
                        this.gravityTimeDrop = 0;
                    }
                }
            }


        }

    }

    private void resetTheGame() {
        this.gameLoop.start();
        this.timeFromStart = 0;
        pipes = Pipe.generateInitPipes(this.timeFromStart);
        this.isStarted = false;
        this.gravityTimeDrop = 0;
        this.isGameOver = false;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
}
