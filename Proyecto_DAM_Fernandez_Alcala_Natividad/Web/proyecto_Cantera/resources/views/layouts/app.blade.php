<!doctype html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- CSRF Token -->
    <meta name="csrf-token" content="{{ csrf_token() }}">

    {{-- <title>{{ config('app.name', 'Cantera Foncanal') }}</title> --}}
    <title>Cantera Foncanal</title>

    <link rel="shortcut icon" href="{{ asset('img/dumper.png') }}">
    
    <!-- Fonts -->
    <link rel="dns-prefetch" href="//fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css?family=Nunito" rel="stylesheet">

    <!-- CSS Bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">

    <!-- Styles -->
    <link rel="stylesheet" href="{{ asset('css/app.css') }}" >
    <link rel="stylesheet" href="{{ asset('css/btnFlotante.css')}}">
    <link rel="stylesheet" href="{{ asset('css/styleMenu.css') }}">
    <link rel="stylesheet" href="{{ asset('css/mainTabla.css') }}">
    <link rel="stylesheet" href="{{ asset('css/styleLogin.css')}}">
    @yield('link')


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.7.0/css/buttons.bootstrap4.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.2.7/css/responsive.bootstrap4.min.css">
    
    <script src="https://kit.fontawesome.com/a076d05399.js"></script>

