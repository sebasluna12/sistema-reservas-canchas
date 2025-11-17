package conexionBD;

/**
 * Clase abstracta Cancha
 * Adaptada del TP3 para trabajar con MySQL
 * 
 * Esta clase sirve como base para los diferentes tipos de canchas
 * Corresponde a la tabla: canchas
 * 
 * @author Sebastian Luna
 * @version 2.0 (TP4 - Con persistencia en BD)
 */
public abstract class Cancha {
    
    // ========== ATRIBUTOS PROTECTED ==========
    
    /**
     * ID único de la cancha (clave primaria)
     * Corresponde a: canchas.cancha_id
     */
    protected int idCancha;
    
    /**
     * Nombre de la cancha
     * Corresponde a: canchas.nombre
     * Ejemplo: "Cancha Principal", "Cancha A"
     */
    protected String nombre;
    
    /**
     * Tipo de cancha
     * Corresponde a: canchas.tipo
     * Ejemplos: "Futbol 11", "Futbol 7", "Futbol 5"
     */
    protected String tipo;
    
    /**
     * Tipo de césped
     * Corresponde a: canchas.cesped
     * Ejemplos: "Natural", "Sintético"
     */
    protected String cesped;
    
    /**
     * Precio base por hora
     * Corresponde a: canchas.precio_base
     */
    protected double precioBase;
    
    /**
     * Estado de la cancha
     * Corresponde a: canchas.estado
     * Valores: "Disponible", "Mantenimiento", "No Disponible"
     */
    protected String estado;
    
    
    // ========== CONSTRUCTOR ==========
    
    /**
     * Constructor completo
     */
    public Cancha(int idCancha, String nombre, String tipo, String cesped,
                  double precioBase, String estado) {
        this.idCancha = idCancha;
        this.nombre = nombre;
        this.tipo = tipo;
        this.cesped = cesped;
        this.precioBase = precioBase;
        this.estado = estado;
    }
    
    /**
     * Constructor sin ID (para inserciones)
     */
    public Cancha(String nombre, String tipo, String cesped, double precioBase, String estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.cesped = cesped;
        this.precioBase = precioBase;
        this.estado = estado;
    }
    
    
    // ========== MÉTODOS ABSTRACTOS ==========
    
    /**
     * Método abstracto para calcular precio
     * Cada tipo de cancha implementa su propia lógica
     * 
     * @param horas Cantidad de horas a reservar
     * @return Precio total con descuentos/recargos aplicados
     */
    public abstract double calcularPrecio(int horas);
    
    /**
     * Método abstracto para obtener características específicas
     * 
     * @return String con características de la cancha
     */
    public abstract String getCaracteristicas();
    
    
    // ========== GETTERS Y SETTERS ==========
    
    public int getIdCancha() {
        return idCancha;
    }
    
    public void setIdCancha(int idCancha) {
        this.idCancha = idCancha;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getCesped() {
        return cesped;
    }
    
    public void setCesped(String cesped) {
        this.cesped = cesped;
    }
    
    public double getPrecioBase() {
        return precioBase;
    }
    
    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /**
     * Método auxiliar: verifica si la cancha está disponible
     */
    public boolean estaDisponible() {
        return estado != null && estado.equalsIgnoreCase("Disponible");
    }
    
    /**
     * Método auxiliar: verifica si la cancha está en mantenimiento
     */
    public boolean enMantenimiento() {
        return estado != null && estado.equalsIgnoreCase("Mantenimiento");
    }
    
    
    // ========== MÉTODOS SOBRESCRITOS ==========
    
    @Override
    public String toString() {
        return String.format(
            "Cancha #%d: %s (%s) | Césped: %s | $%.2f/hora | %s",
            idCancha, nombre, tipo, cesped, precioBase, estado
        );
    }
}
        
        System.out.println("\n==============================================");
    }
}
