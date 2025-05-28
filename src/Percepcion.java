public abstract class Percepcion extends Modulo_Estatico{
    private int N_Sensores;

    public Percepcion(int id, String referencia, String descripcion, int largo, int ancho, int profundidad, boolean encendido, int N_Sensores) {
        super(id, referencia, descripcion, largo, ancho, profundidad, encendido);
        this.N_Sensores = N_Sensores;
    }

    public abstract int procesar_datos(int[] mapa);

    public int captar_informacion(){ // Aquí se devuelve el objeto percibido. Es la funcion que verifica si es aire bloque o animal
        return 0;
    }
}
