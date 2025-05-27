public class Sistema_Comunicacion {
    public String recibir_mensaje(String mensaje){
        return mensaje;

    }

    public void enviar_mensaje(Modulo receptor , String mensaje){
        receptor.getSistemaComunicacion().recibir_mensaje(mensaje);
    }
}
