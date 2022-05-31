package com.example.nurseeye;

public class  resultadospuntaje {
    int puntaje;
    private String slocinicial;
    private String slocactual;
    private String snumheridas;
    private String sisquemia;
    private String sinfeccion;
    private String sedema;
    private String sneuropatia;
    private String sarea;
    private String scicatrizacion;
    private String sprofundidad;

    public resultadospuntaje( String slocinicial, String slocactual, String snumheridas, String sisquemia, String sinfeccion, String sedema, String sneuropatia, String sarea, String sprofundidad,
                             String scicatrizacion, int puntaje){

        this.slocinicial = slocinicial;
        this.slocactual = slocactual;
        this.snumheridas= snumheridas;
        this.sisquemia = sisquemia;
        this.sinfeccion = sinfeccion;
        this.sedema = sedema;
        this.sneuropatia = sneuropatia;
        this.sarea = sarea;
        this.sprofundidad = sprofundidad;
        this.scicatrizacion = scicatrizacion;
        this.puntaje = puntaje;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getSlocinicial() {
        return slocinicial;
    }

    public void setSlocinicial(String slocinicial) {
        this.slocinicial = slocinicial;
    }

    public String getSlocactual() {
        return slocactual;
    }

    public void setSlocactual(String slocactual) {
        this.slocactual = slocactual;
    }

    public String getSnumheridas() {
        return snumheridas;
    }

    public void setSnumheridas(String snumheridas) {
        this.snumheridas = snumheridas;
    }

    public String getSisquemia() {
        return sisquemia;
    }

    public void setSisquemia(String sisquemia) {
        this.sisquemia = sisquemia;
    }

    public String getSinfeccion() {
        return sinfeccion;
    }

    public void setSinfeccion(String sinfeccion) {
        this.sinfeccion = sinfeccion;
    }

    public String getSedema() {
        return sedema;
    }

    public void setSedema(String sedema) {
        this.sedema = sedema;
    }

    public String getSneuropatia() {
        return sneuropatia;
    }

    public void setSneuropatia(String sneuropatia) {
        this.sneuropatia = sneuropatia;
    }

    public String getSarea() {
        return sarea;
    }

    public void setSarea(String sarea) {
        this.sarea = sarea;
    }

    public String getScicatrizacion() {
        return scicatrizacion;
    }

    public void setScicatrizacion(String scicatrizacion) {
        this.scicatrizacion = scicatrizacion;
    }

    public String getSprofundidad() {
        return sprofundidad;
    }

    public void setSprofundidad(String sprofundidad) {
        this.sprofundidad = sprofundidad;
    }




}
