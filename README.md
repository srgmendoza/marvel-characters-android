# marvel-characters-android


## Descripción

Aplicacion que lista los personajes pertenecientes al Universo Marvel. Desarrollada para plataforma Android, en lenguaje Kotlin, basada en los principios de Arquitectura Clean Code, asi como con patron de diseño MVP.

##Funcionalidad

A nivel de UI el app consta de un solo activity declarado como host para la navegacion en donde se lleva a cabo la interaccion de los usuarios mediante fragments, encargados de renderizar la informacion de la siguiente manera:

- Listado: Presenta el listado de clientes que se obtiene del API expuesto por Marvel, aunque tiene funcionalidad de paginacion, solo implementa los listeners necesarios para su deteccion, la logica inherente a esto, descansa en el useCase correspondiente.
- Detalle: Presenta la inforacion obtenida del personaje previamente seleccionado en el listado, como seria Imagen, Nombre, Descripcion (si posee), informacion adicional acerca de las publicaciones relacionadas, ademas de un boton para dirigirse a un nuevo fragement con un Webwiew que muestra la informacion del personaje en el entorno web de Marvel.
- Webview: Lo mencionado, implementa el webview, para la visualizacion de informacion adicional del personaje en un entorno web.

La data necesaria para poder renderizar estas vistas es gestionada a traves del presenter.

Consta de una capa de dominio, conformada por use cases, cuya ejecucion implementa las funcionalidades requeridas por las vistas.

La obtencion de informacion, y su posterior mapeo a data apta para el dominio, sera realizado a traves de la capa de Data, la cual implementa los repositorios para las llamadas a red, sin perjuicio de poder agregar nuevos origenes de datos, gracias a la abstraccion de estos repositorios mediante el uso de interfaces.


##Tecnologías y librerias utilizadas

- [Koin](https://insert-koin.io): Para manejo de Inyeccion de Dependencias.
- Retrofit 2: Como cliente Http y manejador de peticiones a red.
- Android Navigation Component: Para la navegacion entre fragments.
- Mockito for kotlin: Mocker para pruebas unitarias

##Porque MVP sobre otras alternativas

En principio por el bajo nivel de complejidad, sin sacrificar necesariamente robustez, que otorga al proyecto, ademas de la flexibilidad de poder abstraer la funcionalidad de los presentadores adaptandose asi a los principios propuestos como buenas practicas para Clean Code.


