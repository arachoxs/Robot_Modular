public class SistemaComunicacion {
    private Modulo modulo_propietario;
    private RedComunicacion red;

    public SistemaComunicacion(Modulo modulo, RedComunicacion red){
        this.modulo_propietario = modulo;
        this.red = red;
        red.registrar_sistema(modulo.get_id(),this);
    }

    // Getters y Setters
    public Modulo get_modulo_propietario() { return modulo_propietario; }
    public void set_modulo_propietario(Modulo modulo_propietario) { this.modulo_propietario = modulo_propietario; }

    public void recibir_mensaje(String mensaje){ //recibe un mensaje de otro modulo
        System.out.println("Mensaje recibido en módulo " + modulo_propietario.get_referencia() + ": " + mensaje);
        this.modulo_propietario.get_sistema_control().interpretar(mensaje);
    }

    public void enviar_mensaje(int receptor, String mensaje){
        this.red.enviar_mensaje(receptor,mensaje);
    }

}

