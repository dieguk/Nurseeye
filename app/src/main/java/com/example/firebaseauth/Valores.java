package com.example.firebaseauth;

public class Valores {
    int largopx;
    int areareal;
    int areapx;
    int perimetro;

    public int CalculoPerimetro (int largo, int valorpixel){
            if(largo > 0){
                perimetro = largo * valorpixel;

            }

        return perimetro;
    }

}
