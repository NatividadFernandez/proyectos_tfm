<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Cantera Foncanal</title>

        <link rel="shortcut icon" href="{{ asset('img/dumper.png') }}">

        <!-- CSS Bootstrap -->
        <link rel="stylesheet" href="{{ asset('css/bootstrap.min.css') }}">

        <!-- CDN Font awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css"/>

        <!-- CSS Main -->
        <link rel="stylesheet" href="{{ asset('css/main.css') }}">
       
    </head>
    <body>
        <!-- Header -->
        <header class="header linear-white">
            <div class="container vh-50">
                <!-- Navbar -->
                <nav class="row text-white justify-content-between align-items-center text-uppercase pt-4">
                    <!-- Logo -->
                    <a href="#" class="col-12 col-md-auto text-white text-center mb-3 mb-md-0 cambioMenu">
                    <img src="./img/dumper.png" alt="Logo Weston" class="img-logo">
                    Cantera Foncanal
                    </a>
                    <!-- Ancla -->
                    @if (Route::has('login'))
                        <div class="col-12 col-md-auto d-flex flex-wrap justify-content-around">       
                                <a href="#" class="text-reset pr-3 cambioMenu">Inicio</a> <!-- text-dark-->
                                <a href="#produccion" class="text-reset pr-3 cambioMenu">Producción</a>            
                                <a href="#productos" class="text-reset pr-3 cambioMenu">Productos</a>
                                <a href="#conocenos" class="text-reset pr-3 cambioMenu">Conócenos</a>
                                <a href="#contacto" class="text-reset pr-3 cambioMenu">Contacto</a>
                            @auth                                
                                <a href="{{ url('/home') }}" class="text-reset pr-3 cambioMenu">Home</a>
                            @else   
                                <a href="{{ route('login') }}" class="text-reset cambioMenu">¿Eres cliente?</a> 
                            @endauth           
                        </div>
                    @endif
                </nav>
                <!-- Description -->
                <div class="row h-100 align-items-center">
                    <div class="col-md-10 col-lg-6 text-white">
                    <!-- Title -->
                    <h1 class="text-capitalize"> 
                        <span class="text-warning h5">Belleza y resistencia</span><br/>
                        piedra
                    </h1>
                    <!-- Description -->
                    <p class="d-none d-md-block">
                        En 1980 se funda Cantera Cerro Foncanal, heredera de dos generaciones
                        de los conocimientos de explotación y transformación de la piedra.
                        40 años después, la empresa se mantiene tan firme como la roca que explota.              
                    </p>
                    <!-- Btn -->
                    <a href="#" class="text-reset btn btn-warning">¡Ubícanos!</a>
                    </div>
                </div>
            </div>
            <img src="./img/cuts/cut-header.svg" alt="">
        </header>

        <!-- Experience -->
        <section id="produccion" class="container">
            <div class="row justify-content-md-center">
            <!-- Texts -->
            <div class="col-12 text-center">
                <h2 class="text-warning text-uppercase h6">Producción</h2>
                <h3 class="text-capitalize">Fases de la producción</h3>
                <p class="text-muted">
                Transformamos la piedra extraida del macizo rocoso para
                poder ser utilizada en obra civil y edificación.            
                </p>
            </div>
            <!-- Cards-->
            <!-- Card #1-->
            <article class="col-md-6 col-lg-4 card-effect mb-3 mb-md-0">
                <div class="card">
                <img src="./img/voladuraprueba2.jpg" alt="Voladura" class="card-img card-img-filter">
                <div class="card-img-overlay text-white d-flex flex-column justify-content-center text-center">
                    <h4>Voladura</h4>
                    <p>
                    Arrancamos la piedra del macizo rocoso usando explosivos.
                    La voladura es realizada por empresa expecializada.
                    Obtenemos piedras de un m<sup>2</sup>.
                    <!-- en voladuras controladas. -->
                    </p>
                    <a href="#" class="text-reset">Read more</a>
                </div>
                </div>
            </article>
            <!-- Card #2-->
            <article class="col-md-6 col-lg-4 card-effect mb-3 mb-md-0">
                <div class="card">
                <img src="./img/camionprueba.jpg" alt="Camion" class="card-img card-img-filter">
                <div class="card-img-overlay text-white d-flex flex-column justify-content-center text-center">
                    <h4>Carga</h4>
                    <p>
                    Una vez arracanda la roca, se carga con retroexcavadoras
                    a camiones dumperes, que lo transportan a la planta de trituración.
                    </p>
                    <a href="#" class="text-reset">Read more</a>
                </div>
                </div>
            </article>
            <!-- Card #3-->
            <article class="col-md-6 col-lg-4 card-effect mt-md-4 mt-lg-0">
                <div class="card">
                <img src="./img/plantaprueba.jpg" alt="Planta" class="card-img card-img-filter">
                <div class="card-img-overlay text-white d-flex flex-column justify-content-center text-center">
                    <h4>Tituración y Clasificación</h4>
                    <p>
                    La piedra procedente de la voladura se descarga en la planta de trituración.
                    Alli se recude su tamaño a centímetros y milímetros.<!-- hasta conseguir los demandados por la industria hormigonera y
                    del asfalto. -->
                    </p>
                    <a href="#" class="text-reset">Read more</a>
                </div>
                </div>
            </article>
            </div>
        </section>

        <!-- Commets -->
        <section class="comments linear-white mb-5">
            <img src="./img/cuts/cut-header.svg" alt="" class="transform-turn">
            <div class="container">
                <div class="row">
                    <blockquote class="col-12 text-center cambio2"><!--text-white-->
                    <!-- img -->
                    <img src="./img/Andy_Goldsworthy2.jpg" alt="John Doe" class="commets_img mb-2">
                    <!-- comment -->
                    <p>
                        Hay vida en una piedra. Cualquier piedra que se asiente
                        en un campo o en una playa toma el recuerdo de ese lugar.
                        Puedes sentir que las piedras han sido testigos de tantas cosas.
                    </p>
                    <!-- name -->
                    <footer class="blockquote-footer cambio2"><!--text-white-->
                        <cite>Andy Goldsworthy</cite>
                    </blockquote>
                </div>
            </div>
        </section>

        <!-- Inspiration -->
        <section id="productos" class="container">
            <div class="row">
            <!-- Title -->
            <div class="col-12 text-center">
                <h3 class="text-warning text-uppercase h6">Productos</h3>
                <h2 class="text-capitalize">Áridos clasificados, Escolleras y Mamposterías</h2>
            </div>
            <!-- Media object #1 -->
            <ul class="col-12 col-md-6">
                <li class="media">
                <img src="./img/sparks/arena.jpeg" alt="Boat" class="media-object-img">
                <div class="media-body">
                    <h4 class="h5">
                    <a href="#" class="text-reset">Arena</a>
                    </h4>
                    <h5 class="h6 mb-0 mb-1">Tamaño 0-6mm</h5>              
                    <p>
                    Hormigón, capas de rodadura(carreteras), revoque.
                    </p>
                </div>
                </li>
                <li class="media">
                <img src="./img/sparks/gravilla.jpg" alt="Signal" class="media-object-img">
                <div class="media-body">
                    <h4 class="h5">
                    <a href="#" class="text-reset">Gravas</a>
                    </h4>
                    <h5 class="h6 mb-0 mb-1">Tamaño 6-12mm, 12-20mm, 20-32mm</h5>              
                    <p>
                    Hormigón, capas de rodadura, filtros,
                    jardínes, caminos.
                    </p>
                </div>
                </li>
                <li class="media">
                <img src="./img/sparks/mortero.jpg" alt="Town" class="media-object-img">
                <div class="media-body">
                    <h4 class="h5">
                    <a href="#" class="text-reset">Árido para Mortero</a>
                    </h4>
                    <h5 class="h6 mb-0 mb-1">Tamaño 0-12mm</h5>              
                    <p>
                    Aparejar elementos de construcción, como
                    ladrillos, piedras o bloques de hormigón.
                    </p>
                </div>
                </li>
            </ul>
            <!-- Media object #2 -->
            <ul class="col-12 col-md-6">          
                <li class="media">
                <img src="./img/sparks/escollera.jpg" alt="Signal" class="media-object-img">
                <div class="media-body">
                    <h4 class="h5">
                    <a href="#" class="text-reset">Escolleras</a>
                    </h4>
                    <h5 class="h6 mb-0 mb-1">Peso 1000-3000kg</h5>              
                    <p>
                    Puertos, diques, presas y muros.
                    </p>
                </div>
                </li>
                <li class="media">
                <img src="./img/sparks/mamposteria.jpg" alt="Town" class="media-object-img">
                <div class="media-body">
                    <h4 class="h5">
                    <a href="#" class="text-reset">Mampostería</a>
                    </h4>
                    <h5 class="h6 mb-0 mb-1">Tamaño 30-50cm</h5>              
                    <p>
                    Diques, muros y cerramientos.
                    </p>
                </div>
                </li>
                <li class="media">
                <img src="./img/sparks/zahorra.jpg" alt="Boat" class="media-object-img">
                <div class="media-body">
                    <h4 class="h5">
                    <a href="#" class="text-reset">Zahorra</a>
                    </h4>
                    <h5 class="h6 mb-0 mb-1">Tamaño 0-32mm</h5>              
                    <p>
                    Caminos rurales, soleras de naves y viviendas, 
                    bases y subbases(carreteras).
                    </p>
                </div>
                </li>
            </ul>
            </div>
        </section>

        <!-- Carousel -->
        <section class="carousel slide" id="carousel-weston">
            <div class="carousel-caption carousel-weston-middle">
            <h2 class="d-none d-md-block text-uppercase cambio">Apostamos por una minería sostenible</h2>
            <p class="d-none d-lg-block cambio3">
                La restauración de las canteras, es la última etapa de un proceso de 
                explotación imprescindible para fabricar un producto surgido de la NATURALEZA.
            </p>
            <!-- <button class="d-none d-md-block btn btn-light px-4 text-capitalize mx-auto">
                Let's go!
            </button> -->
    
            </div>
            <!-- Slides -->
            <div class="carousel-inner">
            <!-- Slide #1 -->
            <div class="carousel-item active">
                <img src="./img/carousel/cantera22.jpg" alt="Camping" class="d-block w-100">
            </div>
            <!-- Slide #2 -->
            <div class="carousel-item">
                <img src="./img/carousel/arbol1.jpg" alt="Boat" class="d-block w-100">
            </div>
            <!-- Slide #3 -->
            <div class="carousel-item">
                <img src="./img/carousel/resta1.jpg" alt="Town" class="d-block w-100">
            </div>
            </div>
            <a href="#carousel-weston" class="carousel-control-prev" data-slide="prev">
            <span class="carousel-control-prev-icon"></span>
            </a>
            <a href="#carousel-weston" class="carousel-control-next" data-slide="next">
            <span class="carousel-control-next-icon"></span>
            </a>  
        </section>

        <!-- Features -->
        <section id="conocenos" class="container">
            <div class="row">
            <!-- Title -->
            <h2 class="col-12 text-center">
                <span class="text-warning h6 text-uppercase">Conócenos</span><br/>
                    <span class="text-capitalize">jornada de puertas abiertas</span>              
            </h2>
            <!-- Description #1-->
            <p class="col-12 col-md-6">
                Anualmente abrimos nuestras instalaciones al público en general 
                y a nuestros vecinos en particular. Pretendemos hacer cercana la
                actividad. Se visiona un audiovisual con todas las activiades que
                se realizan dentro de la cantera. Paseamos por el interior de las
                zonas de extracción, mostramos la maquinária, los sistemas de trituración.
            </p>
            <!-- Description #2-->
            <p class="col-12 col-md-6">
                Explicamos las medidas correctoras medioambientales 
                y sostenibles. Los escolares del pueblo junto con los operarios de la explotación,
                plantan árboles que contribuyen a la reforestación de la cantera. Compartimos 
                un pequeño almuerzo con productos locales.
    
            </p>
            <!-- Questions -->
            <div class="col-6 col-md-3 text-center">
                <h3 class="text-warning h6">¿Día?</h3>
                <p class="font-weight-bold text-capitalize">20 de Mayo del 2021</p>
            </div>
            <div class="col-6 col-md-3 text-center">
                <h3 class="text-warning h6">¿Dónde?</h3>
                <p class="font-weight-bold text-capitalize">Cantera Foncanal, La Calahorra-Granada</p>
            </div>
            <div class="col-6 col-md-3 text-center">
                <h3 class="text-warning h6">¿Cuánto tiempo?</h3>
                <p class="font-weight-bold text-capitalize">09:00AM - 13:00PM</p>
            </div>
            <div class="col-6 col-md-3 text-center">
                <h3 class="text-warning h6">¿Qué llevar?</h3>
                <p class="font-weight-bold text-capitalize">Calzado cómodo y antideslizante, ropa cómoda y
                protector solar</p>
            </div>
            <!-- btn -->
            <div class="w-100"></div>
            <!-- <button class="col-auto btn btn-warning text-white mx-auto my-4 text-capitalize">
                !Apuntate¡
            </button>  -->       
            </div>
        </section>

        <!-- Boton Arriba -->
        <div id="button-up"><i class="fas fa-chevron-up"></i></div>

        <!-- Footer -->
        <footer id="contacto" class="bg-dark text-white py-4">
            <div class="container">
            <nav class="row justify-content-center">
                <!-- Logo -->
                <a href="3" class="col-12 col-md-3 text-reset text-uppercase d-flex align-items-center justify-content-center mb-3 mb-md-0">
                <img src="./img/dumper.png" alt="Logo Weston" class="img-logo mr-2">Cantera Foncanal
                </a>
                <!-- Menu #1 -->
                <ul class="col-5 col-md-3 list-unstyled">
                <li class="font-weight-bold text-uppercase">Asociaciones</li>
                <li><a href="https://www.aridos.org/" class="text-reset">ANEFA</a></li>
                <li><a href="https://www.oficemen.com/" class="text-reset">OFICEME</a></li>
                <li><a href="https://www.anefhop.com/" class="text-reset">ANEFHOP</a></li>
                </ul>
                <!-- Menu #2 -->
                <ul class="col-5 col-md-3 list-unstyled">
                <li class="font-weight-bold text-uppercase">Otras Canteras</li>
                <li><a href="https://dogilo.com/" class="text-reset">HDG</a></li>
                <li><a href="http://www.loslinos.es/" class="text-reset">Los Linos</a></li>
                <li><a href="https://www.aridosanfersa.com/" class="text-reset">Aridos Anfersa</a></li>
                </ul>
                <!-- social networks -->
                <ul class="col-10 col-md-3 list-unstyled">
                <li class="font-weight-bold text-uppercase">Redes Sociales</li>
                <li class="d-flex justify-content-between">
                    <!-- facebook -->
                    <a href="#" class="text-reset"><i class="fab fa-facebook-f"></i></a>
                    <!-- twitter -->
                    <a href="#" class="text-reset"><i class="fab fa-twitter"></i></a>
                    <!-- instagram -->
                    <a href="#" class="text-reset"><i class="fab fa-instagram"></i></a>
                    <!-- pinteres -->
                    <a href="#" class="text-reset"><i class="fab fa-pinterest"></i></a>
                </li>
                </ul>
            </nav>
            </div>
        </footer>

        <!-- btn jquery -->
        <script src="./js/flecha.js"></script>
        <!-- Jquery -->
        <script src="./js/jquery-3.5.0.min.js"></script>
        <!-- Popper -->
        <script src="./js/popper.min.js"></script>    
        <!-- JS Bootstrap -->
        <script src="./js/bootstrap.min.js"></script>
       
    </body>
</html>