</head>
<body>
    <div id="app">
        <nav id="navMenu">
            <div class="logo">
                <a href="{{ url('/')}}">Cantera<span class="tipoLetra">Foncanal</span></a>                                           
            </div>
            <label for="btn" class="icon">
            <span class="fa fa-bars btnCancelar"></span>
            </label>
            <input class="checkbox-input" type="checkbox" id="btn">
            <ul>
                @guest
                    @if (Route::has('login'))
                        <li>
                            <a class="titulo" href="{{ route('login') }}">{{ __('Iniciar Sesión') }}</a>
                        </li>
                    @endif
                    
                    @if (Route::has('register'))
                        <li>
                            <a class="titulo" href="{{ route('register') }}">{{ __('Registrarse') }}</a>
                        </li>
                    @endif                    
                @else  
                    <li class="{{ Request::is('home') ? 'active' : '' }}"><a class="cmbColorMA" href="{{ route('home')}}">Inicio</a></li>
                    {{-- <li class="{{ Request::is('productos') ? 'active' : '' }}">
                        <label for="btn-10" class="show">Productos +</label>
                        <a class="cmbColor" href="#">Productos</a>
                        <input class="checkbox-input" type="checkbox" id="btn-10">
                        <ul class="posicion">  
                            
                            <li class="prueba2"><a class="cmbColor" href="{{ route('productos.index') }}">Productos</a></li>
                            <li class="prueba2"><a class="cmbColor" href="{{ route('stock_productos.index') }}">Stock Productos</a></li>
                            <div class="arrow"></div>                            
                        </ul>
                    </li>
                    <li>
                        <label for="btn-11" class="show">Consumos +</label>
                        <a href="#">Consumos</a>
                        <input class="checkbox-input" type="checkbox" id="btn-11">
                        <ul class="posicion">  
                            <li class="prueba2"><a href="{{ route('consumos.index') }}">Consumos</a></li>
                            <li class="prueba2"><a href="{{ route('stock_consumos.index') }}">Stock Consumos</a></li>
                            <div class="arrow"></div>                            
                        </ul>
                    </li>
                    <li class="{{ Request::is('voladuras') ? 'active' : '' }}"><a href="{{ route('voladuras.index') }}">Voladuras</a></li> --}}
                    @if (Auth::user()->role_id == 1 || Auth::user()->role_id == 2)
                    {{-- Request.is -> Itera sobre los argumentos, es como si recorriera un for --}}
                        <li class="{{ Request::is('consumos','stock_consumos','productos','stock_productos','voladuras','empleados') ? 'active' : '' }}">
                            <label for="btn-2" class="show cmbColorM">Departamentos +</label>
                            <a class="cmbColorM" href="#">Departamentos</a>
                            <input class="checkbox-input" type="checkbox" id="btn-2">
                            <ul class="posicion">

                                <li><a href="#">Dirección Técnica</a></li>

                                <li><a href="#">Administración</a></li>

                                {{-- <li><a href="#">Contabilidad</a></li> --}}
                                <li class="{{ Request::is('consumos','stock_consumos') ? 'active' : '' }}">
                                    <label for="btn-5" class="show submenu">Consumos +</label>
                                    <a class="cmbColor" href="#">Consumos<i class="fas fa-plus"></i></span></a>
                                    <input class="checkbox-input" type="checkbox" id="btn-5">
                                    <ul class="ulCmbColor">

                                        <li class="{{ Request::is('consumos') ? 'active' : '' }} prueba2"><a class="cmbColorA" href="{{ route('consumos.index') }}">Consumos</a></li>
                                        <li class="{{ Request::is('stock_consumos') ? 'active' : '' }} prueba2"><a class="cmbColorA" href="{{ route('stock_consumos.index') }}">Stock Consumos</a></li>
                                        <div class="arrow"></div>

                                    </ul>
                                </li> 

                                <li class="{{ Request::is('productos','stock_productos') ? 'active' : '' }}">
                                    <label for="btn-4" class="show submenu">Productos +</label>
                                    <a class="cmbColor" href="#">Productos<i class="fas fa-plus"></i></span></a>
                                    <input class="checkbox-input" type="checkbox" id="btn-4">
                                    <ul>

                                        <li class="{{ Request::is('productos') ? 'active' : '' }} prueba2"><a class="cmbColorA" href="{{ route('productos.index') }}">Productos</a></li>
                                        {{-- <li class="{{ Request::is('voladuras') ? 'active' : '' }} prueba2"><a class="cmbColorA" href="{{ route('voladuras.index') }}">Voladuras</a></li> --}}
                                        <li class="{{ Request::is('stock_productos') ? 'active' : '' }} prueba2"><a class="cmbColorA" href="{{ route('stock_productos.index') }}">Stock Productos</a></li>
                                        <div class="arrow"></div>

                                    </ul>
                                </li>

                                <li class="{{ Request::is('voladuras') ? 'active' : '' }}"><a class="cmbColor" href="{{ route('voladuras.index') }}">Voladuras</a></li>
                                
                                <li><a href="#">Proveedores</a></li>

                                {{-- <li><a href="#">Marketing</a></li> --}}

                                @if (Auth::user()->role_id == 1)
                                    <li class="{{ Request::is('empleados' ) ? 'active' : '' }}">
                                        <label for="btn-3" class="show submenu">RRHH +</label><!-- Recursos Humanos-->
                                        <a class="cmbColor" href="#">RRHH<i class="fas fa-plus"></i></span></a>
                                        <input class="checkbox-input" type="checkbox" id="btn-3">
                                        <ul>

                                            <li class="{{ Request::is('empleados' ) ? 'active' : '' }} prueba2"><a class="cmbColorA" href="{{ route('empleados.index') }}">Empleados</a></li>{{-- Contratación --}}
                                            <li class="prueba2"><a href="#">Formación</a></li>
                                            <li class="prueba2"><a href="#">Carnet</a></li>
                                            <div class="arrow"></div>

                                        </ul>
                                    </li>
                                @endif
                                <div class="arrow"></div>
                            </ul>
                        </li>

                        <li class="{{ Request::is('presencialidad' ) ? 'active' : '' }}"><a class="cmbColorMA" href="{{ route('presencialidad.index') }}">Control Horario</a></li>
                    @endif                        
                        {{-- <li><a href="#">Contacto</a></li> --}}
                        <li>
                            <label for="btn-1" class="show"><i class="fas fa-user mr-2"></i> {{ Auth::user()->name }} + </label>
                            <a href="#"><i class="fas fa-user mr-2"></i>{{ Auth::user()->name }}</a>
                            <input class="checkbox-input" type="checkbox" id="btn-1">
                            <ul class="posicion">
                                <li class="prueba"><a href="{{ route('logout') }}" onclick="event.preventDefault(); document.getElementById('logout-form').submit();">
                                        {{ __('Salir') }}
                                    </a>
                                    <form id="logout-form" action="{{ route('logout') }}" method="POST" class="d-none">
                                        @csrf
                                    </form>
                                </li>
                                <div class="arrow"></div>                                                    
                            </ul>
                        </li>             
                @endguest
            </ul>
        </nav>
        
            <main class="py-2">{{-- py-4 --}}
                <nav id="bread" class="pb-3">
                    <ol class="breadcrumb breadColor">                        

                        @if (trim($__env->yieldContent('breadcrumb')))
                        <li class="breadcrumb-item">
                            <a href="{{ route('home')}}">Inicio</a>
                        </li>

                        @yield('breadcrumb')

                        @endif
                    </ol>
                </nav>
                @yield('content')
            </main>
    </div>

    <!-- Scripts -->
    <script src="{{ asset('js/app.js') }}" {{-- defer --}}></script>
    <!-- Jquery -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <!-- Boostrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
    <!-- ToolTip -->
    <script src="{{ asset('js/tooltip.js') }}" {{-- defer --}}></script>
    <!-- JS DataTable -->
    <script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
    
    <script src="{{ asset('js/mainTabla.js') }}"></script>

    <script src="https://cdn.datatables.net/buttons/1.7.0/js/dataTables.buttons.min.js" ></script>
    <script src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js" ></script>
    <script src="https://cdn.datatables.net/buttons/1.7.0/js/buttons.bootstrap4.min.js" ></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js" ></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js" ></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js" ></script>
    <script src="https://cdn.datatables.net/buttons/1.7.0/js/buttons.html5.min.js" ></script>
    <script src="https://cdn.datatables.net/buttons/1.7.0/js/buttons.print.min.js" ></script>
    <script src="https://cdn.datatables.net/buttons/1.7.0/js/buttons.colVis.min.js" ></script>
    <script src="https://cdn.datatables.net/responsive/2.2.7/js/dataTables.responsive.min.js"></script>
    <script src="https://cdn.datatables.net/responsive/2.2.7/js/responsive.bootstrap4.min.js"></script>    

    <script>
        $('.icon').click(function(){
          $('span').toggleClass("cancel");
        });
     </script>
</body>
</html>
