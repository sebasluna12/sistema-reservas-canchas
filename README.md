# 🏟️ Sistema de Gestión de Reservas - Club Atlético Ñuñorco

Repositorio de trabajos prácticos del Seminario de Práctica de Sistemas de Información - Universidad Siglo 21

---

## 👨‍💻 Autor

**Sebastian Luna**
- **Legajo:** VINF04827
- **Universidad:** Siglo 21
- **Carrera:** Analista de Sistemas
- **Materia:** Seminario de Práctica de Sistemas de Información

---

## 📂 Estructura del Repositorio

Este repositorio contiene **dos trabajos prácticos** del sistema de gestión de reservas para el Club Atlético Ñuñorco:

```
sistema-reservas-canchas/
├── sistema-reserva-cancha (TP3)/                    # Trabajo Práctico 3 - Sistema en Memoria
└── TP4/                                              # Trabajo Práctico 4 - Sistema con MySQL
```

---

## 📋 Trabajos Prácticos

### 🔵 [sistema-reserva-cancha (TP3) - Sistema en Memoria](./TP3/)

**Descripción:** Sistema de gestión de reservas implementado completamente en memoria usando estructuras de datos Java (ArrayList, HashMap).

**Características:**
- ✅ Gestión completa de Usuarios
- ✅ Gestión completa de Canchas
- ✅ Gestión de Reservas
- ✅ Todo almacenado en memoria (RAM)
- ✅ Patrón de diseño: POO básico

**Tecnologías:**
- Java
- Estructuras de datos (ArrayList, HashMap)
- NetBeans

**Limitaciones:**
- ❌ Los datos se pierden al cerrar el programa
- ❌ No hay persistencia

---

### 🟢 [TP4 - Sistema con MySQL](./TP4/) ⭐ **ACTUAL**

**Descripción:** Sistema de gestión de reservas con **persistencia en base de datos MySQL** utilizando JDBC.

**Características:**
- ✅ Gestión de Reservas con persistencia en MySQL
- ✅ Validación de usuarios y canchas existentes
- ✅ Detección de conflictos de horario
- ✅ Patrón de diseño **MVC** (Modelo-Vista-Controlador)
- ✅ Patrón **Singleton** para conexiones
- ✅ **PreparedStatement** (prevención de SQL Injection)
- ✅ Operaciones **CRUD completas**

**Tecnologías:**
- Java 17+
- MySQL 8.0+
- JDBC (mysql-connector-j 9.4.0)
- NetBeans 23

**Mejoras respecto a TP3:**
- ✅ **Persistencia permanente** de datos
- ✅ **Arquitectura MVC** (mejor organización)
- ✅ **Seguridad** (PreparedStatement)
- ✅ **Validaciones robustas**
- ✅ **Código más profesional**

---

## 📊 Comparación TP3 vs TP4

| Característica | TP3 | TP4 |
|----------------|-----|-----|
| **Persistencia** | ❌ Memoria (se pierde al cerrar) | ✅ MySQL (permanente) |
| **Arquitectura** | Básica POO | ✅ Patrón MVC |
| **Seguridad** | ❌ Sin validación SQL | ✅ PreparedStatement |
| **Patrones de Diseño** | Ninguno específico | ✅ MVC + Singleton |
| **Gestión de Usuarios** | ✅ Completa desde app | ⚠️ Pre-cargados en BD |
| **Gestión de Canchas** | ✅ Completa desde app | ⚠️ Pre-cargadas en BD |
| **Gestión de Reservas** | ✅ CRUD básico | ✅ CRUD avanzado + validaciones |
| **Conflictos de Horario** | ❌ No detecta | ✅ Detecta automáticamente |
| **Complejidad** | 🟡 Media | 🔴 Alta |
| **Profesionalismo** | 🟡 Educativo | 🟢 Nivel producción |

---

## 🎓 Aprendizajes Clave

### TP3 - Fundamentos
- Programación Orientada a Objetos
- Estructuras de datos en Java
- Manejo de colecciones (ArrayList, HashMap)
- Diseño de clases y métodos

### TP4 - Nivel Avanzado
- Conectividad con bases de datos (JDBC)
- Patrón de diseño MVC
- Patrón Singleton
- Seguridad en bases de datos
- Validaciones de negocio
- Arquitectura de software profesional

---

## 📈 Evolución del Proyecto

```
TP3 (Octubre 2025)
    ↓
    [Aprendizaje de Bases de Datos]
    ↓
    [Estudio de Patrones de Diseño]
    ↓
TP4 (Noviembre 2025) ⭐
```

---
