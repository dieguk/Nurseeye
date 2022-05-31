package com.example.nurseeye;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.nurseeye.interfaces.ListenerFB;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.IOException;

public class LoadResultados<area1> extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String rutdelpaciente,heridaprincipal;
    String Nruta1,Nruta2,Nruta3,Nruta4,Nruta5;
    String Cfc1,Cfc2,Cfc3,Cfc4,Cfc5;
    String area1,area2,area3,area4,area5;
    String ptotales ;
    Integer p1,p2,p3,p4,p5;
    Integer Area1 = 0, Area2 = 0, Area3= 0, Area4 = 0, Area5 = 0;
    FirebaseFirestore dbc = FirebaseFirestore.getInstance();
    private StorageReference mStoragereference;
    FirebaseStorage firebaseStorage;

    GraphView grafico1;
    GraphView grafico2 ;
    GraphView grafico3;
    ImageView herida1 ;
    ImageView herida2 ;
    ImageView herida3;

    EditText resultadoescala ;

    TextView fechas, fechas2, fechafoto1, fechafoto2, fechafoto3 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_resultados);
        Bundle bundle = getIntent().getExtras();

                grafico1 = findViewById(R.id.graficopuntostotales);
         grafico2 = findViewById(R.id.graficoclasificacion);
         grafico3 = findViewById(R.id.graficoarea);
         herida1 = findViewById(R.id.Herida1);
         herida2 = findViewById(R.id.Herida2);
         herida3 = findViewById(R.id.Herida3);
        resultadoescala = findViewById(R.id.TxtClasificacion);
        fechafoto1 = findViewById(R.id.fechafoto1);
        fechafoto2 = findViewById(R.id.fechafoto2);
        fechafoto3 = findViewById(R.id.fechafoto3);
        Button botonfinal = findViewById(R.id.botonfinal);

         fechas = findViewById(R.id.uno);
         fechas2 = findViewById(R.id.textResultados2);

        rutdelpaciente = bundle.getString("rutdelpaciente");
        heridaprincipal = bundle.getString("nombreherida");
        Nruta1 = bundle.getString("ruta1");
        Nruta2 = bundle.getString("ruta2");
        Nruta3 = bundle.getString("ruta3");
        Nruta4 = bundle.getString("ruta4");
        Nruta5 = bundle.getString("ruta5");




        Tareahilo2();

    }

    public void Tareahilo2() {

        loadrutas leerarea = new loadrutas();


        leerarea.loadrutas(Nruta1, rutdelpaciente, heridaprincipal, new ListenerFB() {


            @Override
            public void exito(Integer area, Integer puntos) {


                mStoragereference = FirebaseStorage.getInstance().getReference("images/").child(rutdelpaciente+heridaprincipal+Nruta5);

              try {


                    final File localfile = File.createTempFile("foto","jpg");



                    mStoragereference.getFile(localfile).addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                        herida1.setImageBitmap(bitmap);



                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Area1 = area;
                p1 = puntos;
                traductorgravedad(p1);
                pp1 = pc;

                Log.i("area", String.valueOf(area1));
                grafico1.setVisibility(View.INVISIBLE);


                leerarea.loadrutas(Nruta2, rutdelpaciente, heridaprincipal, new ListenerFB() {
                    @Override
                    public void exito(Integer area, Integer puntos) {
                        mStoragereference = FirebaseStorage.getInstance().getReference("images/"+rutdelpaciente+heridaprincipal+Nruta4);
                        try {
                            final File localfile = File.createTempFile("foto","jpg");
                            mStoragereference.getFile(localfile).addOnSuccessListener(taskSnapshot -> {
                                Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                herida2.setImageBitmap(bitmap);

                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        Area2 = area;
                        p2 = puntos;
                        traductorgravedad(p2);
                        pp2 = pc;
                        Toast.makeText(LoadResultados.this,"Datos leidos correctamente",Toast.LENGTH_LONG).show();
                        leerarea.loadrutas(Nruta3, rutdelpaciente, heridaprincipal, new ListenerFB() {
                            @Override
                            public void exito(Integer area,Integer puntos) {
                                mStoragereference = FirebaseStorage.getInstance().getReference("images/"+rutdelpaciente+heridaprincipal+Nruta3);
                                try {
                                    final File localfile = File.createTempFile("foto","jpg");
                                    mStoragereference.getFile(localfile).addOnSuccessListener(taskSnapshot -> {
                                        Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                        herida3.setImageBitmap(bitmap);

                                    });

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                Area3 = area;
                                p3= puntos;
                                traductorgravedad(p3);
                                pp3 = pc;
                                leerarea.loadrutas(Nruta4, rutdelpaciente, heridaprincipal, new ListenerFB() {
                                    @Override
                                    public void exito(Integer area,Integer puntos) {
                                        Area4 = area;
                                        p4 = puntos;
                                        traductorgravedad(p4);
                                        pp4 = pc;
                                        leerarea.loadrutas(Nruta5, rutdelpaciente, heridaprincipal, new ListenerFB() {
                                            @Override
                                            public void exito(Integer area,Integer puntos) {
                                                Area5 = area;
                                                p5 = puntos;
                                                traductorgravedad(p5);
                                                pp5 = pc;

                                                //seria de datos para graficar
                                                LineGraphSeries<DataPoint> Sarea = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                                        new DataPoint(1, Area1), new DataPoint(2, Area2), new DataPoint(3, Area3), new DataPoint(4, Area4), new DataPoint(5, Area5)
                                                });

                                                LineGraphSeries<DataPoint> Spuntos = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                                        new DataPoint(1, p1), new DataPoint(2, p2), new DataPoint(3, p3), new DataPoint(4, p4), new DataPoint(5, p5)
                                                });
                                                resultados(puntos);
                                                LineGraphSeries<DataPoint> Sclasifc = new LineGraphSeries<DataPoint>(new DataPoint[]{

                                                        new DataPoint(1, pp1), new DataPoint(2, pp2), new DataPoint(3, pp3), new DataPoint(4, pp4), new DataPoint(5, pp5)
                                                });
                                                resultados(puntos);

                                                resultadoescala.setText(resultado);
                                                grafico1.addSeries(Sarea);
                                                grafico2.addSeries(Spuntos);
                                                grafico3.addSeries(Sclasifc);
                                                grafico1.setVisibility(View.VISIBLE);
                                                String fechas1 = " 1:" + Nruta1 + "    2:" + Nruta2 + "    3:" + Nruta3 + "    4:" + Nruta4;
                                                String fechas22 = "  5:" + Nruta5;
                                                fechas.setText(fechas1);
                                                fechas2.setText(fechas22);
                                                fechafoto1.setText(Nruta5);
                                                fechafoto2.setText(Nruta4);
                                                fechafoto3.setText(Nruta3);
                                                Log.i("A5", String.valueOf(Area5));
                                            }

                                            @Override
                                            public void fracaso() {

                                            }
                                        });

                                    }

                                    @Override
                                    public void fracaso() {

                                    }
                                });

                            }

                            @Override
                            public void fracaso() {

                            }
                        });
                    }

                    @Override
                    public void fracaso() {

                    }
                });


            }

            @Override
            public void fracaso() {

            }
        });



    }
    String resultado;
    public void resultados (Integer puntos){
        if (puntos < 10){
            resultado = "Herida leve";
        }
        if (puntos > 10 && puntos <=20){
            resultado= "herida moderada";
        }
        if (puntos >20){
            resultado = "herida grave";
        }

    }
    Integer pc,pp1,pp2,pp3,pp4,pp5;
    public void traductorgravedad (Integer p){
        if (p < 10){
            pc = 1;
        }
        if (p > 10 && p <=20){
            pc = 2;
        }
        if (p >20){
            pc= 3;
        }
    }

    public void evaluacionterminada(View view) {
        Intent intent = new Intent(LoadResultados.this,LoadActivity.class);
        startActivity(intent);
    }
}
          //comentario de prueba branch1