@extends('layouts.app')

@section('breadcrumb')
    <li class="breadcrumb-item active" aria-current="page">
        Stock Consumos
    </li>
@endsection

@section('content')

@foreach ($stock_consumos as $stock_consumo )
    <?php $stock = $stock_consumo ?> 
@endforeach

<div class="container containerTablaConsumo">

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
                            <th>Fecha Consumo</th>
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
                        @foreach ($stock_consumos as $stock_consumo)
                            <tr>
                                <td>{{$stock_consumo->fecha_consumo}}</td>
                                <td>{{$stock_consumo->gasoleo}}</td>
                                <td>{{$stock_consumo->aceite_motor}}</td>
                                <td>{{$stock_consumo->aceite_hidraulico}}</td>
                                <td>{{$stock_consumo->aceite_transmisiones}}</td>
                                <td>{{$stock_consumo->valvulina}}</td>
                                <td>{{$stock_consumo->grasas}}</td>
                                <td>
                                    <div class="linear">
                                        <a class="btn botones mr-2 btn-edit" data-toggle="tooltip" data-placement="left" title="Modificar Stock Consumo" href="{{ route('stock_consumos.edit', $stock_consumo) }}"><i class="far fa-edit"></i></a>
                                        <a class="btn botones mr-2 btn-show" data-toggle="tooltip" data-placement="left" title="Ficha Stock Consumo" href="{{ route('stock_consumos.show',$stock_consumo->id) }}"><i class="far fa-file"></i></a>
                                        <form action="{{ route('stock_consumos.destroy',$stock_consumo) }}" method="POST">                                                
                                            @csrf
                                            @method('delete')
                                            <button class="btn botones btn-delete" type="submit" data-toggle="tooltip" data-placement="left" title="Eliminar Stock Consumo" onclick="return confirm('¿Borrar stock del consumo con la fecha: {{$stock_consumo->fecha_consumo}}?')"><i class="far fa-trash-alt"></i></button>
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
            @isset($record)
            <a class="fa fa-plus btnFlotante" data-toggle="tooltip" data-placement="left" title="Crear Stock Consumo" href="{{ route('stock_consumos.edit',$stock) }}"></a>           
            @endisset            
        </div>
   </div>
</div> 
 
@endsection