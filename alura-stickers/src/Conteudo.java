
public class Conteudo {
    
    private String titulo;
    private String urlImagem;
    private String imDbRating;
    private String copyright;
    private String date;

    // Construtor Endpoint's IMDB
    public Conteudo(String titulo, String imagem, String imDbRating) {
        this.titulo     = titulo;
        this.urlImagem  = imagem;
        this.imDbRating = imDbRating;
    }
    
    // Construtor Endpoint NASA
    public Conteudo(String titulo, String urlImagem, String copyright, String date) {
        this.titulo    = titulo;
        this.urlImagem = urlImagem;
        this.copyright = copyright;
        this.date      = date;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getDate() {
        return date;
    }

}