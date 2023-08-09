@section('link')
    <link rel="stylesheet" href="{{ asset('css/styleShow.css')}}">
@endsection

@section('breadcrumb')
    <li class="breadcrumb-item">
        <a href="{{ route('stock_productos.index') }}">Stock Productos</a>
    </li>
    <li class="breadcrumb-item active" aria-current="page">
        Ficha Stock Producto
    </li>
@endsection

@extends('layouts.app')

@section('content')

<section>
    <div class="container">
        <div class="contactInfo">
            <div>
                <h2>Inf. Stock del Producto </h2>
                <ul class="info">
                    <li>
                        <span><i class="fas fa-calendar-day fa-lg"></i></span>
                        <span>{{$stock_producto->voladura_fecha}}</span>
                    </li>  
                </ul>
            </div>
        </div>

        <div class="contactForm">
            <h2>Ficha Técnica</h2>
            <div class="formBox">
                <div class="inputBox w50">
                    <input type="text" disabled value="{{$stock_producto->arena_06}}">
                    <span>Arena 06</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$stock_producto->grava_612}}">
                    <span>Grava 612</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$stock_producto->grava_1220}}">
                    <span>Grava 1220</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$stock_producto->grava_2023}}">
                    <span>Grava 2023</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$stock_producto->rechazo}}">
                    <span>Rechazo</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$stock_producto->escollera}}">
                    <span>Escollera</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$stock_producto->mamposteria}}">
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