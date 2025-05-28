public class SistemaComunicacion {
    private Modulo modulo_propietario;

    public SistemaComunicacion(Modulo modulo){
        this.modulo_propietario = modulo;
    }

    // Getters y Setters
    public Modulo get_modulo_propietario() { return modulo_propietario; }
    public void set_modulo_propietario(Modulo modulo_propietario) { this.modulo_propietario = modulo_propietario; }

    public void recibir_mensaje(String mensaje){ //recibe un mensaje de otro modulo
        System.out.println("Mensaje recibido en módulo " + modulo_propietario.get_id() + ": " + mensaje);
        //this.modulo_propietario.get_sistema_control().interpretar(mensaje);
    }

    public void enviar_mensaje(Modulo receptor , String mensaje){ //envia un mensaje a otro modulo
        receptor.get_sistema_comunicacion().recibir_mensaje(mensaje);
    }
}
