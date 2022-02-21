package com.example.firebaseauth;


public class datospaciente {
    String RUT;
    String name;
    String diag;
    String sexo;
    String edad;
    String fecha;
    String tiempoev;
    String medicamentos;
    String amorbidos;
    String herida1;


    public datospaciente(String RUT, String name, String diag, String sexo, String edad, String fecha, String tiempoev, String medicamentos, String amorbidos,String herida1) {
        this.RUT = RUT;
        this.name = name;
        this.diag = diag;
        this.sexo = sexo;
        this.edad = edad;
        this.fecha = fecha;
        this.tiempoev = tiempoev;
        this.medicamentos = medicamentos;
        this.amorbidos = amorbidos;
        this.herida1 = herida1;
    }


    public String getHerida1(){return  herida1;}

    public void setHerida1(String herida1){this.herida1 = herida1;}

    public String getRUT() {
        return RUT;
    }

    public void setRUT(String RUT) {
        this.RUT = RUT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiag() {
        return diag;
    }

    public void setDiag(String diag) {
        this.diag = diag;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTiempoev() {
        return tiempoev;
    }

    public void setTiempoev(String tiempoev) {
        this.tiempoev = tiempoev;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getAmorbidos() {
        return amorbidos;
    }

    public void setAmorbidos(String amorbidos) {
        this.amorbidos = amorbidos;
    }
}