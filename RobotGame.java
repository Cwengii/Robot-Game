import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RobotGame extends JFrame {

    public RobotGame() {
        setTitle("Robot World Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GamePanel panel = new GamePanel();
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new RobotGame();
    }
}

class GamePanel extends JPanel implements KeyListener {
    private int robotX = 100;
    private int robotY = 100;
    private int robotSize = 40;

    private int[][] obstacles = {
            {200, 100}, {400, 300}, {600, 200}, {300, 400}, {500, 100}
    };
    private int obstacleSize = 50;

    private int goalX = 700;
    private int goalY = 500;
    private int goalSize = 60;

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the goal
        g.setColor(Color.GREEN);
        g.fillOval(goalX, goalY, goalSize, goalSize);

        // Draw obstacles
        g.setColor(Color.RED);
        for (int[] obstacle : obstacles) {
            g.fillRect(obstacle[0], obstacle[1], obstacleSize, obstacleSize);
        }

        // Draw the robot
        g.setColor(Color.BLUE);
        g.fillRect(robotX, robotY, robotSize, robotSize);

        // Draw robot's eyes
        g.setColor(Color.WHITE);
        g.fillOval(robotX + 5, robotY + 5, 10, 10);
        g.fillOval(robotX + 25, robotY + 5, 10, 10);

        // Draw robot's mouth
        g.fillRect(robotX + 10, robotY + 25, 20, 5);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int newX = robotX;
        int newY = robotY;

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) newX -= 10;
        if (key == KeyEvent.VK_RIGHT) newX += 10;
        if (key == KeyEvent.VK_UP) newY -= 10;
        if (key == KeyEvent.VK_DOWN) newY += 10;

        // Check if new position hits any obstacle
        boolean canMove = true;
        for (int[] obstacle : obstacles) {
            if (newX < obstacle[0] + obstacleSize &&
                    newX + robotSize > obstacle[0] &&
                    newY < obstacle[1] + obstacleSize &&
                    newY + robotSize > obstacle[1]) {
                canMove = false;
                break;
            }
        }

        // Check if robot is trying to go outside the window
        if (newX < 0 || newX + robotSize > getWidth() ||
                newY < 0 || newY + robotSize > getHeight()) {
            canMove = false;
        }

        if (canMove) {
            robotX = newX;
            robotY = newY;
            repaint();
        }

        // Check if robot reached the goal
        if (robotX < goalX + goalSize &&
                robotX + robotSize > goalX &&
                robotY < goalY + goalSize &&
                robotY + robotSize > goalY) {
            JOptionPane.showMessageDialog(this, "Congratulations! You won!\nRobot reached the goal!");
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}