@section('breadcrumb')
    <li class="breadcrumb-item">
        <a href="{{ route('stock_consumos.index') }}">Stock Consumos</a>
    </li>
    <li class="breadcrumb-item active" aria-current="page">
        @if ($Modo == 'Registrar') Registrar Stock Consumo @else Modificar Stock Consumo @endif 
    </li>
@endsection

<div class="datos pb-3">
    <div class="input">
        <label for="fecha_consumo">Fecha Recepcion</label>
        <input type="datetime-local" id="fecha_consumo" name="fecha_consumo" value="{{ isset($stock_consumo->fecha_consumo)?  old('fecha_consumo', date('Y-m-d\\TH:i', strtotime($stock_consumo->fecha_consumo))) : old('fecha_consumo') }}" required  @if ($Modo == 'Registrar') autofocus @endif spellcheck="false">
        <div class="input_indicator"></div>
        @error('fecha_consumo')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>

    <div class="input">
        <label for="gasoleo">Gasoleo</label>
        <input type="text" id="gasoleo" name="gasoleo" value="{{ isset($stock_consumo->gasoleo)?  old('gasoleo', $stock_consumo->gasoleo) : old('gasoleo') }}" required spellcheck="false">
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
        <input type="text" id="valvulina" name="valvulina" value="{{ isset($stock_consumo->valvulina)?  old('valvulina', $stock_consumo->valvulina) : old('valvulina') }}" required>
        <div class="input_indicator"></div>
        @error('valvulina')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>

    <div class="input">
        <label for="grasas">Grasas</label>
        <input type="text" id="grasas" name="grasas" value="{{ isset($stock_consumo->grasas)?  old('grasas', $stock_consumo->grasas) : old('grasas') }}" required spellcheck="false">
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
            <input type="text" id="aceite_motor" name="aceite_motor" value="{{ isset($stock_consumo->aceite_motor)?  old('aceite_motor', $stock_consumo->aceite_motor) : old('aceite_motor') }}" required spellcheck="false">
            <div class="input_indicator"></div>
            @error('aceite_motor')
                <br>
                <small class="mensaje">*{{$message}}</small>                
            @enderror
        </div>

        <div class="input">
            <label for="aceite_hidraulico">Hidráulico</label>
            <input type="text" id="aceite_hidraulico" name="aceite_hidraulico" value="{{ isset($stock_consumo->aceite_hidraulico)?  old('aceite_hidraulico', $stock_consumo->aceite_hidraulico) : old('aceite_hidraulico') }}" required spellcheck="false">
            <div class="input_indicator"></div>
            @error('aceite_hidraulico')
                <br>
                <small class="mensaje">*{{$message}}</small>                
            @enderror
        </div>

        <div class="input">
            <label for="aceite_transmisiones">Transmisiones</label>
            <input type="text" id="aceite_transmisiones" name="aceite_transmisiones" value="{{ isset($stock_consumo->aceite_transmisiones)?  old('aceite_transmisiones', $stock_consumo->aceite_transmisiones) : old('aceite_transmisiones') }}" required spellcheck="false">
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