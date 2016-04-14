package stockme.stockme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputType;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import stockme.stockme.adaptadores.AdaptadorListItemListas;
import stockme.stockme.logica.Articulo;
import stockme.stockme.logica.Lista;
import stockme.stockme.logica.ListaArticulo;
import stockme.stockme.persistencia.BDHandler;
import stockme.stockme.util.Preferencias;
import stockme.stockme.util.Util;

public class Fragment_listas extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ListView listas;
    private Button btn_mas;


    public Fragment_listas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listas, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Preferencias.addPreferencia("anterior", "Listas");

        view.setVerticalScrollBarEnabled(false);

        listas = (ListView)view.findViewById(R.id.fragment_listas_listview);
        //recojo las listas existenes
        final BDHandler manejador = new BDHandler(view.getContext());
        List<Lista> listaListas = manejador.obtenerListas();
        //las añado al adaptador
        AdaptadorListItemListas adaptador = new AdaptadorListItemListas(view.getContext(), listaListas);
        //asigno el adaptador a la list view
        listas.setAdapter(adaptador);

        btn_mas = (Button)view.findViewById(R.id.fragment_listas_btn_mas);
        btn_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ListaAdd.class);
                startActivityForResult(i, 1);
            }
        });

        //al click en un elemento de la lista voy a inspeccionar sus artículos
        listas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(parent.getContext(), ListaDeArticulos.class);
                String nombreLista = ((Lista) parent.getItemAtPosition(position)).getNombre();
                Util.mostrarToast(parent.getContext(), nombreLista);
                i.putExtra("NombreLista", nombreLista);
                startActivityForResult(i, 1);
            }
        });

        manejador.cerrar();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1) && (resultCode == Activity.RESULT_OK)){
            Fragment fragmento = new Fragment_listas();
            FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.contenido_principal, fragmento);
            ft.attach(fragmento).commit();

            Util.mostrarToast(getContext(),"hey");
        }
    }
}
