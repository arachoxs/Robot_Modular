public class SistemaControl {
    private Modulo modulo_propietario;

    public SistemaControl(Modulo modulo){
        this.modulo_propietario = modulo;
    }

    // Getters y Setters
    public Modulo get_modulo_propietario() { return modulo_propietario; }
    public void set_modulo_propietario(Modulo modulo_propietario) { this.modulo_propietario = modulo_propietario; }

    public boolean enviar_respuesta_accion(int id,String mensaje){
        // Lógica para enviar respuesta de acción
        System.out.println("Sistema de control del módulo " + modulo_propietario.get_id() + " enviando accion a seguir");
        this.modulo_propietario.get_sistema_comunicacion().enviar_mensaje(id,mensaje);
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
        this.modulo_propietario.interpretar_mensaje(mensaje);
    }

}
