![Duoc UC](https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png)

# 🧠 Semana 9 - Evaluación Final Transversal (EFT) - Desarrollo Orientado a Objetos II

## 👤 Autor del proyecto

* **Nombre completo:** Mauricio Francisco Valenzuela Fuentes
* **Carrera:** Analista Programador Computacional
* **Sede:** Online

---

## 📘 Descripción general del sistema

Este proyecto corresponde a la **Evaluación Final Transversal (EFT)** de la asignatura **Desarrollo Orientado a Objetos II**.

El proyecto consiste en un **Sistema de Gestión de Biblioteca Escolar**, desarrollado en Java como aplicación de escritorio.

El sistema permite administrar libros, estudiantes, préstamos y devoluciones, además de autenticar usuarios según su rol y generar reportes asociados a la actividad de la biblioteca.

La aplicación integra los principales contenidos desarrollados durante la asignatura:

* Programación Orientada a Objetos.
* Herencia.
* Polimorfismo.
* Clases abstractas.
* Interfaces.
* Patrón MVC.
* Patrón DAO.
* Patrón Singleton.
* Persistencia mediante JDBC y MySQL.
* Operaciones CRUD.
* Transacciones.
* Hilos.
* Sincronización.
* Interfaz gráfica mediante Java Swing.
* Validaciones de datos.
* Manejo de excepciones.

El sistema distingue dos tipos de usuario:

```text
bibliotecario
estudiante
```

El bibliotecario posee acceso a la gestión general del sistema, mientras que el estudiante dispone de funciones restringidas relacionadas con consulta de libros y préstamos.

---

## 📚 Funcionalidades principales

El sistema permite:

* Autenticar usuarios utilizando correo y contraseña.
* Diferenciar permisos según rol.
* Registrar, consultar, modificar y eliminar libros.
* Registrar, consultar, modificar y eliminar estudiantes.
* Crear automáticamente una cuenta de usuario al registrar un estudiante.
* Consultar libros disponibles.
* Registrar préstamos.
* Actualizar automáticamente el stock al realizar un préstamo.
* Registrar devoluciones.
* Recuperar automáticamente el stock al realizar una devolución.
* Evitar devoluciones duplicadas.
* Calcular automáticamente la fecha de vencimiento de un préstamo.
* Detectar préstamos atrasados.
* Ejecutar préstamos y devoluciones mediante hilos independientes.
* Evitar bloqueos de la interfaz gráfica.
* Sincronizar las operaciones que modifican el stock.
* Ejecutar operaciones críticas utilizando transacciones.
* Confirmar operaciones mediante `COMMIT`.
* Deshacer operaciones mediante `ROLLBACK`.
* Mostrar libros más prestados.
* Mostrar historial de préstamos por estudiante.
* Mostrar libros actualmente en préstamo.
* Mostrar información mediante `JTable`.
* Seleccionar libros y estudiantes mediante `JComboBox`.
* Mostrar mensajes mediante `JOptionPane`.
* Validar los datos antes de almacenarlos en MySQL.

---

## 🧱 Estructura general del proyecto

