import java.util.List;

/**
 * Clase abstracta ModuloDinamico - Módulos del robot con capacidades de
 * movimiento
 * 
 * Esta clase base define la estructura común para todos los módulos que
 * proporcionan
 * movimiento al robot. Extiende la funcionalidad básica de Modulo añadiendo
 * capacidades de control de motores y definiendo la interfaz para movimiento.
 * 
 * Los módulos dinámicos incluyen extensión (movimiento lineal), rotación
 * (movimiento angular) y helicoidal (movimiento combinado).
 */
public abstract class ModuloDinamico extends Modulo {

    /** Número de motores que controla este módulo */
    private int n_motores;

    /**
     * Constructor para módulos dinámicos.
     * 
     * @param id          Identificador único del módulo
     * @param referencia  Referencia comercial del módulo
     * @param descripcion Descripción de capacidades
     * @param largo       Dimensión largo en milímetros
     * @param ancho       Dimensión ancho en milímetros
     * @param profundidad Dimensión profundidad en milímetros
     * @param encendido   Estado inicial de encendido
     * @param n_motores   Número de motores del módulo
     */
    public ModuloDinamico(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
            boolean encendido, int n_motores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido);
        this.n_motores = n_motores;
    }

    /**
     * Obtiene el número de motores del módulo.
     * 
     * @return Cantidad de motores
     */
    public int get_n_motores() {
        return n_motores;
    }

    /**
     * Establece el número de motores del módulo.
     * 
     * @param n_motores Nueva cantidad de motores
     */
    public void set_n_motores(int n_motores) {
        this.n_motores = n_motores;
    }

    /**
     * Método abstracto para realizar movimiento del robot.
     * Debe ser implementado por cada tipo específico de módulo dinámico.
     * 
     * @param n_pasos    Número de pasos a mover
     * @param grados     Grados a rotar (si aplica)
     * @param pasos_giro Pasos antes del primer giro (para movimiento helicoidal)
     * @return true si el movimiento fue exitoso, false en caso contrario
     */
    public abstract boolean moverse(int n_pasos, int grados, int pasos_giro);
}
