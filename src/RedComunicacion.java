import java.util.HashMap;

public class RedComunicacion {
    private final HashMap<Integer, SistemaComunicacion> sistemas = new HashMap<>();

    public void registrar_sistema(int idModulo, SistemaComunicacion sistema) {
        sistemas.put(idModulo, sistema);
    }

    public void enviar_mensaje(int receptor, String mensaje) {
        SistemaComunicacion destino = sistemas.get(receptor);
        if (destino != null) {
            destino.recibir_mensaje(mensaje);
        } else {
            System.out.println("Destino [" + receptor + "] no encontrado.");
        }
    }
}
