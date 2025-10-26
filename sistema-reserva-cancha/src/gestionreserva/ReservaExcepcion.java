package gestionreserva;

/**
 * Clase para manejar excepciones personalizadas del sistema de reservas
 */
public class ReservaExcepcion extends Exception {
    
    // Constructor que recibe un mensaje de error
    public ReservaExcepcion(String mensaje) {
        super(mensaje);
    }
}