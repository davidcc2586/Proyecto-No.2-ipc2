DROP DATABASE IF EXISTS ColegioPrestigioso;
CREATE DATABASE IF NOT EXISTS ColegioPrestigioso;
USE ColegioPrestigioso;
CREATE TABLE ConfiguracionColegio(
    anioLectivo INT NOT NULL PRIMARY KEY UNIQUE,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'INACTIVO'
);
CREATE TABLE Usuario(
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    cui VARCHAR(13) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    usuario VARCHAR(50) NOT NULL,
    clave VARCHAR(50) NOT NULL,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
    rol ENUM('SUPERADMIN','ADMIN','SECRETARIO','BIBLIOTECARIO','PERSONAL','MAESTRO')  DEFAULT 'PERSONAL',
    fechaContratacion DATE NOT NULL,
    salario DOUBLE(10,2) NOT NULL
);
CREATE TABLE Estudiante(
    idEstudiante INT AUTO_INCREMENT PRIMARY KEY,
    cui VARCHAR(13) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    usuario VARCHAR(50) NOT NULL,
    clave VARCHAR(50) NOT NULL,
    anioInscripcion int NOT NULL,
    estado ENUM('ACTIVO', 'INACTIVO', 'GRADUADO') DEFAULT 'ACTIVO',
    tipoSangre VARCHAR(5) NOT NULL,
    alergias VARCHAR(100) DEFAULT 'Ninguna',
    nombreEncargado VARCHAR(100) DEFAULT 'Ninguna',
    telefonoEncargado VARCHAR(15) DEFAULT 'Ninguna',
    correoEncargado VARCHAR(100) DEFAULT 'Ninguna',
    FOREIGN KEY(anioInscripcion) REFERENCES ConfiguracionColegio(anioLectivo)
);
CREATE TABLE Carrera(
    idCarrera INT AUTO_INCREMENT PRIMARY KEY,
    nombreCarrera VARCHAR(100),
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO'
);
CREATE TABLE Grado(
    idGrado INT AUTO_INCREMENT PRIMARY KEY,
    nombreGrado varchar(150) NOT NULL,
    grado ENUM('PRE_PRIMARIA', 'PRIMARIA', 'BASICO', 'DIVERSIFICADO'),
    idCarrera INT DEFAULT 0,
    FOREIGN KEY(idCarrera) REFERENCES Carrera(idCarrera)
);
CREATE TABLE Curso(
    idCurso INT AUTO_INCREMENT PRIMARY KEY,
    nombreCurso VARCHAR(50) NOT NULL,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO'
);
CREATE TABLE CurriculumCursosGrado(
    idGrado INT NOT NULL,
    idCurso INT NOT NULL,
    PRIMARY KEY(idGrado,idCurso),
    FOREIGN KEY(idGrado) REFERENCES Grado(idGrado),
    FOREIGN KEY(idCurso) REFERENCES Curso(idCurso)
);
CREATE TABLE Seccion(
    idSeccion INT AUTO_INCREMENT PRIMARY KEY,
    idGrado INT NOT NULL,
    anio INT NOT NULL,
    seccion ENUM('A','B','C','D','E','F','G','H','I','J','K') NOT NULL,
    UNIQUE(idGrado,anio,seccion),
    FOREIGN KEY(idGrado) REFERENCES Grado(idGrado),
    FOREIGN KEY(anio) REFERENCES ConfiguracionColegio(anioLectivo)
);
CREATE TABLE Actividad (
    idActividad INT AUTO_INCREMENT PRIMARY KEY,
    idSeccion INT NOT NULL,
    idCurso INT NOT NULL,
    nombreActividad VARCHAR(100) NOT NULL,
    ponderacion DOUBLE(5,2) NOT NULL,
    tipo ENUM('PARCIAL', 'ACTIVIDAD', 'ZONA','EXAMEN_FINAL') DEFAULT 'ACTIVIDAD',
    FOREIGN KEY (idSeccion) REFERENCES Seccion(idSeccion),
    FOREIGN KEY (idCurso) REFERENCES Curso(idCurso)
);
CREATE TABLE NotaActividad (
    idActividad INT NOT NULL,
    idEstudiante INT NOT NULL,
    punteoObtenido DOUBLE(5,2) DEFAULT 0.00,
    PRIMARY KEY (idActividad, idEstudiante),
    FOREIGN KEY (idActividad) REFERENCES Actividad(idActividad) ON DELETE CASCADE,
    FOREIGN KEY (idEstudiante) REFERENCES Estudiante(idEstudiante)
);
CREATE TABLE AsignacionEstudiantes(
    idSeccion INT NOT NULL,
    idEstudiante INT NOT NULL,
    PRIMARY KEY(idSeccion,idEstudiante),
    FOREIGN KEY(idEstudiante) REFERENCES Estudiante(idEstudiante),
    FOREIGN KEY(idSeccion) REFERENCES Seccion(idSeccion)
);
CREATE TABLE AsignacionMaestrosSeccion(
    idSeccion INT NOT NULL,
    idCurso INT NOT NULL,
    idMaestro INT NOT NULL,
    PRIMARY KEY(idSeccion, idCurso),
    FOREIGN KEY(idSeccion) REFERENCES Seccion(idSeccion) ON DELETE CASCADE,
    FOREIGN KEY(idCurso) REFERENCES Curso(idCurso) ON DELETE CASCADE,
    FOREIGN KEY(idMaestro) REFERENCES Usuario(idUsuario)
);
CREATE TABLE Libro(
    idLibro INT AUTO_INCREMENT PRIMARY KEY,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO',
    autor VARCHAR(50) NOT NULL,
    anio INT NOT NULL,
    titulo VARCHAR(50) NOT NULL,
    editorial VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL DEFAULT 0
);
CREATE TABLE PrestamoLibro(
    idPrestamoLibro INT AUTO_INCREMENT PRIMARY KEY,
    idLibro INT NOT NULL,
    idEstudiante INT NOT NULL,
    estadoPrestamo ENUM('PRESTADO','ENTREGADO') DEFAULT 'PRESTADO',
    estadoFinal ENUM('MOROSO', 'PUNTUAL') DEFAULT 'PUNTUAL',
    costoDia DOUBLE(10,2) NOT NULL,
    fechaPrestamo DATE NOT NULL,
    fechaEstimadaDevolucion DATE NOT NULL,
    fechaDevolucion DATE,
    multa DOUBLE(10,2),
    FOREIGN KEY(idLibro) REFERENCES Libro(idLibro),
    FOREIGN KEY(idEstudiante) REFERENCES Estudiante(idEstudiante)
);
CREATE TABLE Pago(
    idPago INT AUTO_INCREMENT PRIMARY KEY,
    idEstudiante INT NOT NULL,
    idSecretario INT NOT NULL,
    tipoPago ENUM('COLEGIATURA','ACTIVIDAD_EXTRACURRICULAR','INSCRIPCIÓN','MULTA') NOT NULL,
    cantidad DOUBLE(10,2) NOT NULL,
    descripcion TEXT NOT NULL,
    fecha DATE NOT NULL,
    FOREIGN KEY(idEstudiante) REFERENCES Estudiante(idEstudiante),
    FOREIGN KEY(idSecretario) REFERENCES Usuario(idUsuario)
);
CREATE TABLE Factura(
    idFactura INT AUTO_INCREMENT PRIMARY KEY,
    idPago INT NOT NULL,
    nombreConsumidor VARCHAR(50) default 'CONSUMIDOR FINAL',
    NIT VARCHAR(20) DEFAULT 'CF',
    FOREIGN KEY(idPago)REFERENCES Pago(idPago)
);
CREATE TABLE DetallePagoColegiatura (
    idDetalle INT AUTO_INCREMENT PRIMARY KEY,
    idPago INT NOT NULL,
    idEstudiante INT NOT NULL,
    anio INT NOT NULL,
    mes INT NOT NULL,
    FOREIGN KEY(idPago) REFERENCES Pago(idPago) ON DELETE CASCADE,
    FOREIGN KEY(idEstudiante) REFERENCES Estudiante(idEstudiante) ON DELETE CASCADE,
    UNIQUE(anio, mes, idPago)
);