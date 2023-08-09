@section('link')
    <link rel="stylesheet" href="{{ asset('css/styleShow.css')}}">
@endsection
@extends('layouts.app')

@section('breadcrumb')
    <li class="breadcrumb-item">
        <a href="{{ route('empleados.index') }}">Empleados</a>
    </li>
    <li class="breadcrumb-item active" aria-current="page">
        Ficha Empleado
    </li>
@endsection

@section('content')

<section>
    <div class="container">
        <div class="contactInfo">
            <div>
                <h2>Inf. Empleado</h2>
                <ul class="info">
                    <li>
                        <span><i class="far fa-address-card fa-lg"></i></span>
                        <span>{{$empleado->dni}}</span>
                    </li>
                    <li>
                        <span><i class="fas fa-user fa-lg"></i></span>
                        <span>{{$empleado->genero}}</span>
                    </li>
                    <li>
                        <span><i class="fas fa-birthday-cake fa-lg"></i></span>
                        <span>{{$empleado->fechaNacimiento}}</span>
                    </li>
                    <li>
                        <span><i class="fas fa-envelope-open-text fa-lg"></i></i></span>
                        <span>{{$empleado->email}}</span>
                    </li>
                    <li>
                        <span><i class="fas fa-phone-alt fa-lg"></i></span>
                        <span>{{$empleado->telefono}}</span>
                    </li>                    
                </ul>
            </div>
        </div>

        <div class="contactForm">
            <h2>Ficha Técnica</h2>
            <div class="formBox">
                <div class="inputBox w50">
                    <input type="text" disabled value="{{$empleado->nombre}}">
                    <span>Nombre</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$empleado->apellidos}}">
                    <span>Apellidos</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$empleado->puesto}}">
                    <span>Puesto de Trabajo</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$empleado->contrato}}">
                    <span>Contrato</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled value="{{$empleado->created_at}}">
                    <span>Fecha de entrada</span>
                </div>

                <div class="inputBox w50">
                    <input type="text" disabled @if ($empleado->estado == 1) value="Sin Información" @else value="{{$empleado->updated_at}}" @endif>
                    <span>Fecha de salida</span>
                </div>               

                <div class="inputBox w100">
                    <input type="submit" onclick="history.back()" value="Volver atrás">
                </div>
            </div>
        </div>
    </div> 
</section>

@endsection