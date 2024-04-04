package com.example.cadastros_financas;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;


public class ListaCategoriaFragment extends Fragment {

    ListView listaCategoria;

    ArrayList<Categoria> categorias;

    CategoriaAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    class CategoriaAdapter extends ArrayAdapter<Categoria> {
        public CategoriaAdapter(Context context) {
            super(context, 0, categorias);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if(v == null){
                v = getLayoutInflater().inflate(R.layout.item_categoria, null);
            }
            Categoria categoria = categorias.get(position);
            ((TextView) v.findViewById(R.id.item_nome)).setText(categoria.getDescricao());
            ((TextView) v.findViewById(R.id.item_qnt)).setText(Integer.toString(categoria.retornaQntContas()));
            ((TextView) v.findViewById(R.id.item_valor)).setText(Double.toString(categoria.retornaSomaContas()));
            return v;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lista_categoria, container, false);
        listaCategoria = (ListView) v.findViewById(R.id.listaCategorias);
        return v;
    }

    public void adicionar(Categoria c){
        categorias.add(c);
        adapter.notifyDataSetChanged();
    }

    public void setCategorias(ArrayList<Categoria> categorias) {
        this.categorias = categorias;
        adapter = new CategoriaAdapter( getActivity() );
        listaCategoria.setAdapter( adapter );
    }
}