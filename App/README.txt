PROYECTO MAKITETRIX - DPOO 2016-2017

Para poder ejecutar el proyecto servidor hay que asegurar que esté definido como modulo del proyecto
en la IDE en la que se ejecute. En IntelliJ, si no se tiene definido como modulo:

1. Click derecho en App > New > Module > Next
2. Definir modulo con el nombre "Server" y aceptar la operación

Una vez hecho esto ya se podrán ejecutar cada uno de los módulos desde sus respectivos puntos
de entrada: ClientMain.java y ServerMain.java

IMPORTANTE

Dependiendo del sistema en el que se ejecute el proyecto, la ruta del fichero "config.json" del servidor
hay que definirla de manera diferente. En la clase ServerMain:

Si no funciona con "./App/Server/resources/config.json", utilizar "./Server/resources/config.json".

LIBRERIAS NECESARIAS PARA EJECUTAR EL PROYECTO

· commons-crypto-1.0.0 de Apache
· commons-validator-1.6 de Apache
· mysql-connector-java-5.1.42
· gson-2.8.1