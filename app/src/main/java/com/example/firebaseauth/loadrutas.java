package com.example.firebaseauth;

import com.example.firebaseauth.interfaces.ListenerFB;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class loadrutas {
    String area;
    Long puntos;
    int area1, puntos1;
    public void loadrutas(String ruta, String rut, String herida, ListenerFB listener) {


            String karea = "sarea";

            FirebaseFirestore dbc = FirebaseFirestore.getInstance();
            DocumentReference load3 = dbc.collection("datosPacienteins").document(rut).collection("resultados").document(herida + ruta);
            load3.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    area = documentSnapshot.getString("sarea");
                    puntos = documentSnapshot.getLong("puntaje");
                    puntos1 = puntos.intValue();

                    if (area.equals("mayor a 40cm2)")){
                        area1 = 50;

                    }
                    if (area.equals("menor a 10 cm2 (equivalente a 2 monedas de 500)")){
                        area1 = 10;

                    }
                    if (area.equals("10 a 40 cm2")){
                        area1 = 40;

                    }

                    listener.exito(area1,puntos1);
                }

            }).addOnFailureListener(e -> listener.fracaso());




    }


    public int traductor (String area){

        if (area == "mayor a 40cm2)"){
            area1 = 70;

        }
        if (area == "menor a 10 cm2 (equivalente a 2 monedas de 500)"){
            area1 = 5;

        }
        if (area == "10 a 40 cm2"){
            area1 = 5;

        }
        return area1;
    }

}
