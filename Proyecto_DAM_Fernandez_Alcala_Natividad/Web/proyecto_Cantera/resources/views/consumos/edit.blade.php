@extends('layouts.app')

@section('content')

<div id="containerForm">   

    <div class="panel-containerForm">
      <div class="panel">
        <h1 class="panel_heading">Modificar</h1>
        <p class="panel_subheading">
            Modificar consumos.
        </p>

        <form action="{{ route('consumos.update',$consumo)}}" method="POST">
            @csrf
            @method('put')
            @include('consumos.form',['Modo' => 'Modificar'])              
        </form>
      </div>
    </div>
</div>
@endsection