<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateEmpleadosTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('empleados', function (Blueprint $table) {
            $table->id();
            $table->string('dni');
            $table->string('nombre');
            $table->string('apellidos');
            $table->date('fechaNacimiento');
            $table->string('email');
            $table->string('telefono')->nullable();
            $table->integer('estado')->nullable();
            $table->string('genero')->nullable();
            $table->string('puesto')->nullable();
            $table->string('contrato')->nullable();
            $table->integer('role_id')->nullable();
            $table->unsignedBigInteger('user_id');
            $table->foreign('user_id')->references('id')->on('users');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('empleados');
    }
}
