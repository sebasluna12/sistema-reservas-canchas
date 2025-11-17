package conexionBD;

/**
 * Clase Reserva - Entidad que representa una reserva de cancha
 * Adaptada del TP3 para trabajar con MySQL mediante JDBC
 * 
 * @author Sebastian Luna
 * @version 2.0 (TP4 - Con persistencia en BD)
 */
public class Reserva {
    
    // ========== ATRIBUTOS ==========
    
    /**
     * ID único de la reserva (clave primaria en BD)
     * Corresponde a: reservas.reserva_id
     */
    private int idReserva;
    
    /**
     * ID del usuario que realizó la reserva (clave foránea)
     * Corresponde a: reservas.usuario_id
     */
    private int idUsuario;
    
    /**
     * ID de la cancha reservada (clave foránea)
     * Corresponde a: reservas.cancha_id
     */
    private int idCancha;
    
    /**
     * Nombre del cliente (para mostrar, no se guarda en BD reservas)
     * Se obtiene haciendo JOIN con tabla usuarios
     */
    private String nombreCliente;
    
    /**
     * Fecha de la reserva (formato: YYYY-MM-DD)
     * Corresponde a: reservas.fecha (DATE)
     */
    private String fecha;
    
    /**
     * Hora de inicio en formato String (HH:mm)
     * Corresponde a: reservas.hora_inicio (TIME)
     * Ejemplo: "14:00", "16:30"
     */
    private String horaInicio;
    
    /**
     * Hora de fin en formato String (HH:mm)
     * Corresponde a: reservas.hora_fin (TIME)
     * Ejemplo: "16:00", "18:30"
     */
    private String horaFin;
    
    /**
     * Estado de la reserva: PENDIENTE, PAGADO, CANCELADO
     * Corresponde a: reservas.estado (VARCHAR)
     */
    private String estado;
    
    /**
     * Monto total a pagar
     * Corresponde a: reservas.monto_total (DECIMAL)
     */
    private double montoTotal;
    
    
    // ========== CONSTRUCTORES ==========
    
    /**
     * Constructor vacío
     * Se usa para crear objetos que se llenarán después
     */
    public Reserva() {
        this.estado = "PENDIENTE";
    }
    
    /**
     * Constructor completo (con ID de reserva)
     * Se usa al LEER desde la base de datos
     */
    public Reserva(int idReserva, int idUsuario, int idCancha, String nombreCliente,
                   String fecha, String horaInicio, String horaFin, 
                   String estado, double montoTotal) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.idCancha = idCancha;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.montoTotal = montoTotal;
    }
    
    /**
     * Constructor sin ID de reserva
     * Se usa al INSERTAR en la base de datos (el ID se genera automáticamente)
     */
    public Reserva(int idUsuario, int idCancha, String nombreCliente,
                   String fecha, String horaInicio, String horaFin, 
                   String estado, double montoTotal) {
        this.idUsuario = idUsuario;
        this.idCancha = idCancha;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.montoTotal = montoTotal;
    }
    
    /**
     * Constructor simplificado (compatibilidad con TP3)
     * Asume idUsuario = 1 por defecto
     */
    public Reserva(int idCancha, String nombreCliente, String fecha, 
                   String horaInicio, String horaFin, double montoTotal) {
        this.idUsuario = 1; // Usuario por defecto
        this.idCancha = idCancha;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = "PENDIENTE";
        this.montoTotal = montoTotal;
    }
    
    
    // ========== GETTERS Y SETTERS ==========
    
    public int getIdReserva() {
        return idReserva;
    }
    
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public int getIdCancha() {
        return idCancha;
    }
    
    public void setIdCancha(int idCancha) {
        this.idCancha = idCancha;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getHoraInicio() {
        return horaInicio;
    }
    
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public String getHoraFin() {
        return horaFin;
    }
    
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public double getMontoTotal() {
        return montoTotal;
    }
    
    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }
    
    
    // ========== MÉTODOS AUXILIARES ==========
    
    /**
     * Verifica si la reserva está pagada
     */
    public boolean estaPagada() {
        return estado != null && estado.equalsIgnoreCase("PAGADO");
    }
    
    /**
     * Verifica si la reserva está cancelada
     */
    public boolean estaCancelada() {
        return estado != null && estado.equalsIgnoreCase("CANCELADO");
    }
    
    /**
     * Verifica si la reserva está pendiente
     */
    public boolean estaPendiente() {
        return estado != null && estado.equalsIgnoreCase("PENDIENTE");
    }
    
    
    // ========== MÉTODOS SOBRESCRITOS ==========
    
    /**
     * Representación en String de la reserva
     */
    @Override
    public String toString() {
        return String.format(
            "Reserva #%d | Cliente: %s | Cancha: %d | Fecha: %s | %s-%s | Estado: %s | $%.2f",
            idReserva, nombreCliente, idCancha, fecha, horaInicio, horaFin, estado, montoTotal
        );
    }
    
    /**
     * Representación detallada para mostrar en consola
     */
    public String toStringDetallado() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("        DETALLE DE RESERVA\n");
        sb.append("═══════════════════════════════════════\n");
        sb.append(String.format("ID Reserva     : %d\n", idReserva));
        sb.append(String.format("Cliente        : %s\n", nombreCliente));
        sb.append(String.format("ID Usuario     : %d\n", idUsuario));
        sb.append(String.format("ID Cancha      : %d\n", idCancha));
        sb.append(String.format("Fecha          : %s\n", fecha));
        sb.append(String.format("Hora Inicio    : %s\n", horaInicio));
        sb.append(String.format("Hora Fin       : %s\n", horaFin));
        sb.append(String.format("Estado         : %s\n", estado));
        sb.append(String.format("Monto Total    : $%.2f\n", montoTotal));
        sb.append("═══════════════════════════════════════\n");
        return sb.toString();
    }
}
