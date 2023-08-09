@extends('layouts.app')

@section('breadcrumb')
    <li class="breadcrumb-item active" aria-current="page">
        Consumos
    </li>
@endsection

@section('content')  

<div class="container containerTablaconsumo">

    @if (Session::has('Mensaje'))
        <div class="alert alert-success" role="alert">
            {{ Session::get('Mensaje') }}    
        </div>
    @endif
    
    <div class="contenedor2">       
        <div class="row">
            <div class="col-lg-12">
                <div class="table-responsive">        
                    <table id="example" class="table table-striped table-bordered" style="width:100%">
                    <thead class="headerTabla">
                        <tr>
                            <th>Fecha Recepción</th>
                            <th>Gasoleo</th>
                            <th>Aceite Motor</th>
                            <th>Aceite Hidráulico</th>
                            <th>Aceite Transmisiones</th>
                            <th>Valvulina</th>
                            <th>Grasas</th>
                            <th>Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        @foreach ($consumos as $consumo)
                            <tr>
                                <td>{{$consumo->fecha_recepcion}}</td>
                                <td>{{$consumo->gasoleo}}</td>
                                <td>{{$consumo->aceite_motor}}</td>
                                <td>{{$consumo->aceite_hidraulico}}</td>
                                <td>{{$consumo->aceite_transmisiones}}</td>
                                <td>{{$consumo->valvulina}}</td>
                                <td>{{$consumo->grasas}}</td>
                                <td>
                                    <div class="linear">
                                        <a class="btn botones mr-2 btn-edit" data-toggle="tooltip" data-placement="left" title="Modificar Consumo" href="{{ route('consumos.edit', $consumo) }}"><i class="far fa-edit"></i></a>
                                        <a class="btn botones mr-2 btn-show" data-toggle="tooltip" data-placement="left" title="Ficha Consumo" href="{{ route('consumos.show',$consumo->id) }}"><i class="far fa-file"></i></a>
                                        <form action="{{ route('consumos.destroy',$consumo) }}" method="POST">                                                
                                            @csrf
                                            @method('delete')
                                            <button class="btn botones btn-delete" type="submit" data-toggle="tooltip" data-placement="left" title="Eliminar Consumo" onclick="return confirm('¿Borrar consumo con la fecha de recepción: {{$consumo->fecha_recepcion}}?')"><i class="far fa-trash-alt"></i></button>
                                        </form>
                                        
                                    </div>
                                </td>
                            </tr>
                        @endforeach                             
                    </tbody>        
                    </table>                  
                </div>
            </div>
        </div> 
    </div> 
    <div class="containeer">
        <div class="btn-mas"> 
            <a class="fa fa-plus btnFlotante" data-toggle="tooltip" data-placement="left" title="Crear Consumo" href="{{ route('consumos.create')}}"></a>           
        </div>
   </div>
</div> 
 
@endsection