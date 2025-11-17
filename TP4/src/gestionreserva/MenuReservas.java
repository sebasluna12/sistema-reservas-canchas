package gestionreserva;

import java.util.Scanner;

/**
 * Clase MenuReservas - Patrón MVC (VISTA)
 * 
 * Gestiona la interfaz de usuario del sistema de reservas
 * Muestra menús y delega operaciones al Controlador (GestionReservas)
 * 
 * Adaptada del TP3 al TP4 con MySQL
 * 
 * @author Sebastian Luna
 * @version 2.0 (TP4 - Con MySQL)
 */
public class MenuReservas {
    
    // ========== ATRIBUTOS ==========
    
    /**
     * Controlador que gestiona la lógica de negocio
     */
    private GestionReservas controlador;
    
    /**
     * Scanner para entrada de usuario
     */
    private Scanner scanner;
    
    
    // ========== CONSTRUCTOR ==========
    
    /**
     * Constructor de la vista
     * Inicializa el controlador y el scanner
     */
    public MenuReservas() {
        this.controlador = new GestionReservas();
        this.scanner = new Scanner(System.in);
    }
    
    
    // ========== MENÚ PRINCIPAL ==========
    
    /**
     * Muestra el menú principal y gestiona las opciones
     */
    public void mostrarMenuPrincipal() {
        int opcion = 0;
        
        do {
            mostrarBanner();
            mostrarOpciones();
            
            try {
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                procesarOpcion(opcion);
                
                if (opcion != 0) {
                    pausar();
                }
                
            } catch (Exception e) {
                System.err.println("\n✗ Error: Por favor ingrese un número válido");
                scanner.nextLine(); // Limpiar buffer
                pausar();
            }
            
        } while (opcion != 0);
        
        despedida();
    }
    
    
    // ========== MOSTRAR ELEMENTOS DEL MENÚ ==========
    
    /**
     * Muestra el banner del sistema
     */
    private void mostrarBanner() {
        limpiarPantalla();
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║                                                   ║");
        System.out.println("║     SISTEMA DE GESTIÓN DE RESERVAS DE CANCHAS    ║");
        System.out.println("║            Club Atlético Ñuñorco                  ║");
        System.out.println("║                                                   ║");
        System.out.println("║              Versión 2.0 - Con MySQL             ║");
        System.out.println("║                                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
    }
    
    /**
     * Muestra las opciones del menú
     */
    private void mostrarOpciones() {
        System.out.println("┌───────────────────────────────────────────────────┐");
        System.out.println("│             MENÚ DE RESERVAS                      │");
        System.out.println("├───────────────────────────────────────────────────┤");
        System.out.println("│  1. Crear Nueva Reserva                           │");
        System.out.println("│  2. Consultar Todas las Reservas                  │");
        System.out.println("│  3. Consultar Reservas por Estado                 │");
        System.out.println("│  4. Buscar Reserva por ID                         │");
        System.out.println("│  5. Actualizar Estado de Reserva                  │");
        System.out.println("│  6. Eliminar Reserva                              │");
        System.out.println("│  7. Ver Estadísticas                              │");
        System.out.println("│  0. Salir                                         │");
        System.out.println("└───────────────────────────────────────────────────┘");
        System.out.println();
    }
    
    
    // ========== PROCESAR OPCIONES ==========
    
    /**
     * Procesa la opción seleccionada por el usuario
     * 
     * @param opcion Número de opción seleccionada
     */
    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                controlador.crearNuevaReserva();
                break;
                
            case 2:
                controlador.consultarTodasLasReservas();
                break;
                
            case 3:
                consultarPorEstado();
                break;
                
            case 4:
                controlador.buscarReservaPorId();
                break;
                
            case 5:
                controlador.actualizarEstadoReserva();
                break;
                
            case 6:
                controlador.eliminarReserva();
                break;
                
            case 7:
                controlador.mostrarEstadisticas();
                break;
                
            case 0:
                // Salir (se maneja en el do-while)
                break;
                
            default:
                System.out.println("\n✗ Opción inválida. Por favor seleccione una opción del 0 al 7.");
        }
    }
    
    
    // ========== MÉTODOS AUXILIARES DE MENÚ ==========
    
    /**
     * Submenú para consultar reservas por estado
     */
    private void consultarPorEstado() {
        System.out.println("\n┌───────────────────────────────────────┐");
        System.out.println("│   CONSULTAR RESERVAS POR ESTADO       │");
        System.out.println("├───────────────────────────────────────┤");
        System.out.println("│  1. PENDIENTES                        │");
        System.out.println("│  2. PAGADAS                           │");
        System.out.println("│  3. CANCELADAS                        │");
        System.out.println("└───────────────────────────────────────┘");
        System.out.print("\nSeleccione estado: ");
        
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            String estado = "";
            switch (opcion) {
                case 1: estado = "PENDIENTE"; break;
                case 2: estado = "PAGADO"; break;
                case 3: estado = "CANCELADO"; break;
                default:
                    System.out.println("✗ Opción inválida");
                    return;
            }
            
            controlador.consultarReservasPorEstado(estado);
            
        } catch (Exception e) {
            System.err.println("✗ Error: Ingrese un número válido");
            scanner.nextLine();
        }
    }
    
    
    // ========== MÉTODOS DE UTILIDAD ==========
    
    /**
     * Limpia la pantalla de la consola
     */
    private void limpiarPantalla() {
        try {
            // Para Windows
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Para Linux/Mac
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Si falla, simplemente imprimir líneas en blanco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    /**
     * Pausa la ejecución hasta que el usuario presione ENTER
     */
    private void pausar() {
        System.out.println("\n─────────────────────────────────────────────────────");
        System.out.print("Presione ENTER para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Muestra mensaje de despedida y cierra recursos
     */
    private void despedida() {
        limpiarPantalla();
        System.out.println("\n╔═══════════════════════════════════════════════════╗");
        System.out.println("║                                                   ║");
        System.out.println("║          ¡GRACIAS POR USAR EL SISTEMA!            ║");
        System.out.println("║                                                   ║");
        System.out.println("║            Club Atlético Ñuñorco                  ║");
        System.out.println("║                                                   ║");
        System.out.println("║     Sistema desarrollado por Sebastian Luna       ║");
        System.out.println("║          Universidad Siglo 21 - 2025              ║");
        System.out.println("║                                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
        
        // Cerrar recursos
        controlador.cerrar();
        scanner.close();
    }
    
    
    // ========== MÉTODO MAIN ==========
    
    /**
     * Método principal para ejecutar el sistema
     * 
     * @param args Argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        // Crear instancia del menú
        MenuReservas menu = new MenuReservas();
        
        // Mostrar menú principal
        menu.mostrarMenuPrincipal();
    }
}
