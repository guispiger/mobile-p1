package com.example.cadastros_financas;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Categoria> categorias;
    Categoria editando = null;
    CadastroCategoriaFragment fragCadastroCategoria;
    ListaCategoriaFragment fragListaCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragCadastroCategoria = (CadastroCategoriaFragment) getFragmentManager().findFragmentByTag("fragCadastroCategoria");
        fragListaCategoria = (ListaCategoriaFragment) getFragmentManager().findFragmentByTag("fragListaCategorias");
        if (savedInstanceState != null) {
            categorias = (ArrayList<Categoria>) savedInstanceState.getSerializable("listaCategorias");
        }
        if(categorias == null){
            categorias = new ArrayList<Categoria>();
        }

        fragListaCategoria.setCategorias(categorias);
    }

    @Override
    public void onSaveInstanceState(Bundle bld) {
        super.onSaveInstanceState(bld);
        bld.putSerializable( "listaCategorias", categorias);
    }

    public void adicionar(View v){
        if(fragCadastroCategoria != null){
            Categoria categoria = fragCadastroCategoria.validarDados();
            if(categoria != null){
                AlertDialog.Builder bld = new AlertDialog.Builder(MainActivity.this);
                bld.setTitle("Confirmar");
                bld.setMessage("Deseja adicionar a categoria: " + categoria.getDescricao() + " ?");

                bld.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fragListaCategoria.adicionar(categoria);
                        Log.d("CATEGORIA", "Adicionada");
                    }
                });

                bld.setNeutralButton("Cancelar", null);

                bld.show();
            }
        }
    }
}