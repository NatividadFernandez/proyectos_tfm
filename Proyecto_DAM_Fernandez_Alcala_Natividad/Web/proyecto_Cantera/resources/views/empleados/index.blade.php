@extends('layouts.app')

@section('breadcrumb')
    <li class="breadcrumb-item active" aria-current="page">
        Empleados
    </li>
@endsection

@section('content')   
<div class="container containerTabla">

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
                            <th>Dni</th>
                            <th>Nombre</th>
                            <th>Apellidos</th>
                            <th>Género</th>
                            <th>Email</th>
                            <th>Teléfono</th>                                    
                            <th>Estado</th>
                            <th>Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        @foreach ($empleados as $empleado)
                            <tr>
                                <td>                                            
                                    <span class="dni-fondo">
                                        {{$empleado->dni}}
                                    </span>
                                </td>
                                <td>{{$empleado->nombre}}</td>
                                <td>{{$empleado->apellidos}}</td>
                                <td>{{$empleado->genero}}</td>
                                <td>{{$empleado->email}}</td>
                                <td>{{$empleado->telefono}}</td>
                                <td>
                                    @if ($empleado->estado == 1)                                                
                                        <span class="estadoA">Activo</span>
                                    @else                                              
                                        <span class="estadoB">Inactivo</span>                                               
                                    @endif
                                </td>
                                <td>
                                    <div class="linear">
                                        <a class="btn botones mr-2 btn-edit" data-toggle="tooltip" data-placement="left" title="Modificar Empleado" href="{{ route('empleados.edit', $empleado) }}"><i class="far fa-edit"></i></a>
                                        <a class="btn botones mr-2 btn-show" data-toggle="tooltip" data-placement="left" title="Ficha Empleado" href="{{ route('empleados.show',$empleado->id) }}"><i class="far fa-user"></i></a>
                                        <form action="{{ route('empleados.destroy',$empleado) }}" method="POST">                                                
                                            @csrf
                                            @method('delete')
                                            <button class="btn botones btn-delete" @if ($empleado->estado == 1) type="submit" data-toggle="tooltip" data-placement="left" title="Eliminar Empleado" onclick="return confirm('¿Dar de baja a este empleado?')" @else type="button" onclick="return confirm('Este usuario ya está dado de baja.')" @endif><i class="far fa-trash-alt"></i></button>
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
            <a class="fa fa-plus btnFlotante" data-toggle="tooltip" data-placement="left" title="Crear Empleado " href="{{ route('empleados.create')}}"></a>           
        </div>
    </div>
</div>       
    
@endsection


