@section('link')
    <link rel="stylesheet" href="{{ asset('css/styleShow.css')}}">
@endsection
@extends('layouts.app')

@section('breadcrumb')
    <li class="breadcrumb-item">
        <a href="{{ route('productos.index') }}">Productos</a>
    </li>
    <li class="breadcrumb-item active" aria-current="page">
        Ficha Producto
    </li>
@endsection

@section('content')

<section>
    <div class="container">
        <div class="contactInfo">
            <div>
                <h2>Inf. producto</h2>
                <ul class="info">
                    <li>
                        <span><i class="fas fa-calendar-day fa-lg"></i></span>
                        <span>{{$producto->voladura_fecha}}</span>
                    </li>  
                </ul>
            </div>
        </div>

        <div class="contactForm">
            <h2>Ficha Técnica</h2>
            <div class="formBox">
                <div class="inputBox w50">
                    <input type="text" disabled value="{{$producto->arena_06}}">
                    <span>Arena 06</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$producto->grava_612}}">
                    <span>Grava 612</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$producto->grava_1220}}">
                    <span>Grava 1220</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$producto->grava_2023}}">
                    <span>Grava 2023</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$producto->rechazo}}">
                    <span>Rechazo</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$producto->escollera}}">
                    <span>Escollera</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$producto->mamposteria}}">
                    <span>Mampostería</span>
                </div>

                <div class="inputBox w100">
                    <input type="submit" onclick="history.back()" value="Volver atrás">
                </div>
            </div>
        </div>
    </div> 
</section>

@endsection