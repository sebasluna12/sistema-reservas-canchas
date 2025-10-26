-- ==========================================
-- CONSULTAS ÚTILES - SISTEMA DE RESERVAS
-- Club Atlético Ñuñorco
-- ==========================================

USE `club_reservas`;

-- ==========================================
-- CONSULTAS DE CANCHAS
-- ==========================================

-- Listar todas las canchas disponibles
SELECT * FROM canchas 
WHERE estado = 'Disponible';

-- Listar canchas ordenadas por precio
SELECT cancha_id, nombre, tipo, cesped, precio_base, estado
FROM canchas
ORDER BY precio_base DESC;

-- Contar canchas por tipo
SELECT tipo, COUNT(*) as cantidad
FROM canchas
GROUP BY tipo;

-- Buscar canchas por tipo de césped
SELECT * FROM canchas
WHERE cesped = 'Natural';

-- ==========================================
-- CONSULTAS DE RESERVAS
-- ==========================================

-- Listar todas las reservas con información completa
SELECT 
    r.reserva_id,
    u.nombre AS cliente,
    c.nombre AS cancha,
    r.fecha,
    r.hora_inicio,
    r.hora_fin,
    r.estado,
    r.monto_total
FROM reservas r
JOIN usuarios u ON r.usuario_id = u.usuario_id
JOIN canchas c ON r.cancha_id = c.cancha_id
ORDER BY r.fecha DESC, r.hora_inicio;

-- Listar reservas pendientes de pago
SELECT 
    r.reserva_id,
    u.nombre AS cliente,
    u.email,
    c.nombre AS cancha,
    r.fecha,
    r.monto_total
FROM reservas r
JOIN usuarios u ON r.usuario_id = u.usuario_id
JOIN canchas c ON r.cancha_id = c.cancha_id
WHERE r.estado = 'PENDIENTE'
ORDER BY r.fecha;

-- Buscar reservas de un cliente específico
SELECT 
    r.reserva_id,
    c.nombre AS cancha,
    r.fecha,
    r.hora_inicio,
    r.hora_fin,
    r.estado,
    r.monto_total
FROM reservas r
JOIN canchas c ON r.cancha_id = c.cancha_id
WHERE r.usuario_id = 1
ORDER BY r.fecha DESC;

-- Reservas de hoy
SELECT 
    r.reserva_id,
    u.nombre AS cliente,
    c.nombre AS cancha,
    r.hora_inicio,
    r.hora_fin,
    r.estado
FROM reservas r
JOIN usuarios u ON r.usuario_id = u.usuario_id
JOIN canchas c ON r.cancha_id = c.cancha_id
WHERE r.fecha = CURDATE()
ORDER BY r.hora_inicio;

-- Reservas de la semana actual
SELECT 
    r.reserva_id,
    u.nombre AS cliente,
    c.nombre AS cancha,
    r.fecha,
    r.hora_inicio,
    r.estado,
    r.monto_total
FROM reservas r
JOIN usuarios u ON r.usuario_id = u.usuario_id
JOIN canchas c ON r.cancha_id = c.cancha_id
WHERE YEARWEEK(r.fecha, 1) = YEARWEEK(CURDATE(), 1)
ORDER BY r.fecha, r.hora_inicio;

-- ==========================================
-- CONSULTAS DE PAGOS
-- ==========================================

-- Listar todos los pagos con información de la reserva
SELECT 
    p.pago_id,
    p.comprobante,
    u.nombre AS cliente,
    c.nombre AS cancha,
    r.fecha,
    p.monto,
    p.metodo_pago,
    p.fecha_pago
FROM pagos p
JOIN reservas r ON p.reserva_id = r.reserva_id
JOIN usuarios u ON r.usuario_id = u.usuario_id
JOIN canchas c ON r.cancha_id = c.cancha_id
ORDER BY p.fecha_pago DESC;

-- Total recaudado por método de pago
SELECT 
    metodo_pago,
    COUNT(*) as cantidad_pagos,
    SUM(monto) as total_recaudado
FROM pagos
GROUP BY metodo_pago;

-- Pagos del mes actual
SELECT 
    p.pago_id,
    u.nombre AS cliente,
    p.monto,
    p.metodo_pago,
    p.fecha_pago
FROM pagos p
JOIN reservas r ON p.reserva_id = r.reserva_id
JOIN usuarios u ON r.usuario_id = u.usuario_id
WHERE MONTH(p.fecha_pago) = MONTH(CURDATE())
  AND YEAR(p.fecha_pago) = YEAR(CURDATE())
ORDER BY p.fecha_pago DESC;

-- ==========================================
-- CONSULTAS DE USUARIOS
-- ==========================================

-- Listar todos los clientes activos
SELECT 
    usuario_id,
    nombre,
    email,
    nro_socio,
    fecha_registro
FROM usuarios
WHERE tipo_usuario = 'Cliente'
ORDER BY nombre;

-- Cliente con más reservas
SELECT 
    u.nombre,
    u.email,
    COUNT(r.reserva_id) as total_reservas,
    SUM(r.monto_total) as gasto_total
