-- ==========================================
-- SISTEMA DE GESTIÓN DE RESERVAS DE CANCHAS
-- Club Atlético Ñuñorco
-- ==========================================
-- Autor: Sebastian Miguel Luna
-- Legajo: VINF04827
-- Universidad: Siglo 21
-- Fecha: Octubre 2025
-- ==========================================

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- ==========================================
-- CREACIÓN DE BASE DE DATOS
-- ==========================================

CREATE DATABASE IF NOT EXISTS `club_reservas`;
USE `club_reservas`;

-- ==========================================
-- TABLA: CANCHAS
-- Almacena información de las canchas deportivas
-- ==========================================

CREATE TABLE `canchas` (
  `cancha_id` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `cesped` varchar(50) DEFAULT NULL,
  `precio_base` decimal(10,2) DEFAULT NULL,
  `estado` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Datos de ejemplo para canchas
INSERT INTO `canchas` (`cancha_id`, `nombre`, `tipo`, `cesped`, `precio_base`, `estado`) VALUES
(1, 'Cancha Principal', 'Futbol 11', 'Natural', 8000.00, 'Disponible'),
(2, 'Cancha A', 'Futbol 7', 'Sintético', 5000.00, 'Disponible'),
(3, 'Cancha B', 'Futbol 5', 'Sintético', 3500.00, 'Disponible'),
(4, 'Cancha C', 'Futbol 5', 'Natural', 4000.00, 'Mantenimiento');

-- ==========================================
-- TABLA: USUARIOS
-- Almacena clientes y administradores del sistema
-- ==========================================

CREATE TABLE `usuarios` (
  `usuario_id` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `tipo_usuario` varchar(20) DEFAULT NULL,
  `nro_socio` varchar(50) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Datos de ejemplo para usuarios
INSERT INTO `usuarios` (`usuario_id`, `nombre`, `email`, `password`, `tipo_usuario`, `nro_socio`, `fecha_registro`) VALUES
(1, 'Juan Pérez', 'juan.perez@email.com', 'pass123', 'Cliente', 'SOC001', '2024-01-15 10:30:00'),
(2, 'María García', 'maria.garcia@email.com', 'pass456', 'Cliente', 'SOC002', '2024-02-20 14:15:00'),
(3, 'Carlos Admin', 'admin@clubnunorco.com', 'admin123', 'Administrador', NULL, '2023-12-01 09:00:00');

-- ==========================================
-- TABLA: RESERVAS
-- Almacena las reservas de canchas realizadas por los usuarios
-- ==========================================

CREATE TABLE `reservas` (
  `reserva_id` int(11) NOT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  `cancha_id` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora_inicio` time DEFAULT NULL,
  `hora_fin` time DEFAULT NULL,
  `estado` varchar(20) DEFAULT NULL,
  `monto_total` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Datos de ejemplo para reservas
INSERT INTO `reservas` (`reserva_id`, `usuario_id`, `cancha_id`, `fecha`, `hora_inicio`, `hora_fin`, `estado`, `monto_total`) VALUES
(1, 1, 2, '2025-10-25', '14:00:00', '15:00:00', 'PAGADO', 5000.00),
(2, 1, 3, '2025-10-28', '16:00:00', '17:00:00', 'PENDIENTE', 3500.00),
(3, 2, 1, '2025-10-26', '18:00:00', '20:00:00', 'PAGADO', 16000.00),
(4, 2, 2, '2025-11-02', '10:00:00', '11:00:00', 'PENDIENTE', 5000.00);

-- ==========================================
-- TABLA: PAGOS
-- Registra los pagos realizados por las reservas
-- ==========================================

CREATE TABLE `pagos` (
  `pago_id` int(11) NOT NULL,
  `reserva_id` int(11) DEFAULT NULL,
  `monto` decimal(10,2) DEFAULT NULL,
  `metodo_pago` varchar(50) DEFAULT NULL,
  `fecha_pago` datetime DEFAULT NULL,
  `comprobante` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Datos de ejemplo para pagos
INSERT INTO `pagos` (`pago_id`, `reserva_id`, `monto`, `metodo_pago`, `fecha_pago`, `comprobante`) VALUES
(1, 1, 5000.00, 'Transferencia', '2025-10-20 15:30:00', 'COMP-001'),
(2, 3, 16000.00, 'Efectivo', '2025-10-20 11:20:00', 'COMP-002');

-- ==========================================
-- ÍNDICES Y CLAVES PRIMARIAS
-- ==========================================

-- Índices para la tabla canchas
ALTER TABLE `canchas`
  ADD PRIMARY KEY (`cancha_id`);

-- Índices para la tabla usuarios
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`usuario_id`),
  ADD UNIQUE KEY `email` (`email`);

-- Índices para la tabla reservas
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`reserva_id`),
  ADD KEY `usuario_id` (`usuario_id`),
  ADD KEY `cancha_id` (`cancha_id`);

-- Índices para la tabla pagos
ALTER TABLE `pagos`
  ADD PRIMARY KEY (`pago_id`),
  ADD KEY `reserva_id` (`reserva_id`);

-- ==========================================
-- AUTO_INCREMENT
-- ==========================================

ALTER TABLE `canchas`
  MODIFY `cancha_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `usuarios`
  MODIFY `usuario_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `reservas`
  MODIFY `reserva_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `pagos`
  MODIFY `pago_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

-- ==========================================
-- RELACIONES Y CLAVES FORÁNEAS
-- ==========================================

-- Relación: pagos -> reservas
ALTER TABLE `pagos`
  ADD CONSTRAINT `pagos_ibfk_1` FOREIGN KEY (`reserva_id`) REFERENCES `reservas` (`reserva_id`);

-- Relación: reservas -> usuarios
ALTER TABLE `reservas`
  ADD CONSTRAINT `reservas_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`usuario_id`);

-- Relación: reservas -> canchas
ALTER TABLE `reservas`
  ADD CONSTRAINT `reservas_ibfk_2` FOREIGN KEY (`cancha_id`) REFERENCES `canchas` (`cancha_id`);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- ==========================================
-- NOTAS:
-- 
-- Para importar esta base de datos:
-- 1. Abrir phpMyAdmin o MySQL Workbench
-- 2. Importar este archivo
-- 3. La base de datos 'club_reservas' se creará automáticamente
-- 
-- Estados de reserva válidos:
-- - PENDIENTE: Reserva creada pero no pagada
-- - PAGADO: Reserva confirmada y pagada
-- - CANCELADO: Reserva cancelada
-- 
-- Tipos de usuario:
-- - Cliente: Usuario regular del club
-- - Administrador: Gestiona el sistema
-- ==========================================
