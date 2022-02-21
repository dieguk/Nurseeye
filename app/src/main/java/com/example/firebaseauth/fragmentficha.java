package com.example.firebaseauth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class fragmentficha extends Fragment{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public EditText txtinprut,txtinpnombre, txtinpdiagnostico,txtinptiempoev,txtinpedad,txtinpsexo,txtinpfecha,txtinpamorbidos,txtinpmedicamentos;
    public String disrut = "rut",disnombre="name",disdiagnostico="diag",distiempo="tiempoev",dissexo="sexo",disfecha="fecha",disamorbidos="amorbidos",dismedicamentos="medicamentos", ruta;
    private View view;
    private Object DocumentSnapshot;
    public DocumentSnapshot documentSnapshot;

    public fragmentficha() {
        // Required empty public constructorlayout.fragment_blank, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmentficha, container, false);
        return view;



    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtinprut = view.findViewById(R.id.RUT);
        txtinpnombre = view.findViewById(R.id.nombre);
        txtinpdiagnostico = view.findViewById(R.id.diagnostico);
        txtinptiempoev = view.findViewById(R.id.tiempoevolucion);
        txtinpedad = view.findViewById(R.id.edad);
        txtinpsexo = view.findViewById(R.id.sexo);
        txtinpfecha = view.findViewById(R.id.fecha);
        txtinpamorbidos = view.findViewById(R.id.amorbidos);
        txtinpmedicamentos=view.findViewById(R.id.medicamentos);

    }

    public void mostrarPacientes(String rut) {
        txtinprut.setText("q wuea");
        /*DocumentReference load = db.collection("datosPacienteins").document(rut);

        if(load.get().isSuccessful()){
                String nrut = load.;
                txtinprut.setText(nrut);
        }

    /*.addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                String nrut = documentSnapshot.getString(disrut);
                txtinprut.setText(nrut);

                // textView.setText(documentSnapshot.getData().toString()); //toma todo los valores de la ruta


            }
        });


                    String rut = documentSnapshot.getString(dato1);
                    textView.setText(rut);
                    // textView.setText(documentSnapshot.getData().toString());*/


    }


}