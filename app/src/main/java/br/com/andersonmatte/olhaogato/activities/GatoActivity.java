package br.com.andersonmatte.olhaogato.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.andersonmatte.olhaogato.R;
import br.com.andersonmatte.olhaogato.base.ActivityMenuBase;
import br.com.andersonmatte.olhaogato.entidade.Gato;
import br.com.andersonmatte.olhaogato.entidade.ImagemGato;
import br.com.andersonmatte.olhaogato.entidade.ImagensGato;
import br.com.andersonmatte.olhaogato.service.GatoService;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GatoActivity extends ActivityMenuBase {

    private List<Gato> listaGato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_gato);
        //Cria o Tonteador.
        progress = new ProgressDialog(this);
        //Busca na API http://thecatapi.com/docs.html as imagens dos gatos.
        buscaGatos();
        //Clique do botão buscar mais imagens de Gato.
        Button buttonBuscarUsuario = (Button) findViewById(R.id.buttonGato);
        buttonBuscarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscaGatos();
            }
        });
    }

    //Busca as imagens e de gatos e retosna em uma lista de url de imagens
    // com o auxilio do Adapter e do Picasso as imagens são exibidas na tela.
    public void buscaGatos() {
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage(this.getResources().getString(R.string.warning));
        progress.setCancelable(true);
        progress.show();
        GatoService retrofitTheCatApi = GatoService.retrofit.create(GatoService.class);
        retrofitTheCatApi.getGatos().enqueue(new Callback<ImagensGato>() {
            @Override
            public void onResponse(Call<ImagensGato> call, Response<ImagensGato> response) {
                if (response != null && response.body() != null
                        && response.body().catImages != null) {
                    listaGato = new ArrayList<Gato>();
                    for (ImagemGato img : response.body().catImages) {
                        Gato gato = new Gato();
                        gato.setUrl(img.url);
                        listaGato.add(gato);
                    }
                }
                if (listaGato != null){
                    GatoActivity.super.populaListViewGatos(listaGato, false);
                    progress.hide();
                }
            }
            @Override
            public void onFailure(Call<ImagensGato> call, Throwable t) {
                Log.i("ERRO", getResources().getString(R.string.erro_buscar_gato) + t.getMessage());
            }
        });
    }

    //Pega a linha selecionada na ListView.
    public void onClickFavorito(View view){
        if (view.getTag() != null){
            this.salvar((Integer) view.getTag());
        }
    }

    //Salva o objeto no Banco,
    public void salvar(int posicao) {
        super.realm.beginTransaction();
        Gato gatoSalvar = this.listaGato.get(posicao);
        super.realm.insertOrUpdate(gatoSalvar);
        super.realm.commitTransaction();
        Toasty.success(this, getResources().getString(R.string.registro_favoritado), Toast.LENGTH_SHORT, true).show();
    }

    public List<Gato> getListaGato() {
        return listaGato;
    }

    public void setListaGato(List<Gato> listaGato) {
        this.listaGato = listaGato;
    }

}
