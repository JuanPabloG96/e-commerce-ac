# Práctica 2: Arquitecturas en Capas

## Caso práctico de E-Commerce en Arquitectura en Capas

### Descripción del Proyecto

Este proyecto consiste en la implementación de una aplicación web de comercio electrónico basada en una arquitectura en capas utilizando el servidor Java Tomcat. La aplicación está diseñada para ser escalable y mantenible, con una clara separación de responsabilidades a través de diferentes capas.

### Objetivo

Desarrollar una aplicación web de comercio electrónico que incluya seis módulos fundamentales:

1. **Gestión de Productos**: Módulo para agregar, editar y eliminar productos.
2. **Gestión de Usuarios**: Módulo para el registro, autenticación y gestión de usuarios.
3. **Gestión de Pedidos**: Módulo para la creación, seguimiento y gestión de pedidos.
4. **Carrito de Compras**: Módulo que permite a los usuarios agregar productos y gestionar su carrito.
5. **Gestión de Pagos**: Módulo para procesar y gestionar pagos de pedidos.
6. **Administración**: Módulo para la gestión general de la aplicación, incluyendo usuarios y productos.

### Estructura del Proyecto

La aplicación está dividida en varias capas para mejorar su organización y mantenimiento:

- **Capa de Presentación**: Interfaz de usuario que interactúa con los usuarios finales.
- **Capa de Lógica de Negocio**: Contiene la lógica del negocio y las reglas de la aplicación.
- **Capa de Acceso a Datos**: Gestiona la interacción con la base de datos y el almacenamiento de datos.

### Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal.
- **Apache Tomcat**: Servidor web para desplegar la aplicación.
- **HTML/CSS/JavaScript**: Tecnologías para el desarrollo de la interfaz de usuario.
- **Base de Datos (MySQL/PostgreSQL)**: Para la gestión de datos.

### Requisitos

- Java JDK 17 o superior.
- Apache Tomcat.
- Configuración de base de datos.
