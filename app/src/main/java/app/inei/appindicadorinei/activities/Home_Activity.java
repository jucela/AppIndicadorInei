package app.inei.appindicadorinei.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.adapters.ItemHomeAdapter;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.pojos.Indicador;

public class Home_Activity extends AppCompatActivity {
    ItemHomeAdapter itemHomeAdapter;
    RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);



        recyclerView = (RecyclerView) findViewById(R.id.lista_item_home);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        itemHomeAdapter = new ItemHomeAdapter(obtenerAllIndicador(),new ItemHomeAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getApplicationContext(),"posiciòn:"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Indicador_Search_Activity.class);
                int id_indicador = position;
                //intent.putExtra("id",id_indicador);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemHomeAdapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Search_Activity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public ArrayList<Indicador> obtenerAllIndicador()
    { ArrayList<Indicador> indicadores = new ArrayList<>();
      try {
          Data data = new Data(Home_Activity.this);
          data.open();
          indicadores = data.getAllIndicador();
          data.close();
      }
      catch (IOException e){
          e.printStackTrace();
      }
     return  indicadores;
    }



}
