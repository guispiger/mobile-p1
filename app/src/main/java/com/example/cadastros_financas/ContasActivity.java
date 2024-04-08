package com.example.cadastros_financas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContasActivity extends AppCompatActivity {
    ArrayList<Conta> contas;
    CadastroContaFragment fragCadastroConta;
    ListaContasFragment fragListaContas;
    Conta contaEditando = null;
    Categoria categoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);

        Intent origem =  getIntent();
        categoria = origem.getSerializableExtra("categoria", Categoria.class);

        fragCadastroConta = (CadastroContaFragment) getFragmentManager().findFragmentByTag("fragCadastroConta");
        fragListaContas = (ListaContasFragment) getFragmentManager().findFragmentByTag("fragListaContas");
        if (savedInstanceState != null) {
            contas = (ArrayList<Conta>) savedInstanceState.getSerializable("listaContas");
        }
        if(categoria.getContas() != null){
            contas = categoria.getContas();
        }
        if (contas == null) {
            contas = new ArrayList<Conta>();
        }

        fragListaContas.setContas(contas);
    }

    //-----------------------------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle bld) {
        super.onSaveInstanceState(bld);
        bld.putSerializable("listaContas", contas);
    }

    //-----------------------------------------------------------------
    public void cadastrarConta(View v) {
        if (fragCadastroConta != null) {
            Conta conta = fragCadastroConta.validarDados(this.categoria);
            if (conta != null) {
                if (contaEditando != null) {
                    this.confirmaEdit(contaEditando, conta);
                    Log.d("CONTA", "Alterada");
                } else {
                    this.confirmaAdd(conta);
                    Log.d("CONTA", "Adicionada");
                }
                contaEditando = null;
            }
        }
    }

    //----------------------------------------------------------------
    public void confirmaAdd(Conta conta) {
        AlertDialog.Builder bld = new AlertDialog.Builder(ContasActivity.this);
        bld.setTitle("Confirmar");
        bld.setMessage("Confirma a adição da Conta: " + conta.getDescricao() + " ?");

        bld.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fragListaContas.adicionar(conta);

                Intent itResult = new Intent();
                itResult.putExtra("contas", fragListaContas.getContas());
                setResult(RESULT_OK, itResult);
                finish();
            }
        });

        bld.setNeutralButton("Cancelar", null);

        bld.show();
    }

    //----------------------------------------------------------------
    public void confirmaEdit(Conta contaEditando, Conta conta) {
        AlertDialog.Builder bld = new AlertDialog.Builder(ContasActivity.this);
        bld.setTitle("Confirmar");
        bld.setMessage("Confirma a alteração da conta: " + conta.getDescricao() + " ?");

        bld.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fragListaContas.substituir(contaEditando, conta);
            }
        });

        bld.setNeutralButton("Cancelar", null);

        bld.show();
    }

}
