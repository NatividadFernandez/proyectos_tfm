@extends('layouts.app')

@section('content')

<div id="containerForm">   

    <div class="panel-containerForm">
      <div class="panel">
        <h1 class="panel_heading">Modificar Stock Consumos</h1>
        <p class="panel_subheading">
            Modificar stock consumos.
        </p>

        <form action="{{ route('stock_consumos.update',$stock_consumo)}}" method="POST">
            @csrf
            @method('put')
            @include('stock_consumos.form',['Modo' => 'Modificar'])              
        </form>
      </div>
    </div>
</div>
@endsection