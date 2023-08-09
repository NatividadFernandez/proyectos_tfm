-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-06-2021 a las 22:32:39
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.3.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cantera`
--
CREATE DATABASE IF NOT EXISTS `cantera` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;
USE `cantera`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consumos`
--

CREATE TABLE `consumos` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `gasoleo` double NOT NULL,
  `aceite_motor` double NOT NULL,
  `aceite_hidraulico` double NOT NULL,
  `aceite_transmisiones` double NOT NULL,
  `valvulina` double NOT NULL,
  `grasas` double NOT NULL,
  `fecha_recepcion` datetime NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `consumos`
--

INSERT INTO `consumos` (`id`, `gasoleo`, `aceite_motor`, `aceite_hidraulico`, `aceite_transmisiones`, `valvulina`, `grasas`, `fecha_recepcion`, `id_empleado`, `created_at`, `updated_at`) VALUES
(2, 4500, 45, 100, 0, 5, 4, '2021-04-15 13:10:00', 1, '2021-04-28 09:11:09', '2021-06-07 17:58:23'),
(3, 0, 200, 90, 90, 0, 0, '2021-04-28 16:36:00', 1, '2021-04-28 12:36:59', '2021-06-07 17:59:28'),
(5, 2000, 15, 90, 60, 0, 0, '2021-04-23 16:40:00', 1, '2021-04-28 12:41:13', '2021-06-07 17:59:03'),
(6, 5000, 20, 80, 5, 0, 5, '2021-05-21 19:25:00', 1, '2021-04-28 13:17:43', '2021-06-07 17:59:48'),
(10, 10, 10, 10, 10, 10, 10, '2021-06-05 22:03:00', 1, '2021-06-07 18:03:40', '2021-06-07 18:03:40');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `dni` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellidos` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `genero` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `puesto` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contrato` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`id`, `dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `telefono`, `estado`, `genero`, `puesto`, `contrato`, `role_id`, `user_id`, `created_at`, `updated_at`) VALUES
(1, '99999999R', 'Pepe', 'Sánchez', '2021-04-14', 'pepe@cantera.foncanal.com', '619747489', 0, 'Hombre', 'Operario', 'Fijo', 2, 2, '2021-04-19 07:18:58', '2021-04-28 08:03:10'),
(2, '77678965F', 'Lolita', 'Pérez', '2021-04-21', 'loles@cantera.foncanal.com', '619747489', 1, 'Mujer', 'Camionero', 'Practicas', 2, 3, '2021-04-19 07:25:21', '2021-05-31 17:39:44'),
(3, '65004204V', 'Natividad', 'Hidalgo Fernández', '1999-06-16', 'natHi@cantera.foncanal.com', '652145789', 1, 'Mujer', 'Palista', 'Fijo', 2, 6, '2021-06-07 17:18:25', '2021-06-07 17:18:25'),
(4, '50000556M', 'Pedro', 'Sánchez Ferrer', '2005-10-17', 'pedro@cantera.foncanal.com', '685478963', 1, 'Hombre', 'Camionero', 'Temporal', 2, 7, '2021-06-07 17:20:12', '2021-06-07 17:20:12'),
(5, '00000023T', 'Luis', 'Moreno Puertas', '1963-08-24', 'luis@cantera.foncanal.com', '639852147', 1, 'Hombre', 'Camionero', 'Fijo', 1, 8, '2021-06-07 17:25:06', '2021-06-07 17:25:06');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `failed_jobs`
--

CREATE TABLE `failed_jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `uuid` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `connection` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `queue` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `exception` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(78, '2014_10_12_000000_create_users_table', 1),
(79, '2014_10_12_100000_create_password_resets_table', 1),
(80, '2019_08_19_000000_create_failed_jobs_table', 1),
(81, '2021_03_18_161918_create_roles_table', 1),
(82, '2021_04_05_142814_create_empleados_table', 2),
(83, '2021_04_18_054157_create_voladuras_table', 3),
(84, '2021_04_18_113820_create_productos_table', 3),
(85, '2021_04_18_145726_create_stock_productos_table', 3),
(86, '2021_04_28_100503_create_consumos_table', 4),
(87, '2021_04_28_134957_create_stock_consumos_table', 5),
(89, '2021_05_31_210846_create_presencialidads_table', 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `password_resets`
--

CREATE TABLE `password_resets` (
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `presencialidads`
--

CREATE TABLE `presencialidads` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `fecha_check_in` datetime NOT NULL,
  `fecha_check_out` datetime DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `presencialidads`
--

INSERT INTO `presencialidads` (`id`, `id_empleado`, `fecha`, `fecha_check_in`, `fecha_check_out`, `created_at`, `updated_at`) VALUES
(1, 3, '2021-06-01', '2021-06-01 19:44:44', '2021-06-01 19:45:37', NULL, NULL),
(2, 2, '2021-06-01', '2021-06-01 19:48:46', '2021-06-01 19:49:58', NULL, NULL),
(3, 2, '2021-06-05', '2021-06-05 13:42:18', '2021-06-05 13:47:09', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `arena_06` double NOT NULL,
  `grava_612` double NOT NULL,
  `grava_1220` double NOT NULL,
  `grava_2023` double NOT NULL,
  `rechazo` double NOT NULL,
  `zahorra` double NOT NULL,
  `escollera` double NOT NULL,
  `mamposteria` double NOT NULL,
  `voladura` int(11) NOT NULL,
  `voladura_fecha` datetime NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `arena_06`, `grava_612`, `grava_1220`, `grava_2023`, `rechazo`, `zahorra`, `escollera`, `mamposteria`, `voladura`, `voladura_fecha`, `id_empleado`, `created_at`, `updated_at`) VALUES
(30, 7000, 3500, 4000, 2000, 0, 6500, 4000, 0, 25, '2020-12-16 11:35:00', 1, '2021-06-07 17:54:22', '2021-06-07 17:54:22'),
(31, 1000, 1000, 1000, 1000, 500, 6000, 4000, 10500, 22, '2021-01-08 11:28:00', 1, '2021-06-07 17:54:59', '2021-06-07 17:54:59'),
(32, 2000, 2000, 2000, 2000, 1000, 6000, 5000, 3000, 23, '2021-03-14 12:14:00', 1, '2021-06-07 17:55:33', '2021-06-07 17:55:33'),
(33, 0, 0, 0, 0, 0, 2000, 5000, 0, 24, '2021-05-05 11:30:00', 1, '2021-06-07 17:55:59', '2021-06-07 17:55:59');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `nombre_rol` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `nombre_rol`, `created_at`, `updated_at`) VALUES
(1, 'administrador', NULL, NULL),
(2, 'empleado', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `stock_consumos`
--

CREATE TABLE `stock_consumos` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `gasoleo` double NOT NULL,
  `aceite_motor` double NOT NULL,
  `aceite_hidraulico` double NOT NULL,
  `aceite_transmisiones` double NOT NULL,
  `valvulina` double NOT NULL,
  `grasas` double NOT NULL,
  `fecha_consumo` datetime NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `stock_consumos`
--

INSERT INTO `stock_consumos` (`id`, `gasoleo`, `aceite_motor`, `aceite_hidraulico`, `aceite_transmisiones`, `valvulina`, `grasas`, `fecha_consumo`, `id_empleado`, `created_at`, `updated_at`) VALUES
(1, 4500, 45, 100, 0, 5, 4, '2021-04-15 13:10:00', 1, '2021-04-28 07:11:09', '2021-06-07 15:58:23'),
(2, 0, 200, 90, 90, 0, 0, '2021-04-28 16:36:00', 1, '2021-04-28 10:36:59', '2021-06-07 15:59:28'),
(3, 2000, 15, 90, 60, 0, 0, '2021-04-23 16:40:00', 1, '2021-04-28 10:41:13', '2021-06-07 15:59:03'),
(4, 5000, 20, 80, 5, 0, 5, '2021-05-21 19:25:00', 1, '2021-04-28 11:17:43', '2021-06-07 15:59:48'),
(5, 10, 10, 10, 10, 10, 10, '2021-06-05 22:03:00', 1, '2021-06-07 16:03:40', '2021-06-07 16:03:40');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `stock_productos`
--

CREATE TABLE `stock_productos` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `arena_06` double NOT NULL,
  `grava_612` double NOT NULL,
  `grava_1220` double NOT NULL,
  `grava_2023` double NOT NULL,
  `rechazo` double NOT NULL,
  `zahorra` double NOT NULL,
  `escollera` double NOT NULL,
  `mamposteria` double NOT NULL,
  `voladura` int(11) NOT NULL,
  `voladura_fecha` datetime NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `stock_productos`
--

INSERT INTO `stock_productos` (`id`, `arena_06`, `grava_612`, `grava_1220`, `grava_2023`, `rechazo`, `zahorra`, `escollera`, `mamposteria`, `voladura`, `voladura_fecha`, `id_empleado`, `created_at`, `updated_at`) VALUES
(21, 7000, 3500, 4000, 2000, 0, 6500, 4000, 0, 25, '2020-12-16 11:35:00', 1, '2021-06-07 17:54:22', '2021-06-07 17:54:22'),
(22, 1000, 1000, 1000, 1000, 500, 6000, 4000, 10500, 22, '2021-01-08 11:28:00', 1, '2021-06-07 17:54:59', '2021-06-07 17:54:59'),
(23, 2000, 2000, 2000, 2000, 1000, 6000, 5000, 3000, 23, '2021-03-14 12:14:00', 1, '2021-06-07 17:55:34', '2021-06-07 17:55:34'),
(24, 0, 0, 0, 0, 0, 2000, 5000, 0, 24, '2021-05-05 11:30:00', 1, '2021-06-07 17:55:59', '2021-06-07 17:55:59');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `email_verified_at`, `password`, `role_id`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'Nati', 'nati@gmail.com', NULL, '$2y$10$WP8mugVLefpOcYIFbDn3Y.h5/a7r/aNKElshwbouoqNKBnMTjJSGW', 1, NULL, '2021-04-19 07:14:00', '2021-04-19 07:14:00'),
(2, 'Pepe', 'pepe@cantera.foncanal.com', NULL, '$2y$10$s9..E3kJ0PLmngTD2c2JM.ISRfX8o.IyXNppa86uN3g3RofLYkpJy', 2, NULL, '2021-04-19 07:18:58', '2021-04-19 07:18:58'),
(3, 'Lolita', 'loles@cantera.foncanal.com', NULL, '$2y$10$ou2cMco14.OEMOcYjo28e.ZVpRZDda7Qsfb8x4okOmtF8AwTHQTYe', 2, NULL, '2021-04-19 07:25:21', '2021-05-31 17:39:44'),
(6, 'Natividad', 'natHi@cantera.foncanal.com', NULL, '$2y$10$SZqEaZ1yZsPjovBgSRvSVuMjTKFLcKF8PD.3GH8lsh0C7gTeE16wW', 2, NULL, '2021-06-07 17:18:25', '2021-06-07 17:18:25'),
(7, 'Pedro', 'pedro@cantera.foncanal.com', NULL, '$2y$10$CqkiNxh8pZSRYXVpSOOpdOtjzmrmZ.rZFa3VQ/F6QaJZOF5JLhJLq', 2, NULL, '2021-06-07 17:20:12', '2021-06-07 17:20:12'),
(8, 'Luis', 'luis@cantera.foncanal.com', NULL, '$2y$10$7kLSNkwukdTZEy1PFS.gTeXC5AX1eItFJAqfD1HVqYM32CKqz.OfS', 1, NULL, '2021-06-07 17:25:06', '2021-06-07 17:25:06');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `voladuras`
--

CREATE TABLE `voladuras` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `localizacion` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `m2_superficie` double NOT NULL,
  `malla_perforacion` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `profundidad_barrenos` double NOT NULL,
  `numero_barrenos` int(11) NOT NULL,
  `kg_explosivo` double NOT NULL,
  `precio` double NOT NULL,
  `piedra_bruta` double NOT NULL,
  `fecha_voladura` datetime NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `voladuras`
--

INSERT INTO `voladuras` (`id`, `localizacion`, `m2_superficie`, `malla_perforacion`, `profundidad_barrenos`, `numero_barrenos`, `kg_explosivo`, `precio`, `piedra_bruta`, `fecha_voladura`, `id_empleado`, `created_at`, `updated_at`) VALUES
(22, 'Frente Norte', 900, '3x3.5', 16, 62, 3200, 10000, 25000, '2021-01-08 11:28:00', 1, '2021-06-07 17:28:40', '2021-06-07 17:28:40'),
(23, 'Frente Sur', 750, '3.5x3.5', 18, 58, 3000, 9700, 23000, '2021-03-14 12:14:00', 1, '2021-06-07 17:30:49', '2021-06-07 17:30:49'),
(24, 'Frente Norte', 900, '3x3.5', 16, 62, 3200, 10000, 25000, '2021-05-05 11:30:00', 1, '2021-06-07 17:31:33', '2021-06-07 17:31:33'),
(25, 'Frente Sur', 750, '4x3.5', 19, 56, 3200, 10000, 25000, '2020-12-16 11:35:00', 1, '2021-06-07 17:32:39', '2021-06-07 17:32:39');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `consumos`
--
ALTER TABLE `consumos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`id`),
  ADD KEY `empleados_user_id_foreign` (`user_id`);

--
-- Indices de la tabla `failed_jobs`
--
ALTER TABLE `failed_jobs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `failed_jobs_uuid_unique` (`uuid`);

--
-- Indices de la tabla `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `password_resets`
--
ALTER TABLE `password_resets`
  ADD KEY `password_resets_email_index` (`email`);

--
-- Indices de la tabla `presencialidads`
--
ALTER TABLE `presencialidads`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `stock_consumos`
--
ALTER TABLE `stock_consumos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `stock_productos`
--
ALTER TABLE `stock_productos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- Indices de la tabla `voladuras`
--
ALTER TABLE `voladuras`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `consumos`
--
ALTER TABLE `consumos`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `empleados`
--
ALTER TABLE `empleados`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=90;

--
-- AUTO_INCREMENT de la tabla `presencialidads`
--
ALTER TABLE `presencialidads`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `stock_consumos`
--
ALTER TABLE `stock_consumos`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `stock_productos`
--
ALTER TABLE `stock_productos`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `voladuras`
--
ALTER TABLE `voladuras`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD CONSTRAINT `empleados_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
