@section('breadcrumb')
    <li class="breadcrumb-item">
        <a href="{{ route('consumos.index') }}">Consumos</a>
    </li>
    <li class="breadcrumb-item active" aria-current="page">
        @if ($Modo == 'Registrar') Registrar Consumo @else Modificar Consumo @endif 
    </li>
@endsection

<div class="datos pb-3">
    <div class="input">
        <label for="fecha_recepcion">Fecha Recepcion</label>
        <input type="datetime-local" id="fecha_recepcion" name="fecha_recepcion" value="{{ isset($consumo->fecha_recepcion)?  old('fecha_recepcion', date('Y-m-d\\TH:i', strtotime($consumo->fecha_recepcion))) : old('fecha_recepcion') }}" required  @if ($Modo == 'Registrar') autofocus @endif spellcheck="false">
        <div class="input_indicator"></div>
        @error('fecha_recepcion')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>

    <div class="input">
        <label for="gasoleo">Gasoleo</label>
        <input type="text" id="gasoleo" name="gasoleo" value="{{ isset($consumo->gasoleo)?  old('gasoleo', $consumo->gasoleo) : old('gasoleo') }}" required spellcheck="false">
        <div class="input_indicator"></div>
        @error('gasoleo')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
</div>

<div class="datos pb-3">

    <div class="input">
        <label for="valvulina">Valvulina</label>
        <input type="text" id="valvulina" name="valvulina" value="{{ isset($consumo->valvulina)?  old('valvulina', $consumo->valvulina) : old('valvulina') }}" required>
        <div class="input_indicator"></div>
        @error('valvulina')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>

    <div class="input">
        <label for="grasas">Grasas</label>
        <input type="text" id="grasas" name="grasas" value="{{ isset($consumo->grasas)?  old('grasas', $consumo->grasas) : old('grasas') }}" required spellcheck="false">
        <div class="input_indicator "></div>
        @error('grasas')
            <br>
            <small class="mensaje">*{{$message}}</small>               
        @enderror        
    </div>
</div>

<div class="date-of-birth">
    <h6 class="datos_heading">Aceites</h6>

    <div class="datos_inputs">
        <div class="input">
            <label for="aceite_motor">Motor</label>
            <input type="text" id="aceite_motor" name="aceite_motor" value="{{ isset($consumo->aceite_motor)?  old('aceite_motor', $consumo->aceite_motor) : old('aceite_motor') }}" required spellcheck="false">
            <div class="input_indicator"></div>
            @error('aceite_motor')
                <br>
                <small class="mensaje">*{{$message}}</small>                
            @enderror
        </div>

        <div class="input">
            <label for="aceite_hidraulico">Hidráulico</label>
            <input type="text" id="aceite_hidraulico" name="aceite_hidraulico" value="{{ isset($consumo->aceite_hidraulico)?  old('aceite_hidraulico', $consumo->aceite_hidraulico) : old('aceite_hidraulico') }}" required spellcheck="false">
            <div class="input_indicator"></div>
            @error('aceite_hidraulico')
                <br>
                <small class="mensaje">*{{$message}}</small>                
            @enderror
        </div>

        <div class="input">
            <label for="aceite_transmisiones">Transmisiones</label>
            <input type="text" id="aceite_transmisiones" name="aceite_transmisiones" value="{{ isset($consumo->aceite_transmisiones)?  old('aceite_transmisiones', $consumo->aceite_transmisiones) : old('aceite_transmisiones') }}" required spellcheck="false">
            <div class="input_indicator"></div>
            @error('aceite_transmisiones')
                <br>
                <small class="mensaje">*{{$message}}</small>                
            @enderror
        </div>      
    </div>   

</div>
   
<div class="datos">
    <button type="submit" class="btnForm">{{ $Modo=='Registrar' ? 'Registrar':'Modificar'}}</button>   

    <button type="button" class="btnForm cambioColor" onclick="history.back()">{{'Volver Atrás'}}</button> 
</div>