<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Empleado;
use App\Rules\Telefono;
use App\Rules\Dni;
use App\Models\User;

class EmpleadoController extends Controller
{
    // Método que comprueba si el usuario está logueado

    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Método que muestra todos los empleados
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $empleados =  Empleado::all();
        return view('empleados.index',compact('empleados'));
    }

    /**
     * Método que nos devuelve la view create (form) para poder añadir un nuevo empleado
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        // Devolvemos la view create
        return view('empleados.create');
    }

    /**
     * Método que obtiene los valores insertados por el usuario, para ser añadidos a la base de datos
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
       //Validamos los campos
        $request->validate([
            'nombre'=>['required'],
            'apellidos'=>['required'],
            'fechaNacimiento'=>['required'],
            'dni'=>['required', ' unique:empleados', new Dni],
            'email'=>['required', 'email', 'unique:users', 'unique:empleados'],
            'telefono' => ['required', new Telefono],
            'genero'=>['required'],
            'pass'=>['required','min:6']
        ]);

        // Creo un nuevo Usuario
        $usuario = new User();  

        // Añado los datos obtenidos a los atributos del usuario
        $usuario->name = $request->nombre;
        $usuario->password = bcrypt($request->pass);
        $usuario->email = $request->email;
        $usuario->role_id = $request->rol;

        // Guardamos/Insertamos el nuevo usuario
        $usuario->save();

        // Creamos un nuevo Empleado
        $empleado = new Empleado(); 

        // Añado los datos obtenidos a los atributos del empleado
        $empleado->nombre = $request->nombre;
        $empleado->apellidos = $request->apellidos;
        $empleado->fechaNacimiento = date('Y-m-d', strtotime($request->fechaNacimiento));
        $empleado->dni = strtoupper($request->dni);
        $empleado->email = $request->email;
        $empleado->telefono = $request->telefono;                
        $empleado->genero= $request->genero;        
        $empleado->puesto = $request->puesto;
        $empleado->contrato = $request->contrato;
        if($request->estado == 'Activo'){
            $empleado->estado = 1;
        } else {
            $empleado->estado = 0;
        }
        $empleado->user_id = $usuario->id;   
        $empleado->role_id = $request->rol;
        
        // Guardamos/Insertamos el nuevo empleado
        $empleado->save();

       // Redirigimos al index donde nos mostrarán todos los empleados inclusive el añadido
        return redirect()->route('empleados.index')->with('Mensaje','Empleado agregado con éxito');
    }

    /**
     * Método que nos devuelve la ficha de información sobre la fila seleccionada 
     *
     * @param  Empleado  $empleado
     * @return \Illuminate\Http\Response
     */
    public function show(Empleado $empleado) {  
        //Al view show, pasamos como parametro el empleado seleccionado
        return view('empleados.show',compact('empleado'));
    }
    
    /**
     * Método que nos devuelve la view edit (form) para poder actualizar un empleado
     *
     * @param  Empleado  $empleado
     * @return \Illuminate\Http\Response
     */
    public function edit(Empleado $empleado) { 

        //Al estar user y empleado unidos, tengo que buscar el id del empleado en la tabla de user
        $user = User::findOrFail($empleado->user_id);

        //Al view edit, pasamos como parametro el empleado y user seleccionado
        return view('empleados.edit',compact('empleado','user'));
    }

    /**
     *  Método que obtiene los valores insertados por el usuario, para ser actualizados sobre la fila seleccionada
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  Empleado $empleado
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Empleado $empleado){

        //Validamos los campos
        $request->validate([
            'nombre'=>['required'],
            'apellidos'=>['required'],
            'fechaNacimiento'=>['required'],
            'dni'=>['required',new Dni],            
            'telefono' => ['required', new Telefono],
            'genero'=>['required']
        ]); 

        // Si la contraseña no es nula, la cambiamos por la nueva
        if($request->pass != null){
            $password = bcrypt($request->pass);
        } else {
            $usuario = User::where('id',$empleado->user_id)->first();
            $password = $usuario->password;
        }

        // Actualizamos lso datos del usuario
        User::where('id',$empleado->user_id)->update([
            'name'=>$request->nombre,
            'password'=>$password,
            'role_id'=>$request->rol,
        ]);  
        
        if($request->estado == 'Activo'){
            $estado = 1;
        } else {
            $estado = 0;
        }        
        
        // Actualizamos el Empleado
        Empleado::where('id',$empleado->id)->update([
            'nombre'=>$request->nombre,
            'apellidos'=>$request->apellidos,
            'dni'=>$request->dni,
            'fechaNacimiento'=> date('Y-m-d', strtotime($request->fechaNacimiento)),
            'email'=>$empleado->email,
            'telefono'=>$request->telefono,
            'genero'=>$request->genero,
            'contrato'=>$request->contrato,
            'estado'=>$estado,
            'role_id'=>$request->rol,
        ]);

        // Redirigimos a index
        return redirect()->route('empleados.show', $empleado->id)->with('Mensaje','Empleado modificado con éxito');
    }

    /**
     * Método que elimina la fila seleccionada
     *
     * @param  Empleado  $empleado
     * @return \Illuminate\Http\Response
     */
    public function destroy(Empleado $empleado){

        // Cambiamos el estado del empleado a 0
        // Para ponermo como inacctivo, pero no lo borramos
        $empleado->estado = 0;
        $empleado->save();

        //Redirigimos a index
        return redirect()->route('empleados.index');
    }

}