```text
📁 SistemaBibliotecaEscolar/
│
├── 📁 config/
│   ├── database.properties                (archivo local, ignorado por Git)
│   └── database.properties.example
│
├── 📁 database/
│   ├── PRY2203_EFT_S9_Script_crea_tablas_biblioteca.sql
│   └── PRY2203_EFT_S9_Script_poblado_tablas_biblioteca.sql
│
├── 📁 lib/
│   └── mysql-connector-j-26.7.0.jar
│
├── 📁 src/
│   ├── 📁 main/
│   │   └── Main.java
│   │
│   ├── 📁 modelo/
│   │   ├── Autenticable.java
│   │   ├── Persona.java
│   │   ├── Usuario.java
│   │   ├── Estudiante.java
│   │   ├── Categoria.java
│   │   ├── Libro.java
│   │   └── Prestamo.java
│   │
│   ├── 📁 dao/
│   │   ├── UsuarioDAO.java
│   │   ├── EstudianteDAO.java
│   │   ├── CategoriaDAO.java
│   │   ├── LibroDAO.java
│   │   ├── PrestamoDAO.java
│   │   ├── ReporteDAO.java
│   │   │
│   │   └── 📁 impl/
│   │       ├── UsuarioDAOImpl.java
│   │       ├── EstudianteDAOImpl.java
│   │       ├── CategoriaDAOImpl.java
│   │       ├── LibroDAOImpl.java
│   │       ├── PrestamoDAOImpl.java
│   │       └── ReporteDAOImpl.java
│   │
│   ├── 📁 controlador/
│   │   ├── ControladorUsuarios.java
│   │   ├── ControladorEstudiantes.java
│   │   ├── ControladorCategorias.java
│   │   ├── ControladorLibros.java
│   │   ├── ControladorPrestamos.java
│   │   └── ControladorReportes.java
│   │
│   ├── 📁 vista/
│   │   ├── VentanaLogin.java
│   │   ├── VentanaPrincipal.java
│   │   ├── VentanaGestionLibros.java
│   │   ├── VentanaGestionEstudiantes.java
│   │   ├── VentanaPrestamos.java
│   │   └── VentanaReportes.java
│   │
│   └── 📁 util/
│       ├── DatabaseConnection.java
│       └── Validador.java
│
├── 📄 .gitignore
├── 📄 SistemaBibliotecaEscolar.iml
└── 📄 README.md
```

---

## 🧩 Organización por paquetes

El proyecto se encuentra organizado en seis paquetes principales.

### 1. `main`

Contiene la clase encargada de iniciar la aplicación.

#### `Main.java`

Es el punto de entrada del sistema.

Utiliza:

```java
SwingUtilities.invokeLater()
```

para iniciar correctamente la interfaz gráfica de Swing.

La aplicación comienza mostrando:

```text
VentanaLogin
```

---

### 2. `modelo`

Contiene las clases que representan las entidades y conceptos principales del sistema.

#### `Persona.java`

Clase abstracta que representa los atributos compartidos por las personas del sistema:

```text
id
nombre
rut
correo
```

Define el método abstracto:

```java
getTipoPersona()
```

que debe ser implementado por las clases derivadas.

También contiene:

```java
getDescripcion()
```

que reutiliza el comportamiento definido por las subclases.

---

#### `Usuario.java`

Representa a un usuario capaz de iniciar sesión.

Hereda de:

```text
Persona
```

e implementa:

```text
Autenticable
```

Contiene además:

```text
contraseña
rol
```

Los roles utilizados son:

```text
bibliotecario
estudiante
```

---

#### `Estudiante.java`

Representa a un estudiante registrado en la biblioteca.

Hereda de:

```text
Persona
```

y agrega el atributo:

```text
curso
```

---

#### `Autenticable.java`

Interfaz que define el comportamiento necesario para autenticar usuarios.

Contiene:

```java
verificarCredenciales()
```

La clase `Usuario` implementa esta interfaz.

---

#### `Categoria.java`

Representa una categoría utilizada para clasificar los libros.

Contiene:

```text
id
nombre
```

---

#### `Libro.java`

Representa un libro disponible en la biblioteca.

Contiene:

```text
id
titulo
autor
isbn
editorial
stock
idCategoria
```

---

#### `Prestamo.java`

Representa un préstamo realizado por un estudiante.

Contiene:

```text
id
idEstudiante
idLibro
fechaPrestamo
fechaDevolucion
devuelto
```

La fecha de devolución funciona como fecha límite o vencimiento del préstamo.

---

## 🧬 Programación Orientada a Objetos

El proyecto aplica una jerarquía basada en:

```text
Persona
│
├── Usuario
│      └── implements Autenticable
│
└── Estudiante
```

### Herencia

`Usuario` y `Estudiante` reutilizan los atributos y métodos definidos en `Persona`.

