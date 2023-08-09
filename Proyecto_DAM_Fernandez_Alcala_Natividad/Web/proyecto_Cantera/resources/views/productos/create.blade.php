@extends('layouts.app')

@section('content')
<div id="containerForm">   

    <div class="panel-containerForm">
      <div class="panel">
        <h1 class="panel_heading">Registrar Productos</h1>
        <p class="panel_subheading">
            Inserción de productos.
        </p>

        <form action="{{ route('productos.store')}}" method="POST">
            @csrf
            @include('productos.form',['Modo' => 'Registrar'])   
            
        </form>
      </div>
    </div>
</div>
@endsection