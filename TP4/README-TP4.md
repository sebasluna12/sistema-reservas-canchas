# 🏟️ Sistema de Gestión de Reservas - Club Atlético Ñuñorco

Sistema de gestión de reservas de canchas deportivas desarrollado en Java con persistencia en base de datos MySQL, implementando el patrón de diseño MVC y JDBC para la conexión con la base de datos.

## 📋 Descripción del Proyecto

Este proyecto fue desarrollado como **Trabajo Práctico N°4** del Seminario de Práctica de Sistemas de Información de la Universidad Siglo 21. El sistema permite gestionar reservas de canchas deportivas del Club Atlético Ñuñorco, realizando operaciones CRUD completas sobre una base de datos MySQL.

### 🎯 Objetivos

- Implementar persistencia de datos utilizando MySQL
- Aplicar el patrón de diseño MVC (Modelo-Vista-Controlador)
- Utilizar JDBC para la conexión con base de datos
- Implementar PreparedStatement para prevenir SQL Injection
- Aplicar el patrón Singleton para la gestión de conexiones

## 🚀 Funcionalidades

El sistema ofrece las siguientes funcionalidades:

1. **Crear Nueva Reserva**: Permite registrar una nueva reserva validando usuario, cancha, fecha y horarios
2. **Consultar Todas las Reservas**: Lista todas las reservas con información del cliente
3. **Consultar Reservas por Estado**: Filtra reservas por estado (PENDIENTE, PAGADO, CANCELADO)
4. **Buscar Reserva por ID**: Busca y muestra los detalles de una reserva específica
5. **Actualizar Estado de Reserva**: Cambia el estado de una reserva existente
6. **Eliminar Reserva**: Elimina una reserva del sistema
7. **Ver Estadísticas**: Muestra estadísticas generales de las reservas

### ✨ Características Destacadas

- ✅ **Validación de datos**: Verifica que usuarios y canchas existan antes de crear reservas
- ✅ **Validación de formato**: Valida fechas y horarios en formato correcto
- ✅ **Detección de conflictos**: Previene reservas en horarios ya ocupados
- ✅ **Seguridad**: Utiliza PreparedStatement para prevenir SQL Injection
- ✅ **Patrón MVC**: Separación clara de responsabilidades
- ✅ **Patrón Singleton**: Gestión eficiente de conexiones a BD

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Java 17+
- **Base de Datos**: MySQL 8.0+
- **Driver JDBC**: mysql-connector-j 9.4.0
- **IDE**: NetBeans 23
- **Control de Versiones**: Git & GitHub

## 📁 Estructura del Proyecto

```
sistema-reservas-canchas/
├── src/
│   ├── conexionBD/                    # Paquete del Modelo (MVC)
│   │   ├── ConexionBD.java           # Singleton - Gestión de conexiones
│   │   ├── Reserva.java              # Entidad Reserva
│   │   ├── Cancha.java               # Clase abstracta Cancha
│   │   ├── CanchaFutbol.java         # Implementación concreta
│   │   └── ModeloGestionReservas.java # CRUD de Reservas
│   └── gestionreserva/               # Controlador y Vista (MVC)
│       ├── GestionReservas.java      # Controlador
│       ├── MenuReservas.java         # Vista (Interfaz de usuario)
│       └── ReservaExcepcion.java     # Manejo de excepciones
├── database/
│   └── schema.sql                    # Script de creación de BD
├── lib/
│   └── mysql-connector-j-9.4.0.jar   # Driver MySQL
└── README.md
```

## 🔧 Instalación y Configuración

### Requisitos Previos

- Java JDK 17 o superior
- MySQL Server 8.0 o superior
- XAMPP (o cualquier servidor MySQL)
- NetBeans / IntelliJ IDEA / Eclipse

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/sebasluna12/sistema-reservas-canchas.git
   cd sistema-reservas-canchas
   ```

2. **Crear la base de datos**
   - Iniciar MySQL Server (XAMPP)
   - Abrir phpMyAdmin (http://localhost/phpmyadmin)
   - Crear base de datos `club_reservas`
   - Importar el archivo `database/schema.sql`

3. **Configurar la conexión**
   
   Editar el archivo `src/conexionBD/ConexionBD.java`:
   
   ```java
   private static final String URL = "jdbc:mysql://localhost:3307/club_reservas";
   private static final String USUARIO = "root";
   private static final String PASSWORD = "";
   ```
   
   **NOTA:** Si tu MySQL corre en el puerto 3306 (por defecto), cambiar `3307` a `3306`.

4. **Agregar el driver MySQL**
   - En NetBeans: Click derecho en "Libraries" → Add JAR/Folder
   - Seleccionar `lib/mysql-connector-j-9.4.0.jar`

5. **Ejecutar el proyecto**
   - Abrir el proyecto en NetBeans
   - Click derecho en `MenuReservas.java`
   - Seleccionar "Run File" (Shift + F6)

## 💻 Uso del Sistema

### Ejemplo: Crear una Reserva

```
Seleccione una opción: 1

═══════════════════════════════════════
        CREAR NUEVA RESERVA
═══════════════════════════════════════

Usuarios disponibles: [1, 2, 3]
ID de Usuario: 1
  ✓ Usuario válido

Canchas disponibles: [1, 2, 3]
ID de Cancha: 1
  ✓ Cancha válida

Fecha (formato: AAAA-MM-DD): 2025-11-20
  ✓ Fecha válida: 2025-11-20
Hora de Inicio (HH:mm): 20:00
Hora de Fin (HH:mm): 22:00
Monto Total: $10000

✓ Horario disponible
✓ Reserva insertada exitosamente con ID: 5

╔═══════════════════════════════════════╗
║  ✓✓✓ RESERVA CREADA EXITOSAMENTE ✓✓✓ ║
╚═══════════════════════════════════════╝
```

## 🏗️ Arquitectura - Patrón MVC

- **Modelo**: `conexionBD` package - Gestión de datos y lógica de negocio
- **Vista**: `MenuReservas.java` - Interfaz de usuario
- **Controlador**: `GestionReservas.java` - Coordinación entre modelo y vista

## 🔒 Seguridad

- **PreparedStatement**: Previene SQL Injection
- **Validación de datos**: Todos los datos son validados antes de procesarlos
- **Manejo de excepciones**: Try-catch en operaciones críticas

## 👨‍💻 Autor

**Sebastian Luna**
- Legajo: VINF04827
- Universidad: Siglo 21
- Carrera: Analista de Sistemas