### Clase abstracta

`Persona` es una clase abstracta y define:

```java
public abstract String getTipoPersona();
```

### Sobrescritura

Las clases derivadas implementan su propia versión de:

```java
getTipoPersona()
```

### Polimorfismo

El sistema puede utilizar una referencia general:

```java
Persona persona = usuario;
```

y ejecutar:

```java
persona.getDescripcion();
```

El comportamiento específico depende del objeto concreto utilizado.

También se utiliza polimorfismo mediante la interfaz:

```java
Autenticable autenticable = usuario;
```

permitiendo ejecutar:

```java
autenticable.verificarCredenciales(...)
```

### Sobrecarga

Se utilizan constructores y métodos sobrecargados para trabajar con objetos nuevos y objetos recuperados desde la base de datos.

También existen métodos DAO que reciben una conexión JDBC existente para participar en transacciones compartidas.

---

## 🗂️ Patrón MVC

El sistema utiliza el patrón:

```text
Modelo - Vista - Controlador
```

El flujo general es:

```text
Vista
  ↓
Controlador
  ↓
DAO
  ↓
DAOImpl
  ↓
JDBC
  ↓
MySQL
```

### Modelo

Representa las entidades y comportamientos del sistema.

### Vista

Contiene las interfaces gráficas desarrolladas con Swing.

### Controlador

Coordina las operaciones entre las vistas, los modelos y los DAO.

Esto permite evitar que las ventanas ejecuten directamente sentencias SQL.

---

## 💾 Patrón DAO

El acceso a la base de datos se encuentra separado mediante interfaces DAO e implementaciones concretas.

Las interfaces principales son:

```text
UsuarioDAO
EstudianteDAO
CategoriaDAO
LibroDAO
PrestamoDAO
ReporteDAO
```

Las implementaciones se encuentran en:

```text
dao.impl
```

y utilizan:

```java
PreparedStatement
ResultSet
```

para interactuar con MySQL.

Las entidades principales implementan operaciones CRUD:

```text
create()
readAll()
update()
delete()
```

---

## 🔗 Patrón Singleton

La conexión con MySQL es administrada mediante:

```text
DatabaseConnection
```

Esta clase utiliza el patrón:

```text
Singleton
```

para mantener una única instancia responsable de administrar la conexión.

El método:

```java
DatabaseConnection.getInstance()
```

permite obtener dicha instancia.

La conexión se establece mediante:

```java
DriverManager.getConnection()
```

---

## 🔐 Configuración local de la base de datos

Las credenciales reales de MySQL no se almacenan directamente dentro del código Java.

La configuración local se encuentra en:

```text
config/database.properties
```

Este archivo es ignorado por Git mediante:

```text
.gitignore
```

por lo que la contraseña local utilizada durante el desarrollo no se publica en el repositorio.

El proyecto incluye una plantilla:

```text
config/database.properties.example
```

con el siguiente formato:

```properties
db.url=jdbc:mysql://127.0.0.1:3306/biblioteca
db.usuario=root
db.contrasena=TU_CONTRASENA
```

Cada usuario debe crear su propio:

```text
config/database.properties
```

y configurar allí sus credenciales locales.

---

## 🎨 Interfaz gráfica

La interfaz fue desarrollada mediante:

```text
Java Swing
```

utilizando principalmente:

```text
JFrame
JPanel
JLabel
JTextField
JPasswordField
JButton
JTable
JComboBox
JOptionPane
JScrollPane
```

---

### `VentanaLogin.java`

Permite ingresar al sistema utilizando:

```text
correo
contraseña
```

La aplicación consulta el usuario almacenado en MySQL y verifica sus credenciales.

Según el rol, se habilitan diferentes opciones.

---

### `VentanaPrincipal.java`

Funciona como menú principal.

Para un bibliotecario muestra:

```text
Gestión de Libros
Gestión de Estudiantes
Préstamos y Devoluciones
Reportes
Cerrar Sesión
```

