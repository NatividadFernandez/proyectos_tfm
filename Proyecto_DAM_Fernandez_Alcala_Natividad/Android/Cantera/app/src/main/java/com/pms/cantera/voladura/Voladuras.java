package com.pms.cantera.voladura;

public class Voladuras {

    private Integer id;
    private String localizacion;
    private Double m2_superficie;
    private String malla_perforacion;
    private Double profundidad_barrenos;
    private Integer numero_barrenos;
    private Double kg_explosivo;
    private Double precio;
    private Double piedra_bruta;
    private String fecha_voladura;
    private Integer id_empleado;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Double getM2_superficie() {
        return m2_superficie;
    }

    public void setM2_superficie(Double m2_superficie) {
        this.m2_superficie = m2_superficie;
    }

    public String getMalla_perforacion() {
        return malla_perforacion;
    }

    public void setMalla_perforacion(String malla_perforacion) {
        this.malla_perforacion = malla_perforacion;
    }

    public Double getProfundidad_barrenos() {
        return profundidad_barrenos;
    }

    public void setProfundidad_barrenos(Double profundidad_barrenos) {
        this.profundidad_barrenos = profundidad_barrenos;
    }

    public Integer getNumero_barrenos() {
        return numero_barrenos;
    }

    public void setNumero_barrenos(Integer numero_barrenos) {
        this.numero_barrenos = numero_barrenos;
    }

    public Double getKg_explosivo() {
        return kg_explosivo;
    }

    public void setKg_explosivo(Double kg_explosivo) {
        this.kg_explosivo = kg_explosivo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPiedra_bruta() {
        return piedra_bruta;
    }

    public void setPiedra_bruta(Double piedra_bruta) {
        this.piedra_bruta = piedra_bruta;
    }

    public String getFecha_voladura() {
        return fecha_voladura;
    }

    public void setFecha_voladura(String fecha_voladura) {
        this.fecha_voladura = fecha_voladura;
    }

    public Integer getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(Integer id_empleado) {
        this.id_empleado = id_empleado;
    }
}
