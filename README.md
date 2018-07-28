# MovieInfo

Muestra una lista de las películas y shows de televisión ordenados por Más populares, Mejor puntuados o Por estrenar obtenidos de la API de TheMovieDB https://developers.themoviedb.org/3/

# Configuración

Registrarse en TheMovieDB para obtener una API Key para su proyecto.
</br>Acceder a la clase <b>com.ics.apps.movieinfo.libs.WebServices</b> para incluir la API Key obtenida de The Movie DB
</br>Las consultas proporcionadas por la API están en formato JSON y son procesadas con la librería Volley
</br>Las imágenes son procesadas con la librería Picasso.
</br>Todas las consultas que se realicen y se muestren en pantalla son almacenadas en Caché para su uso sin conexión a Internet. El caché permanecerá guardado durante 24 horas.
</br>Requiere Android 4.1 o superior.

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
