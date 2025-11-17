package gestionreserva;

import conexionBD.ConexionBD;
import conexionBD.ModeloGestionReservas;
import conexionBD.Reserva;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase ControladorGestionReservas - Patrón MVC (CONTROLADOR)
 * 
 * Coordina las operaciones entre la Vista (MenuReservas) y el Modelo (ModeloGestionReservas)
 * Contiene la lógica de negocio y validaciones
 * 
 * Adaptada del TP3 al TP4 con MySQL
 * 
 * @author Sebastian Luna
 * @version 2.0 (TP4 - Con MySQL)
 */
public class GestionReservas {
    
    // ========== ATRIBUTOS ==========
    
    /**
     * Modelo que gestiona el acceso a la base de datos
     */
    private ModeloGestionReservas modelo;
    
    /**
     * Scanner para entrada de usuario
     */
    private Scanner scanner;
    
    
    // ========== CONSTRUCTOR ==========
    
    /**
     * Constructor del controlador
     * Inicializa el modelo y el scanner
     */
    public GestionReservas() {
        this.modelo = new ModeloGestionReservas();
        this.scanner = new Scanner(System.in);
    }
    
    
    // ========== MÉTODO PRINCIPAL: CREAR RESERVA ==========
    
    /**
     * Crea una nueva reserva solicitando datos al usuario
     * Valida que no haya conflictos de horario antes de insertar
     * 
     * @return true si se creó exitosamente, false si hubo error
     */
    public boolean crearNuevaReserva() {
    System.out.println("\n═══════════════════════════════════════");
    System.out.println("        CREAR NUEVA RESERVA");
    System.out.println("═══════════════════════════════════════\n");
    
    try {
        // ========== SOLICITAR Y VALIDAR USUARIO ==========
        int idUsuario = 0;
        boolean usuarioValido = false;
        
        // Mostrar usuarios disponibles
        ArrayList<Integer> idsUsuarios = modelo.listarIdsUsuarios();
        System.out.println("Usuarios disponibles: " + idsUsuarios);
        
        while (!usuarioValido) {
            System.out.print("ID de Usuario: ");
            idUsuario = scanner.nextInt();
            
            if (modelo.existeUsuario(idUsuario)) {
                usuarioValido = true;
                System.out.println("  ✓ Usuario válido");
            } else {
                System.out.println("  ✗ El usuario con ID " + idUsuario + " no existe");
                System.out.println("  Use uno de los IDs disponibles: " + idsUsuarios);
            }
        }
        
        // ========== SOLICITAR Y VALIDAR CANCHA ==========
        int idCancha = 0;
        boolean canchaValida = false;
        
        // Mostrar canchas disponibles
        ArrayList<Integer> idsCanchas = modelo.listarIdsCanchas();
        System.out.println("\nCanchas disponibles: " + idsCanchas);
        
        while (!canchaValida) {
            System.out.print("ID de Cancha: ");
            idCancha = scanner.nextInt();
            
            if (modelo.existeCancha(idCancha)) {
                canchaValida = true;
                System.out.println("  ✓ Cancha válida");
            } else {
                System.out.println("  ✗ La cancha con ID " + idCancha + " no existe o no está disponible");
                System.out.println("  Use uno de los IDs disponibles: " + idsCanchas);
            }
        }
        
        scanner.nextLine(); // Limpiar buffer
        
        // ========== SOLICITAR Y VALIDAR FECHA ==========
        String fecha = "";
        boolean fechaValida = false;
        
        while (!fechaValida) {
            System.out.print("\nFecha (formato: AAAA-MM-DD, ejemplo: 2025-11-15): ");
            fecha = scanner.nextLine().trim();
            
            if (fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                fechaValida = true;
                System.out.println("  ✓ Fecha válida: " + fecha);
            } else {
                System.out.println("  ✗ Formato incorrecto. Use AAAA-MM-DD (ejemplo: 2025-11-15)");
            }
        }
        
        // ========== SOLICITAR HORARIOS ==========
        System.out.print("Hora de Inicio (HH:mm, ejemplo: 14:00): ");
        String horaInicio = scanner.nextLine();
        
        System.out.print("Hora de Fin (HH:mm, ejemplo: 16:00): ");
        String horaFin = scanner.nextLine();
        
        System.out.print("Monto Total: $");
        double montoTotal = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer
        
        // ========== VALIDAR CONFLICTO DE HORARIO ==========
        System.out.println("\nVerificando disponibilidad de horario...");
        if (modelo.existeConflictoHorario(idCancha, fecha, horaInicio, horaFin)) {
            System.out.println("\n✗ ERROR: Ya existe una reserva en ese horario");
            System.out.println("  Por favor, seleccione otro horario.");
            return false;
        }
        
        System.out.println("✓ Horario disponible");
        
        // ========== CREAR OBJETO RESERVA ==========
        Reserva nuevaReserva = new Reserva(
            idUsuario, 
            idCancha, 
            "Cliente", 
            fecha, 
            horaInicio, 
            horaFin, 
            "PENDIENTE", 
            montoTotal
        );
        
        // ========== INSERTAR EN BD ==========
        System.out.println("\nGuardando reserva en la base de datos...");
        int idGenerado = modelo.insertarReserva(nuevaReserva);
        
        if (idGenerado > 0) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║  ✓✓✓ RESERVA CREADA EXITOSAMENTE ✓✓✓ ║");
            System.out.println("╚═══════════════════════════════════════╝");
            System.out.println("    ID de Reserva: " + idGenerado);
            System.out.println("    Usuario ID: " + idUsuario);
            System.out.println("    Cancha ID: " + idCancha);
            System.out.println("    Fecha: " + fecha);
            System.out.println("    Horario: " + horaInicio + " - " + horaFin);
            System.out.println("    Estado: PENDIENTE");
            System.out.println("    Monto: $" + montoTotal);
            return true;
        } else {
            System.out.println("\n✗ Error al crear la reserva");
            return false;
        }
        
    } catch (Exception e) {
        System.err.println("\n✗ Error inesperado: " + e.getMessage());
        e.printStackTrace();
        scanner.nextLine(); // Limpiar buffer
        return false;
    }
}


    
    
    // ========== CONSULTAR RESERVAS ==========
    
    /**
     * Muestra TODAS las reservas de la base de datos
     */
    public void consultarTodasLasReservas() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("        TODAS LAS RESERVAS");
        System.out.println("═══════════════════════════════════════\n");
        
        ArrayList<Reserva> reservas = modelo.consultarTodasReservas();
        
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            for (Reserva r : reservas) {
                System.out.println(r);
            }
            System.out.println("\nTotal de reservas: " + reservas.size());
        }
    }
    
    /**
     * Muestra reservas filtradas por estado
     * 
     * @param estado Estado a filtrar (PENDIENTE, PAGADO, CANCELADO)
     */
    public void consultarReservasPorEstado(String estado) {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("   RESERVAS CON ESTADO: " + estado);
        System.out.println("═══════════════════════════════════════\n");
        
        ArrayList<Reserva> reservas = modelo.consultarReservasPorEstado(estado);
        
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas con estado: " + estado);
        } else {
            for (Reserva r : reservas) {
                System.out.println(r);
            }
            System.out.println("\nTotal: " + reservas.size() + " reservas");
        }
    }
    
    /**
     * Busca y muestra una reserva específica por ID
     */
    public void buscarReservaPorId() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("        BUSCAR RESERVA POR ID");
        System.out.println("═══════════════════════════════════════\n");
        
        try {
            System.out.print("Ingrese ID de Reserva: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            Reserva reserva = modelo.buscarReservaPorId(id);
            
            if (reserva != null) {
                System.out.println("\n" + reserva.toStringDetallado());
            } else {
                System.out.println("\n✗ No se encontró reserva con ID: " + id);
            }
            
        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }
    
    
    // ========== ACTUALIZAR RESERVAS ==========
    
    /**
     * Actualiza el estado de una reserva
     * Permite cambiar entre PENDIENTE, PAGADO, CANCELADO
     */
    public void actualizarEstadoReserva() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("      ACTUALIZAR ESTADO DE RESERVA");
        System.out.println("═══════════════════════════════════════\n");
        
        try {
            System.out.print("Ingrese ID de Reserva: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            // Primero verificar que existe
            Reserva reserva = modelo.buscarReservaPorId(id);
            if (reserva == null) {
                System.out.println("\n✗ No se encontró reserva con ID: " + id);
                return;
            }
            
            // Mostrar estado actual
            System.out.println("\nEstado actual: " + reserva.getEstado());
            
            // Solicitar nuevo estado
            System.out.println("\nNuevo estado:");
            System.out.println("1. PENDIENTE");
            System.out.println("2. PAGADO");
            System.out.println("3. CANCELADO");
            System.out.print("Seleccione opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            String nuevoEstado = "";
            switch (opcion) {
                case 1: nuevoEstado = "PENDIENTE"; break;
                case 2: nuevoEstado = "PAGADO"; break;
                case 3: nuevoEstado = "CANCELADO"; break;
                default:
                    System.out.println("✗ Opción inválida");
                    return;
            }
            
            // Actualizar en BD
            if (modelo.actualizarEstadoReserva(id, nuevoEstado)) {
                System.out.println("\n✓ Estado actualizado correctamente");
                System.out.println("  " + reserva.getEstado() + " → " + nuevoEstado);
            } else {
                System.out.println("\n✗ Error al actualizar el estado");
            }
            
        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }
    
    
    // ========== ELIMINAR RESERVA ==========
    
    /**
     * Elimina una reserva de la base de datos
     * Solicita confirmación antes de eliminar
     */
    public void eliminarReserva() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("          ELIMINAR RESERVA");
        System.out.println("═══════════════════════════════════════\n");
        
        try {
            System.out.print("Ingrese ID de Reserva a eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            // Verificar que existe
            Reserva reserva = modelo.buscarReservaPorId(id);
            if (reserva == null) {
                System.out.println("\n✗ No se encontró reserva con ID: " + id);
                return;
            }
            
            // Mostrar datos y pedir confirmación
            System.out.println("\n" + reserva);
            System.out.print("\n¿Está seguro de eliminar esta reserva? (S/N): ");
            String confirmacion = scanner.nextLine().toUpperCase();
            
            if (confirmacion.equals("S") || confirmacion.equals("SI")) {
                if (modelo.eliminarReserva(id)) {
                    System.out.println("\n✓ Reserva eliminada exitosamente");
                } else {
                    System.out.println("\n✗ Error al eliminar la reserva");
                }
            } else {
                System.out.println("\n✗ Operación cancelada");
            }
            
        } catch (Exception e) {
            System.err.println("✗ Error: " + e.getMessage());
            scanner.nextLine();
        }
    }
    
    
    // ========== REPORTES Y ESTADÍSTICAS ==========
    
    /**
     * Muestra estadísticas de reservas por estado
     */
    public void mostrarEstadisticas() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("        ESTADÍSTICAS DE RESERVAS");
        System.out.println("═══════════════════════════════════════\n");
        
        int pendientes = modelo.contarReservasPorEstado("PENDIENTE");
        int pagadas = modelo.contarReservasPorEstado("PAGADO");
        int canceladas = modelo.contarReservasPorEstado("CANCELADO");
        int total = pendientes + pagadas + canceladas;
        
        System.out.println("Total de Reservas: " + total);
        System.out.println("─────────────────────────────────────");
        System.out.println("PENDIENTES  : " + pendientes);
        System.out.println("PAGADAS     : " + pagadas);
        System.out.println("CANCELADAS  : " + canceladas);
        System.out.println("═══════════════════════════════════════");
    }
    
    
    // ========== MÉTODOS AUXILIARES ==========
    
    /**
     * Cierra recursos
     */
    public void cerrar() {
        if (scanner != null) {
            scanner.close();
        }
        ConexionBD.cerrarConexion();
    }
}
