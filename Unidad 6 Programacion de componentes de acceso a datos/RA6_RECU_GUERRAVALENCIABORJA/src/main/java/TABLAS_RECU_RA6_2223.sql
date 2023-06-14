
DROP table if exists platosmenus;
DROP table if exists alergenosproductos;
DROP table if exists productos;
DROP table if exists alergenos;
DROP table if exists categorias;

--
-- Base de datos: `ra6recu`   USUARIO Y CLAVE ra6recu
--


--
-- Table structure for table `alergenos`
--

CREATE TABLE `alergenos` (
  `id` int(10) UNSIGNED NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `numproductos` int(11) DEFAULT NULL,
  `nombreproductos` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `alergenos`
--

INSERT INTO `alergenos` (`id`, `nombre`, `numproductos`, `nombreproductos`) VALUES
(1, 'Leche y productos lácteos', 0, ''),
(2, 'Huevos', 0, ''),
(3, 'Frutos secos', 0, ''),
(4, 'Cacahuetes', 0, ''),
(5, 'Soja', 0, ''),
(6, 'Trigo y otros cereales', 0, ''),
(7, 'Pescados y mariscos', 0, ''),
(8, 'Mostaza', 0, ''),
(9, 'Semillas y granos', 0, ''),
(10, 'Frutas y verduras', 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `alergenosproductos`
--

CREATE TABLE `alergenosproductos` (
  `idproducto` int(10) UNSIGNED NOT NULL,
  `idalergeno` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `alergenosproductos`
--

INSERT INTO `alergenosproductos` (`idproducto`, `idalergeno`) VALUES
(1, 1),
(1, 4),
(1, 9),
(2, 2),
(2, 5),
(3, 3),
(3, 6),
(4, 7),
(4, 8),
(5, 1),
(5, 2),
(5, 10),
(6, 3),
(7, 4),
(7, 5),
(8, 6),
(8, 7),
(9, 8),
(9, 9),
(10, 1),
(10, 2),
(10, 10),
(11, 3),
(11, 4),
(11, 5),
(12, 6),
(13, 7),
(13, 8),
(13, 9),
(14, 1),
(14, 10),
(15, 2),
(15, 3),
(16, 4),
(17, 5),
(17, 6),
(17, 7),
(18, 8),
(18, 9),
(19, 10),
(20, 1),
(21, 2),
(21, 3),
(22, 4),
(23, 5),
(24, 1),
(24, 6),
(25, 1),
(25, 2),
(26, 10),
(27, 6),
(28, 1),
(29, 1),
(29, 6),
(30, 1),
(30, 6);

-- --------------------------------------------------------

--
-- Table structure for table `categorias`
--

CREATE TABLE `categorias` (
  `id` int(10) UNSIGNED NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `numproductos` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categorias`
--

INSERT INTO `categorias` (`id`, `nombre`, `numproductos`) VALUES
(1, 'Pasta', 0),
(2, 'Pescados', 0),
(3, 'Carnes', 0),
(4, 'Desayunos', 0),
(5, 'Postres', 0),
(6, 'Bebidas', 0),
(7, 'Chuches', 0);

-- --------------------------------------------------------

--
-- Table structure for table `platosmenus`
--

CREATE TABLE `platosmenus` (
  `idmenu` int(10) UNSIGNED NOT NULL,
  `idplato` int(10) UNSIGNED NOT NULL,
  `orden` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `platosmenus`
--

INSERT INTO `platosmenus` (`idmenu`, `idplato`, `orden`) VALUES
(37, 1, 1),
(37, 7, 2),
(37, 19, 3),
(38, 2, 1),
(38, 14, 2),
(38, 16, 3),
(39, 3, 1),
(39, 13, 2),
(39, 18, 3);

-- --------------------------------------------------------

--
-- Table structure for table `productos`
--

CREATE TABLE `productos` (
  `id` int(10) UNSIGNED NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `pvp` double(8,2) NOT NULL,
  `tipo` varchar(10) NOT NULL,
  `idcategoria` int(10) UNSIGNED NOT NULL,
  `numalergenos` int(11) DEFAULT NULL,
  `nombrealergenos` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `productos`
--

INSERT INTO `productos` (`id`, `nombre`, `pvp`, `tipo`, `idcategoria`, `numalergenos`, `nombrealergenos`) VALUES
(1, 'Espaguetis', 12.00, 'plato', 1, 0, ''),
(2, 'Macarrones', 11.00, 'plato', 1, 0, ''),
(3, 'Tortellinis', 15.00, 'plato', 1, 0, ''),
(4, 'Pasta de colores', 12.00, 'plato', 1, 0, ''),
(5, 'Raviolis', 10.00, 'plato', 1, 0, ''),
(6, 'Lasaña', 11.00, 'plato', 1, 0, ''),
(7, 'Salmón', 15.00, 'plato', 2, 0, ''),
(8, 'Merluza', 16.00, 'plato', 2, 0, ''),
(9, 'Sardinas', 18.00, 'plato', 2, 0, ''),
(10, 'Calamares', 10.00, 'plato', 2, 0, ''),
(11, 'Boquerones', 9.00, 'plato', 2, 0, ''),
(12, 'Trucha', 11.00, 'plato', 2, 0, ''),
(13, 'Ternera', 15.00, 'plato', 3, 0, ''),
(14, 'Filete de Buey', 16.00, 'plato', 3, 0, ''),
(15, 'Muslos de Pollo', 12.00, 'plato', 3, 0, ''),
(16, 'Pechuga de pollo', 11.00, 'plato', 3, 0, ''),
(17, 'Carrilleras', 15.00, 'plato', 3, 0, ''),
(18, 'Higado encebollado', 12.00, 'plato', 3, 0, ''),
(19, 'Churros', 10.00, 'plato', 4, 0, ''),
(20, 'Pan con Tomate', 11.00, 'plato', 4, 0, ''),
(21, 'Tostadas', 9.00, 'plato', 4, 0, ''),
(22, 'Pincho Tortilla', 14.00, 'plato', 4, 0, ''),
(23, 'Croissants', 15.00, 'plato', 4, 0, ''),
(24, 'Madalenas', 11.00, 'plato', 4, 0, ''),
(25, 'Flan con helado', 12.00, 'plato', 5, 0, ''),
(26, 'Fruta variada', 9.00, 'plato', 5, 0, ''),
(27, 'Tarta al wisky', 14.00, 'plato', 5, 0, ''),
(28, 'Helado', 14.00, 'plato', 5, 0, ''),
(29, 'Tarta de queso', 12.00, 'plato', 5, 0, ''),
(30, 'Tarta San Marcos', 15.00, 'plato', 5, 0, ''),
(31, 'Agua', 5.00, 'plato', 6, 0, ''),
(32, 'Vino tinto', 6.00, 'plato', 6, 0, ''),
(33, 'Vino Blanco', 6.00, 'plato', 6, 0, ''),
(34, 'Vino Rosado', 6.00, 'plato', 6, 0, ''),
(35, 'Refresco', 7.00, 'plato', 6, 0, ''),
(36, 'Agua con gas', 6.00, 'plato', 6, 0, ''),
(37, 'La Alcarria', 0.00, 'menu', 3, 0, ''),
(38, 'La Primavera', 0.00, 'menu', 2, 0, ''),
(39, 'Especial de la semana', 0.00, 'menu', 1, 0, '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alergenos`
--
ALTER TABLE `alergenos`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `alergenosproductos`
--
ALTER TABLE `alergenosproductos`
  ADD PRIMARY KEY (`idproducto`,`idalergeno`),
  ADD KEY `idalergeno` (`idalergeno`);

--
-- Indexes for table `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `platosmenus`
--
ALTER TABLE `platosmenus`
  ADD PRIMARY KEY (`idmenu`,`idplato`),
  ADD KEY `fkPM2` (`idplato`);

--
-- Indexes for table `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkp` (`idcategoria`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `alergenosproductos`
--
ALTER TABLE `alergenosproductos`
  ADD CONSTRAINT `alergenosproductos_ibfk_1` FOREIGN KEY (`idproducto`) REFERENCES `productos` (`id`),
  ADD CONSTRAINT `alergenosproductos_ibfk_2` FOREIGN KEY (`idalergeno`) REFERENCES `alergenos` (`id`);

--
-- Constraints for table `platosmenus`
--
ALTER TABLE `platosmenus`
  ADD CONSTRAINT `fkPM1` FOREIGN KEY (`idmenu`) REFERENCES `productos` (`id`),
  ADD CONSTRAINT `fkPM2` FOREIGN KEY (`idplato`) REFERENCES `productos` (`id`);

--
-- Constraints for table `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `fkp` FOREIGN KEY (`idcategoria`) REFERENCES `categorias` (`id`);
COMMIT;



-- -------------------------------------------------------------
-- datos generados al azar, puede que no tengan mucho sentido---
-- -------------------------------------------------------------

