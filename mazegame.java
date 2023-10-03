import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MazeGame extends JPanel {
    private char[][] maze;
    private int playerRow, playerCol;

    public MazeGame(char[][] maze) {
        this.maze = maze;
        findPlayerStart();
        setupKeyBindings();
        setFocusable(true); // Allow the panel to receive keyboard input
        requestFocusInWindow(); // Request focus to receive key events
    }

    private void findPlayerStart() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == 'S') {
                    playerRow = row;
                    playerCol = col;
                    return;
                }
            }
        }
    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // Define key bindings for arrow keys
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");

        // Define actions for key bindings
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(-1, 0);
            }
        });

        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(1, 0);
            }
        });

        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(0, -1);
            }
        });

        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move(0, 1);
            }
        });
    }

    public void move(int dx, int dy) {
        int newRow = playerRow + dy;
        int newCol = playerCol + dx;

        if (isValidMove(newRow, newCol)) {
            if (maze[newRow][newCol] == 'E') {
                JOptionPane.showMessageDialog(this, "You won!");
                restartGame();
            } else {
                maze[playerRow][playerCol] = ' ';
                playerRow = newRow;
                playerCol = newCol;
                maze[playerRow][playerCol] = 'S';
                repaint();
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < maze.length &&
                col >= 0 && col < maze[0].length &&
                maze[row][col] != '#';
    }

    private void restartGame() {
        char[][] initialMaze = {
                { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
                { '#', 'S', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#' },
                { '#', '#', '#', ' ', '#', ' ', '#', '#', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#' },
                { '#', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', '#', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#' },
                { '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', '#', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#' },
                { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', ' ', ' ', '#', '#', '#', '#', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
                { '#', '#', ' ', '#', '#', '#', '#', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', '#', '#', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', '#', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#' },
                { '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#', ' ', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#', ' ', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', '#', '#', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', '#', '#', ' ', '#', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', '#' },
                { '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'E' },
                { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
        };

        maze = initialMaze;
        findPlayerStart();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 30;

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                if (maze[row][col] == '#') {
                    g.setColor(Color.BLACK);
                } else if (maze[row][col] == 'S') {
                    g.setColor(Color.GREEN);
                } else if (maze[row][col] == 'E') {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        char[][] mazeData = {
                { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
                { '#', 'S', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#' },
                { '#', '#', '#', ' ', '#', ' ', '#', '#', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#' },
                { '#', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', '#', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#' },
                { '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', '#', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#' },
                { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', ' ', ' ', '#', '#', '#', '#', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
                { '#', '#', ' ', '#', '#', '#', '#', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', '#', '#', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', '#', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#' },
                { '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#', ' ', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#', ' ', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', '#', '#', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', '#', ' ', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', '#', '#', ' ', '#', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', '#' },
                { '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', '#' },
                { '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'E' },
                { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
        };

        MazeGame mazePanel = new MazeGame(mazeData);
        frame.add(mazePanel);

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazePanel.restartGame();
            }
        });
        frame.add(restartButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
