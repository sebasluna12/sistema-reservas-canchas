package conexionBD;

import java.sql.*;
import java.util.ArrayList;

/**
 * Clase ModeloGestionReservas - Patrón MVC (MODELO)
 * 
 * Gestiona todas las operaciones CRUD sobre la tabla reservas
 * Usa JDBC y PreparedStatement para prevenir SQL Injection
 * 
 * @author Sebastian Luna
 * @version 2.0 (TP4 - Con MySQL)
 */
public class ModeloGestionReservas {
    
    // ========== CONSTRUCTOR ==========
    
    public ModeloGestionReservas() {
        // Constructor vacío
    }
    
    
    // ========== CRUD: CREATE (INSERTAR) ==========
    
    /**
     * Inserta una nueva reserva en la base de datos
     * 
     * @param reserva Objeto Reserva con los datos
     * @return ID de la reserva creada, o -1 si hubo error
     */
    public int insertarReserva(Reserva reserva) {
        String sql = "INSERT INTO reservas (usuario_id, cancha_id, fecha, hora_inicio, hora_fin, estado, monto_total) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Configurar parámetros
            pstmt.setInt(1, reserva.getIdUsuario());
            pstmt.setInt(2, reserva.getIdCancha());
            pstmt.setString(3, reserva.getFecha());
            pstmt.setString(4, reserva.getHoraInicio());
            pstmt.setString(5, reserva.getHoraFin());
            pstmt.setString(6, reserva.getEstado());
            pstmt.setDouble(7, reserva.getMontoTotal());
            
            // Ejecutar inserción
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Obtener el ID generado automáticamente
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    reserva.setIdReserva(idGenerado);
                    System.out.println("✓ Reserva insertada exitosamente con ID: " + idGenerado);
                    return idGenerado;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al insertar reserva: " + e.getMessage());
            e.printStackTrace();
        }
        
