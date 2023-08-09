@extends('layouts.app')

@section('content')
<div id="containerForm">   

    <div class="panel-containerForm">
      <div class="panel">
        <h1 class="panel_heading">Registrar Voladura</h1>
        <p class="panel_subheading">
            Creación nueva voladura.
        </p>

        <form action="{{ route('voladuras.store')}}" method="POST">
            @csrf
            @include('voladuras.form',['Modo' => 'Registrar'])   
            
        </form>
      </div>
    </div>
</div>
@endsection