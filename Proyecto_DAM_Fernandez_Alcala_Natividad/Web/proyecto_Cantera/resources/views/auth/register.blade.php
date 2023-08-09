@extends('layouts.app')

@section('content')

<div id="containerLogin">

  <div class="panel-container">
    <div class="panel">
      <h1 class="panel_heading">{{'Registrarse'}}</h1>
      <p class="panel_subheading">
        ¿Ya tienes cuenta? <a href="{{ route('login') }}">{{'Iniciar sesión'}}</a>
      </p>

      <form method="POST" action="{{ route('register') }}">
          @csrf
          <div class="input">
            <label for="name">Nombre</label>
            <input type="text" id="name" class="@error('name') is-invalid @enderror" name="name" value="{{ old('name') }}" required autocomplete="name" autofocus>
            <div class="input_indicator"></div>
            @error('name')
                <span class="invalid-feedback mensaje" role="alert">
                    <strong>{{ $message }}</strong>
                </span>
            @enderror
        </div>

          <div class="input">
              <label for="email">Dirección de Correo</label>
              <input id="email" type="email" class=" @error('email') is-invalid @enderror" name="email" value="{{ old('email') }}" required autocomplete="email">
              <div class="input_indicator"></div>
              @error('email')
                  <span class="invalid-feedback mensaje" role="alert">
                      <strong>{{ $message }}</strong>
                  </span>
              @enderror
          </div>

          <div class="input">
              <label for="password">Contraseña</label>
              <input type="password" id="password" class="@error('password') is-invalid @enderror" name="password" required autocomplete="new-password">
              <div class="input_indicator"></div>
              @error('password')
                  <span class="invalid-feedback mensaje" role="alert">
                      <strong>{{ $message }}</strong>
                  </span>
              @enderror
          </div>
          
          <div class="input">
            <label for="password-confirm">Confirmar Contraseña</label>
            <input type="password" id="password-confirm" name="password_confirmation" required autocomplete="new-password">
            <div class="input_indicator"></div>
            @error('password')
                <span class="invalid-feedback mensaje" role="alert">
                    <strong>{{ $message }}</strong>
                </span>
            @enderror
        </div>
          

          <p class="consents">
              Al hacer clic en Reguistrar, acepto que he leído y aceptado 
              los <a href="">Terminos de Uso</a> y <a href="">Política de Privacidad</a>.
          </p>

          <button type="submit" class="btnLogin">{{'Registrarse'}}</button>            
      </form>
    </div>
  </div>
</div>

{{-- <div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header">{{ __('Register') }}</div>

                <div class="card-body">
                    <form method="POST" action="{{ route('register') }}">
                        @csrf

                        <div class="form-group row">
                            <label for="name" class="col-md-4 col-form-label text-md-right">{{ __('Name') }}</label>

                            <div class="col-md-6">
                                <input id="name" type="text" class="form-control @error('name') is-invalid @enderror" name="name" value="{{ old('name') }}" required autocomplete="name" autofocus>

                                @error('name')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="email" class="col-md-4 col-form-label text-md-right">{{ __('E-Mail Address') }}</label>

                            <div class="col-md-6">
                                <input id="email" type="email" class="form-control @error('email') is-invalid @enderror" name="email" value="{{ old('email') }}" required autocomplete="email">

                                @error('email')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="password" class="col-md-4 col-form-label text-md-right">{{ __('Password') }}</label>

                            <div class="col-md-6">
                                <input id="password" type="password" class="form-control @error('password') is-invalid @enderror" name="password" required autocomplete="new-password">

                                @error('password')
                                    <span class="invalid-feedback" role="alert">
                                        <strong>{{ $message }}</strong>
                                    </span>
                                @enderror
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="password-confirm" class="col-md-4 col-form-label text-md-right">{{ __('Confirm Password') }}</label>

                            <div class="col-md-6">
                                <input id="password-confirm" type="password" class="form-control" name="password_confirmation" required autocomplete="new-password">
                            </div>
                        </div>

                        <div class="form-group row mb-0">
                            <div class="col-md-6 offset-md-4">
                                <button type="submit" class="btn btn-primary">
                                    {{ __('Register') }}
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div> --}}
@endsection


