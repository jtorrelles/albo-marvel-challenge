# API Marvel Challenge

 Servicio encargado de mantener actualizado diariamente el listado de colaboradores y personajes de marvel relacionados con los Super Heroes Capitan America y Iron Man con la intencion de ofrecer un vializacion simple de los datos y asi pagar regalias a cada colaborador.

## Requisitos de Instalacion
Se debe tener instalado Java 11 para su ejecucion, para terminos de simplicidad se utilizo una base de datos en memoria.

## Instalacion
Se deben seguir los siguientes pasos:
1- Ejecutar el archivo assemble.sh ubicado en la raiz del proyecto el cual  ejecutara el ciclo de build incluyendo los tests de la aplicacion.
2- Ejecutar el archivo avengers.sh ubicado en la raiz del proyecto el levantara la aplicacion en el puerto 80 del host 

## Instalacion ALternativa
Se puede realizar la instalacion de la aplicacion ya sea mediante el wrapper de maven o creando una imagen que luego puede ser ejecutada en containers:

- ##### Maven 
Se utiliza el wrapper de maven el cual esta versionado en los source del repositorio y se deberan seguir los siguentes pasos:
```sh 
./mvnw clean install
java -jar target/app.jar
```

## Documentacion
La api expone un swagger en la siguiente url:
[Swagger](http:localhost:80/swagger-ui.html "Swagger")

Se detalle aca el listado de los endpoints disponibles:

> GET http://localhost:80/colaborators/capamerica

> GET http://localhost:80/characters/capamerica