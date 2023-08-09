@section('link')
    <link rel="stylesheet" href="{{ asset('css/styleShow.css')}}">
@endsection
@extends('layouts.app')

@section('breadcrumb')
    <li class="breadcrumb-item">
        <a href="{{ route('consumos.index') }}">Consumos</a>
    </li>
    <li class="breadcrumb-item active" aria-current="page">
        Ficha Consumo
    </li>
@endsection

@section('content')

<section>
    <div class="container">
        <div class="contactInfo">
            <div>
                <h2>Inf. consumo</h2>
                <ul class="info">
                    <li>
                        <span><i class="fas fa-calendar-day fa-lg"></i></span>
                        <span>{{$consumo->fecha_recepcion}}</span>
                    </li>  
                </ul>
            </div>
        </div>

        <div class="contactForm">
            <h2>Ficha Técnica</h2>
            <div class="formBox">
                <div class="inputBox w50">
                    <input type="text" disabled value="{{$consumo->gasoleo}}">
                    <span>Gasoleo</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$consumo->aceite_motor}}">
                    <span>Aceite Motor</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$consumo->aceite_hidraulico}}">
                    <span>Aceite Hidraulico</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$consumo->aceite_transmisiones}}">
                    <span>Aceite Transmisones</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$consumo->valvulina}}">
                    <span>Valvulina</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$consumo->grasas}}">
                    <span>Grasas</span>
                </div>

                <div class="inputBox w100">
                    <input type="submit" onclick="history.back()" value="Volver atrás">
                </div>
            </div>
        </div>
    </div> 
</section>

@endsection