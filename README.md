## KinalApp - API REST
API REST desarrollada con Spring Boot para la gestión de clientes y usuarios.
## Tecnologías
* **java 17**
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
2. Abrir en IntelliJ IDEA
3. Compilar el proyecto
4. Ejecutar la clase Main

## Estructura del proyecto
src/main/java/com/gahelrodriguez/kinalapp/
│
├── controller/
│   ├── ClienteController.java
│   └── UsuarioController.java
│
├── entity/
│   ├── Cliente.java
│   └── Usuario.java
│
├── repository/
│   ├── ClienteRepository.java
│   └── UsuarioRepository.java
│
├── service/
│   ├── IClienteService.java
│   ├── ClienteService.java
│   ├── IUsuarioService.java
│   └── UsuarioService.java
│
└── KinalAppApplication.java