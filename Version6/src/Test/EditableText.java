package Test;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EditableText extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final String TEXT = "The one of the best ! ...";

    private StringBuilder input;
    private Font font;
    private FontMetrics metrics;
    private int x, y;
    private int cursorPos;

    public EditableText() {
        input = new StringBuilder(TEXT);
        font = new Font("Arial", Font.PLAIN, 12);
        metrics = getFontMetrics(font);
        x = 10;
        y = 20;
        cursorPos = input.length();
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (cursorPos > 0 && input.charAt(cursorPos - 1) == ' ') {
                        input.deleteCharAt(cursorPos - 1);
                        cursorPos--;
                        x -= metrics.stringWidth(" ");
                    }
                    while (cursorPos > 0 && input.charAt(cursorPos - 1) != ' ') {
                        input.deleteCharAt(cursorPos - 1);
                        cursorPos--;
                        x -= metrics.charWidth(input.charAt(cursorPos));
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (cursorPos > 0) {
                        cursorPos--;
                        x -= metrics.charWidth(input.charAt(cursorPos));
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (cursorPos < input.length()) {
                        x += metrics.charWidth(input.charAt(cursorPos));
                        cursorPos++;
                    }
                }
                repaint();
            }
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                input.insert(cursorPos, c);
                cursorPos++;
                x += metrics.charWidth(c);
                if (x + metrics.charWidth(c) > getWidth()) {
                    x = 10;
                    y += metrics.getHeight();
                }
                repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);
        for (String line : input.toString().split("\n")) {
            int lineStart = 0;
            for (String word : line.split("\\s")) {
                int width = metrics.stringWidth(word + " ");
                if (x + width > getWidth()) {
                    x = 10;
                    y += metrics.getHeight();
                    lineStart = input.indexOf(word, lineStart) - 1;
                }
                g.drawString(word + " ", x, y);
                x += width;
            }
            x = 10;
            y += metrics.getHeight();
        }
        int cursorX = x;
        for (int i = 0; i < cursorPos; i++) {
            char c = input.charAt(i);
            cursorX += metrics.charWidth(c);
        }
        g.drawLine(cursorX, y - metrics.getHeight() + 2, cursorX, y - 2);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Editable Text");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.add(new EditableText());
        frame.setVisible(true);
    }
}

