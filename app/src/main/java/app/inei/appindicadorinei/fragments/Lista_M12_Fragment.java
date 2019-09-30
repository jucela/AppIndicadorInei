package app.inei.appindicadorinei.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.activities.Search_Activity;
import app.inei.appindicadorinei.adapters.ItemIndicadorAdapter;
import app.inei.appindicadorinei.adapters.ItemListaM12Adapter;
import app.inei.appindicadorinei.interfaces.IComunicaFragments;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.pojos.DataIndicador;
import app.inei.appindicadorinei.modelo.pojos.NroSubindicador;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Lista_M12_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Lista_M12_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Lista_M12_Fragment extends Fragment {
    ArrayList<SubIndicador> subindicadores;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    Activity actividad;
    IComunicaFragments interfaceComunicaFragments;
    int id_indicador;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "dato";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Lista_M12_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Lista_M12_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Lista_M12_Fragment newInstance(String param1, String param2) {
        Lista_M12_Fragment fragment = new Lista_M12_Fragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle datos = getActivity().getIntent().getExtras();
        if(datos!=null)
        {id_indicador= datos.getInt("id",0);
        Log.i("valor_size",""+filtrarNroSubindicador(obtenerDataNroSubIndicadoresXId(4)).size());
        Log.i("valores",""+filtrarNroSubindicador(obtenerDataNroSubIndicadoresXId(4)).get(3).getEjex());}




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_data_m12_lista, container, false);
        View vista=inflater.inflate(R.layout.fragment_data_m12_lista, container, false);
        recyclerView = (RecyclerView) vista.findViewById(R.id.lista_item_m12);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ItemListaM12Adapter itemListaM12Adapter = new ItemListaM12Adapter(filtrarEjeX(obtenerDataEjeXSubIndicadoresXId(id_indicador)), new ItemListaM12Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("id_array_fragmentList",""+position);
                //Toast.makeText(getContext(),"POSICION:"+position,Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarDatos(position);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemListaM12Adapter);

//        String myValue = this.getArguments().getString("message");
        Log.i("valor",""+obtenerDataNroSubIndicadoresXId(id_indicador));
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
        if(context instanceof Activity){
            this.actividad= (Activity) context;
            interfaceComunicaFragments= (IComunicaFragments) this.actividad;
        }

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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public ArrayList<SubIndicador> obtenerAllSubIndicadores()
    { ArrayList<SubIndicador> subindicadores = new ArrayList<>();
        try {
            Data data = new Data(getContext());
            data.open();
            subindicadores = data.getAllSubIndicador();
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  subindicadores;

    }

    public ArrayList<String> filtrarEjeX(ArrayList<String> lista) {
        ArrayList<String> nuevaLista = new ArrayList<>();
        if (lista.size() > 0) {

            if (lista.get(0).isEmpty()) {
                for (String objeto : lista) {
                    if (!nuevaLista.contains(objeto))
                        nuevaLista.add(objeto);
                }
            } else {
                for (String objeto : lista) {
                    if (!nuevaLista.contains(objeto))
                        nuevaLista.add(objeto);
                }
            }
        } else {
             }
            return nuevaLista;
        }


    public ArrayList<String> obtenerDataEjeXSubIndicadoresXId(int id_subindicador )
    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
        ArrayList<String> EjeX = new ArrayList<>();
        String ejex="";
        try {
            Data data = new Data(getContext());
            data.open();
            datossubindicador = data.getDataSubIndicadorXId(id_subindicador);

            for (int i=0;i<datossubindicador.size();i++)
            {
                ejex = datossubindicador.get(i).getEjex();
                EjeX.add(ejex);
            }
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  EjeX;

    }

//    public ArrayList<Integer> obtenerDataNroSubIndicadoresXId(int id_subindicador )
//    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
//        ArrayList<Integer> Numeros= new ArrayList<>();
//        int numero=0;
//        try {
//            Data data = new Data(getContext());
//            data.open();
//            datossubindicador = data.getDataSubIndicadorXId(id_subindicador);
//
//            for (int i=0;i<datossubindicador.size();i++)
//            {
//                numero = datossubindicador.get(i).getNro_subindicador();
//                Numeros.add(numero);
//            }
//            data.close();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        return  Numeros;
//
//    }
//
//    public ArrayList<Integer> filtrarNroSubindicador(ArrayList<Integer> lista) {
//        ArrayList<Integer> nuevaLista = new ArrayList<>();
//        if (lista.size() > 0) {
//
//            if (lista.get(0)==null) {
//                for (Integer objeto : lista) {
//                    if (!nuevaLista.contains(objeto))
//                        nuevaLista.add(objeto);
//                }
//            } else {
//                for (Integer objeto : lista) {
//                    if (!nuevaLista.contains(objeto))
//                        nuevaLista.add(objeto);
//                }
//            }
//        } else {
//        }
//        return nuevaLista;
//    }

    public ArrayList<DataIndicador> obtenerDataNroSubIndicadoresXId(int id_subindicador )
    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
        ArrayList<DataIndicador> Numeros= new ArrayList<>();
        DataIndicador numero = new DataIndicador();
        try {
            Data data = new Data(getContext());
            data.open();
            datossubindicador = data.getDataSubIndicadorXId(id_subindicador);

            for (int i=0;i<datossubindicador.size();i++)
            {
                numero = datossubindicador.get(i);
                Numeros.add(numero);
            }
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  Numeros;

    }

    public ArrayList<NroSubindicador> filtrarNroSubindicador(ArrayList<DataIndicador> lista) {
        ArrayList<NroSubindicador> nuevaLista = new ArrayList<>();
        if (lista.size() > 0) {

            if (lista.get(0)==null) {
                for (DataIndicador objeto : lista) {
                    if (!nuevaLista.contains(objeto.getNro_subindicador()))
                     nuevaLista.add(new NroSubindicador(objeto.getNro_subindicador(),objeto.getEjex()));
                }
            } else {
                for (DataIndicador objeto : lista) {
                    if (!nuevaLista.contains(objeto.getNro_subindicador()))
                        nuevaLista.add(new NroSubindicador(objeto.getNro_subindicador(),objeto.getEjex()));
                }
            }
        } else {
        }
        return nuevaLista;
    }
}
