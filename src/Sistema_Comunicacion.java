public class Sistema_Comunicacion {
    Modulo modulo;

    public Sistema_Comunicacion(Modulo modulo){
        this.modulo = modulo;
    }

    public void recibir_mensaje(String mensaje){ //recibe un mensaje de otro modulo
        this.modulo.getSistemaControl().interpretar(mensaje);
    }

    public void enviar_mensaje(Modulo receptor , String mensaje){ //envia un mensaje a otro modulo
        receptor.getSistemaComunicacion().recibir_mensaje(mensaje);
    }
}
