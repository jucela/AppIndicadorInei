package app.inei.appindicadorinei.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.adapters.ItemContenidoM12Adapter;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.pojos.DataIndicador;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Contenido_M12_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Contenido_M12_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contenido_M12_Fragment extends Fragment {
    ArrayList<DataIndicador> dataindicador;
    RecyclerView recyclerView;
    int id_indicador;
    int nro_grafico;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public Contenido_M12_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contenido_M12_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Contenido_M12_Fragment newInstance(String param1, String param2) {
        Contenido_M12_Fragment fragment = new Contenido_M12_Fragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle datos = getActivity().getIntent().getExtras();
        if(datos!=null)
        {id_indicador= datos.getInt("id",0);
            nro_grafico  = datos.getInt("nro_grafico",0);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista=inflater.inflate(R.layout.fragment_data_m12_contenido, container, false);
        recyclerView = (RecyclerView) vista.findViewById(R.id.contenido_item_m12);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ItemContenidoM12Adapter itemListaM12Adapter = new ItemContenidoM12Adapter(obtenerDataSubIndicadoresXId(id_indicador,1,nro_grafico));
        recyclerView.setAdapter(itemListaM12Adapter);
        recyclerView.setLayoutManager(layoutManager);
        return vista;
    }

    public void asignarDatos(int nro_subindicador,int nro_grafico) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ItemContenidoM12Adapter itemListaM12Adapter = new ItemContenidoM12Adapter(obtenerDataSubIndicadoresXId(id_indicador,nro_subindicador,nro_grafico));
        recyclerView.setAdapter(itemListaM12Adapter);
        recyclerView.setLayoutManager(layoutManager);
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

    public ArrayList<DataIndicador> obtenerDataSubIndicadoresXId(int id_subindicador,int nro_subindicador,int nro_grafico )
    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
        try {
            Data data = new Data(getContext());
            data.open();
            datossubindicador = data.getDataSubIndicadorXAnio(id_subindicador,nro_subindicador,nro_grafico);
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  datossubindicador;

    }

    public ArrayList<DataIndicador> obtenerDataXNroSubindicador(int id_subindicador,int nro_subindicador )
    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
        try {
            Data data = new Data(getContext());
            data.open();
            datossubindicador = data.getDataSubIndicadorXNrosubindicador(id_subindicador,nro_subindicador);
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  datossubindicador;

    }
}
