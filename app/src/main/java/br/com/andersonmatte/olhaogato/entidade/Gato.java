package br.com.andersonmatte.olhaogato.entidade;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Gato extends RealmObject {

    private long id;

    @Required
    private String url;

    private byte [] foto;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

}
