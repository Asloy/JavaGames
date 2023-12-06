/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;
 import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 *
 * @author Emo_CPE
 */
public class PongGame extends JPanel implements ActionListener, KeyListener {
    private int ballX = 250, ballY = 250; // Ball initial position
    private int ballXSpeed = 4, ballYSpeed = 4; // Ball speed

    private int paddle1Y = 500, paddle2Y = 500; // Paddle initial positions
    private final int PADDLE_WIDTH = 15, PADDLE_HEIGHT = 100; // Paddle size

    private boolean up1, down1, up2, down2; // Paddle movement flags

    private int player1Score = 0, player2Score = 0; // Players' scores

    public PongGame() {
        Timer timer = new Timer(5, this); // Timer for game updates
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);

        // Ball
        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, 20, 20);

        // Paddles
        g.setColor(Color.WHITE);
        g.fillRect(50, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(550, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Middle line
        g.setColor(Color.WHITE);
        g.drawLine(300, 0, 300, 600);

        // Scores
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Player 1: " + player1Score, 100, 30);
        g.drawString("Player 2: " + player2Score, 400, 30);
    }

    public void actionPerformed(ActionEvent e) {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Ball collision with top and bottom edges
        if (ballY <= 0 || ballY >= 580) {
            ballYSpeed = -ballYSpeed;
        }

        // Ball collision with paddles
        if (ballX <= 70 && ballY >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
            ballXSpeed = -ballXSpeed;
        } else if (ballX >= 530 && ballY >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
            ballXSpeed = -ballXSpeed;
        }

        // Ball goes beyond the paddles - score or reset
        if (ballX <= 0) {
            player2Score++;
            reset();
        } else if (ballX >= 580) {
            player1Score++;
            reset();
        }

        // Move paddles
        if (up1 && paddle1Y > 0) {
            paddle1Y -= 5;
        }
        if (down1 && paddle1Y < 500) {
            paddle1Y += 5;
        }
        if (up2 && paddle2Y > 0) {
            paddle2Y -= 5;
        }
        if (down2 && paddle2Y < 500) {
            paddle2Y += 5;
        }

        repaint();
    }

    private void reset() {
        ballX = 250;
        ballY = 250;
        ballXSpeed = -ballXSpeed;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            up1 = true;
        }
        if (keyCode == KeyEvent.VK_S) {
            down1 = true;
        }
        if (keyCode == KeyEvent.VK_UP) {
            up2 = true;
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            down2 = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            up1 = false;
        }
        if (keyCode == KeyEvent.VK_S) {
            down1 = false;
        }
        if (keyCode == KeyEvent.VK_UP) {
            up2 = false;
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            down2 = false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame game = new PongGame();
        frame.add(game);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}