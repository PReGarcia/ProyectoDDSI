-- init.sql

-- Tabla MONITOR
CREATE TABLE IF NOT EXISTS MONITOR (
    codMonitor VARCHAR(10) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    fechaEntrada VARCHAR(20) NOT NULL,
    nick VARCHAR(50),
    PRIMARY KEY (codMonitor)
);

-- Tabla SOCIO
CREATE TABLE IF NOT EXISTS SOCIO (
    numeroSocio VARCHAR(10) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL,
    fechaNacimiento VARCHAR(20),
    telefono VARCHAR(20),
    correo VARCHAR(100),
    fechaEntrada VARCHAR(20) NOT NULL,
    categoria CHAR(1) NOT NULL,
    PRIMARY KEY (numeroSocio)
);

-- Tabla ACTIVIDAD
CREATE TABLE IF NOT EXISTS ACTIVIDAD (
    idActividad VARCHAR(10) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    dia VARCHAR(20) NOT NULL,
    hora INT NOT NULL,
    descripcion VARCHAR(255),
    precioBaseMes INT NOT NULL,
    monitorResponsable VARCHAR(10),
    PRIMARY KEY (idActividad),
    FOREIGN KEY (monitorResponsable) REFERENCES MONITOR(codMonitor)
);

-- Tabla REALIZA (Relación N:M)
CREATE TABLE IF NOT EXISTS REALIZA (
    idActividad VARCHAR(10) NOT NULL,
    numeroSocio VARCHAR(10) NOT NULL,
    PRIMARY KEY (idActividad, numeroSocio),
    FOREIGN KEY (idActividad) REFERENCES ACTIVIDAD(idActividad),
    FOREIGN KEY (numeroSocio) REFERENCES SOCIO(numeroSocio)
);


INSERT INTO MONITOR (codMonitor, nombre, dni, telefono, correo, fechaEntrada, nick)
VALUES ('M001', 'Monitor Default', '00000000X', '600000000', 'admin@gym.com', '01/01/2023', 'admin');