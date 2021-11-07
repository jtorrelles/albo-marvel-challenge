![Java](https://img.shields.io/badge/JAVA-11-blue)
![Spring](https://img.shields.io/badge/SPRING--BOOT-2.5.6-blue)

# API Marvel Challenge

 Servicio encargado de mantener actualizado diariamente el listado de colaboradores y personajes de marvel relacionados con los Super Heroes Capitan America y Iron Man con la intencion de ofrecer un vializacion simple de los datos y asi pagar regalias a cada colaborador.

## Requisitos de Instalacion
Se debe tener instalado Java 11 para su ejecucion. Para esta solucion en terminos de simplicidad se utilizo una BD H2 en memoria por lo cual no se necesitara instalar un motor de base de datos.

## Instalacion
Se deben seguir los siguientes pasos:
1- Ejecutar el archivo assemble.sh ubicado en la raiz del proyecto el cual  ejecutara el ciclo de build incluyendo los tests de la aplicacion.

```sh
bash assemble.sh
```

2- Ejecutar el archivo avengers.sh ubicado en la raiz del proyecto el levantara la aplicacion en el puerto 80 del host. NOTA: se deben pasar como argumentos tanto el valor de la llave publica y la llave privada de la api de marvel studios en ese orden.

- Ejemplo:

```sh
bash avengers.sh 51f70bb07e8fae9bb1f9328532eca6f8 dff9dc72b32645303726a4184a79c23a1e0469ee
```

## Documentacion
La api expone un swagger en la siguiente url:
[Swagger](http:localhost/swagger-ui.html "Swagger")

Se detalle aca el listado de los endpoints disponibles:

- Colaborators: 

> GET http://localhost/colaborators/capamerica

> GET http://localhost/colaborators/ironman

- Characters: 

> GET http://localhost/characters/capamerica

> GET http://localhost/characters/ironman