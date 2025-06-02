public class Helicoidal extends ModuloDinamico {

    //Constructor
    public Helicoidal(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
    }

    //Methods
    @Override
    public boolean moverse(int n_pasos, int grados, int pasos_giro) {
        // Lógica específica para movimiento helicoidal (gira y se traslada)
        String sentido = "";
        if (grados >= 0) sentido = "horario";
        else if (grados < 0) sentido = "antihorario";
        if (Global.log == true) System.out.println("Movimiento helicoidal: " + n_pasos
                + " pasos totales, sentido " + sentido + ", " + pasos_giro + " pasos antes del primer giro");

        int pasos_restantes = n_pasos;
        int pasos_restantes_giro = pasos_giro;
        while (pasos_restantes > 0) {
            while (pasos_restantes_giro > 0) {
                this.get_sistema_control().enviar_respuesta_accion(Global.SENSOR_PROXIMIDAD_PRINCIPAL,"VERIFICAR HELICOIDAL");
                if (Global.helicoidal_fallido){
                    pasos_restantes = 0;
                    Global.helicoidal_fallido = false;
                    break;
                }
                pasos_restantes--;
                if (pasos_restantes <= 0) break;
                pasos_restantes_giro--;
            }
            if (pasos_restantes > 0) Global.robot.get_modulo_id(Global.ROTACION).get_sistema_comunicacion().recibir_mensaje("ROTAR " + grados);
            pasos_giro++;
            pasos_restantes_giro = pasos_giro;
        }
        return true;
    }

    @Override
    public void encender() {
        this.set_encendido(true);
        if (Global.log) System.out.println("Módulo Helicoidal encendido");
    }

    @Override
    public void apagar() {
        this.set_encendido(false);
        if (Global.log) System.out.println("Módulo Helicoidal apagado");
    }

    @Override
    public void enviar_respuesta_accion(boolean respuesta) {
        if(respuesta){
            if (Global.log) System.out.println("Movimiento Helicoidal del robot ejecutada sin problemas.");
        }
        else{
            if (Global.log)  System.out.println("Error en movimiento helicoidal detectada, ejecutando gestion de errores.");
            this.gestionar_solucion();
        }
    }
    /**
     * Extrae el valor de n_pasos del mensaje HELICOIDAL
     * @param mensaje String con formato "HELICOIDAL " + n_pasos + "," + grados + "," + pasos_giro
     * @return entero con el valor de n_pasos
     */
    public static int extraer_pasos(String mensaje) {
        // Remover el prefijo "HELICOIDAL "
        String datos = mensaje.substring("HELICOIDAL ".length());

        // Encontrar la primera coma
        int primeraComa = datos.indexOf(',');

        // Extraer la subcadena hasta la primera coma
        String nPasosStr = datos.substring(0, primeraComa);

        return Integer.parseInt(nPasosStr);
    }

    /**
     * Extrae el valor de grados del mensaje HELICOIDAL
     * @param mensaje String con formato "HELICOIDAL " + n_pasos + "," + grados + "," + pasos_giro
     * @return entero con el valor de grados
     */
    public static int extraer_grados(String mensaje) {
        // Remover el prefijo "HELICOIDAL "
        String datos = mensaje.substring("HELICOIDAL ".length());

        // Encontrar la primera y segunda coma
        int primeraComa = datos.indexOf(',');
        int segundaComa = datos.indexOf(',', primeraComa + 1);

        // Extraer la subcadena entre las dos comas
        String gradosStr = datos.substring(primeraComa + 1, segundaComa);

        return Integer.parseInt(gradosStr);
    }

    /**
     * Extrae el valor de pasos_giro del mensaje HELICOIDAL
     * @param mensaje String con formato "HELICOIDAL " + n_pasos + "," + grados + "," + pasos_giro
     * @return entero con el valor de pasos_giro
     */
    public static int extraer_pasos_giro(String mensaje) {
        // Remover el prefijo "HELICOIDAL "
        String datos = mensaje.substring("HELICOIDAL ".length());

        // Encontrar la segunda coma
        int primeraComa = datos.indexOf(',');
        int segundaComa = datos.indexOf(',', primeraComa + 1);

        // Extraer la subcadena después de la segunda coma
        String pasosGiroStr = datos.substring(segundaComa + 1);

        return Integer.parseInt(pasosGiroStr);
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
        mensaje = mensaje.trim().toUpperCase(); // Normaliza el mensaje
        boolean resultado_accion = false;

        if (mensaje.startsWith("HELICOIDAL")) {
            int n_pasos = extraer_pasos(mensaje);
            int grados = extraer_grados(mensaje);
            int pasos_giro = extraer_pasos_giro(mensaje);
            resultado_accion = moverse(n_pasos, grados, pasos_giro);
        }else if (mensaje.equals("MOVIMIENTO HELICOIDAL FALLIDO")){
            Global.helicoidal_fallido = true;
        }

        this.enviar_respuesta_accion(resultado_accion);
    }
}
