# Introduccion 
Jurex-docs-extractor es un proceso batch encargado de obtener los contratos alojados en la base de Jurex.
Los mismos son convertidos de Blob a archivos.Estos son colocados en un directorio del sistema,
 complementario a esto se persiste una referencia de el en una base de datos con la fecha de procesamiento. 
# Compilación

Para compilar el proyecto es necesario contar con la instalacion de Maven. Una vez instalado puede ejecutar 
```sh
$ mvn clean install
```
El Jar quedara dentro de la carpeta "target" listo para ser ejecutado .

La dependencia de Oracle JDBC que se encuentra en el archivo `pom.xml` referencia al repositorio Maven de Oracle.
Para utilizar dicho repositorio hay que tener un cuenta registrada en oracle y configurarlo en el archivo settings.xml (y el archivo `settings-security.xml` para alojar de manera segura la password).
El procedimiento para lo descripto anteriormente se describe en los pasos 2 al 4 de este [post](https://blogs.oracle.com/dev2dev/get-oracle-jdbc-drivers-and-ucp-from-oracle-maven-repository-without-ides#pom).



# Ejecución y Configuración
Para ejecutar el servicio es necesario tener intalada la openJDK 11 [OpenJDK Download](https://jdk.java.net/archive/)
  
```sh
$ java -jar nombreDelJar
```

El archivo de configuracion se encuentra en el directorio /jurex-docs-extractor/src/main/resources/**application.properties** 

##### Path de los archivos convertidos .
El proceso batch se encarga de convertir los registros en formato Blob en archivos. Los mismos son colocados en un Path el cual es configurable desde la propiedad :
```sh
converter.path-out=/home/user/archivos/
```



##### Credenciales de la base de datos .
El proceso batch contiene dos datasource distintas , esto es debido a que el proceso interactua con dos bases de datos .
Las credenciales de la base de datos de donde se extraeran los documento a ser procesados deben ser ingresados en :  
   
   ```sh
spring.jurex.username=SuUsuario
spring.jurex.password=SuPassword
   ```
Las credenciales de la base de datos donde se colocaran los archivos procesasados deben ser ingresadas en  :
   ```sh
spring.maite.username=SuUsuario
spring.maite.password=SuPassword
   ```

