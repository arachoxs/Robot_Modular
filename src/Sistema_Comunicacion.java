public class Sistema_Comunicacion {
    Modulo modulo;

    public Sistema_Comunicacion(Modulo modulo){
        this.modulo = modulo;
    }

    public String recibir_mensaje(String mensaje){ //recibe un mensaje de otro modulo
        return this.modulo.getSistemaControl().interpretar(mensaje);
    }

    public String enviar_mensaje(Modulo receptor , String mensaje){ //envia un mensaje a otro modulo
        return receptor.getSistemaComunicacion().recibir_mensaje(mensaje);
    }
}
