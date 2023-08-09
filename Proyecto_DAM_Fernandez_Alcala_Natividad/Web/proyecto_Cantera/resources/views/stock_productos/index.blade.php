@extends('layouts.app')

@section('breadcrumb')
    <li class="breadcrumb-item active" aria-current="page">
        Stock Productos
    </li>
@endsection

@section('content')

@foreach ($stock_productos as $stock_producto )
    <?php $stock = $stock_producto ?> 
@endforeach   

<div class="container containerTablaProducto">

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
                            <th>Fecha voladura</th>
                            <th>Arena 06</th>
                            <th>Grava 612</th>
                            <th>Grava 1220</th>
                            <th>Grava 2023</th>
                            <th>Rechazo</th>
                            <th>Zahorra</th>                                    
                            <th>Escollera</th>
                            <th>Mampostería</th>
                            <th>Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        @foreach ($stock_productos as $stock_producto )
                            <tr>
                                <td>{{$stock_producto->voladura_fecha}}</td>
                                <td>{{$stock_producto->arena_06}}</td>
                                <td>{{$stock_producto->grava_612}}</td>
                                <td>{{$stock_producto->grava_1220}}</td>
                                <td>{{$stock_producto->grava_2023}}</td>
                                <td>{{$stock_producto->rechazo}}</td>
                                <td>{{$stock_producto->zahorra}}</td>
                                <td>{{$stock_producto->escollera}}</td>
                                <td>{{$stock_producto->mamposteria}}</td>
                                <td>
                                    <div class="linear">
                                        <a class="btn botones mr-2 btn-edit" data-toggle="tooltip" data-placement="left" title="Modificar Stock Producto" href="{{ route('stock_productos.edit', $stock_producto) }}"><i class="far fa-edit"></i></a>
                                        <a class="btn botones mr-2 btn-show" data-toggle="tooltip" data-placement="left" title="Ficha Stock Producto" href="{{ route('stock_productos.show',$stock_producto->id) }}"><i class="far fa-file"></i></a>
                                        <form action="{{ route('stock_productos.destroy',$stock_producto) }}" method="POST">                                                
                                            @csrf
                                            @method('delete')
                                            <button class="btn botones btn-delete" type="submit" data-toggle="tooltip" data-placement="left" title="Eliminar Stock Producto" onclick="return confirm('¿Borrar stock del produco con la fecha de la voladura: {{ $stock_producto->voladura_fecha}}?')"><i class="far fa-trash-alt"></i></button>
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
            @isset($stock)
            <a class="fa fa-plus btnFlotante" data-toggle="tooltip" data-placement="left" title="Crear Stock Producto" href="{{ route('stock_productos.edit', $stock) }}"></a>       
            @endisset            
        </div>
   </div>
</div> 
 
@endsection