package com.pms.cantera.consumos.stockconsumos;

public class StockConsumos {

    private Integer id;
    private Double gasoleo;
    private Double aceite_motor;
    private Double aceite_hidraulico;
    private Double aceite_transmisiones;
    private Double valvulina;
    private Double grasas;
    private String fecha_consumo;
    private Integer id_empleado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getGasoleo() {
        return gasoleo;
    }

    public void setGasoleo(Double gasoleo) {
        this.gasoleo = gasoleo;
    }

    public Double getAceite_motor() {
        return aceite_motor;
    }

    public void setAceite_motor(Double aceite_motor) {
        this.aceite_motor = aceite_motor;
    }

    public Double getAceite_hidraulico() {
        return aceite_hidraulico;
    }

    public void setAceite_hidraulico(Double aceite_hidraulico) {
        this.aceite_hidraulico = aceite_hidraulico;
    }

    public Double getAceite_transmisiones() {
        return aceite_transmisiones;
    }

    public void setAceite_transmisiones(Double aceite_transmisiones) {
        this.aceite_transmisiones = aceite_transmisiones;
    }

    public Double getValvulina() {
        return valvulina;
    }

    public void setValvulina(Double valvulina) {
        this.valvulina = valvulina;
    }

    public Double getGrasas() {
        return grasas;
    }

    public void setGrasas(Double grasas) {
        this.grasas = grasas;
    }

    public Integer getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(Integer id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getFecha_consumo() {
        return fecha_consumo;
    }

    public void setFecha_consumo(String fecha_consumo) {
        this.fecha_consumo = fecha_consumo;
    }
}
