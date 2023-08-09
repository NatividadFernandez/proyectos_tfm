@section('breadcrumb')
    <li class="breadcrumb-item">
        <a href="{{ route('empleados.index') }}">Empleados</a>
    </li>
    <li class="breadcrumb-item active" aria-current="page">
        @if ($Modo == 'Registrar') Registrar Empleado @else Modificar Empleado @endif 
    </li>
@endsection


<div class="nameForm">
    <div class="input">
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" value="{{ isset($empleado->nombre)?  old('nombre', $empleado->nombre) : old('nombre') }}" required  @if ($Modo == 'Registrar') autofocus @endif spellcheck="false">
        <div class="input_indicator"></div>
        @error('nombre')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
    <div class="input">
        <label for="apellidos">Apellidos</label>
        <input type="text" id="apellidos" name="apellidos" value="{{ isset($empleado->apellidos)?  old('apellidos', $empleado->apellidos) : old('apellidos') }}" required spellcheck="false">
        <div class="input_indicator"></div>
        @error('apellidos')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>

    <div class="input">
        <label for="dni">Dni</label>
        <input type="text" id="dni" name="dni" value="{{ isset($empleado->dni)?  old('dni', $empleado->dni) : old('dni') }}" required>
        <div class="input_indicator"></div>
        @error('dni')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>
</div>

<div class="datos">
    <div class="input">
        <label for="telefono">Teléfono</label>
        <input type="text" id="telefono" name="telefono" value="{{ isset($empleado->telefono)?  old('telefono', $empleado->telefono) : old('telefono') }}" required>
        <div class="input_indicator"></div>
        @error('telefono')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>

    <div class="input">
        <label for="fechaNacimiento">Fecha de Nacimiento</label>
        <input type="date" id="fechaNacimiento" name="fechaNacimiento" value="{{ isset($empleado->fechaNacimiento)?  old('fechaNacimiento', $empleado->fechaNacimiento) : old('fechaNacimiento') }}" required>
        <div class="input_indicator"></div>
        @error('fechaNacimiento')
            <br>
            <small class="mensaje">*{{$message}}</small>                
        @enderror
    </div>    
</div>

<div class="input">
    <label for="email">Dirección de Correo</label>
    <input type="email" id="email" name="email" class="separadoForm" value="{{ isset($empleado->email)?  old('email', $empleado->email) : old('email') }}" @if ($Modo == 'Modificar') disabled @endif @if ($Modo == 'Registrar') required @endif>
    <div class="input_indicator"></div>
    @error('email')
        <br>
        <small class="mensaje">*{{$message}}</small>                
    @enderror
</div>           

<div class="input">
    <label for="pass">Contraseña</label>
    <input type="password" id="pass" name="pass" class="separadoForm" value="{{ isset($user->pass)?  old('pass', $user->pass) : old('pass') }}" @if ($Modo == 'Registrar') required @endif>
    <div class="input_indicator"></div>
    @error('pass')
        <br>
        <small class="mensaje">*{{$message}}</small>                
    @enderror
</div>

<div class="datos">
    <div class="dropdownForm espacio">
        <label for="genero">Género</label>
        <select name="genero" id="genero">
          <option value="Hombre" @if (isset($empleado->genero))  @if (old('genero', $empleado->genero) == 'Hombre') selected @endif @else @if (old('genero')=='Hombre') selected @endif @endif>Hombre</option>
          <option value="Mujer" @if (isset($empleado->genero))  @if (old('genero', $empleado->genero) == 'Mujer') selected @endif @else @if (old('genero')=='Mujer') selected @endif @endif>Mujer</option>
          <option value="No Binario" @if (isset($empleado->genero))  @if (old('genero', $empleado->genero) == 'No Binario') selected @endif @else @if (old('genero')=='No Binario') selected @endif @endif>No Binario</option>
        </select>
        @error('genero')
            <br>
            <small class="mensaje2">*{{$message}}</small>                
        @enderror
    </div>    
        

    <div class="dropdownForm espacio">
        <label for="rol">Rol</label>
        <select name="rol" id="rol">
          <option value="2">Empleado</option>
          <option value="1">Administrador</option>
        </select>
        @error('rol')
            <br>
            <small class="mensaje2">*{{$message}}</small>                
        @enderror
    </div>    
</div>

<div class="date-of-birth">
    <h6 class="datos_heading">Más datos</h6>

    <div class="datos_inputs">
      <div class="dropdownForm">
        <label for="contrato">Contrato</label>
        <select name="contrato" id="contrato">
          <option value="Temporal" @if (isset($empleado->contrato))  @if (old('contrato', $empleado->contrato) == 'Temporal') selected @endif @else @if (old('contrato')=='Temporal') selected @endif @endif>Temporal</option>
            <option value="Fijo" @if (isset($empleado->contrato))  @if (old('contrato', $empleado->contrato) == 'Fijo') selected @endif @else @if (old('contrato')=='Fijo') selected @endif @endif>Fijo</option>
            <option value="Practicas" @if (isset($empleado->contrato))  @if (old('contrato', $empleado->contrato) == 'Practicas') selected @endif @else @if (old('contrato')=='Practicas') selected @endif @endif>Prácticas</option>
        </select>
        @error('contrato')
            <br>
            <small class="mensaje2">*{{$message}}</small>                
        @enderror
      </div>

      <div class="dropdownForm">
        <label for="puesto">Pruesto de Trabajo</label>
        <select name="puesto" id="puesto">
            <option value="Camionero" @if (isset($empleado->puesto))  @if (old('puesto', $empleado->puesto) == 'Camionero') selected @endif @else @if (old('puesto')=='Camionero') selected @endif @endif>Camionero</option>
            <option value="Palista" @if (isset($empleado->puesto))  @if (old('puesto', $empleado->puesto) == 'Palista') selected @endif @else @if (old('puesto')=='Palista') selected @endif @endif>Palista</option>
            <option value="Operario" @if (isset($empleado->puesto))  @if (old('puesto', $empleado->puesto) == 'Operario') selected @endif @else @if (old('puesto')=='Operario') selected @endif @endif>Operario</option>
        </select>
        @error('puesto')
            <br>
            <small class="mensaje2">*{{$message}}</small>                
        @enderror
      </div>

      <div class="dropdownForm">
        <label for="estado">Estado</label>
        <select name="estado" id="estado">
            <option value="Activo" @if (isset($empleado->estado))  @if (old('estado', $empleado->estado) == 'Activo') selected @endif @else @if (old('estado')== 'Activo') selected @endif @endif>Activo</option>
            <option value="Inactivo" @if (isset($empleado->estado))  @if (old('estado', $empleado->estado) == 'Inactivo') selected @endif @else @if (old('estado')== 'Inactivo') selected @endif @endif>Inactivo</option>
        </select>
        @error('estado')
            <br>
            <small class="mensaje2">*{{$message}}</small>                
        @enderror
      </div>      
    </div>   

</div>   

<div class="datos">
    <button type="submit" class="btnForm">{{ $Modo=='Registrar' ? 'Registrar':'Modificar'}}</button>   

    <button type="button" class="btnForm cambioColor" onclick="history.back()">{{'Volver Atrás'}}</button> 
</div>       
       



        