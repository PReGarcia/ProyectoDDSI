========================================================================
PROYECTO DDSI - APLICACION JAVA CON MARIADB
========================================================================

DESCRIPCION:
Este proyecto conecta una aplicacion Java (Hibernate) con una base de 
datos MariaDB simulando una aplicacion de gestión de un gimnasio.

CONFIGURACION DE CONEXION 
------------------------------------------------------------------------
Para que la aplicacion funcione, es OBLIGATORIO editar la configuracion
de Hibernate para que apunte a la base de datos. 

Si no haces esto, obtendras errores de conexion.

Debes modificar los siguientes dos archivos:

A) ARCHIVO: src/main/resources/hibernate.cfg.xml
   - Busca la linea <property name="connection.url">
   - Cambia cualquier IP interna (como 172.x.x.x) por "localhost".
   - Ejemplo correcto: jdbc:mariadb://localhost:3306/nombre_de_tu_db
   - Verifica que el usuario y la password coincidan con el de la base de datos.

B) ARCHIVO: src/main/java/Config/HibernateUtil.java
   - Cambia la configuracion de conexión del código.