Para un estudiante muestra:

```text
Consulta de Libros
Préstamos y Devoluciones
Cerrar Sesión
```

---

### `VentanaGestionLibros.java`

Permite al bibliotecario:

```text
Registrar
Consultar
Editar
Eliminar
Actualizar
Limpiar formulario
```

Los libros almacenados se muestran mediante:

```java
JTable
```

Las categorías se muestran mediante:

```java
JComboBox
```

Cuando ingresa un estudiante, la misma ventana funciona como consulta y oculta las operaciones de modificación.

---

### `VentanaGestionEstudiantes.java`

Permite:

```text
Registrar estudiantes
Consultar estudiantes
Editar estudiantes
Eliminar estudiantes
Actualizar información
```

Al registrar un estudiante se crea también su correspondiente cuenta en:

```text
usuarios
```

con:

```text
rol = estudiante
```

De esta forma el estudiante puede posteriormente iniciar sesión utilizando su correo y contraseña.

---

### `VentanaPrestamos.java`

Permite registrar:

```text
Préstamos
Devoluciones
```

La ventana muestra:

```text
Estudiante
Libro
Fecha de préstamo
Fecha de vencimiento
Estado
```

Los estados visuales posibles incluyen:

```text
EN PRÉSTAMO
ATRASADO
DEVUELTO
```

---

### `VentanaReportes.java`

Permite consultar tres reportes:

```text
Libros más prestados
Historial por estudiante
Libros actualmente en préstamo
```

Los resultados se muestran mediante:

```java
JTable
```

---

## 🧵 Concurrencia y sincronización

El registro de préstamos se ejecuta mediante un hilo independiente.

Desde la interfaz se crea:

```java
Thread
```

para realizar el proceso en segundo plano.

El resultado vuelve al hilo gráfico mediante:

```java
SwingUtilities.invokeLater()
```

Esto permite evitar que la interfaz se congele mientras se ejecutan operaciones relacionadas con MySQL.

Las operaciones que modifican el stock utilizan sincronización.

`ControladorPrestamos` mantiene un bloqueo compartido:

```text
BLOQUEO_PRESTAMOS
```

para proteger las operaciones críticas frente a accesos simultáneos.

También se utiliza:

```java
synchronized
```

en operaciones relacionadas con la modificación del stock.

---

## 🔄 Flujo de registro de un préstamo

```text
VentanaPrestamos
        ↓
Seleccionar estudiante
        +
Seleccionar libro
        ↓
Crear hilo independiente
        ↓
ControladorPrestamos
        ↓
Adquirir bloqueo compartido
        ↓
Iniciar transacción
        ↓
Consultar libro
        ↓
¿Stock disponible?
        │
     ┌──┴──┐
     │     │
     Sí    No
     │     │
     ↓     ↓
INSERT   ROLLBACK
préstamo
     +
UPDATE stock - 1
     ↓
¿Operaciones correctas?
     │
 ┌───┴───┐
 │       │
 Sí      No
 │       │
 ↓       ↓
COMMIT  ROLLBACK
```

La fecha de vencimiento se calcula automáticamente a partir de la fecha del préstamo.

---

## 🔄 Flujo de devolución

```text
Seleccionar préstamo
        ↓
ControladorPrestamos
        ↓
Adquirir bloqueo compartido
        ↓
Iniciar transacción
        ↓
Buscar préstamo
        ↓
¿Ya fue devuelto?
        │
     ┌──┴──┐
     │     │
     No    Sí
     │     │
     ↓     ↓
Marcar   ROLLBACK
devuelto
     +
UPDATE stock + 1
     ↓
COMMIT
```

El sistema evita que un mismo préstamo sea devuelto dos veces y aumente incorrectamente el stock.

---

## ⏰ Detección de atrasos

La aplicación compara:

```text
fecha actual
```

con:

```text
fecha de vencimiento
```

Si el libro continúa sin ser devuelto después de su fecha límite, el préstamo se identifica como:

