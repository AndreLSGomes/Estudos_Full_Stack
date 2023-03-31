import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoDaNasa implements ExtratorDeConteudo {

    public List<Conteudo> extraiConteudos(String json) {

        // Extrair apenas dados que nos interessam
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaAtributos = parser.parse(json);

        List<Conteudo> conteudos = new ArrayList<>();

        // Popular a lista de conteúdos
        for (Map<String, String> atributos : listaAtributos) {

            // Setando nome da figurinha
            String titulo = atributos.get("title");
            titulo        = titulo.replaceAll("[^a-zA-Z0-9]","_") + ".png";

            String urlImage  = atributos.get("url");
            String copyright = atributos.get("copyright");
            String date      = atributos.get("date");
            
            Conteudo conteudo = new Conteudo(titulo, urlImage, copyright, date);

            conteudos.add(conteudo);

        }

        return conteudos;
        
    }
    
}