@extends('layouts.app')

@section('content')
<div id="containerForm">   

    <div class="panel-containerForm">
      <div class="panel">
        <h1 class="panel_heading">Registrar consumos</h1>
        <p class="panel_subheading">
            Inserción de consumos.
        </p>

        <form action="{{ route('consumos.store')}}" method="POST">
            @csrf
            @include('consumos.form',['Modo' => 'Registrar'])   
            
        </form>
      </div>
    </div>
</div>
@endsection