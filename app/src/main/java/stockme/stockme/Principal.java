package stockme.stockme;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import stockme.stockme.persistencia.BDHandler;
import stockme.stockme.util.Preferencias;
import stockme.stockme.util.Util;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Fragment_listas.OnFragmentInteractionListener, Fragment_stock.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //inicialziar preferencias
        Preferencias.inicializarPreferencias(this);
        //con esto ya podremos usar los métodos estáticos de Preferencias
        crearPreferenciasPorDefecto();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BDHandler handler = new BDHandler(this);
        handler.obtenerArticulos();

        Fragment fragmento = new Fragment_listas();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenido_principal, fragmento).commit();
        this.setTitle("Listas");

        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void crearPreferenciasPorDefecto() {
        if(Preferencias.getPreferenciaString("moneda") == null)
            Preferencias.addPreferencia("moneda", "€");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean fragTransact = false;
        Fragment fragmento = null;

        if (id == R.id.nav_listas) {
            fragmento = new Fragment_listas();
            fragTransact = true;
        } else if (id == R.id.nav_stock) {
            fragmento = new Fragment_stock();
            fragTransact = true;
        } else if (id == R.id.nav_supermercados) {

        } else if (id == R.id.nav_ajustes) {

        }


        if(fragTransact){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenido_principal, fragmento).commit();
            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
        if(i != null) {
            Bundle extras = i.getExtras();
            if(extras != null) {
                String opc = extras.getString("Opcion");
                //TODO: Aquí habrá que cargar el fragment corresopndiente a la opción elegida
                if (opc != null)
                    Util.mostrarToast(this, "He seleccionado: " + opc);
            }
        }
    }
}
