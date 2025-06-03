/**
 * Clase Helicoidal - Módulo dinámico para movimiento helicoidal del robot
 * 
 * Este módulo proporciona capacidades de movimiento combinado que integra
 * traslación
 * y rotación en patrones helicoidales. Ejecuta secuencias de pasos lineales
 * intercalados
 * con rotaciones periódicas, coordinando con sensores para navegación segura.
 * 
 * El patrón helicoidal incrementa progresivamente el número de pasos entre
 * giros,
 * creando trayectorias en espiral útiles para exploración y evasión de
 * obstáculos.
 */
public class Helicoidal extends ModuloDinamico {

    /**
     * Constructor del módulo helicoidal.
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
    public Helicoidal(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
            boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
    }

    /**
     * Ejecuta movimiento helicoidal combinando traslación y rotación progresiva.
     * El patrón alterna entre secuencias de pasos lineales y rotaciones,
     * incrementando
     * gradualmente el número de pasos entre cada giro.
     * 
     * @param n_pasos    Número total de pasos a ejecutar
     * @param grados     Grados a rotar en cada giro (positivo=horario,
     *                   negativo=antihorario)
     * @param pasos_giro Número inicial de pasos antes del primer giro
     * @return true si el movimiento fue exitoso
     */
    @Override
    public boolean moverse(int n_pasos, int grados, int pasos_giro) {
        String sentido = "";
        if (grados >= 0)
            sentido = "horario";
        else if (grados < 0)
            sentido = "antihorario";
        if (Global.log == true)
            System.out.println("Movimiento helicoidal: " + n_pasos
                    + " pasos totales, sentido " + sentido + ", " + pasos_giro + " pasos antes del primer giro");

        int pasos_restantes = n_pasos;
        int pasos_restantes_giro = pasos_giro;
        while (pasos_restantes > 0) {
            while (pasos_restantes_giro > 0) {
                this.get_sistema_control().enviar_respuesta_accion(Global.SENSOR_PROXIMIDAD_PRINCIPAL,
                        "VERIFICAR HELICOIDAL");
                if (Global.helicoidal_fallido) {
                    pasos_restantes = 0;
                    Global.helicoidal_fallido = false;
                    break;
                }
                pasos_restantes--;
                if (pasos_restantes <= 0)
                    break;
                pasos_restantes_giro--;
            }
            if (pasos_restantes > 0)
                Global.robot.get_modulo_id(Global.ROTACION).get_sistema_comunicacion()
                        .recibir_mensaje("ROTAR " + grados);
            pasos_giro++;
            pasos_restantes_giro = pasos_giro;
        }
        return true;
    }

    @Override
    public void encender() {
        this.set_encendido(true);
        if (Global.log)
            System.out.println("Módulo Helicoidal encendido");
    }

    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log)
            System.out.println("Módulo Helicoidal apagado");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {
        if (respuesta) {
            if (Global.log)
                System.out.println("Movimiento Helicoidal del robot ejecutada sin problemas.");
        } else {
            if (Global.log)
                System.out.println("Error en movimiento helicoidal detectada, ejecutando gestion de errores.");
            this.gestionar_solucion();
        }
    }

    /**
     * Extrae el valor de n_pasos del mensaje HELICOIDAL.
     * 
     * @param mensaje String con formato "HELICOIDAL n_pasos,grados,pasos_giro"
     * @return Número de pasos totales a ejecutar
     */
    public static int extraer_pasos(String mensaje) {
        String datos = mensaje.substring("HELICOIDAL ".length());
        int primeraComa = datos.indexOf(',');
        String nPasosStr = datos.substring(0, primeraComa);
        return Integer.parseInt(nPasosStr);
    }

    /**
     * Extrae el valor de grados del mensaje HELICOIDAL.
     * 
     * @param mensaje String con formato "HELICOIDAL n_pasos,grados,pasos_giro"
     * @return Grados a rotar en cada giro
     */
    public static int extraer_grados(String mensaje) {
        String datos = mensaje.substring("HELICOIDAL ".length());
        int primeraComa = datos.indexOf(',');
        int segundaComa = datos.indexOf(',', primeraComa + 1);
        String gradosStr = datos.substring(primeraComa + 1, segundaComa);
        return Integer.parseInt(gradosStr);
    }

    /**
     * Extrae el valor de pasos_giro del mensaje HELICOIDAL.
     * 
     * @param mensaje String con formato "HELICOIDAL n_pasos,grados,pasos_giro"
     * @return Número inicial de pasos antes del primer giro
     */
    public static int extraer_pasos_giro(String mensaje) {
        String datos = mensaje.substring("HELICOIDAL ".length());
        int primeraComa = datos.indexOf(',');
        int segundaComa = datos.indexOf(',', primeraComa + 1);
        String pasosGiroStr = datos.substring(segundaComa + 1);
        return Integer.parseInt(pasosGiroStr);
    }

    /**
     * Interpreta y procesa mensajes de movimiento helicoidal.
     * Soporta comandos complejos con múltiples parámetros y manejo de fallos.
     * 
     * @param mensaje Comando helicoidal a interpretar
     */
    @Override
    public void interpretar_mensaje(String mensaje) {
        mensaje = mensaje.trim().toUpperCase();
        boolean resultado_accion = false;

        if (mensaje.startsWith("HELICOIDAL")) {
            int n_pasos = extraer_pasos(mensaje);
            int grados = extraer_grados(mensaje);
            int pasos_giro = extraer_pasos_giro(mensaje);
            resultado_accion = moverse(n_pasos, grados, pasos_giro);
        } else if (mensaje.equals("MOVIMIENTO HELICOIDAL FALLIDO")) {
            Global.helicoidal_fallido = true;
        }

        this.enviar_respuesta_accion(resultado_accion);
    }
}
