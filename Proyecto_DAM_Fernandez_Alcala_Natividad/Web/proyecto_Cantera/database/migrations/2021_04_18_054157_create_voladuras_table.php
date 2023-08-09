<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateVoladurasTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('voladuras', function (Blueprint $table) {
            $table->id();
            $table->string('localizacion');
            $table->double('m2_superficie');
            $table->string('malla_perforacion');
            $table->double('profundidad_barrenos');
            $table->integer('numero_barrenos');
            $table->double('kg_explosivo');
            $table->double('precio');
            $table->double('piedra_bruta');
            $table->dateTime('fecha_voladura');
            $table->integer('id_empleado');
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
        Schema::dropIfExists('voladuras');
    }
}