```text
ATRASADO
```

---

## 📊 Reportes

### Libros más prestados

Consulta la cantidad de préstamos asociados a cada libro y los ordena según su frecuencia.

### Historial por estudiante

Permite seleccionar un estudiante y consultar los préstamos asociados a su historial.

### Libros actualmente en préstamo

Muestra los registros cuyo estado:

```text
devuelto = false
```

---

## ✅ Validaciones implementadas

El sistema incorpora una clase reutilizable:

```text
Validador
```

ubicada en:

```text
src/util/Validador.java
```

Entre las validaciones se encuentran:

```text
Nombre válido
Formato de RUT
Formato de correo
Formato de curso
Contraseña
Título de libro
Autor
ISBN
Editorial
Stock no negativo
Categoría seleccionada
Registro seleccionado antes de editar
Registro seleccionado antes de eliminar
Préstamo seleccionado antes de devolver
```

### RUT

Se valida el formato:

```text
12345678-9
```

### Correo

Se valida una estructura similar a:

```text
nombre@correo.cl
```

### Curso

Se utiliza un formato como:

```text
3ro Medio A
```

### Contraseña

Debe contener:

```text
Entre 6 y 50 caracteres
Al menos una letra
Al menos un número
```

### ISBN

Se aceptan formatos ISBN de 10 o 13 caracteres válidos según la estructura implementada.

### Stock

Debe ser:

```text
0 o mayor
```

Los mensajes de validación se muestran mediante:

```java
JOptionPane
```

---

## 💾 Base de datos

El sistema utiliza:

```text
biblioteca
```

La base de datos contiene las tablas mínimas:

```text
usuarios
estudiantes
categorias
libros
prestamos
```

---

### Tabla `usuarios`

Contiene:

```text
id
nombre
rut
correo
contraseña
rol
```

---

### Tabla `estudiantes`

Contiene:

```text
id
nombre
rut
curso
correo
```

---

### Tabla `categorias`

Contiene:

```text
id
nombre
```

---

### Tabla `libros`

Contiene:

```text
id
titulo
autor
isbn
editorial
stock
id_categoria
```

`id_categoria` corresponde a una clave foránea relacionada con:

```text
categorias
```

---

### Tabla `prestamos`

Contiene:

```text
id
id_estudiante
id_libro
fecha_prestamo
fecha_devolucion
devuelto
```

Las columnas:

```text
id_estudiante
id_libro
```

son claves foráneas.

---

## 📄 Scripts SQL

Los scripts oficiales utilizados se encuentran en:

```text
database/PRY2203_EFT_S9_Script_crea_tablas_biblioteca.sql

database/PRY2203_EFT_S9_Script_poblado_tablas_biblioteca.sql
```

El primer script crea la base de datos y sus tablas.

El segundo agrega los registros iniciales utilizados para probar el funcionamiento del sistema.

---

## ⚙️ Instrucciones para ejecutar el proyecto

### 1. Clonar el repositorio

Una vez publicado el proyecto:

```bash
git clone https://github.com/mauvalenzuelaf-oss/SistemaBibliotecaEscolar.git
```

---

### 2. Abrir el proyecto

Abre **IntelliJ IDEA** y selecciona:

```text
Open
```

Luego abre:

```text
SistemaBibliotecaEscolar
```

---

### 3. Crear la base de datos

Abre **MySQL Workbench**.

Primero ejecuta:

```text
database/PRY2203_EFT_S9_Script_crea_tablas_biblioteca.sql
```

Después ejecuta:

```text
database/PRY2203_EFT_S9_Script_poblado_tablas_biblioteca.sql
```

La base de datos creada se llama:

```text
biblioteca
```

---

### 4. Configurar MySQL Connector/J

El proyecto incluye el conector JDBC:

```text
lib/mysql-connector-j-26.7.0.jar
```

La dependencia se encuentra registrada en:

```text
SistemaBibliotecaEscolar.iml
```

Puede comprobarse mediante:

