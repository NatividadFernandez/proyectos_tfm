@section('link')
    <link rel="stylesheet" href="{{ asset('css/styleShow.css')}}">
@endsection

@section('breadcrumb')
    <li class="breadcrumb-item">
        <a href="{{ route('voladuras.index') }}">Voladuras</a>
    </li>
    <li class="breadcrumb-item active" aria-current="page">
        Ficha Voladura
    </li>
@endsection

@extends('layouts.app')

@section('content')

<section>
    <div class="container">
        <div class="contactInfo">
            <div>
                <h2>Inf. Voladura</h2>
                <ul class="info">
                    <li>
                        <span><i class="fas fa-calendar-day fa-lg"></i></span>
                        <span>{{$voladura->fecha_voladura}}</span>
                    </li>
                    <li>
                        <span><i class="far fa-compass fa-lg"></i></span>
                        <span>{{$voladura->localizacion}}</span>
                    </li> 
                </ul>
            </div>
        </div>

        <div class="contactForm">
            <h2>Ficha Técnica</h2>
            <div class="formBox">
                <div class="inputBox w50">
                    <input type="text" disabled value="{{$voladura->m2_superficie}} m2">
                    <span>Superfície m2</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$voladura->malla_perforacion}} m">
                    <span>Malla de Perforación</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$voladura->profundidad_barrenos}} m">
                    <span>Profuncidad Barrenos</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$voladura->numero_barrenos}}">
                    <span>Número Barrenos</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$voladura->kg_explosivo}} kg">
                    <span>KG. explosivo</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$voladura->precio}} €">
                    <span>Precio</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$voladura->piedra_bruta}} tm">
                    <span>Piedra Bruta</span>
                </div>

                <div class="inputBox w100">
                    <input type="submit" onclick="history.back()" value="Volver atrás">
                </div>
            </div>
        </div>
    </div> 
</section>

@endsection