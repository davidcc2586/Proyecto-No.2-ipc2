CREATE DATABASE IF NOT EXISTS ColegioPrestigioso;
USE ColegioPrestigioso;
CREATE TABLE ConfiguracionColegio(
    anioLectivo INT NOT NULL PRIMARY KEY
);
CREATE TABLE Usuario(
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    cui VARCHAR(13) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    usuario VARCHAR(50) NOT NULL,
    clave VARCHAR(50) NOT NULL,
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
    alergias TEXT,
    nombreEncargado VARCHAR(100) NOT NULL,
    telefonoEncargado VARCHAR(15) NOT NULL,
    correoEncargado VARCHAR(100),
    FOREIGN KEY(anioInscripcion) REFERENCES ConfiguracionColegio(anioLectivo)
);
CREATE TABLE Carrera(
    idCarrera INT AUTO_INCREMENT PRIMARY KEY,
    nombreCarrera VARCHAR(100),
    estadoCarrera ENUM('ACTIVO', 'INACTIVO')
);
CREATE TABLE Grado(
    idGrado INT AUTO_INCREMENT PRIMARY KEY,
    grado ENUM('PRE_PRIMARIA', 'PRIMARIA', 'BASICO', 'DIVERSIFICADO'),
    idCarrera INT,
    FOREIGN KEY(idCarrera) REFERENCES Carrera(idCarrera)
);
CREATE TABLE Curso(
    idCurso INT AUTO_INCREMENT PRIMARY KEY,
    IdGrado INT NOT NULL,
    nombreCurso VARCHAR(50) NOT NULL,
    FOREIGN KEY(IdGrado) REFERENCES Grado(idGrado)
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
    punteoObtenido DOUBLE(5,2) NOT NULL DEFAULT 0.00,
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
    idMaestro INT NOT NULL,
    idCurso INT NOT NULL,
    PRIMARY KEY(idSeccion,idMaestro),
    FOREIGN KEY(idSeccion) REFERENCES Seccion(idSeccion),
    FOREIGN KEY(idCurso) REFERENCES Curso(idCurso),
    FOREIGN KEY(idMaestro) REFERENCES Usuario(idUsuario)
);
CREATE TABLE Libro(
    idLibro INT AUTO_INCREMENT PRIMARY KEY,
    autor VARCHAR(50) NOT NULL,
    anio VARCHAR(4) NOT NULL,
    titulo VARCHAR(50) NOT NULL,
    editorial VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL DEFAULT 0
);
CREATE TABLE PrestamoLibro(
    idPrestamoLibro INT AUTO_INCREMENT PRIMARY KEY,
    idLibro INT NOT NULL,
    idEstudiante INT NOT NULL,
    estadoPrestamo ENUM('PRESTADO','ENTREGADO'),
    estadoFinal ENUM('MOROSO', 'Puntual'),
    costoDia DOUBLE(10,2) NOT NULL,
    fechaPrestamo DATE NOT NULL,
    fechaEstimadaDevolucion DATE NOT NULL,
    fechaDevolucion DATE,
    multa DOUBLE(10,2) NOT NULL,
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