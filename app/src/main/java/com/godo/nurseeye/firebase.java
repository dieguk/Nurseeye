package com.godo.nurseeye;

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

import java.text.SimpleDateFormat;
import java.util.Date;

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

        if(!rut.equals("")&& !heridas1.equals("")){



        datospaciente datosPacienteins = new datospaciente(rut, nombre, diagnostico, sexo, edad, fecha, tiempoev, medicamentos, amorbidos, heridas1);
            myRef.setValue(datosPacienteins);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String currentDateandTime = sdf.format(new Date());
            String TRuta6 = currentDateandTime;
            String Nruta1 = null, Nruta2 = null, Nruta3 = null, Nruta4 = null, Nruta5 = null, Nruta6 = null, Rutastemporales = "Rutastemporales";
            ;

            FbRutas rutasTemporales = new FbRutas(Nruta1, Nruta2, Nruta3, Nruta4, Nruta5, Nruta6);

            db.collection("datosPacienteins").document(rut).set(datosPacienteins)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(firebase.this, "Paciente registrado correctamente", Toast.LENGTH_LONG).show();
                            //leerdatos();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(firebase.this, "no se pudo registrar al paciente", Toast.LENGTH_LONG).show();

                        }
                    });

            db.collection("datosPacienteins").document(rut).collection("resultados").document("Rutastemporales").set(rutasTemporales)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(firebase.this,LoadActivity.class);
                            startActivity(intent);
                        }
                    });


        }else {
            Toast.makeText(firebase.this, "por favor complete todo los campos", Toast.LENGTH_LONG).show();
        }
    }


    public void leerdatos(View view) {




            Intent intent = new Intent(firebase.this,LoadActivity.class);
            startActivity(intent);



    }
}