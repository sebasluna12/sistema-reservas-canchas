package gestionreserva;

import java.util.Scanner;

/**
 * Clase principal que gestiona el menú del sistema de reservas
 */
public class MenuReservas {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionReservas gestion = new GestionReservas();
        
        // Contador para IDs automáticos
        int contadorReservas = 1;
        int contadorCanchas = 1;
        
        while (true) {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║   SISTEMA DE RESERVAS - CLUB ÑUÑORCO      ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println("1. Agregar Cancha");
            System.out.println("2. Consultar Disponibilidad");
            System.out.println("3. Reservar Cancha");
            System.out.println("4. Pagar Reserva");
            System.out.println("5. Cancelar Reserva");
            System.out.println("6. Listar Reservas");
            System.out.println("7. Salir");
            System.out.println("════════════════════════════════════════════");
            System.out.print("Elija una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            
            switch (opcion) {
                case 1:
                    // Agregar Cancha
                    System.out.println("\n--- AGREGAR CANCHA ---");
                    System.out.print("Nombre de la cancha: ");
                    String nombre = scanner.nextLine();
                    
                    System.out.print("Tipo (Futbol 5 / Futbol 7 / Futbol 11): ");
                    String tipo = scanner.nextLine();
                    
                    System.out.print("Precio base por hora: $");
                    double precioBase = scanner.nextDouble();
                    scanner.nextLine();
                    
                    System.out.print("Tipo de césped (Natural / Sintético): ");
                    String cesped = scanner.nextLine();
                    
                    System.out.print("Tamaño (Futbol 5 / Futbol 7 / Futbol 11): ");
                    String tamanio = scanner.nextLine();
                    
                    CanchaFutbol cancha = new CanchaFutbol(contadorCanchas, nombre, tipo, 
                                                           precioBase, cesped, tamanio);
                    gestion.agregarCancha(cancha);
                    contadorCanchas++;
                    
                    System.out.println();
                    break;
                    
                case 2:
                    // Consultar Disponibilidad
                    System.out.println("\n--- CONSULTAR DISPONIBILIDAD ---");
                    gestion.listarCanchasDisponibles();
                    break;
                    
                case 3:
                    // Reservar Cancha
                    System.out.println("\n--- RESERVAR CANCHA ---");
                    
                    if (!gestion.hayCanchas()) {
                        System.out.println("No hay canchas registradas. Agregue canchas primero.\n");
                        break;
                    }
                    
                    gestion.mostrarTodasLasCanchas();
                    
                    try {
                        System.out.print("ID de la cancha a reservar: ");
                        int idCancha = scanner.nextInt();
                        scanner.nextLine();
                        
                        System.out.print("Nombre del cliente: ");
                        String nombreCliente = scanner.nextLine();
                        
                        System.out.print("Fecha (DD/MM/YYYY): ");
                        String fecha = scanner.nextLine();
                        
                        System.out.print("Hora de inicio (formato 24hs, ej: 14): ");
                        int horaInicio = scanner.nextInt();
                        
                        System.out.print("Hora de fin (formato 24hs, ej: 16): ");
                        int horaFin = scanner.nextInt();
                        scanner.nextLine();
                        
                        Reserva reserva = new Reserva(contadorReservas, idCancha, nombreCliente,
                                                      fecha, horaInicio, horaFin);
                        
                        gestion.agregarReserva(reserva);
                        contadorReservas++;
                        
                    } catch (ReservaExcepcion e) {
                        System.out.println(e.getMessage());
                    }
                    
                    System.out.println();
                    break;
                    
                case 4:
                    // Pagar Reserva
                    System.out.println("\n--- PAGAR RESERVA ---");
                    gestion.listarReservas();
                    
                    try {
                        System.out.print("ID de la reserva a pagar: ");
                        int idReservaPagar = scanner.nextInt();
                        scanner.nextLine();
                        
                        gestion.pagarReserva(idReservaPagar);
                        
                    } catch (ReservaExcepcion e) {
                        System.out.println(e.getMessage());
                    }
                    
                    System.out.println();
                    break;
                    
                case 5:
                    // Cancelar Reserva
                    System.out.println("\n--- CANCELAR RESERVA ---");
                    gestion.listarReservas();
                    
                    try {
                        System.out.print("ID de la reserva a cancelar: ");
                        int idReservaCancelar = scanner.nextInt();
                        scanner.nextLine();
                        
                        gestion.cancelarReserva(idReservaCancelar);
                        
                    } catch (ReservaExcepcion e) {
                        System.out.println(e.getMessage());
                    }
                    
                    System.out.println();
                    break;
                    
                case 6:
                    // Listar Reservas
                    System.out.println("\n--- LISTAR RESERVAS ---");
                    gestion.listarReservas();
                    break;
                    
                case 7:
                    // Salir
                    System.out.println("\n¡Gracias por usar el sistema! Hasta pronto.");
                    scanner.close();
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("\nOpción inválida. Intente nuevamente.\n");
            }
        }
    }
}