package br.com.andersonmatte.olhaogato.entidade;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "image")
public class ImagemGato {

    @Element(name = "id")
    public String id;

    @Element(name = "url")
    public String url;

    @Element(name = "source_url")
    public String source_url;

}
