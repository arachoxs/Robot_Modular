public class Extension extends ModuloDinamico {

    // Constructor
    public Extension(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
                     boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido, n_motores);
    }

    // Methods
    @Override
    public boolean moverse(int n_pasos, int grados , int pasos_giro) {
        // Lógica específica para movimiento de extensión (línea recta)
        System.out.println("Moviéndose en línea recta: " + n_pasos + " metros");
        for(int i=0; i<n_pasos; i++){
            this.get_sistema_comunicacion().enviar_mensaje(5,"Verificar"); //manda mensaje para que verifique alfrente a proximidad
        }

        return true;
    }

    // Sobrecarga para movimiento en línea recta (grados y pasos_giro en 0)
    public boolean moverse(int n_pasos) {
        return moverse(n_pasos, 0, 0);
    }

    public boolean mueve_un_paso(){
        int[] direccionActual = Global.robot.getDireccion();
        int[] posicionActual = Global.robot.getPos();

        // Limpiar la celda actual
        Global.matriz.setCelda(posicionActual[1], posicionActual[0], 0);

        // Calcular nueva posición
        int nuevoX = posicionActual[0] + direccionActual[0];
        int nuevoY = posicionActual[1] + direccionActual[1];

        // Mover el robot
        Global.robot.setPos(nuevoX, nuevoY);

        Global.matriz.actualizarMatriz();

        return true;
    }

    @Override
    public void interpretar_mensaje(String mensaje) {
        if(mensaje.equals("MOVER FIJO")){
            this.mueve_un_paso();
        }

        if (mensaje.startsWith("mover")) {
            // Extraer lo que viene después del "+"

            // Extraer lo que viene después de "mover"
            String numeroStr = mensaje.substring("mover".length()).trim(); // Elimina espacios

// Convertir a entero


            // Convertir a entero
            int pasos = Integer.parseInt(numeroStr);

            this.moverse(pasos);

        } else {
            System.out.println("Mensaje no reconocido");
        }
        System.out.println("Movimiento");
    }

    @Override
    public void encender() {
        System.out.println("Módulo de Extensión encendido");
    }

    @Override
    public void apagar() {
        System.out.println("Módulo de Extensión apagado");
    }
}
