@extends('layouts.app')

@section('content')
<div id="containerForm">   

    <div class="panel-containerForm">
      <div class="panel">
        <h1 class="panel_heading">Registrar</h1>
        <p class="panel_subheading">
            Creación nuevo empleado.
        </p>

        <form action="{{ route('empleados.store')}}" method="POST">
            @csrf
            @include('empleados.form',['Modo' => 'Registrar'])   
            
        </form>
      </div>
    </div>
</div>
@endsection