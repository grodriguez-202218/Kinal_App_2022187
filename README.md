## KinalApp_2022187
API REST desarrollada con Spring Boot para la gestión de clientes, usuarios, productos, ventas y detalle de ventas.
## Tecnologías
* **java 21**
* **Spring Boot 4.0.2**
* **Maven** (Gestor de dependencias)
* **MySQL** (Sistema Gestor de Base de Datos)

## Requisitos Previos
Antes de ejecutar la aplicación, debe tener instalado:
* JDK 17 o superior
* Maven Instalado
* Una instancia activa en MySQL

## Instalación y Ejecución
1. Clonar el repositorio(gh repo clone grodriguez-202218/Kinal_App_2022187)
2. Abrir el proyecto en IntelliJ IDEA
3. Compilar el proyecto
4. Ejecutar "KinalAppApplication"
5. Descargar la carpeta de postman para poder acceder a los endpoints especificos.
6. Usar la ruta de (http://localhost:8088/) para poder acceder o entrar a la pagina principal del proyecto.

## Estructura del proyecto
src/
└── main/
└── java/
└── com/gahelrodriguez/kinalapp/
│
├── controller/
│   ├── ClienteController.java
│   ├── DetalleVentaController.java
│   ├── ProductoController.java
│   ├── UsuarioController.java
│   └── VentaController.java
│
├── entity/
│   ├── Cliente.java
│   ├── DetalleVenta.java
│   ├── Producto.java
│   ├── Usuario.java
│   └── Venta.java
│
├── repository/
│   ├── ClienteRepository.java
│   ├── DetalleVentaRepository.java
│   ├── ProductoRepository.java
│   ├── UsuarioRepository.java
│   └── VentaRepository.java
│
├── service/
│   ├── IClienteService.java
│   ├── ClienteService.java
│   ├── IDetalleVentaService.java
│   ├── DetalleVentaService.java
│   ├── IProductoService.java
│   ├── ProductoService.java
│   ├── IUsuarioService.java
│   ├── UsuarioService.java
│   ├── IVentaService.java
│   └── VentaService.java
│
└── KinalAppApplication.java