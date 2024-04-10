package com.example.cadastros_financas;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;


public class ListaCategoriaFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView listaCategoria;
    ArrayList<Categoria> categorias;
    CategoriaAdapter adapter;
    int selectedPosition = -1;
    //-----------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //-----------------------------------------------------------------
    class CategoriaAdapter extends ArrayAdapter<Categoria> {
        public CategoriaAdapter(Context context) {
            super(context, 0, categorias);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = getLayoutInflater().inflate(R.layout.item_categoria, null);
            }
            Categoria categoria = categorias.get(position);
            ((TextView) v.findViewById(R.id.item_nome)).setText(categoria.getDescricao());
            ((TextView) v.findViewById(R.id.item_qnt)).setText(Integer.toString(categoria.retornaQntContas()));
            ((TextView) v.findViewById(R.id.item_valor)).setText(Double.toString(categoria.retornaSomaContas()));
            if (position == selectedPosition) {
                v.setBackgroundColor( Color.LTGRAY);
            } else {
                v.setBackgroundColor( Color.WHITE);
            }
            return v;
        }
    }

    //-----------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lista_categoria, container, false);
        listaCategoria = (ListView) v.findViewById(R.id.listaCategorias);
        listaCategoria.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listaCategoria.setOnItemClickListener(this);
        return v;
    }

    //-----------------------------------------------------------------
   public ListView retornaListViewCategorias(){
        return listaCategoria;
   }

    //-----------------------------------------------------------------
    public void adicionar(Categoria c) {
        categorias.add(c);
        adapter.notifyDataSetChanged();
    }

    //-----------------------------------------------------------------
    public void setCategorias(ArrayList<Categoria> categorias) {
        this.categorias = categorias;
        adapter = new CategoriaAdapter(getActivity());
        listaCategoria.setAdapter(adapter);
    }

    //-----------------------------------------------------------------
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        if (pos == selectedPosition) {
            selectedPosition = -1;
        } else {
            selectedPosition = pos;
        }
        adapter.notifyDataSetChanged();
    }

    //-----------------------------------------------------------------
    public Categoria getCategoriaSelecionada() {
        if (selectedPosition < 0) {
            return null;
        }
        return categorias.get(selectedPosition);
    }

    //-----------------------------------------------------------------
    public void substituir(Categoria old, Categoria novo) {
        int posicao = categorias.indexOf(old);
        if (posicao >= 0) {
            categorias.set(posicao, novo);
            adapter.notifyDataSetChanged();
        }
    }
    //-----------------------------------------------------------------
    public boolean remover(Categoria categoria) {
        try {
            categorias.remove(categoria);
            adapter.notifyDataSetChanged();
            return true;
        }catch (Exception e){
            Log.d("LISTA-CATEGORIA", e.getMessage());
            return false;
        }
    }
}