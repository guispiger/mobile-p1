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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Categoria> categorias;
    CadastroCategoriaFragment fragCadastroCategoria;
    ListaCategoriaFragment fragListaCategoria;
    Categoria categoriaEditando = null;

    //-----------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragCadastroCategoria = (CadastroCategoriaFragment) getFragmentManager().findFragmentByTag("fragCadastroCategoria");
        fragListaCategoria = (ListaCategoriaFragment) getFragmentManager().findFragmentByTag("fragListaCategorias");
        if (savedInstanceState != null) {
            categorias = (ArrayList<Categoria>) savedInstanceState.getSerializable("listaCategorias");
        }
        if (categorias == null) {
            categorias = new ArrayList<Categoria>();
        }

        fragListaCategoria.setCategorias(categorias);
    }

    //-----------------------------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle bld) {
        super.onSaveInstanceState(bld);
        bld.putSerializable("listaCategorias", categorias);
    }

    //-----------------------------------------------------------------
    public void adicionar(View v) {
        if (fragCadastroCategoria != null) {
            Categoria categoria = fragCadastroCategoria.validarDados();
            if (categoria != null) {
                if (categoriaEditando != null) {
                    fragListaCategoria.substituir(categoriaEditando, categoria);
                    Log.d("CATEGORIA", "Alterada");
                } else {
                    this.confirmaAddEdit(categoria);
                    Log.d("CATEGORIA", "Adicionada");
                }
                categoriaEditando = null;
            }
        }
    }

    //----------------------------------------------------------------
    public void removerCategoria(View v) {
        if (fragListaCategoria != null) {
            Categoria categoria = fragListaCategoria.getCategoriaSelecionada();
            if (categoria == null) {
                Toast.makeText(this, "Selecione o categoria que deseja remover", Toast.LENGTH_SHORT).show();
            } else {
                if (!fragListaCategoria.remover(categoria)) {
                    Toast.makeText(this, "Erro inesperado ao remover categoria, verificar Logs!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //----------------------------------------------------------------
    public void editarCategoria(View v) {
        if (fragListaCategoria != null) {
            Categoria categoria = fragListaCategoria.getCategoriaSelecionada();
            if (categoria == null) {
                Toast.makeText(this, "Selecione a categoria a editar", Toast.LENGTH_SHORT).show();
            } else {
                fragCadastroCategoria.ajustarEdicao(categoria);
                categoriaEditando = categoria;
            }
        }
    }

    //----------------------------------------------------------------
    public void confirmaAddEdit(Categoria categoria) {
        AlertDialog.Builder bld = new AlertDialog.Builder(MainActivity.this);
        bld.setTitle("Confirmar");
        bld.setMessage("Confirma a adição da categoria: " + categoria.getDescricao() + " ?");

        bld.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fragListaCategoria.adicionar(categoria);
            }
        });

        bld.setNeutralButton("Cancelar", null);

        bld.show();
    }
}