@extends('layouts.app')

@section('content')

<div id="containerForm">   

    <div class="panel-containerForm">
      <div class="panel">
        <h1 class="panel_heading">Modificar Stock Productos</h1>
        <p class="panel_subheading">
            Stock total del material obtenido.
        </p>

        <form action="{{ route('stock_productos.update',$stock_producto)}}" method="POST">
            @csrf
            @method('put')
            @include('stock_productos.form',['Modo' => 'Modificar'])              
        </form>
      </div>
    </div>
</div>
@endsection