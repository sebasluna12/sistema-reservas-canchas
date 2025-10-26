package gestionreserva;

/**
 * Clase que representa una cancha de fútbol específica
 * Hereda de la clase abstracta Cancha
 */
public class CanchaFutbol extends Cancha {
    
    // Atributos adicionales específicos de cancha de fútbol
    private String cesped;  // Natural o Sintético
    private String tamanio; // Futbol 5, 7 u 11
    
    // Constructor
    public CanchaFutbol(int idCancha, String nombre, String tipo, double precioBase, 
                        String cesped, String tamanio) {
        // Llamamos al constructor de la clase padre (Cancha)
        super(idCancha, nombre, tipo, precioBase);
        this.cesped = cesped;
        this.tamanio = tamanio;
    }
    
    // Implementación del método abstracto de la clase padre
    @Override
    public double calcularPrecio(int horas) {
        // Lógica de cálculo de precio
        double precioTotal = precioBase * horas;
        
        // Si es césped natural, agregamos un 20% más
        if (cesped.equalsIgnoreCase("Natural")) {
            precioTotal = precioTotal * 1.20;
        }
        
        return precioTotal;
    }
    
    // Getters adicionales
    public String getCesped() {
        return cesped;
    }
    
    public String getTamanio() {
        return tamanio;
    }
    
    // toString mejorado
    @Override
    public String toString() {
        return super.toString() + 
               " - Césped: " + cesped + 
               " - Tamaño: " + tamanio;
    }
}