FROM usuarios u
JOIN reservas r ON u.usuario_id = r.usuario_id
GROUP BY u.usuario_id, u.nombre, u.email
ORDER BY total_reservas DESC
LIMIT 1;

-- ==========================================
-- CONSULTAS ESTADÍSTICAS
-- ==========================================

-- Resumen de ingresos por estado de reserva
SELECT 
    estado,
    COUNT(*) as cantidad_reservas,
    SUM(monto_total) as total_monto
FROM reservas
GROUP BY estado;

-- Ingresos totales confirmados (pagados)
SELECT 
    SUM(monto_total) as ingresos_totales
FROM reservas
WHERE estado = 'PAGADO';

-- Cancha más reservada
SELECT 
    c.nombre,
    c.tipo,
    COUNT(r.reserva_id) as veces_reservada,
    SUM(r.monto_total) as ingresos_generados
FROM canchas c
LEFT JOIN reservas r ON c.cancha_id = r.cancha_id
GROUP BY c.cancha_id, c.nombre, c.tipo
ORDER BY veces_reservada DESC;

-- Horarios más demandados
SELECT 
    HOUR(hora_inicio) as hora,
    COUNT(*) as cantidad_reservas
FROM reservas
GROUP BY HOUR(hora_inicio)
ORDER BY cantidad_reservas DESC;

-- Promedio de monto por reserva
SELECT 
    AVG(monto_total) as promedio_monto,
    MIN(monto_total) as monto_minimo,
    MAX(monto_total) as monto_maximo
FROM reservas
WHERE estado != 'CANCELADO';

-- Reservas por mes (último año)
SELECT 
    YEAR(fecha) as año,
    MONTH(fecha) as mes,
    COUNT(*) as cantidad_reservas,
    SUM(monto_total) as ingresos
FROM reservas
WHERE fecha >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH)
GROUP BY YEAR(fecha), MONTH(fecha)
ORDER BY año DESC, mes DESC;

-- ==========================================
-- CONSULTAS DE DISPONIBILIDAD
-- ==========================================

-- Verificar disponibilidad de una cancha en fecha y horario específico
-- (Ejemplo: Cancha 1, fecha 2025-10-30, hora 14:00 a 15:00)
SELECT 
    c.nombre,
    c.estado,
    CASE 
        WHEN c.estado != 'Disponible' THEN 'Cancha en mantenimiento'
        WHEN EXISTS (
            SELECT 1 FROM reservas r
            WHERE r.cancha_id = 1
              AND r.fecha = '2025-10-30'
              AND r.estado != 'CANCELADO'
              AND (
                  (r.hora_inicio <= '14:00:00' AND r.hora_fin > '14:00:00')
                  OR (r.hora_inicio < '15:00:00' AND r.hora_fin >= '15:00:00')
                  OR (r.hora_inicio >= '14:00:00' AND r.hora_fin <= '15:00:00')
              )
        ) THEN 'No disponible - Ya reservada'
        ELSE 'Disponible'
    END as disponibilidad
FROM canchas c
WHERE c.cancha_id = 1;

-- Horarios libres de una cancha en una fecha específica
-- (Mostrar qué horarios NO están ocupados)
SELECT DISTINCT
    HOUR(r.hora_fin) as hora_libre
FROM reservas r
WHERE r.cancha_id = 1
  AND r.fecha = '2025-10-26'
  AND r.estado != 'CANCELADO'
  AND HOUR(r.hora_fin) < 22
ORDER BY hora_libre;

-- ==========================================
-- CONSULTAS DE MANTENIMIENTO
-- ==========================================

-- Verificar integridad: Reservas sin pago pero con estado PAGADO
SELECT 
    r.reserva_id,
    r.usuario_id,
    r.estado,
    r.monto_total
FROM reservas r
LEFT JOIN pagos p ON r.reserva_id = p.reserva_id
WHERE r.estado = 'PAGADO' AND p.pago_id IS NULL;

-- Usuarios sin reservas
SELECT 
    u.usuario_id,
    u.nombre,
    u.email,
    u.tipo_usuario
FROM usuarios u
LEFT JOIN reservas r ON u.usuario_id = r.usuario_id
WHERE r.reserva_id IS NULL
  AND u.tipo_usuario = 'Cliente';

-- Reservas antiguas sin pagar (más de 7 días)
SELECT 
    r.reserva_id,
    u.nombre AS cliente,
    u.email,
    r.fecha,
    r.monto_total,
    DATEDIFF(CURDATE(), r.fecha) as dias_vencidos
FROM reservas r
JOIN usuarios u ON r.usuario_id = u.usuario_id
WHERE r.estado = 'PENDIENTE'
  AND r.fecha < DATE_SUB(CURDATE(), INTERVAL 7 DAY)
ORDER BY r.fecha;

-- ==========================================
-- NOTAS:
-- 
-- Estas consultas son ejemplos útiles para:
-- - Gestión diaria del sistema
-- - Análisis de datos
-- - Generación de reportes
-- - Verificación de integridad
-- 
-- Modificar los parámetros según sea necesario
-- (fechas, IDs, rangos, etc.)
-- ==========================================
