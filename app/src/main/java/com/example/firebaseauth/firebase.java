package com.example.firebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class firebase extends AppCompatActivity {
    private EditText txtinprut,txtinpnombre, txtinpdiagnostico,txtinptiempoev,txtinpedad,txtinpsexo,txtinpfecha,txtinpamorbidos,txtinpmedicamentos,txtinpheridas1;
    String dato1 = "rut";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference load = db.collection("datosPacienteins").document("paciente");
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase);


        textView = findViewById(R.id.textView);

        txtinprut = findViewById(R.id.RUT);
        txtinpnombre = findViewById(R.id.nombre);
        txtinpdiagnostico = findViewById(R.id.diagnostico);
        txtinptiempoev = findViewById(R.id.tiempoevolucion);
        txtinpedad = findViewById(R.id.edad);
        txtinpsexo = findViewById(R.id.sexo);
        txtinpfecha = findViewById(R.id.fecha);
        txtinpamorbidos = findViewById(R.id.amorbidos);
        txtinpmedicamentos=findViewById(R.id.medicamentos);
        txtinpheridas1 =findViewById(R.id.herida1);


    }

    public void Subirdatos(View view) {

        String rut = txtinprut.getText().toString();
        String nombre = txtinpnombre.getText().toString();
        String diagnostico = txtinpdiagnostico.getText().toString();
        String sexo = txtinpsexo.getText().toString();
        String edad = txtinpedad.getText().toString();
        String fecha = txtinpfecha.getText().toString();
        String tiempoev = txtinptiempoev.getText().toString();
        String medicamentos = txtinpmedicamentos.getText().toString();
        String amorbidos = txtinpamorbidos.getText().toString();
        String heridas1 = txtinpheridas1.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference(rut);

        datospaciente datosPacienteins = new datospaciente(rut,nombre,diagnostico,sexo,edad,fecha,tiempoev,medicamentos,amorbidos,heridas1);
        myRef.setValue(datosPacienteins);

        db.collection("datosPacienteins").document(rut).set(datosPacienteins)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(firebase.this,"Paciente registrado correctamente",Toast.LENGTH_LONG).show();
                        //leerdatos();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


    }

    public void leerdatos(View view) {
        Intent intent = new Intent(firebase.this,LoadActivity.class);
        startActivity(intent);

       /*load.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
               if (documentSnapshot.exists()) {
                   String rut = documentSnapshot.getString(dato1);
                   txtinprut.setText(rut);
                   // textView.setText(documentSnapshot.getData().toString()); //toma todo los valores de la ruta

               }
           }
       });*/
    }
}