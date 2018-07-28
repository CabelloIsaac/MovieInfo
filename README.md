# MovieInfo

Muestra una lista de las películas y shows de televisión ordenados por Más populares, Mejor puntuados o Por estrenar obtenidos de la API de TheMovieDB https://developers.themoviedb.org/3/

# Configuración

Registrarse en TheMovieDB para obtener una API Key para su proyecto.
</br>Acceder a la clase <b>com.ics.apps.movieinfo.libs.WebServices</b> para incluir la API Key obtenida de The Movie DB
</br>Las consultas proporcionadas por la API están en formato JSON y son procesadas con la librería Volley
</br>Las imágenes son procesadas con la librería Picasso.
</br>Todas las consultas que se realicen y se muestren en pantalla son almacenadas en Caché para su uso sin conexión a Internet. El caché permanecerá guardado durante 24 horas.

# Uso

#### Ventana principal

##### Menú izquierdo
Se puede alternar entre Películas y Categorías de Películas, y Series de televisión y Categorías de Series de televisión.

##### Películas

Muestra la listas de las películas ordenadas por Popular, Top Rated o Upcoming según se configure en el menú superior derecho.
Se incluye una barra de búsqueda para filtrar películas según su nombre.

##### Series de Televisión

Muestra la listas de las series de televisión ordenadas por Popular o Top Rated según se configure en el menú superior derecho.
Se incluye una barra de búsqueda para filtrar series de televisión según su nombre.

##### Detalles

Al pulsar sobre una película o serie de televisión se muestra la sinopsis de la misma y un tráiler oficial de YouTube. <b>Se requiere la aplicación oficial de YouTube instalada en el dispositivo</b>

Requerimientos:

- Internet
- Sistema Operativo Android 4.1 o superior.