        return -1;
    }
    
    
    // ========== CRUD: READ (CONSULTAR) ==========
    
    /**
     * Consulta TODAS las reservas con información del usuario (JOIN)
     * 
     * @return ArrayList con todas las reservas
     */
    public ArrayList<Reserva> consultarTodasReservas() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        
        String sql = "SELECT r.reserva_id, r.usuario_id, r.cancha_id, " +
                     "u.nombre AS nombre_cliente, r.fecha, " +
                     "r.hora_inicio, r.hora_fin, r.estado, r.monto_total " +
                     "FROM reservas r " +
                     "INNER JOIN usuarios u ON r.usuario_id = u.usuario_id " +
                     "ORDER BY r.fecha DESC, r.hora_inicio";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Reserva reserva = new Reserva(
                    rs.getInt("reserva_id"),
                    rs.getInt("usuario_id"),
                    rs.getInt("cancha_id"),
                    rs.getString("nombre_cliente"),
                    rs.getString("fecha"),
                    rs.getString("hora_inicio"),
                    rs.getString("hora_fin"),
                    rs.getString("estado"),
                    rs.getDouble("monto_total")
                );
                reservas.add(reserva);
            }
            
            System.out.println("✓ Se consultaron " + reservas.size() + " reservas");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al consultar reservas: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reservas;
    }
    
    /**
     * Consulta reservas por estado específico
     * 
     * @param estado Estado a buscar (PENDIENTE, PAGADO, CANCELADO)
     * @return ArrayList con reservas del estado especificado
     */
    public ArrayList<Reserva> consultarReservasPorEstado(String estado) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        
        String sql = "SELECT r.reserva_id, r.usuario_id, r.cancha_id, " +
                     "u.nombre AS nombre_cliente, r.fecha, " +
                     "r.hora_inicio, r.hora_fin, r.estado, r.monto_total " +
                     "FROM reservas r " +
                     "INNER JOIN usuarios u ON r.usuario_id = u.usuario_id " +
                     "WHERE r.estado = ? " +
                     "ORDER BY r.fecha DESC";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, estado);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reserva reserva = new Reserva(
                        rs.getInt("reserva_id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("cancha_id"),
                        rs.getString("nombre_cliente"),
                        rs.getString("fecha"),
                        rs.getString("hora_inicio"),
                        rs.getString("hora_fin"),
                        rs.getString("estado"),
                        rs.getDouble("monto_total")
                    );
                    reservas.add(reserva);
                }
            }
            
            System.out.println("✓ Se encontraron " + reservas.size() + " reservas con estado: " + estado);
            
        } catch (SQLException e) {
            System.err.println("✗ Error al consultar reservas por estado: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reservas;
    }
    
    /**
     * Busca una reserva específica por ID
     * 
     * @param idReserva ID de la reserva a buscar
     * @return Objeto Reserva, o null si no se encontró
     */
    public Reserva buscarReservaPorId(int idReserva) {
        String sql = "SELECT r.reserva_id, r.usuario_id, r.cancha_id, " +
                     "u.nombre AS nombre_cliente, r.fecha, " +
                     "r.hora_inicio, r.hora_fin, r.estado, r.monto_total " +
                     "FROM reservas r " +
                     "INNER JOIN usuarios u ON r.usuario_id = u.usuario_id " +
                     "WHERE r.reserva_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idReserva);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Reserva reserva = new Reserva(
                        rs.getInt("reserva_id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("cancha_id"),
                        rs.getString("nombre_cliente"),
                        rs.getString("fecha"),
                        rs.getString("hora_inicio"),
                        rs.getString("hora_fin"),
                        rs.getString("estado"),
                        rs.getDouble("monto_total")
                    );
                    System.out.println("✓ Reserva encontrada: ID " + idReserva);
                    return reserva;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar reserva: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("✗ No se encontró reserva con ID: " + idReserva);
        return null;
    }
    
    
    // ========== CRUD: UPDATE (ACTUALIZAR) ==========
    
    /**
     * Actualiza el estado de una reserva
     * 
     * @param idReserva ID de la reserva a actualizar
     * @param nuevoEstado Nuevo estado (PENDIENTE, PAGADO, CANCELADO)
     * @return true si se actualizó correctamente, false si hubo error
     */
    public boolean actualizarEstadoReserva(int idReserva, String nuevoEstado) {
        String sql = "UPDATE reservas SET estado = ? WHERE reserva_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, idReserva);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Estado actualizado a: " + nuevoEstado);
                return true;
            } else {
                System.out.println("✗ No se encontró reserva con ID: " + idReserva);
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar estado: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Actualiza TODOS los datos de una reserva
     * 
     * @param reserva Objeto Reserva con los datos actualizados
     * @return true si se actualizó correctamente, false si hubo error
     */
    public boolean actualizarReserva(Reserva reserva) {
        String sql = "UPDATE reservas SET usuario_id = ?, cancha_id = ?, fecha = ?, " +
                     "hora_inicio = ?, hora_fin = ?, estado = ?, monto_total = ? " +
                     "WHERE reserva_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, reserva.getIdUsuario());
            pstmt.setInt(2, reserva.getIdCancha());
            pstmt.setString(3, reserva.getFecha());
            pstmt.setString(4, reserva.getHoraInicio());
            pstmt.setString(5, reserva.getHoraFin());
            pstmt.setString(6, reserva.getEstado());
            pstmt.setDouble(7, reserva.getMontoTotal());
            pstmt.setInt(8, reserva.getIdReserva());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Reserva actualizada correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar reserva: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    
    // ========== CRUD: DELETE (ELIMINAR) ==========
    
    /**
     * Elimina una reserva de la base de datos
     * 
     * @param idReserva ID de la reserva a eliminar
     * @return true si se eliminó correctamente, false si hubo error
     */
    public boolean eliminarReserva(int idReserva) {
        String sql = "DELETE FROM reservas WHERE reserva_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idReserva);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Reserva eliminada correctamente");
                return true;
            } else {
                System.out.println("✗ No se encontró reserva con ID: " + idReserva);
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar reserva: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    
    // ========== MÉTODOS DE VALIDACIÓN ==========
    
    /**
     * Verifica si existe un conflicto de horario
     * 
     * @param idCancha ID de la cancha
     * @param fecha Fecha de la reserva
     * @param horaInicio Hora de inicio
     * @param horaFin Hora de fin
     * @return true si HAY conflicto, false si NO hay conflicto
     */
    public boolean existeConflictoHorario(int idCancha, String fecha, String horaInicio, String horaFin) {
        String sql = "SELECT COUNT(*) AS conflictos " +
                     "FROM reservas " +
                     "WHERE cancha_id = ? AND fecha = ? AND estado != 'CANCELADO' " +
                     "AND (" +
                     "  (hora_inicio <= ? AND hora_fin > ?) OR " +
                     "  (hora_inicio < ? AND hora_fin >= ?) OR " +
                     "  (hora_inicio >= ? AND hora_fin <= ?)" +
                     ")";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCancha);
            pstmt.setString(2, fecha);
            pstmt.setString(3, horaInicio);
            pstmt.setString(4, horaInicio);
            pstmt.setString(5, horaFin);
            pstmt.setString(6, horaFin);
            pstmt.setString(7, horaInicio);
            pstmt.setString(8, horaFin);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int conflictos = rs.getInt("conflictos");
                    if (conflictos > 0) {
                        System.out.println("✗ CONFLICTO: Ya existe una reserva en ese horario");
                        return true;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al verificar conflicto: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Verifica si existe un usuario con el ID especificado
     * 
     * @param idUsuario ID del usuario a verificar
     * @return true si existe, false si no existe
     */
    public boolean existeUsuario(int idUsuario) {
        String sql = "SELECT COUNT(*) AS total FROM usuarios WHERE usuario_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al verificar usuario: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Verifica si existe una cancha con el ID especificado
     * 
     * @param idCancha ID de la cancha a verificar
     * @return true si existe, false si no existe
     */
    public boolean existeCancha(int idCancha) {
        String sql = "SELECT COUNT(*) AS total FROM canchas WHERE cancha_id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCancha);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al verificar cancha: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Obtiene la lista de IDs de usuarios disponibles
     * 
     * @return ArrayList con IDs de usuarios
     */
    public ArrayList<Integer> listarIdsUsuarios() {
        ArrayList<Integer> ids = new ArrayList<>();
        String sql = "SELECT usuario_id FROM usuarios ORDER BY usuario_id";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                ids.add(rs.getInt("usuario_id"));
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al listar usuarios: " + e.getMessage());
        }
        
        return ids;
    }
    
    /**
     * Obtiene la lista de IDs de canchas disponibles
     * 
     * @return ArrayList con IDs de canchas
     */
    public ArrayList<Integer> listarIdsCanchas() {
        ArrayList<Integer> ids = new ArrayList<>();
        String sql = "SELECT cancha_id FROM canchas WHERE estado = 'Disponible' ORDER BY cancha_id";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                ids.add(rs.getInt("cancha_id"));
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al listar canchas: " + e.getMessage());
        }
        
        return ids;
    }
    
    /**
     * Cuenta cuántas reservas hay en un estado específico
     * 
     * @param estado Estado a contar
     * @return Cantidad de reservas con ese estado
     */
    public int contarReservasPorEstado(String estado) {
        String sql = "SELECT COUNT(*) AS total FROM reservas WHERE estado = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, estado);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al contar reservas: " + e.getMessage());
        }
        
        return 0;
    }
}
