package gestionreserva;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las canchas y reservas del sistema
 */
public class GestionReservas {
    
    // Atributos privados
    private List<Cancha> canchas;
    private List<Reserva> reservas;
    
    // Constructor
    public GestionReservas() {
        this.canchas = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }
    
    // Método para agregar una cancha
    public void agregarCancha(Cancha cancha) {
        canchas.add(cancha);
        System.out.println("Cancha agregada exitosamente.");
    }
    
    // Método para agregar una reserva
    public void agregarReserva(Reserva reserva) throws ReservaExcepcion {
        // Buscar la cancha
        Cancha cancha = buscarCancha(reserva.getIdCancha());
        
        if (cancha == null) {
            throw new ReservaExcepcion("Error: La cancha #" + reserva.getIdCancha() + " no existe.");
        }
        
        if (!cancha.verificarDisponibilidad()) {
            throw new ReservaExcepcion("Error: La cancha #" + reserva.getIdCancha() + " no está disponible.");
        }
        
        // Validar horarios
        if (reserva.getHoraFin() <= reserva.getHoraInicio()) {
            throw new ReservaExcepcion("Error: La hora de fin debe ser posterior a la hora de inicio.");
        }
        
        // Calcular el monto
        reserva.calcularMonto(cancha);
        
        // Agregar la reserva
        reservas.add(reserva);
        System.out.println("Reserva creada exitosamente. Monto total: $" + 
                          String.format("%.2f", reserva.getMontoTotal()));
    }
    
    // Método para buscar una cancha por ID
    private Cancha buscarCancha(int idCancha) {
        for (Cancha cancha : canchas) {
            if (cancha.getIdCancha() == idCancha) {
                return cancha;
            }
        }
        return null;
    }
    
    // Método para buscar una reserva por ID
    public Reserva buscarReserva(int idReserva) {
        for (Reserva reserva : reservas) {
            if (reserva.getIdReserva() == idReserva) {
                return reserva;
            }
        }
        return null;
    }
    
    // Método para pagar una reserva
    public void pagarReserva(int idReserva) throws ReservaExcepcion {
        Reserva reserva = buscarReserva(idReserva);
        
        if (reserva == null) {
            throw new ReservaExcepcion("Error: Reserva #" + idReserva + " no encontrada.");
        }
        
        if (reserva.getEstado().equals("PAGADO")) {
            throw new ReservaExcepcion("Error: La reserva ya está pagada.");
        }
        
        if (reserva.getEstado().equals("CANCELADO")) {
            throw new ReservaExcepcion("Error: No se puede pagar una reserva cancelada.");
        }
        
        reserva.actualizarEstado("PAGADO");
        System.out.println("Reserva #" + idReserva + " pagada exitosamente.");
    }
    
    // Método para cancelar una reserva
    public void cancelarReserva(int idReserva) throws ReservaExcepcion {
        Reserva reserva = buscarReserva(idReserva);
        
        if (reserva == null) {
            throw new ReservaExcepcion("Error: Reserva #" + idReserva + " no encontrada.");
        }
        
        if (reserva.getEstado().equals("CANCELADO")) {
            throw new ReservaExcepcion("Error: La reserva ya está cancelada.");
        }
        
        reserva.actualizarEstado("CANCELADO");
        System.out.println("Reserva #" + idReserva + " cancelada exitosamente.");
    }
    
    // Método para listar todas las reservas
    public void listarReservas() {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            System.out.println("\n========== LISTA DE RESERVAS ==========");
            for (Reserva reserva : reservas) {
                System.out.println(reserva);
            }
            System.out.println("=======================================\n");
        }
    }
    
    // Método para listar canchas disponibles
    public void listarCanchasDisponibles() {
        System.out.println("\n========== CANCHAS DISPONIBLES ==========");
        boolean hayDisponibles = false;
        
        for (Cancha cancha : canchas) {
            if (cancha.verificarDisponibilidad()) {
                System.out.println(cancha);
                hayDisponibles = true;
            }
        }
        
        if (!hayDisponibles) {
            System.out.println("No hay canchas disponibles en este momento.");
        }
        System.out.println("=========================================\n");
    }
    
    // Método para mostrar todas las canchas
    public void mostrarTodasLasCanchas() {
        if (canchas.isEmpty()) {
            System.out.println("No hay canchas registradas.");
        } else {
            System.out.println("\n========== TODAS LAS CANCHAS ==========");
            for (Cancha cancha : canchas) {
                System.out.println(cancha);
            }
            System.out.println("=======================================\n");
        }
    }
    
    // Método para verificar si hay canchas
    public boolean hayCanchas() {
        return !canchas.isEmpty();
    }
    
    // Método para obtener la lista de canchas
    public List<Cancha> getCanchas() {
        return canchas;
    }
    
    // Método para obtener la lista de reservas
    public List<Reserva> getReservas() {
        return reservas;
    }
}