<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateStockProductosTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('stock_productos', function (Blueprint $table) {
            $table->id();
            $table->double('arena_06');
            $table->double('grava_612');
            $table->double('grava_1220');
            $table->double('grava_2023');
            $table->double('rechazo');
            $table->double('zahorra');
            $table->double('escollera');
            $table->double('mamposteria');
            $table->integer('voladura');
            $table->dateTime('voladura_fecha');
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
        Schema::dropIfExists('stock_productos');
    }
}
