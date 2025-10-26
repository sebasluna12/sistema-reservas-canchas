package gestionreserva;

/**
 * Clase abstracta que representa una cancha deportiva
 * Esta clase sirve como base para los diferentes tipos de canchas
 */
public abstract class Cancha {
    
    // Atributos protected (accesibles por las clases hijas)
    protected int idCancha;
    protected String nombre;
    protected String tipo;
    protected double precioBase;
    protected boolean disponible;
    
    // Constructor
    public Cancha(int idCancha, String nombre, String tipo, double precioBase) {
        this.idCancha = idCancha;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precioBase = precioBase;
        this.disponible = true; // Por defecto está disponible
    }
    
    // Método abstracto: las clases hijas DEBEN implementarlo
    public abstract double calcularPrecio(int horas);
    
    // Método para verificar disponibilidad
    public boolean verificarDisponibilidad() {
        return disponible;
    }
    
    // Método para cambiar disponibilidad
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    // Getters
    public int getIdCancha() {
        return idCancha;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public double getPrecioBase() {
        return precioBase;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    // toString para mostrar información
    @Override
    public String toString() {
        return "Cancha #" + idCancha + " - " + nombre + 
               " (" + tipo + ") - Precio base: $" + precioBase + 
               " - " + (disponible ? "Disponible" : "No disponible");
    }
}