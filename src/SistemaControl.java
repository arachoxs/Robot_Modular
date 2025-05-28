public class SistemaControl {
    private Modulo modulo_propietario;

    public SistemaControl(Modulo modulo){
        this.modulo_propietario = modulo;
    }

    // Getters y Setters
    public Modulo get_modulo_propietario() { return modulo_propietario; }
    public void set_modulo_propietario(Modulo modulo_propietario) { this.modulo_propietario = modulo_propietario; }

    public boolean enviar_respuesta_accion(){
        // Lógica para enviar respuesta de acción
        System.out.println("Sistema de control del módulo " + modulo_propietario.get_id() + " enviando respuesta");
        return true;
    }

    public String gestionar_solucion(){
        // Lógica para gestionar solución cuando hay problemas
        System.out.println("Gestionando solución en módulo " + modulo_propietario.get_id());
        return "Solución gestionada";
    }

    public void interpretar(String mensaje){
        System.out.println("Interpretando mensaje en módulo " + modulo_propietario.get_id() + ": " + mensaje);
        //this.modulo_propietario.interpretar_mensaje(mensaje);
        informar_accion();
    }

    public void informar_accion() {
        // Lógica para informar acción al módulo
        System.out.println("Informando acción al módulo " + modulo_propietario.get_id());
    }
}
