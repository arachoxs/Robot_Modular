import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class MapaVisual extends JPanel {
    private static final int TAMAÑO_CELDA = 20;
    private static final int TAMAÑO_MAPA = 30;
    private Mapa mapa;
    private JFrame ventana;
    private JLabel estadisticasLabel;
    private JLabel posicionLabel;
    private JLabel direccionLabel;
    private JLabel pasosLabel;
    private Timer timer;
    private static int n_pasos = 0; // Variable para almacenar los pasos configurados

    // Colores para cada tipo de celda
    private static final Color COLOR_AIRE = Color.WHITE;
    private static final Color COLOR_OBSTACULO = Color.BLACK;
    private static final Color COLOR_MASCOTA = Color.ORANGE;
    private static final Color COLOR_ROBOT = Color.RED;
    private static final Color COLOR_BORDE = Color.GRAY;
    private static final Color COLOR_DIRECCION = Color.BLUE;

    // Direcciones del robot (0=Norte, 1=Este, 2=Sur, 3=Oeste)
    private int direccionRobot = 0;
    private static final String[] NOMBRES_DIRECCION = {"Norte ↑", "Este →", "Sur ↓", "Oeste ←"};

    public MapaVisual() {
        this.mapa = new Mapa();
        inicializarComponentes();
        configurarVentana();
        configurarEventos();

        // Actualizar información inicial
        actualizarInformacion();

        // Timer para actualización automática (opcional)
        timer = new Timer(100, e -> {
            repaint();
            actualizarInformacion();
        });
        timer.start();
    }

    private void inicializarComponentes() {
        setPreferredSize(new Dimension(TAMAÑO_MAPA * TAMAÑO_CELDA + 1, TAMAÑO_MAPA * TAMAÑO_CELDA + 1));
        setBackground(Color.WHITE);
        setFocusable(true);

        estadisticasLabel = new JLabel();
        posicionLabel = new JLabel();
        direccionLabel = new JLabel();
        pasosLabel = new JLabel();

        estadisticasLabel.setFont(new Font("Arial", Font.BOLD, 12));
        posicionLabel.setFont(new Font("Arial", Font.BOLD, 12));
        direccionLabel.setFont(new Font("Arial", Font.BOLD, 12));
        pasosLabel.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void configurarVentana() {
        ventana = new JFrame("Simulador de Robot - Mapa Interactivo");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        // Panel principal (mapa)
        ventana.add(this, BorderLayout.CENTER);

        // Panel de información expandido
        JPanel panelInfo = new JPanel(new GridLayout(4, 1));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información"));
        panelInfo.add(estadisticasLabel);
        panelInfo.add(posicionLabel);
        panelInfo.add(direccionLabel);
        panelInfo.add(pasosLabel);

        // Panel de controles expandido
        JPanel panelControles = new JPanel(new GridLayout(2, 3, 5, 5));
        panelControles.setBorder(BorderFactory.createTitledBorder("Controles"));

        JButton btnRegenerarMapa = new JButton("Regenerar Mapa");
        JButton btnEncenderRobot = new JButton("Encender Robot");
        JButton btnApagarRobot = new JButton("Apagar Robot");
        JButton btnRotarIzq = new JButton("Rotar ←");
        JButton btnRotarDer = new JButton("Rotar →");
        JButton btnConfigurarPasos = new JButton("Config. Pasos");

        btnRegenerarMapa.addActionListener(e -> {
            mapa.regenerarMapa();
            // Actualizar posición del robot global
            if (Global.robot != null) {
                Global.robot.setPos(15, 15);
            }
            direccionRobot = 0; // Reset dirección al norte
            repaint();
            actualizarInformacion();
        });

        btnEncenderRobot.addActionListener(e -> {
            if (Global.robot != null) {
                Global.robot.encender();
            }
        });

        btnApagarRobot.addActionListener(e -> {
            if (Global.robot != null) {
                Global.robot.apagar();
            }
        });

        btnRotarIzq.addActionListener(e -> {
            direccionRobot = (direccionRobot + 3) % 4; // Rotar hacia la izquierda
            repaint();
            actualizarInformacion();
        });

        btnRotarDer.addActionListener(e -> {
            direccionRobot = (direccionRobot + 1) % 4; // Rotar hacia la derecha
            repaint();
            actualizarInformacion();
        });

        btnConfigurarPasos.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana,
                    "Ingrese el número de pasos:",
                    "Configurar Pasos",
                    JOptionPane.QUESTION_MESSAGE);

            if (input != null && !input.trim().isEmpty()) {
                try {
                    n_pasos = Integer.parseInt(input.trim());
                    if (n_pasos < 0) {
                        n_pasos = 0;
                        JOptionPane.showMessageDialog(ventana,
                                "Se estableció en 0 (mínimo permitido)",
                                "Valor Ajustado",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    actualizarInformacion();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ventana,
                            "Por favor ingrese un número válido",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panelControles.add(btnRegenerarMapa);
        panelControles.add(btnEncenderRobot);
        panelControles.add(btnApagarRobot);
        panelControles.add(btnRotarIzq);
        panelControles.add(btnRotarDer);
        panelControles.add(btnConfigurarPasos);

        // Instrucciones actualizadas
        JLabel instrucciones = new JLabel("<html><center>Usa las flechas para mover el robot<br>" +
                "Blanco: Aire | Negro: Obstáculo | Naranja: Mascota | Rojo: Robot<br>" +
                "La flecha azul indica la dirección del robot</center></html>");
        instrucciones.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelInfo, BorderLayout.WEST);
        panelSur.add(panelControles, BorderLayout.CENTER);
        panelSur.add(instrucciones, BorderLayout.SOUTH);

        ventana.add(panelSur, BorderLayout.SOUTH);

        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
    }

    private void configurarEventos() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (Global.robot == null) return;

                int[] posActual = Global.robot.getPos();
                int filaActual = posActual[0];
                int columnaActual = posActual[1];

                int nuevaFila = filaActual;
                int nuevaColumna = columnaActual;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        nuevaFila = Math.max(0, filaActual - 1);
                        direccionRobot = 0; // Norte
                        break;
                    case KeyEvent.VK_DOWN:
                        nuevaFila = Math.min(TAMAÑO_MAPA - 1, filaActual + 1);
                        direccionRobot = 2; // Sur
                        break;
                    case KeyEvent.VK_LEFT:
                        nuevaColumna = Math.max(0, columnaActual - 1);
                        direccionRobot = 3; // Oeste
                        break;
                    case KeyEvent.VK_RIGHT:
                        nuevaColumna = Math.min(TAMAÑO_MAPA - 1, columnaActual + 1);
                        direccionRobot = 1; // Este
                        break;
                }

                // Verificar si la nueva posición es válida (no hay obstáculo)
                if (!mapa.hayObstaculo(nuevaFila, nuevaColumna)) {
                    // Actualizar mapa visual
                    mapa.moverRobot(filaActual, columnaActual, nuevaFila, nuevaColumna);

                    // Actualizar posición en el robot global
                    Global.robot.setPos(nuevaFila, nuevaColumna);

                    // Verificar si encontró una mascota
                    if (mapa.hayMascota(nuevaFila, nuevaColumna)) {
                        JOptionPane.showMessageDialog(ventana,
                                "¡Mascota encontrada en posición (" + nuevaFila + ", " + nuevaColumna + ")!",
                                "Mascota Encontrada",
                                JOptionPane.INFORMATION_MESSAGE);
                        // La mascota se "recoge" - la celda queda como robot
                    }

                    repaint();
                    actualizarInformacion();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar el mapa
        for (int fila = 0; fila < TAMAÑO_MAPA; fila++) {
            for (int columna = 0; columna < TAMAÑO_MAPA; columna++) {
                int x = columna * TAMAÑO_CELDA;
                int y = fila * TAMAÑO_CELDA;

                // Obtener color según el tipo de celda
                Color color = obtenerColorCelda(mapa.getCelda(fila, columna));

                // Rellenar celda
                g2d.setColor(color);
                g2d.fillRect(x, y, TAMAÑO_CELDA, TAMAÑO_CELDA);

                // Dibujar borde
                g2d.setColor(COLOR_BORDE);
                g2d.drawRect(x, y, TAMAÑO_CELDA, TAMAÑO_CELDA);

                // Dibujar símbolo para mejor identificación
                if (mapa.getCelda(fila, columna) == Mapa.MASCOTA) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("♥", x + TAMAÑO_CELDA/2 - 4, y + TAMAÑO_CELDA/2 + 4);
                } else if (mapa.getCelda(fila, columna) == Mapa.ROBOT) {
                    g2d.setColor(Color.WHITE);
                    g2d.drawString("R", x + TAMAÑO_CELDA/2 - 4, y + TAMAÑO_CELDA/2 + 4);

                    // Dibujar flecha de dirección
                    dibujarFlechaDireccion(g2d, x + TAMAÑO_CELDA/2, y + TAMAÑO_CELDA/2, direccionRobot);
                }
            }
        }
    }

    private void dibujarFlechaDireccion(Graphics2D g2d, int centroX, int centroY, int direccion) {
        g2d.setColor(COLOR_DIRECCION);
        g2d.setStroke(new BasicStroke(2));

        int longitud = TAMAÑO_CELDA / 3;
        int puntoX = centroX;
        int puntoY = centroY;

        switch (direccion) {
            case 0: // Norte
                g2d.drawLine(centroX, centroY, centroX, centroY - longitud);
                // Punta de flecha
                g2d.drawLine(centroX, centroY - longitud, centroX - 3, centroY - longitud + 5);
                g2d.drawLine(centroX, centroY - longitud, centroX + 3, centroY - longitud + 5);
                break;
            case 1: // Este
                g2d.drawLine(centroX, centroY, centroX + longitud, centroY);
                // Punta de flecha
                g2d.drawLine(centroX + longitud, centroY, centroX + longitud - 5, centroY - 3);
                g2d.drawLine(centroX + longitud, centroY, centroX + longitud - 5, centroY + 3);
                break;
            case 2: // Sur
                g2d.drawLine(centroX, centroY, centroX, centroY + longitud);
                // Punta de flecha
                g2d.drawLine(centroX, centroY + longitud, centroX - 3, centroY + longitud - 5);
                g2d.drawLine(centroX, centroY + longitud, centroX + 3, centroY + longitud - 5);
                break;
            case 3: // Oeste
                g2d.drawLine(centroX, centroY, centroX - longitud, centroY);
                // Punta de flecha
                g2d.drawLine(centroX - longitud, centroY, centroX - longitud + 5, centroY - 3);
                g2d.drawLine(centroX - longitud, centroY, centroX - longitud + 5, centroY + 3);
                break;
        }

        // Restaurar stroke por defecto
        g2d.setStroke(new BasicStroke(1));
    }

    private Color obtenerColorCelda(int tipoCelda) {
        switch (tipoCelda) {
            case Mapa.AIRE: return COLOR_AIRE;
            case Mapa.OBSTACULO: return COLOR_OBSTACULO;
            case Mapa.MASCOTA: return COLOR_MASCOTA;
            case Mapa.ROBOT: return COLOR_ROBOT;
            default: return Color.PINK; // Color de error
        }
    }

    public void actualizarInformacion() {
        estadisticasLabel.setText(mapa.getEstadisticas());

        if (Global.robot != null) {
            int[] pos = Global.robot.getPos();
            posicionLabel.setText(String.format("Posición Robot: (%d, %d) | Estado: %s",
                    pos[0], pos[1],
                    Global.robot.isEncendido() ? "Encendido" : "Apagado"));
        } else {
            posicionLabel.setText("Robot no inicializado");
        }

        direccionLabel.setText("Dirección: " + NOMBRES_DIRECCION[direccionRobot]);
        pasosLabel.setText("Pasos configurados: " + n_pasos);
    }

    public void mostrar() {
        SwingUtilities.invokeLater(() -> {
            ventana.setVisible(true);
            requestFocusInWindow();
        });
    }

    public Mapa getMapa() {
        return mapa;
    }

    public int getDireccionRobot() {
        return direccionRobot;
    }

    public void setDireccionRobot(int direccion) {
        this.direccionRobot = direccion % 4;
        repaint();
        actualizarInformacion();
    }

    public static int getNPasos() {
        return n_pasos;
    }

    public static void setNPasos(int pasos) {
        n_pasos = Math.max(0, pasos);
    }

    public void cerrar() {
        if (timer != null) {
            timer.stop();
        }
        if (ventana != null) {
            ventana.dispose();
        }
    }

    public static void main(String[] args) {
        // Inicializar usuario
        new Usuario(1, "beta", "Estandar");

        // Configurar pasos iniciales
        n_pasos = 0; // Valor por defecto

        // Inicializar posición y dirección del robot correctamente

        // Agregar módulos al robot
        Global.robot.agregar_extension(1, "ext", "Extension", 10, 10, 10, false, 1);
        Global.robot.agregar_rotacion(2, "rot", "Rotacion", 10, 10, 10, false, 1);
        Global.robot.agregar_helicoidal(3, "heli", "Helicoidal", 10, 10, 10, false, 1);
        Global.robot.agregar_camara(4, "cam", "Camara", 10, 10, 10, false, 1);
        Global.robot.agregar_sensor_proximidad(5, "prox", "Proximidad", 10, 10, 10, false, 1);
        Global.robot.agregar_altavoz(6, "alt", "Altavoz", 10, 10, 10, false, 1);

        // Crear y mostrar el mapa visual

        // Configurar el mapa global para los sensores
        //Sensor.setMapaGlobal(Global.mapaVisual.getMapa());

        // Mostrar la ventana
        //Global.mapaVisual.mostrar();

        Global.matriz.actualizarMatriz();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de pasos a mover: ");
        int n_pasos = scanner.nextInt();
        Global.robot.get_modulo_id(1).get_sistema_comunicacion().recibir_mensaje("mover "+n_pasos);
    }
}