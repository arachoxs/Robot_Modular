/**
 * Clase Rotacion - Módulo dinámico para movimiento angular del robot
 * 
 * Este módulo proporciona capacidades de rotación al robot, permitiendo cambios
 * de dirección en incrementos de 90 grados. Maneja tanto rotaciones en sentido
 * horario como antihorario y coordina con sensores para navegación inteligente.
 * 
 * Convención de grados: Positivo = sentido horario, Negativo = sentido
 * antihorario
 */
public class Rotacion extends ModuloDinamico {

    /**
     * Constructor del módulo de rotación.
     * 
     * @param id          Identificador único del módulo
     * @param referencia  Referencia comercial del módulo
     * @param descripcion Descripción de capacidades
     * @param largo       Dimensión largo en milímetros
     * @param ancho       Dimensión ancho en milímetros
     * @param profundidad Dimensión profundidad en milímetros
     * @param encendido   Estado inicial de encendido
     * @param n_motores   Número de motores del módulo
     */
    public Rotacion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
            boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
    }

    /**
     * Ejecuta rotación del robot cambiando su dirección.
     * Calcula la nueva dirección basada en grados y actualiza el estado del robot.
     * 
     * @param n_pasos    No utilizado en rotación (siempre 0)
     * @param grados     Grados a rotar (múltiplos de 90)
     * @param pasos_giro No utilizado en rotación (siempre 0)
     * @return true si la rotación fue exitosa
     */
    @Override
    public boolean moverse(int n_pasos, int grados, int pasos_giro) {
        // Obtener la dirección actual del robot
        int[] direccion_actual = Global.robot.get_direccion();

        // Definir las 4 direcciones posibles en orden horario
        int[][] direcciones = {
                { 1, 0 }, // Derecha
                { 0, -1 }, // Abajo
                { -1, 0 }, // Izquierda
                { 0, 1 } // Arriba
        };

        // Encontrar el índice de la dirección actual
        int indice_actual = 0;
        for (int i = 0; i < 4; i++) {
            if (direccion_actual[0] == direcciones[i][0] && direccion_actual[1] == direcciones[i][1]) {
                indice_actual = i;
                break;
            }
        }

        // Redondear los grados al múltiplo de 90 más cercano
        int pasos = (int) Math.round(grados / 90.0);

        // Ajustar para negativos y normalizar a 0-3
        int nuevo_indice = (indice_actual + pasos) % 4;
        if (nuevo_indice < 0) {
            nuevo_indice += 4;
        }

        // Cambiar la dirección del robot
        Global.robot.set_direccion(direcciones[nuevo_indice]);

        if (Global.log)
            System.out.println("Robot giró " + grados + " grados. Nueva dirección: [" +
                    direcciones[nuevo_indice][0] + "," + direcciones[nuevo_indice][1] + "]");

        return true;
    }

    /**
     * Sobrecarga para rotación simple.
     * 
     * @param grados Grados a rotar
     * @return true si la rotación fue exitosa
     */
    public boolean moverse(int grados) {
        return moverse(0, grados, 0);
    }

    /**
     * Interpreta y procesa mensajes de rotación.
     * Soporta comandos específicos y rotaciones con grados personalizados.
     * 
     * @param mensaje Comando de rotación a interpretar
     */
    @Override
    public void interpretar_mensaje(String mensaje) {
        mensaje = mensaje.trim().toUpperCase(); // Normaliza el mensaje
        boolean resultado_accion = false;

        switch (mensaje) {
            case "ROTACION IZQUIERDA":
                resultado_accion = moverse(-90);
                this.get_sistema_control().enviar_respuesta_accion(Global.SENSOR_PROXIMIDAD_PRINCIPAL,
                        "VERIFICAR IZQUIERDA");
                break;

            case "ROTACION IZQUIERDA FIJA":
                resultado_accion = moverse(-90);
                break;

            case "ROTACION IZQUIERDA FALLIDA":
                resultado_accion = moverse(180);
                this.get_sistema_control().enviar_respuesta_accion(Global.SENSOR_PROXIMIDAD_PRINCIPAL,
                        "VERIFICAR DERECHA");
                break;

            default:
                try {
                    if (mensaje.startsWith("ROTAR")) {
                        int grados = extraer_grados(mensaje);
                        resultado_accion = moverse(grados);
                    } else {
                        System.out.println("Mensaje no reconocido: " + mensaje);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: número de grados inválido.");
                } catch (Exception e) {
                    System.out.println("Error general al interpretar mensaje: " + e.getMessage());
                }
                break;
        }

        this.enviar_respuesta_accion(resultado_accion);

    }

    /**
     * Extrae el número de grados del comando ROTAR.
     * 
     * @param mensaje Mensaje que contiene el comando
     * @return Número de grados extraído
     * @throws NumberFormatException Si el número no es válido
     */
    private int extraer_grados(String mensaje) throws NumberFormatException {
        String numeroStr = mensaje.substring("ROTAR".length()).trim();
        return Integer.parseInt(numeroStr);
    }

    /**
     * Enciende el módulo de rotación.
     * Registra el estado y muestra mensaje si el log está activo.
     */
    @Override
    public void encender() {
        this.set_encendido(true);
        if (Global.log)
            System.out.println("Módulo de Rotación encendido");
    }

    /**
     * Apaga el módulo de rotación.
     * Registra el estado y muestra mensaje si el log está activo.
     */
    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log)
            System.out.println("Módulo de Rotación apagado");
    }

    /**
     * Envía una respuesta de acción al sistema de control.
     *
     * Este método se invoca para notificar el resultado de una acción ejecutada por
     * el módulo.
     *
     * @param respuesta Resultado de la acción (true=éxito, false=fallo)
     */
    @Override
    public void enviar_respuesta_accion(boolean respuesta) {
        if (respuesta) {
            if (Global.log)
                System.out.println("Rotacion del robot ejecutada sin problemas.");
        } else {
            if (Global.log)
                System.out.println("Error en rotacion detectada, ejecutando gestion de errores.");
            this.gestionar_solucion();
        }
    }

}
