package gestionreserva;

/**
 * Clase que representa una reserva de cancha
 */
public class Reserva {
    
    // Atributos privados
    private int idReserva;
    private int idCancha;
    private String nombreCliente;
    private String fecha;
    private int horaInicio;
    private int horaFin;
    private String estado; // PENDIENTE, PAGADO, CANCELADO
    private double montoTotal;
    
    // Constructor
    public Reserva(int idReserva, int idCancha, String nombreCliente, 
                   String fecha, int horaInicio, int horaFin) {
        this.idReserva = idReserva;
        this.idCancha = idCancha;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = "PENDIENTE"; // Por defecto está pendiente
        this.montoTotal = 0.0;
    }
    
    // Método para calcular el monto de la reserva
    public void calcularMonto(Cancha cancha) {
        int cantidadHoras = horaFin - horaInicio;
        this.montoTotal = cancha.calcularPrecio(cantidadHoras);
    }
    
    // Método para actualizar el estado
    public void actualizarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }
    
    // Getters
    public int getIdReserva() {
        return idReserva;
    }
    
    public int getIdCancha() {
        return idCancha;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public int getHoraInicio() {
        return horaInicio;
    }
    
    public int getHoraFin() {
        return horaFin;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public double getMontoTotal() {
        return montoTotal;
    }
    
    // Setters (solo los necesarios)
    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }
    
    // toString para mostrar información
    @Override
    public String toString() {
        return "Reserva #" + idReserva + 
               " - Cliente: " + nombreCliente + 
               " - Cancha: #" + idCancha + 
               " - Fecha: " + fecha + 
               " - Horario: " + horaInicio + ":00 a " + horaFin + ":00" +
               " - Estado: " + estado + 
               " - Monto: $" + String.format("%.2f", montoTotal);
    }
}