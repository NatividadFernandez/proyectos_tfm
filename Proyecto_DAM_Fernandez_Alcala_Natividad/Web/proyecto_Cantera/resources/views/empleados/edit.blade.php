@extends('layouts.app')

@section('content')

<div id="containerForm">   

    <div class="panel-containerForm">
      <div class="panel">
        <h1 class="panel_heading">Modificar</h1>
        <p class="panel_subheading">
            Modificar empleado.
        </p>

        <form action="{{ route('empleados.update',$empleado)}}" method="POST">
            @csrf
            @method('put')
            @include('empleados.form',['Modo' => 'Modificar'])              
        </form>
      </div>
    </div>
</div>
@endsection