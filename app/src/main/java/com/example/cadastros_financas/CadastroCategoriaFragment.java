package com.example.cadastros_financas;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroCategoriaFragment extends Fragment {
    private EditText edCategoria;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cadastro_categoria, container, false);
        edCategoria = (EditText) v.findViewById(R.id.edCategoria);
        return v;
    }

    public Categoria validarDados(){
        String descricao = edCategoria.getText().toString().trim();
        if(descricao == null || descricao.isEmpty()){
            Toast.makeText(getActivity(), "Informe uma descrição para a categoria", Toast.LENGTH_SHORT).show();
            return null;
        }
        edCategoria.setText("");
        return new Categoria(descricao);
    }

    public void ajustarEdicao(Categoria c){
        edCategoria.setText(c.getDescricao());
    }
}