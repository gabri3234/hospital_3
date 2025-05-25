-- Base de datos: `hospital3`

DROP DATABASE IF EXISTS `hospital`;
CREATE DATABASE `hospital` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `hospital`;

-- Tabla: camas
CREATE TABLE `camas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `habitacion_id` int NOT NULL,
  `paciente_id` int DEFAULT NULL,
  `estado` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `habitacion_id` (`habitacion_id`),
  KEY `paciente_id` (`paciente_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabla: habitaciones
CREATE TABLE `habitaciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `capacidad` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabla: pacientes
CREATE TABLE `pacientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `dni` varchar(20) NOT NULL,
  `gravedad` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabla: usuarios
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `isAdmin` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Inserción de usuarios
INSERT INTO `usuarios` (`id`, `username`, `password`, `isAdmin`) VALUES
(1, 'admin', '$2a$10$aq/BFAimr5bZ/7ItLVp/sOalv74E3PZOk0sRxO3e8.O0IahpgCosq', 1),
(2, 'FranciscoMED', '$2a$10$9rTg/2/03Ws4wNLkhO9i0.E/RIWIYIJ3yTgqRiah5OoTj8tFyPf2W', 0),
(3, 'CristinaMED', '$2a$10$sWZ99WbgKKi.NlcnzGiMdeAE/smFRHCPlYzTjILAdrkU30iGYlKR6', 0),
(4, 'GabrielMED', '$2a$10$vFL67hqFJSV8ewXm7l7jV.t4G42ghuIRiVFXUAduduGyNLqoCUvKG', 0);

-- Relaciones
ALTER TABLE `camas`
  ADD CONSTRAINT `camas_ibfk_1` FOREIGN KEY (`habitacion_id`) REFERENCES `habitaciones` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `camas_ibfk_2` FOREIGN KEY (`paciente_id`) REFERENCES `pacientes` (`id`);
