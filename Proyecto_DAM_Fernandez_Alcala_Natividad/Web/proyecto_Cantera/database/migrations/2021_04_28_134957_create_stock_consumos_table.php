<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateStockConsumosTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('stock_consumos', function (Blueprint $table) {
            $table->id();
            $table->double('gasoleo');
            $table->double('aceite_motor');
            $table->double('aceite_hidraulico');
            $table->double('aceite_transmisiones');
            $table->double('valvulina');
            $table->double('grasas');
            $table->dateTime('fecha_consumo');
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
        Schema::dropIfExists('stock_consumos');
    }
}
