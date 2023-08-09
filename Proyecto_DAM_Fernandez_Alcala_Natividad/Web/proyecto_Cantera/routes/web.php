<?php

use App\Http\Controllers\EmpleadoController;
use App\Http\Controllers\VoladuraController;
use App\Http\Controllers\ProductoController;
use App\Http\Controllers\StockProductoController;
use App\Http\Controllers\ConsumoController;
use App\Http\Controllers\StockConsumoController;
use App\Http\Controllers\PresencialidadController;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\HomeController;
use Illuminate\Support\Facades\Auth;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
    /* return view('index'); */
});

Auth::routes();

Route::get('/home', [HomeController::class, 'index'])->name('home');

Route::get('empleados', [EmpleadoController::class, 'index'])->name('empleados.index');

Route::get('empleados/create', [EmpleadoController::class, 'create'])->name('empleados.create');

Route::post('empleados', [EmpleadoController::class, 'store'])->name('empleados.store');

Route::get('empleados/{empleado}', [EmpleadoController::class, 'show'])->name('empleados.show');

Route::get('empleados/{empleado}/edit', [EmpleadoController::class, 'edit'])->name('empleados.edit');

// Se recomienda put para actualizar en vez de post
Route::put('empleados/{empleado}', [EmpleadoController::class, 'update'])->name('empleados.update');

// Ruta para eliminar un registro
Route::delete('empleados/{empleado}', [EmpleadoController::class, 'destroy'])->name('empleados.destroy');


Route::resource('voladuras',VoladuraController::class);

Route::resource('productos',ProductoController::class);

Route::resource('stock_productos',StockProductoController::class);

Route::resource('consumos',ConsumoController::class);

Route::resource('stock_consumos',StockConsumoController::class);

Route::resource('presencialidad',PresencialidadController::class);

