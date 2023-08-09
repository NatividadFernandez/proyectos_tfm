@section('breadcrumb')
    <li class="breadcrumb-item">
        <a href="{{ route('stock_productos.index') }}">Stock Productos</a>
    </li>
    <li class="breadcrumb-item active" aria-current="page">
        @if ($Modo == 'Registrar') Registrar Stock Producto @else Modificar Stock Producto @endif 
    </li>
@endsection

<div class="nameForm pb-3">

    <div class="dropdownForm">
        <label for="voladura">Fecha Voladura</label>
        <select name="voladura" id="voladura" readonly>
            <option value="{{$voladura->id}}">{{$voladura->fecha_voladura}}</option>
        </select>
        @error('voladura')
            <br>
            <small class="mensaje2">*{{$message}}</small>                
        @enderror
    </div> 
    
    <div class="input">
        <label for="arena_06">Arena 06</label>
        <input type="text" id="arena_06" name="arena_06" value="{{ isset($stock_producto->arena_06)?  old('arena_06', $stock_producto ->arena_06) : old('arena_06') }}" required spellcheck="false">
        <div class="input_indicator"></div>
        @error('arena_06')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
    <div class="input">
        <label for="grava_612">Grava 612</label>
        <input type="text" id="grava_612" name="grava_612" value="{{ isset($stock_producto->grava_612)?  old('grava_612', $stock_producto ->grava_612) : old('grava_612') }}" required spellcheck="false">
        <div class="input_indicator"></div>
        @error('grava_612')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
    
</div>

<div class="nameForm pb-3">

    <div class="input">
        <label for="grava_1220">Grava 1220</label>
        <input type="text" id="grava_1220" name="grava_1220" value="{{ isset($stock_producto->grava_1220)?  old('grava_1220', $stock_producto ->grava_1220) : old('grava_1220') }}" required>
        <div class="input_indicator"></div>
        @error('grava_1220')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>

    <div class="input">
        <label for="grava_2023">Grava 2023</label>
        <input type="text" id="grava_2023" name="grava_2023" value="{{ isset($stock_producto->grava_2023)?  old('grava_2023', $stock_producto ->grava_2023) : old('grava_2023') }}" required spellcheck="false">
        <div class="input_indicator "></div>
        @error('grava_2023')
            <br>
            <small class="mensaje">*{{$message}}</small>               
        @enderror        
    </div>

    <div class="input">
        <label for="rechazo">Rechazo</label>
        <input type="text" id="rechazo" name="rechazo" value="{{ isset($stock_producto->rechazo)?  old('rechazo', $stock_producto ->rechazo) : old('rechazo') }}" required spellcheck="false">
        <div class="input_indicator"></div>
        @error('rechazo')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
       </div>

<div class="nameForm">

    <div class="input">
        <label for="zahorra">Zahorra</label>
        <input type="text" id="zahorra" name="zahorra" value="{{ isset($stock_producto->zahorra)?  old('zahorra', $stock_producto ->zahorra) : old('zahorra') }}" required spellcheck="false">
        <div class="input_indicator"></div>
        @error('zahorra')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>

    <div class="input">
        <label for="escollera">Escollera</label>
        <input type="text" id="escollera" name="escollera" value="{{ isset($stock_producto->escollera)?  old('escollera', $stock_producto ->escollera) : old('escollera') }}" required>
        <div class="input_indicator"></div>
        @error('escollera')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
    
    <div class="input">
        <label for="mamposteria">Mampostería</label>
        <input type="text" id="mamposteria" name="mamposteria" value="{{ isset($stock_producto->mamposteria)?  old('mamposteria', $stock_producto ->mamposteria) : old('mamposteria') }}" required>
        <div class="input_indicator"></div>
        @error('mamposteria')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>       
</div>
   
<div class="datos">
    <button type="submit" class="btnForm">{{ $Modo=='Registrar' ? 'Registrar':'Actualizar Stock'}}</button>   

    <button type="button" class="btnForm cambioColor" onclick="history.back()">{{'Volver Atrás'}}</button> 
</div>