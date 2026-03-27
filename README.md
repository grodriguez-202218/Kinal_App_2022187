## KinalApp - API REST
API REST desarrollada con Spring Boot para la gestión de clientes, usuarios, productos, ventas.
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

## Estructura del proyecto
src/
└── main/
└── java/
└── com/gahelrodriguez/kinalapp/
│
├── controller/
│   ├── ClienteController.java
│   ├── ProductoController.java
│   ├── UsuarioController.java
│   └── VentaController.java
│
├── entity/
│   ├── Cliente.java
│   ├── Producto.java
│   ├── Usuario.java
│   └── Venta.java
│
├── repository/
│   ├── ClienteRepository.java
│   ├── ProductoRepository.java
│   ├── UsuarioRepository.java
│   └── VentaRepository.java
│
├── service/
│   ├── IClienteService.java
│   ├── ClienteService.java
│   ├── IProductoService.java
│   ├── ProductoService.java
│   ├── IUsuarioService.java
│   ├── UsuarioService.java
│   ├── IVentaService.java
│   └── VentaService.java
│
└── KinalAppApplication.java