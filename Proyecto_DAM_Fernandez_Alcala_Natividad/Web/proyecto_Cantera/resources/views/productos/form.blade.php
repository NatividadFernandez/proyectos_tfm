@section('breadcrumb')
    <li class="breadcrumb-item">
        <a href="{{ route('productos.index') }}">Productos</a>
    </li>
    <li class="breadcrumb-item active" aria-current="page">
        @if ($Modo == 'Registrar') Registrar Producto @else Modificar Producto @endif 
    </li>
@endsection

<div class="nameForm pb-3">

    <div class="dropdownForm">
        <label for="voladura">Fecha Voladura</label>
        <select name="voladura" id="voladura">
            @if ($Modo == 'Registrar')
                @foreach ($voladuras as $voladura)           
                    <option value="{{$voladura->id}}" {{ old('voladura') == $voladura->id ? 'selected' : '' }}>{{$voladura->fecha_voladura}}</option>
                @endforeach
            @else                 
                <option value="{{$voladura->id}}">{{$voladura->fecha_voladura}}</option>
            @endif
            
        </select>
        @error('voladura')
            <br>
            <small class="mensaje2">*{{$message}}</small>                
        @enderror
    </div> 
     
    <div class="input">
        <label for="arena_06">Arena 06</label>
        <input type="text" id="arena_06" name="arena_06" value="{{ isset($producto->arena_06)?  old('arena_06', $producto->arena_06) : old('arena_06') }}" required spellcheck="false">
        <div class="input_indicator"></div>
        @error('arena_06')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
    <div class="input">
        <label for="grava_612">Grava 612</label>
        <input type="text" id="grava_612" name="grava_612" value="{{ isset($producto->grava_612)?  old('grava_612', $producto->grava_612) : old('grava_612') }}" required spellcheck="false">
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
        <input type="text" id="grava_1220" name="grava_1220" value="{{ isset($producto->grava_1220)?  old('grava_1220', $producto->grava_1220) : old('grava_1220') }}" required>
        <div class="input_indicator"></div>
        @error('grava_1220')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>

    <div class="input">
        <label for="grava_2023">Grava 2023</label>
        <input type="text" id="grava_2023" name="grava_2023" value="{{ isset($producto->grava_2023)?  old('grava_2023', $producto->grava_2023) : old('grava_2023') }}" required spellcheck="false">
        <div class="input_indicator "></div>
        @error('grava_2023')
            <br>
            <small class="mensaje">*{{$message}}</small>               
        @enderror        
    </div>

    <div class="input">
        <label for="rechazo">Rechazo</label>
        <input type="text" id="rechazo" name="rechazo" value="{{ isset($producto->rechazo)?  old('rechazo', $producto->rechazo) : old('rechazo') }}" required spellcheck="false">
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
        <input type="text" id="zahorra" name="zahorra" value="{{ isset($producto->zahorra)?  old('zahorra', $producto->zahorra) : old('zahorra') }}" required spellcheck="false">
        <div class="input_indicator"></div>
        @error('zahorra')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>

    <div class="input">
        <label for="escollera">Escollera</label>
        <input type="text" id="escollera" name="escollera" value="{{ isset($producto->escollera)?  old('escollera', $producto->escollera) : old('escollera') }}" required>
        <div class="input_indicator"></div>
        @error('escollera')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
    
    <div class="input">
        <label for="mamposteria">Mampostería</label>
        <input type="text" id="mamposteria" name="mamposteria" value="{{ isset($producto->mamposteria)?  old('mamposteria', $producto->mamposteria) : old('mamposteria') }}" required>
        <div class="input_indicator"></div>
        @error('mamposteria')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>       
</div>
   
<div class="datos">
    <button type="submit" class="btnForm">{{ $Modo=='Registrar' ? 'Registrar':'Modificar'}}</button>   

    <button type="button" class="btnForm cambioColor" onclick="history.back()">{{'Volver Atrás'}}</button> 
</div>