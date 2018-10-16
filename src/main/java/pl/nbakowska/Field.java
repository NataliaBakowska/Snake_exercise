package pl.nbakowska;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Field extends JPanel implements ActionListener {
    private final int x[] = new int[Parameter.MAX_SEGMENTS];
    private final int y[] = new int[Parameter.MAX_SEGMENTS];

    private int segment;
    private int apple_x;
    private int apple_y;
    private int banana_x;
    private int banana_y;


    private boolean inPlay = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image banana;
    private Image head;

    public Field() {
        initField();
    }

    private void initField() {

        addKeyListener(new Control());
        setBackground(Color.white);
        setFocusable(true);
        setDoubleBuffered(true);

        setPreferredSize(new Dimension(Parameter.FIELD_WIDTH, Parameter.FIELD_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("src\\main\\resources\\dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src\\main\\resources\\apple.png");
        apple = iia.getImage();

        ImageIcon iib = new ImageIcon("src\\main\\resources\\banana.png");
        banana = iib.getImage();

        ImageIcon iih = new ImageIcon("src\\main\\resources\\circle.png");
        head = iih.getImage();
    }

    private void initGame() {

        segment = 3;

        for (int z = 0; z < segment; z++) {
            x[z] = 100 - z * 10;
            y[z] = 100;
        }

        createApple();
        createBanana();

        timer = new Timer(Parameter.DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if (inPlay) {

            g.drawImage(apple, apple_x, apple_y, this);
            g.drawImage(banana, banana_x, banana_y, this);

            for (int z = 0; z < segment; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (Parameter.FIELD_WIDTH - metr.stringWidth(msg)) / 2, Parameter.FIELD_HEIGHT / 2);

        new Button();
    }

    private void eatApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            segment++;
            createApple();
        }
    }

    private void eatBanana() {
        if ((x[0] == banana_x) && (y[0] == banana_y)) {
            segment = segment +2;
            createBanana();
            timer = new Timer(Parameter.DELAY-1, this);
            timer.start();
        }
    }

    private void move() {
        for (int z = segment; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (Parameter.turnLeft) {
            x[0] -= Parameter.DOT_SIZE;
        }

        if (Parameter.turnRight) {
            x[0] += Parameter.DOT_SIZE;
        }

        if (Parameter.turnUp) {
            y[0] -= Parameter.DOT_SIZE;
        }

        if (Parameter.turnDown) {
            y[0] += Parameter.DOT_SIZE;
        }
    }

    private void checkCollision() {
        for (int z = segment; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inPlay = false;
            }
        }

        if (y[0] >= Parameter.FIELD_HEIGHT) {
            inPlay = false;
        }

        if (y[0] < 0) {
            inPlay = false;
        }

        if (x[0] >= Parameter.FIELD_WIDTH) {
            inPlay = false;
        }

        if (x[0] < 0) {
            inPlay = false;
        }

        if (!inPlay) {
            timer.stop();
        }
    }

    private void createApple() {
        int r = (int) (Math.random() * Parameter.RAND_POS);
        apple_x = ((r * Parameter.DOT_SIZE));

        r = (int) (Math.random() * Parameter.RAND_POS);
        apple_y = ((r * Parameter.DOT_SIZE));
    }

    private void createBanana() {
        int r = (int) (Math.random() * Parameter.RAND_POS);
        banana_x = ((r * Parameter.DOT_SIZE));

        r = (int) (Math.random() * Parameter.RAND_POS);
        banana_y = ((r * Parameter.DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inPlay) {
            eatApple();
            eatBanana();
            checkCollision();
            move();
        }
        repaint();
    }

}