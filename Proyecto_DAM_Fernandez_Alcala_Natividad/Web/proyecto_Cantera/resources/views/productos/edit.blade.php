@extends('layouts.app')

@section('content')

<div id="containerForm">   

    <div class="panel-containerForm">
      <div class="panel">
        <h1 class="panel_heading">Modificar</h1>
        <p class="panel_subheading">
            Modificar productos.
        </p>

        <form action="{{ route('productos.update',$producto)}}" method="POST">
            @csrf
            @method('put')
            @include('productos.form',['Modo' => 'Modificar'])              
        </form>
      </div>
    </div>
</div>
@endsection