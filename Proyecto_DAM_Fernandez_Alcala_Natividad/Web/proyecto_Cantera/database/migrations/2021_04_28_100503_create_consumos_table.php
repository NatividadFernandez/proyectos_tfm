<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateConsumosTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('consumos', function (Blueprint $table) {
            $table->id();
            $table->double('gasoleo');
            $table->double('aceite_motor');
            $table->double('aceite_hidraulico');
            $table->double('aceite_transmisiones');
            $table->double('valvulina');
            $table->double('grasas');
            $table->dateTime('fecha_recepcion');
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
        Schema::dropIfExists('consumos');
    }
}
