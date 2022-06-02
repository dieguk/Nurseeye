package com.example.nurseeye;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoadActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText txtinprut,txtinpnombre, txtinpdiagnostico,txtinptiempoev,txtinpedad,txtinpsexo,txtinpfecha,txtinpamorbidos,txtinpmedicamentos,txtinpherida1;
    public String disrut = "rut",disnombre="name",disdiagnostico="diag",distiempo="tiempoev",dissexo="sexo",disfecha="fecha",disamorbidos="amorbidos",dismedicamentos="medicamentos",disedad ="edad",disherida1="herida1";
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FrameLayout layoutframe;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    EditText buscadorcorto;
    String Ruta1= "ruta1",Ruta2="ruta2",Ruta3="ruta3",Ruta4="ruta4",Ruta5="ruta5",Ruta6="ruta6";
    String TRuta1;
    String TRuta2;
    String TRuta3;
    String TRuta4;
    String TRuta5;


    Button btnbuscarcorto,botonherida;
    //fragmentficha fragment_ficha = new fragmentficha();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        buscadorcorto = findViewById(R.id.editTextbuscador);
        btnbuscarcorto = findViewById(R.id.botonbuscar);

        navigationView = findViewById(R.id.navigationView);
        layoutframe = findViewById(R.id.layoutframe);
        layoutframe.setVisibility(View.INVISIBLE);


        drawerLayout = findViewById(R.id.drawer);
        navigationView.bringToFront();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        FrameLayout frameLayout;
        frameLayout = findViewById(R.id.layoutframe);



        txtinprut = findViewById(R.id.RUT);
        txtinpnombre = findViewById(R.id.nombre);
        txtinpdiagnostico = findViewById(R.id.diagnostico);
        txtinptiempoev = findViewById(R.id.tiempoevolucion);
        txtinpedad = findViewById(R.id.edad);
        txtinpsexo = findViewById(R.id.sexo);
        txtinpfecha = findViewById(R.id.fecha);
        txtinpamorbidos = findViewById(R.id.amorbidos);
        txtinpmedicamentos=findViewById(R.id.medicamentos);
        txtinpherida1 = findViewById(R.id.herida1);
        botonherida=findViewById(R.id.botonherida);
        //getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment_ficha).commit();




    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        if (menuItem.getItemId() == R.id.test) {
            Intent intent = new Intent(this, firebase.class);
            startActivity(intent);

        }

        if (menuItem.getItemId() == R.id.another) {
            Toast.makeText(this, "agrega la actividad flojo", Toast.LENGTH_LONG).show();

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void buscarpaciente(View view) {
        String rut = buscadorcorto.getText().toString();
        if (rut == ""){
            Toast.makeText(LoadActivity.this,"ingrese el RUT del paciente",Toast.LENGTH_LONG).show();
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
       DocumentReference load = db.collection("datosPacienteins").document(rut);
        load.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                layoutframe.setVisibility(View.VISIBLE);

                String ruta = documentSnapshot.getString(disrut);
                txtinprut.setText(ruta);
                String name = documentSnapshot.getString(disnombre);
                txtinpnombre.setText(name);
                String diag = documentSnapshot.getString(disdiagnostico);
                txtinpdiagnostico.setText(diag);
                String tiempoev = documentSnapshot.getString(distiempo);
                txtinptiempoev.setText(tiempoev);
                String edad = documentSnapshot.getString(disedad);
                txtinpedad.setText(edad);
                String sexo = documentSnapshot.getString(dissexo);
                txtinpsexo.setText(sexo);
                String amorbidos = documentSnapshot.getString(disamorbidos);
                txtinpamorbidos.setText(amorbidos);
                String medicamentos = documentSnapshot.getString(dismedicamentos);
                txtinpmedicamentos.setText(medicamentos);
                String fecha = documentSnapshot.getString(disfecha);
                txtinpfecha.setText(fecha);
                String herida1= documentSnapshot.getString(disherida1);
                txtinpherida1.setText(herida1);


                botonherida.setText(herida1);
                if (herida1 == ""){
                    txtinpherida1.setVisibility(View.INVISIBLE);
                }else{
                    botonherida.setText(herida1);
                    botonherida.setVisibility(View.VISIBLE);
                }


            } else {
                Toast.makeText(LoadActivity.this,"no se encuentra el paciente intentelo nuevamente",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void evaluar(View view) {
        Intent intent = new Intent(LoadActivity.this,camaraCV.class);
        intent.putExtra("rutdelpaciente",txtinprut.getText().toString());
        intent.putExtra("nombreherida",botonherida.getText().toString());
        if (botonherida.getText().toString()!= ""){
        startActivity(intent);}
    }

    public void informaciondeescala(View view) {
        TextView textinfo =findViewById(R.id.textView11);
        textinfo.setVisibility(View.VISIBLE);
    }
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void datosanteriores(View view) {
        String rutdelpaciente = txtinprut.getText().toString(), nombreherida = botonherida.getText().toString() ;
        DocumentReference load2 = db.collection("datosPacienteins").document(rutdelpaciente).collection("resultados").document("Rutastemporales");
        load2.get().addOnSuccessListener(documentSnapshot -> {

            if (documentSnapshot.exists()) {
                TRuta1 = documentSnapshot.getString(Ruta1);

                TRuta2 = documentSnapshot.getString(Ruta2);

                TRuta3 = documentSnapshot.getString(Ruta3);

                TRuta4 = documentSnapshot.getString(Ruta4);

                TRuta5 = documentSnapshot.getString(Ruta5);

            }
            String Nruta1,Nruta2,Nruta3,Nruta4,Nruta5;
            Nruta1 = TRuta1;
            Nruta2 = TRuta2;
            Nruta3 = TRuta3;
            Nruta4 = TRuta4;
            Nruta5 = TRuta5;

            Intent intent = new Intent(LoadActivity.this,LoadResultados.class);
            intent.putExtra("rutdelpaciente",rutdelpaciente);
            intent.putExtra("nombreherida",nombreherida);

            intent.putExtra("ruta1",Nruta1);
            intent.putExtra("ruta2",Nruta2);
            intent.putExtra("ruta3",Nruta3);
            intent.putExtra("ruta4",Nruta4);
            intent.putExtra("ruta5",Nruta5);
            startActivity(intent);
        });


    }
}