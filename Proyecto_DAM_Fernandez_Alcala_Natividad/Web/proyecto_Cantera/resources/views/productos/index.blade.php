@extends('layouts.app')

@section('breadcrumb')
    <li class="breadcrumb-item active" aria-current="page">
        Productos
    </li>
@endsection

@section('content')   

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
                            @foreach ($productos as $producto)
                                <tr>
                                    <td>{{$producto->voladura_fecha}}</td>
                                    <td>{{$producto->arena_06}}</td>
                                    <td>{{$producto->grava_612}}</td>
                                    <td>{{$producto->grava_1220}}</td>
                                    <td>{{$producto->grava_2023}}</td>
                                    <td>{{$producto->rechazo}}</td>
                                    <td>{{$producto->zahorra}}</td>
                                    <td>{{$producto->escollera}}</td>
                                    <td>{{$producto->mamposteria}}</td>
                                    <td>
                                        <div class="linear">
                                            <a class="btn botones mr-2 btn-edit" data-toggle="tooltip" data-placement="left" title="Modificar Producto" href="{{ route('productos.edit', $producto) }}"><i class="far fa-edit"></i></a>
                                            <a class="btn botones mr-2 btn-show" data-toggle="tooltip" data-placement="left" title="Ficha Producto" href="{{ route('productos.show',$producto->id) }}"><i class="far fa-file"></i></a>
                                            <form action="{{ route('productos.destroy',$producto) }}" method="POST">                                                
                                                @csrf
                                                @method('delete')
                                                <button class="btn botones btn-delete" type="submit" data-toggle="tooltip" data-placement="left" title="Eliminar Producto" onclick="return confirm('¿Borrar producto con la fecha de la voladura: {{$producto->voladura_fecha}}?')"><i class="far fa-trash-alt"></i></button>
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
            <a class="fa fa-plus btnFlotante" data-toggle="tooltip" data-placement="left" title="Crear Producto" href="{{ route('productos.create')}}"></a>           
        </div>
   </div>
</div> 
 
@endsection