package br.com.andersonmatte.olhaogato.entidade;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "response")
public class ImagensGato {

    @ElementList(inline = true)
    @Path("data/images")
    public List<ImagemGato> catImages;

}
