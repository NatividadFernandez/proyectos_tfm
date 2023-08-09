@extends('layouts.app')

@section('breadcrumb')
    <li class="breadcrumb-item active" aria-current="page">
        Voladuras
    </li>
@endsection

@section('content')   

<div class="container containerTablaVoladura">

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
                            <th>Fecha Voladura</th>
                            <th>Localización</th>
                            <th>Superfície m2</th>
                            <th>Malla de Perforación m</th>
                            <th>Profundidad Barrenos m</th>
                            <th>Número Barrenos</th>
                            <th>Kg Explosivo</th>                                    
                            <th>Precio €</th>
                            <th>Piedra Bruta tm</th>
                            <th>Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        @foreach ($voladuras as $voladura)
                            <tr>
                                <td>{{$voladura->fecha_voladura}}</td>
                                <td>{{$voladura->localizacion}}</td>
                                <td>{{$voladura->m2_superficie}}</td>
                                <td>{{$voladura->malla_perforacion}}</td>
                                <td>{{$voladura->profundidad_barrenos}}</td>
                                <td>{{$voladura->numero_barrenos}}</td>
                                <td>{{$voladura->kg_explosivo}}</td>
                                <td>{{$voladura->precio}}</td>
                                <td>{{$voladura->piedra_bruta}}</td>
                                <td>
                                    <div class="linear">
                                        <a class="btn botones mr-2 btn-edit" data-toggle="tooltip" data-placement="left" title="Modificar Voladura" href="{{ route('voladuras.edit', $voladura) }}"><i class="far fa-edit"></i></a>
                                        <a class="btn botones mr-2 btn-show" data-toggle="tooltip" data-placement="left" title="Ficha Voladura" href="{{ route('voladuras.show',$voladura->id) }}"><i class="far fa-file"></i></a>
                                        <form action="{{ route('voladuras.destroy',$voladura) }}" method="POST">                                                
                                            @csrf
                                            @method('delete')
                                            <button class="btn botones btn-delete" type="submit" data-toggle="tooltip" data-placement="left" title="Eliminar Voladura" onclick="return confirm('¿Dar de baja a este usuario?')"><i class="far fa-trash-alt"></i></button>
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
             <a class="fa fa-plus btnFlotante" data-toggle="tooltip" data-placement="left" title="Crear Voladura" href="{{ route('voladuras.create')}}"></a>           
         </div>
    </div>
</div> 
 
@endsection