```text
File
→ Project Structure
→ Modules
→ Dependencies
```

donde debe aparecer:

```text
mysql-connector-j-26.7.0.jar
Scope: Compile
```

Si IntelliJ no reconoce automáticamente la dependencia, puede agregarse manualmente desde la carpeta `lib`.

---

### 5. Configurar las credenciales locales

Dentro de:

```text
config/
```

se incluye:

```text
database.properties.example
```

Crea una copia con el nombre:

```text
database.properties
```

y configura:

```properties
db.url=jdbc:mysql://127.0.0.1:3306/biblioteca
db.usuario=root
db.contrasena=TU_CONTRASENA
```

El archivo:

```text
config/database.properties
```

se encuentra excluido mediante `.gitignore`.

---

### 6. Ejecutar la aplicación

Abre:

```text
src/main/Main.java
```

y ejecuta:

```java
main()
```

La aplicación mostrará:

```text
VentanaLogin
```

---

## 🔑 Usuarios de prueba

Los datos iniciales permiten utilizar, entre otras, la cuenta de bibliotecario:

```text
Correo: antonia@correo.cl
Contraseña: clave123
Rol: bibliotecario
```

También existen usuarios con rol estudiante.

Por ejemplo:

```text
Correo: carlos@correo.cl
Contraseña: clave123
Rol: estudiante
```

---

## 🖥️ Flujo general de funcionamiento

```text
Inicio
  ↓
VentanaLogin
  ↓
ControladorUsuarios
  ↓
UsuarioDAO
  ↓
MySQL
  ↓
¿Credenciales válidas?
  │
  ├── No → mensaje de error
  │
  └── Sí
       ↓
VentanaPrincipal
       ↓
Comprobar rol
       │
       ├── Bibliotecario
       │      ↓
       │   Libros
       │   Estudiantes
       │   Préstamos y Devoluciones
       │   Reportes
       │
       └── Estudiante
              ↓
           Consulta de Libros
           Préstamos y Devoluciones
```

---

## 🔐 Acceso seguro a datos

Las operaciones SQL utilizan:

```java
PreparedStatement
```

para evitar concatenar directamente los datos ingresados dentro de las consultas.

Los resultados se recuperan mediante:

```java
ResultSet
```

Los recursos JDBC se manejan mediante:

```java
try-with-resources
```

Las operaciones críticas utilizan:

```text
COMMIT
ROLLBACK
```

para mantener la consistencia de los datos.

Las credenciales de MySQL se almacenan localmente fuera del código fuente mediante:

```text
database.properties
```

---

## 💾 Persistencia de datos

La información se almacena de manera persistente en:

```text
MySQL
```

siguiendo el flujo:

```text
Java Swing
    ↓
Controladores
    ↓
DAO
    ↓
DAOImpl
    ↓
JDBC
    ↓
MySQL
    ↓
biblioteca
```

Esto permite cerrar y volver a ejecutar la aplicación sin perder los registros almacenados.

---

## 🧪 Pruebas realizadas

Durante el desarrollo se realizaron pruebas sobre:

```text
Conexión con MySQL
Autenticación correcta e incorrecta
CRUD de usuarios
CRUD de estudiantes
CRUD de categorías
CRUD de libros
CRUD de préstamos
Registro de préstamos
Actualización de stock
Registro de devoluciones
Prevención de doble devolución
COMMIT
ROLLBACK
Préstamos concurrentes
Control de stock ante múltiples hilos
Permisos según rol
Creación de usuario al registrar estudiante
Reportes
```

También se realizaron pruebas con múltiples solicitudes concurrentes sobre un mismo libro para comprobar que:

```text
El stock nunca sea negativo
No existan más préstamos exitosos que unidades disponibles
Todos los hilos finalicen correctamente
```

---

**Repositorio GitHub:** Repositorio GitHub: https://github.com/mauvalenzuelaf-oss/SistemaBibliotecaEscolar

**Fecha de Entrega**: 11/10/2026