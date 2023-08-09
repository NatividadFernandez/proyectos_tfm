@extends('layouts.app')

@section('breadcrumb')
    <li class="breadcrumb-item active" aria-current="page">
        Control Horario
    </li>
@endsection

@section('content')
    <div class="titulos mt-1 ml-5"><h5> > Empleados en el puesto de trabajo</h5></div>
    <div class="container containerTabla mt-3 mb-5">

        <div class="contenedor2">
            <div class="row">
                <div class="col-lg-12">
                    <div class="table-responsive">        
                        <table id="examplePresencial" class="table table-striped table-bordered" style="width:100%">
                            <thead class="headerTabla">
                                <tr>
                                    <th>Fecha</th>
                                    <th>Nombre</th>                                   
                                    <th>Hora Entrada</th>
                                    <th>Hora Salida</th>
                                </tr>
                            </thead>
                            <tbody>
                                @foreach ($presencialidad as $presencialidadOk)                                   
                                    <tr>
                                        <td>
                                            @if ($presencialidadOk->fecha ==strval(date('Y-m-d')))
                                                <span class="estadoA">{{$presencialidadOk->fecha}}</span>
                                            @else 
                                                <span class="estadoB">{{$presencialidadOk->fecha}}</span>
                                            @endif
                                        </td>
                                        <td>{{$presencialidadOk->nombre}} {{$presencialidadOk->apellidos}}</td>                                                                       
                                        <td>{{ date('H:i:s',strtotime($presencialidadOk->fecha_check_in)) }}</td>
                                        @if (date('H:i:s',strtotime($presencialidadOk->fecha_check_out)) == "01:00:00")
                                            <td>No hay información</td>
                                        @else 
                                            <td>{{ date('H:i:s',strtotime($presencialidadOk->fecha_check_out)) }}</td>
                                        @endif
                                        {{-- <td>{{ date('H:i:s',strtotime($presencialidadOk->fecha_check_out)) }}</td> --}}
                                    </tr>
                                @endforeach                             
                            </tbody>        
                        </table>                        
                        
                    </div>
                </div>
            </div> 
        </div> 
        
    </div> 

    <div class="titulos mt-4 ml-5"><h5> > Empleados sin control horario (hoy)</h5></div>
    <div class="container containerTabla mt-3">
        
        <div class="contenedor2">            
            <div class="row">
                <div class="col-lg-12">
                    <div class="table-responsive">                           
                        
                        <table id="example2" class="table table-striped table-bordered" style="width:100%">
                            <thead class="headerTabla">
                                <tr>
                                    <th>Nombre y Apellidos</th>
                                </tr>
                            </thead>
                            <tbody>
                                @foreach ($presencialidadNOT as $presencialidadnN)
                                    <tr>
                                        <td  class="cmbFondoTR"><span class="cmbFondoTD">{{$presencialidadnN->nombre}} {{$presencialidadnN->apellidos}}<span></td>   
                                    </tr>
                                @endforeach                             
                            </tbody>        
                        </table>
                    </div>
                </div>
            </div> 
        </div>         
    </div>
        
@endsection


