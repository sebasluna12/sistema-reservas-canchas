package conexionBD;

/**
 * Clase CanchaFutbol - Hereda de Cancha
 * Implementación concreta para canchas de fútbol
 * 
 * @author Sebastian Luna
 * @version 2.0 (TP4)
 */
public class CanchaFutbol extends Cancha {
    
    // ========== ATRIBUTOS ESPECÍFICOS ==========
    
    /**
     * Cantidad de jugadores (5, 7, 11)
     */
    private int cantidadJugadores;
    
    
    // ========== CONSTRUCTORES ==========
    
    /**
     * Constructor completo (con ID)
     */
    public CanchaFutbol(int idCancha, String nombre, String tipo, String cesped,
                        double precioBase, String estado, int cantidadJugadores) {
        super(idCancha, nombre, tipo, cesped, precioBase, estado);
        this.cantidadJugadores = cantidadJugadores;
    }
    
    /**
     * Constructor sin ID (para inserciones)
     */
    public CanchaFutbol(String nombre, String tipo, String cesped,
                        double precioBase, String estado, int cantidadJugadores) {
        super(nombre, tipo, cesped, precioBase, estado);
        this.cantidadJugadores = cantidadJugadores;
    }
    
    /**
     * Constructor simple desde BD
     * Extrae cantidadJugadores del tipo (ej: "Futbol 11" → 11)
     */
    public CanchaFutbol(int idCancha, String nombre, String tipo, String cesped,
                        double precioBase, String estado) {
        super(idCancha, nombre, tipo, cesped, precioBase, estado);
        this.cantidadJugadores = extraerCantidadJugadores(tipo);
    }
    
    
    // ========== MÉTODOS PRIVADOS AUXILIARES ==========
    
    /**
     * Extrae la cantidad de jugadores del tipo de cancha
     * Ejemplo: "Futbol 11" → 11, "Futbol 7" → 7
     */
    private int extraerCantidadJugadores(String tipo) {
        if (tipo == null) return 11;
        
        if (tipo.contains("11")) return 11;
        if (tipo.contains("7")) return 7;
        if (tipo.contains("5")) return 5;
        
        return 11; // Por defecto
    }
    
    
    // ========== IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS ==========
    
    /**
     * Calcula el precio aplicando descuentos por cantidad de horas
     * 
     * Reglas:
     * - 1-2 horas: Precio base
     * - 3+ horas: 15% descuento
     * - Césped sintético: +10% recargo
     * 
     * @param horas Cantidad de horas a reservar
     * @return Precio total con descuentos/recargos
     */
    @Override
    public double calcularPrecio(int horas) {
        if (horas <= 0) return 0;
        
        double precio = precioBase * horas;
        
        // Descuento por cantidad de horas
        if (horas >= 3) {
            precio *= 0.85; // 15% descuento
        }
        
        // Recargo por césped sintético
        if (cesped != null && cesped.equalsIgnoreCase("Sintético")) {
            precio *= 1.10; // 10% recargo
        }
        
        return Math.round(precio * 100.0) / 100.0; // Redondear a 2 decimales
    }
    
    /**
     * Obtiene las características específicas de esta cancha
     * 
     * @return String con características formateadas
     */
    @Override
    public String getCaracteristicas() {
        return String.format(
            "Fútbol %d | Césped %s | %s",
            cantidadJugadores,
            cesped,
            estado
        );
    }
    
    
    // ========== GETTERS Y SETTERS ==========
    
    public int getCantidadJugadores() {
        return cantidadJugadores;
    }
    
    public void setCantidadJugadores(int cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
    }
    
    
    // ========== MÉTODOS ADICIONALES ==========
    
    /**
     * Verifica si es cancha de fútbol 5
     */
    public boolean esFutbol5() {
        return cantidadJugadores == 5;
    }
    
    /**
     * Verifica si es cancha de fútbol 7
     */
    public boolean esFutbol7() {
        return cantidadJugadores == 7;
    }
    
    /**
     * Verifica si es cancha de fútbol 11
     */
    public boolean esFutbol11() {
        return cantidadJugadores == 11;
    }
    
    
    // ========== MÉTODOS SOBRESCRITOS ==========
    
    @Override
    public String toString() {
        return super.toString() + " | " + getCaracteristicas();
    }
}
