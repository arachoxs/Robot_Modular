import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MapaVisual extends JPanel {

    private static final int TILE_SIZE = 30;
    private static final int MAP_WIDTH = 30;
    private static final int MAP_HEIGHT = 30;

    private int jugadorX = 0;
    private int jugadorY = 0;

    public MapaVisual() {
        setPreferredSize(new Dimension(MAP_WIDTH * TILE_SIZE, MAP_HEIGHT * TILE_SIZE));
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> jugadorY = Math.max(0, jugadorY - 1);
                    case KeyEvent.VK_DOWN -> jugadorY = Math.min(MAP_HEIGHT - 1, jugadorY + 1);
                    case KeyEvent.VK_LEFT -> jugadorX = Math.max(0, jugadorX - 1);
                    case KeyEvent.VK_RIGHT -> jugadorX = Math.min(MAP_WIDTH - 1, jugadorX + 1);
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar mapa
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                if (x == jugadorX && y == jugadorY) {
                    g.setColor(Color.RED);
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mapa Interactivo");
        MapaVisual mapa = new MapaVisual();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mapa);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
