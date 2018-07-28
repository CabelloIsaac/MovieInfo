# MovieInfo

Muestra una lista de las películas y shows de televisión ordenados por Más populares, Mejor puntuados o Por estrenar obtenidos de la API de TheMovieDB https://developers.themoviedb.org/3/

# Configuración

Registrarse en TheMovieDB para obtener una API Key para su proyecto.
</br>Acceder a la clase <b>com.ics.apps.movieinfo.libs.WebServices</b> para incluir la API Key obtenida de The Movie DB
</br>Las consultas proporcionadas por la API están en formato JSON y son procesadas con la librería Volley
</br>Las imágenes son procesadas con la librería Picasso.
</br>Todas las consultas que se realicen y se muestren en pantalla son almacenadas en Caché para su uso sin conexión a Internet. El caché permanecerá guardado durante 24 horas.
</br>Requiere Android 4.1 o superior.

# Capas de la aplicación

### Capa de presentación
Esta capa contiene todo lo que verá el usuario (formularios, vistas, etc).</br>
Archivos XML contenidos en MovieInfo/app/src/main/res/layout</br>
activity_main.xml</br>
activity_tv_show_genres.xml</br>
activity_tv_shows_genres_results.xml</br>
content_detail.xml</br>
fragment_movies_list.xml</br>
fragment_tv_shows_list.xml

### Capa de negociación
Esta capa toma los datos del usuario y genera las consultas con el servidor.</br></br>
Clases:</br>
genres.GenresActivity.java</br>
genres.GenresResultsActivity.java</br>
movies.MoviesListFragment.java</br>
tvshow.TvShowsListFragment.java</br>
DetailActivity</br>

### Capa de Datos
Esta capa posee las direcciones y datos que la aplicación va a manejar y mostrar al usuario.</br></br>
libs.WebServices.java
https://developers.themoviedb.org/3/

# Clases

### Genre
Estructura de datos de las categorías de películas/series

### GenreAdapter
Conecta los datos de una categoría con la pantalla visual al usuario

### GenresActivity
Muestra la lista de categorías de las películas/series

### GenresResultsActivity
Muestra la lista de películas/series de una categoría seleccionada

### Constants
Aquí se declaran las constantes globales a usar en el proyecto

### SquareImageView
Librería para mostrar imágenes con relación de aspecto 1:1

### WebServices
Aquí se declaran las constantes que poseen las direcciones URL para conectarse a la API de TheMovieDB

### Movie
Estructura de datos de las categorías de películas

### MoviesAdapter
Conecta los datos de una película con la pantalla visual al usuario

### MoviesListFragment
Muestra la lista de las películas. Permite ordenarlas por Popular, Top Rated y Upcoming

### TvShow
Estructura de datos de las categorías de series

### tvShowsAdapter
Conecta los datos de una serie con la pantalla visual al usuario

### TvShowsListFragment
Muestra la lista de las series. Permite ordenarlas por Popular y Top Rated

### DetailActivity
Muestra una imagen de portada, la sinopsis y un tráiler de una película o serie seleccionada

### MainActivity
Ventana principal de la aplicación

# Principio de responsabilidad única
Consiste, en mi opinión, en que cada clase debe de encargarse solo de una responsailidad. Es conveniente repartir las tareas en diferentes clases para que los cambios futuros al sistema sean más fáciles de aplicar. Si una clase posee muchas responsabilidades, los cambios a la misma afectarán de forma drástica la aplicación pudiendo ocasionar fallas que retrasen la entrega del proyecto.

# Código limpio
Un buen código debe ser comprensible, bien estructurado y bien documentado. Se deben de seguir las normas de nomenclaturas del lenguaje en el que se trabaja para que cualquier programador pueda leerlo sin problemas.

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

# Créditos

Desarrollado por Isaac Cabello
</br>cabelloisaac97@gmail.com
</br>cabelloisaac.com.ve

# Licencia

MIT License

Copyright (c) 2018 Isaac Cabello

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
