public class SistemaComunicacion {
    private Modulo modulo_propietario;

    public SistemaComunicacion(Modulo modulo){
        this.modulo_propietario = modulo;
    }

    public void recibir_mensaje(String mensaje){ //recibe un mensaje de otro modulo
        this.modulo.getSistemaControl().interpretar(mensaje);
    }

    public void enviar_mensaje(Modulo receptor , String mensaje){ //envia un mensaje a otro modulo
        receptor.getSistemaComunicacion().recibir_mensaje(mensaje);
    }
}
