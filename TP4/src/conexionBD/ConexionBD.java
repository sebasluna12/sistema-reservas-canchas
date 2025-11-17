
package conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    private static Connection conexion = null;
    
    // CONFIGURACIÓN DE CONEXIÓN
    private static final String URL = "jdbc:mysql://localhost:3307/club_reservas?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private ConexionBD() {
        // Constructor privado
    }
    
    public static Connection getConexion() throws SQLException {
        try {
            if (conexion == null || conexion.isClosed()) {
                
                // Cargar driver
                Class.forName(DRIVER);
                
                System.out.println("Intentando conectar a MySQL...");
                System.out.println("URL: " + URL);
                System.out.println("Usuario: " + USUARIO);
                
                // Establecer conexión
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                
                System.out.println("✓ Conexión exitosa a MySQL");
            }
            
            return conexion;
            
        } catch (ClassNotFoundException e) {
            System.err.println("✗ ERROR: Driver MySQL no encontrado");
            System.err.println("  Verificar mysql-connector-j en Libraries");
            throw new SQLException("Driver no encontrado", e);
            
        } catch (SQLException e) {
            System.err.println("✗ ERROR: No se pudo conectar a MySQL");
            System.err.println("  Detalles: " + e.getMessage());
            System.err.println("\nVERIFICACIONES:");
            System.err.println("  1. ¿XAMPP está corriendo?");
            System.err.println("  2. ¿MySQL está en verde en XAMPP?");
            System.err.println("  3. ¿phpMyAdmin abre en el navegador?");
            System.err.println("  4. ¿Existe la BD 'club_reservas'?");
            throw e;
        }
    }
    
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("✓ Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al cerrar: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  TEST DE CONEXION A MYSQL");
        System.out.println("==============================================\n");
        
        try {
            Connection conn = ConexionBD.getConexion();
            
            if (conn != null) {
                System.out.println("\n*** CONEXION EXITOSA ***");
                System.out.println("Estado: Conectado a club_reservas");
                ConexionBD.cerrarConexion();
            }
            
        } catch (SQLException e) {
            System.err.println("\n*** ERROR DE CONEXION ***");
        }
        
        System.out.println("\n==============================================");
    }
}
