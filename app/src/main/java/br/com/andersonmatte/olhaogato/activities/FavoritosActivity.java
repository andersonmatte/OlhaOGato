package br.com.andersonmatte.olhaogato.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.andersonmatte.olhaogato.R;
import br.com.andersonmatte.olhaogato.base.ActivityMenuBase;
import br.com.andersonmatte.olhaogato.entidade.Gato;
import es.dmoral.toasty.Toasty;
import io.realm.RealmResults;

public class FavoritosActivity extends ActivityMenuBase {

    private List<Gato> listaGatosFavoritos;
    private RealmResults<Gato> gatoRealmResults;
    private Bitmap imageBitmap;
    private static final int TIRAR_FOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_favoritos);
        this.recuperarFavoritos(false);
        FloatingActionButton floatingActionButton = findViewById(R.id.botaoTirarFoto);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, TIRAR_FOTO);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TIRAR_FOTO && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            imageBitmap = (Bitmap) bundle.get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (imageBitmap != null){
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte imagemBytes [] = byteArrayOutputStream.toByteArray();
                this.salvarRegistroNovo(imagemBytes);
            }
        }
    }

    //Recupera as URLS das imagens favoritas antes marcadas pelo usuário.
    public void recuperarFavoritos(Boolean origemExcluir){
        gatoRealmResults = realm.where(Gato.class).findAllAsync();
        gatoRealmResults.load();
        if (gatoRealmResults != null && !gatoRealmResults.isEmpty()){
            this.listaGatosFavoritos = gatoRealmResults;
            super.populaListViewGatos(this.listaGatosFavoritos, true);
        } else if (gatoRealmResults.isEmpty() && origemExcluir){
            // Voçê ainda não tem favoritos!
            Intent intent = new Intent(FavoritosActivity.this, GatoActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //Pega a linha selecionada na ListView.
    public void onClickApagar(View view){
        if (view.getTag() != null){
            this.excluir((Integer) view.getTag());
        }
    }

    //Salva o objeto no Banco,
    public void salvarRegistroNovo(byte imagemBytes []) {
        super.realm.beginTransaction();
        Gato gatoSalvar = new Gato();
        gatoSalvar.setUrl("N");
        gatoSalvar.setFoto(imagemBytes);
        super.realm.insertOrUpdate(gatoSalvar);
        super.realm.commitTransaction();
        Toasty.success(this, getResources().getString(R.string.registro_favoritado), Toast.LENGTH_SHORT, true).show();
        this.reseleciona(false);
    }

    //Apaga o registro selecionado da Lista de favoritos.
    public void excluir(int posicao){
        if (this.listaGatosFavoritos != null && !this.listaGatosFavoritos.isEmpty()){
            super.realm.beginTransaction();
            Gato gato = this.listaGatosFavoritos.get(posicao);
            gato.deleteFromRealm();
            super.realm.commitTransaction();
            Toasty.warning(this, getResources().getString(R.string.registro_nao_favoritado), Toast.LENGTH_SHORT, true).show();
            this.reseleciona(true);
        }
    }

    //Reseleciona os registros do BD.
    public void reseleciona(Boolean origemExcluir){
        this.recuperarFavoritos(origemExcluir);
    }

    public List<Gato> getListaGatosFavoritos() {
        return listaGatosFavoritos;
    }

    public void setListaGatosFavoritos(List<Gato> listaGatosFavoritos) {
        this.listaGatosFavoritos = listaGatosFavoritos;
    }

}
