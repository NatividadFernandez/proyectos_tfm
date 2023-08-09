@section('breadcrumb')
    <li class="breadcrumb-item">
        <a href="{{ route('stock_productos.index') }}">Voladuras</a>
    </li>
    <li class="breadcrumb-item active" aria-current="page">
        @if ($Modo == 'Registrar') Registrar Voladura @else Modificar Voladura @endif 
    </li>
@endsection

<div class="nameForm pb-3">
    <div class="input">
        <label for="fechaVoladura">Fecha Voladura</label>
        <input type="datetime-local" id="fechaVoladura" name="fechaVoladura" {{-- value="{{date('Y-m-d\\TH:i', strtotime($voladura->fecha_voladura))}}" --}} {{-- value="{{$voladura->fecha_voladura}}" --}}  {{-- value="2018-06-12T19:30" --}} value="{{ isset($voladura->fecha_voladura)?  old('fechaVoladura', date('Y-m-d\\TH:i', strtotime($voladura->fecha_voladura))) : old('fechaVoladura') }}" required  @if ($Modo == 'Registrar') autofocus @endif spellcheck="false">
        <div class="input_indicator"></div>
        @error('fechaVoladura')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
    <div class="dropdownForm">
        <label for="localizacion">Localización</label>
        <select name="localizacion" id="localizacion">
          <option value="Frente Norte" @if (isset($voladura->localizacion))  @if (old('localizacion', $voladura->localizacion) == 'Frente Norte') selected @endif @else @if (old('localizacion')=='Frente Norte') selected @endif @endif>Frente Norte</option>
            <option value="Frente Sur" @if (isset($voladura->localizacion))  @if (old('localizacion', $voladura->localizacion) == 'Frente Sur') selected @endif @else @if (old('localizacion')=='Frente Sur') selected @endif @endif>Frente Sur</option>
        </select>
        @error('localizacion')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
      </div>

    <div class="input">
        <label for="superficie">Superfície m2</label>
        <input type="text" id="superficie" name="superficie" value="{{ isset($voladura->m2_superficie)?  old('superficie', $voladura->m2_superficie) : old('superficie') }}" required>
        <div class="input_indicator"></div>
        @error('superficie')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
</div>

<div class="nameForm pb-3">
    <div class="input">
        <label for="perforacion">Malla Perforación m</label>
        <input type="text" id="perforacion" name="perforacion" value="{{ isset($voladura->malla_perforacion)?  old('perforacion', $voladura->malla_perforacion) : old('perforacion') }}" required spellcheck="false">
        <div class="input_indicator "></div>
        @error('perforacion')
            <br>
            <small class="mensaje">*{{$message}}</small>               
        @enderror        
    </div>
    <div class="input">
        <label for="profundidadBarrenos">Profundidad Barrenos m</label>
        <input type="text" id="profundidadBarrenos" name="profundidadBarrenos" value="{{ isset($voladura->profundidad_barrenos)?  old('profundidadBarrenos', $voladura->profundidad_barrenos) : old('profundidadBarrenos') }}" required spellcheck="false">
        <div class="input_indicator"></div>
        @error('profundidadBarrenos')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
    <div class="input">
        <label for="numeroBarrenos">Número Barrenos</label>
        <input type="text" id="numeroBarrenos" name="numeroBarrenos" value="{{ isset($voladura->numero_barrenos)?  old('numeroBarrenos', $voladura->numero_barrenos) : old('numeroBarrenos') }}" required spellcheck="false">
        <div class="input_indicator"></div>
        @error('numeroBarrenos')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>   
</div>

<div class="nameForm">
    <div class="input">
        <label for="explosivo">Kg. Explosivo</label>
        <input type="text" id="explosivo" name="explosivo" value="{{ isset($voladura->kg_explosivo)?  old('explosivo', $voladura->kg_explosivo) : old('explosivo') }}" required>
        <div class="input_indicator"></div>
        @error('explosivo')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
    
    <div class="input">
        <label for="precio">Precio €</label>
        <input type="text" id="precio" name="precio" value="{{ isset($voladura->precio)?  old('precio', $voladura->precio) : old('precio') }}" required>
        <div class="input_indicator"></div>
        @error('precio')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>

    <div class="input">
        <label for="piedraBruta">Piedra Bruta tm</label>
        <input type="text" id="piedraBruta" name="piedraBruta" value="{{ isset($voladura->piedra_bruta)?  old('piedraBruta', $voladura->piedra_bruta) : old('piedraBruta') }}" required>
        <div class="input_indicator"></div>
        @error('piedraBruta')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>    
</div>
   
<div class="datos">
    <button type="submit" class="btnForm">{{ $Modo=='Registrar' ? 'Registrar':'Modificar'}}</button>   

    <button type="button" class="btnForm cambioColor" onclick="history.back()">{{'Volver Atrás'}}</button> 
</div>