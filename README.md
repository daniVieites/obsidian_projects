# Obsidian_projects

## Prueba de los ejercicios 1 y 2

### Configuración del container de mongo
 
1. Desde el directorio principal ejecutar:

```
docker-compose up -d
```
2. Conectarse al servidor con MongoDB Compass:
    - Hostname: localhost
    - Port: 27017
    - Authentication: Username / Password
    - Username: root
    - Password: root

3. Crear una base de datos con nombre "obsidian" y una colección llamada "node".

### Iniciar la aplicación

Ejecutar desde el directorio principal:

```
java -jar prueba/target/prueba-0.0.1-SNAPSHOT.jar
```

### Pruebas

Utilizando Postman, realizar una petición POST a la URL http://localhost:8080/insert e introducir en el cuerpo de la petición, en formato JSON, cada uno de los nodos que se encuentran en el archivo nodes.json, que se encuentra en la raíz del repositorio (una petición por cada nodo).

Esto creará un árbol con la siguiente estructura:

![árboles](/images/trees.png)

De esta forma queda comprobado el funcionamiento del endpoint de inserción de nodos.

Para obtener todos los nodos introducidos, y comprobar así el endpoint de listado de nodos, hay que realizar una petición GET a la URL http://localhost:8080/nodes.

Mediante una petición GET a la URL http://localhost:8080/trees se puede ver la estructura de padres e hijos correspondiente al árbol creado.

## Prueba del ejercicio 3

En el directorio /obsidian_projects/ejercicio3 se encuentra el archivo .java correspondiente a este ejercicio.

Ejecutar el comando java Ejercicio3.java para probarlo.

Casos de uso:

![casos](/images/casos.png)