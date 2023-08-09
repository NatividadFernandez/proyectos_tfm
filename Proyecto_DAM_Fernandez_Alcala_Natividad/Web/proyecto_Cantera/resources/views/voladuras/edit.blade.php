@extends('layouts.app')

@section('content')

<div id="containerForm">   

    <div class="panel-containerForm">
      <div class="panel">
        <h1 class="panel_heading">Modificar</h1>
        <p class="panel_subheading">
            Modificar voladura.
        </p>

        <form action="{{ route('voladuras.update',$voladura)}}" method="POST">
            @csrf
            @method('put')
            @include('voladuras.form',['Modo' => 'Modificar'])              
        </form>
      </div>
    </div>
</div>
@endsection