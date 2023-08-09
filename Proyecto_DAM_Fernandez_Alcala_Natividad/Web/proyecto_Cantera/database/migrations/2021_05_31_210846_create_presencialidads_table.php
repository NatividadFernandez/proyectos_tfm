<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreatePresencialidadsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('presencialidads', function (Blueprint $table) {
            $table->id();
            $table->integer('id_empleado');
            $table->date('fecha');
            $table->dateTime('fecha_check_in');
            $table->dateTime('fecha_check_out')->nullable();;
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
        Schema::dropIfExists('presencialidads');
    }
}
