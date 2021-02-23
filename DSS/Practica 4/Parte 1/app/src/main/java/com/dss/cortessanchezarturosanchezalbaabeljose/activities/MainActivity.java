package com.dss.cortessanchezarturosanchezalbaabeljose.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dss.cortessanchezarturosanchezalbaabeljose.R;
import com.dss.cortessanchezarturosanchezalbaabeljose.adapters.ComputerAdapter;
import com.dss.cortessanchezarturosanchezalbaabeljose.api.ComputerApiService;
import com.dss.cortessanchezarturosanchezalbaabeljose.models.Computer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<Computer> computerList;
    RecyclerView rvComputers;
    ComputerAdapter adapterComputers;
    ImageView maps;
    public void showList(){
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvComputers.setLayoutManager(llm);
        adapterComputers = new ComputerAdapter(this, computerList);
        rvComputers.setAdapter(adapterComputers);
        adapterComputers.notifyDataSetChanged();
    }

    public void get(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                // Uso la 10.0.2.2 que es la direccion de enlace
                // del Android Virtual Device con la maquina local
                //  localhost no funciona :)
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ComputerApiService api = retrofit.create(ComputerApiService.class);

        Call<ArrayList<Computer>> llamada = api.getAllComputers();
        llamada.enqueue(new Callback<ArrayList<Computer>>() {
            @Override
            public void onResponse(Call<ArrayList<Computer>> call, Response<ArrayList<Computer>> response) {
                computerList = response.body();
                showList();
                System.out.println(computerList);
            }

            @Override
            public void onFailure(Call<ArrayList<Computer>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void initData(){
        computerList = new ArrayList<>();
        rvComputers = findViewById(R.id.rv_computers);
        this.maps = findViewById(R.id.mapsView);//tomar de la vista
        this.maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cambiaMaps = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(cambiaMaps);
            }
        });
    }



    // A esta función se le llama cuando termina de construirse
    // la actividad. En ella es donde se establece qué layout
    // es decir, qué res/layout/*.xml se muestra en la pantalla (vista)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        // Es aquí, donde tenemos seguridad de que podemos manejar
        // cosas de la actividad  y de la vista, sin que haya excepciones
        get();
    }

}