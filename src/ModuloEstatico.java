/**
 * Clase abstracta ModuloEstatico - Módulos del robot sin capacidades de
 * movimiento
 * 
 * Esta clase base define la estructura común para todos los módulos que
 * proporcionan
 * funcionalidades de percepción y actuación sin generar movimiento en el robot.
 * Extiende la funcionalidad básica de Modulo manteniendo la interfaz común pero
 * especializándose en operaciones estacionarias.
 * 
 * Los módulos estáticos incluyen sensores (cámara, proximidad) y actuadores
 * (altavoz).
 */
public abstract class ModuloEstatico extends Modulo {

    /**
     * Constructor para módulos estáticos.
     * 
     * @param id          Identificador único del módulo
     * @param referencia  Referencia comercial del módulo
     * @param descripcion Descripción de capacidades
     * @param largo       Dimensión largo en milímetros
     * @param ancho       Dimensión ancho en milímetros
     * @param profundidad Dimensión profundidad en milímetros
     * @param encendido   Estado inicial de encendido
     */
    public ModuloEstatico(int id, String referencia, String descripcion, int largo, int ancho, int profundidad,
            boolean encendido) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido);
    }
}
