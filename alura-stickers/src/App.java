import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        /*
            Fazer conexão HTTP para buscar Top 250 filmes
            Extrair apenas dados que nos interessam:
                1- Título
                2- Poster (imagem)
                3- Classificação
            Manipular e exibir os dados em nossa aplicação
        */

        // Fazer conexão HTTP para buscar Top 250 filmes
        String url_top_movies     = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        String url_top_series     = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";
        String url_popular_movies = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        String url_popular_series = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";

        URI endereco = URI.create(url_top_movies);
        Integer opcao = -999;

        Scanner scan = new Scanner(System.in);

        while (opcao != 9) {

            System.out.println("\n \u001b[41mEscolha a lista de filmes e ou series que deseja visualizar!\u001b[m \n");
            System.out.println("\u001b[33m" + "1 - Top Movies");
            System.out.println("2 - Top Series");
            System.out.println("3 - Most Popular Movies");
            System.out.println("4 - Most popular Series");
            System.out.println("9 - SAIR DO SISTEMA\n");

            System.out.print("O que deseja visualizar? " + "\u001b[m");
            opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    endereco = URI.create(url_top_movies);
                    System.out.println("\n\u001b[31mTop Movies \u001b[m");
                    break;
                case 2:
                    endereco = URI.create(url_top_series);
                    System.out.println("\n\u001b[31mTop Series \u001b[m");
                    break;
                case 3:
                    endereco = URI.create(url_popular_movies);
                    System.out.println("\n\u001b[31mPopular Movies \u001b[m");
                    break;
                case 4:
                    endereco = URI.create(url_popular_series);
                    System.out.println("\n\u001b[31mPopular Series \u001b[m");
                    break;
                case 9:
                    System.out.println("\n\u001b[31mVOLTE SEMPRE QUE DESEJAR! \u001b[m \n");
                    break;
                default:
                    System.out.println("\nOPCAO INVALIDA!");
                    continue;
            }

            if(opcao >= 1 && opcao <= 4 ) {

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                String body = response.body();

                // Extrair apenas dados que nos interessam
                JsonParser parser = new JsonParser();
                List<Map<String, String>> listaDeFilmes = parser.parse(body);
                
                var geradora = new GeradoraDeFigurinhas();
            
                for (Map<String,String> filme : listaDeFilmes) {

                    // Lendo Imagem
                    String urlImage = filme.get("image");
                    InputStream inputStream = new URL(urlImage).openStream();

                    // Setando nome da figurinha
                    String nomeArquivo = filme.get("title") + ".png";

                    // Gerando a figurinha
                    Double stars = Double.parseDouble(filme.get("imDbRating"));
                    geradora.cria(inputStream, nomeArquivo, stars);

                    System.out.println("\n\u001b[1m  Title: \u001b[m" + filme.get("title"));
                    System.out.println("\u001b[1m Poster: \u001b[m" + filme.get("image"));
                    System.out.print("\u001b[1m Rating: \u001b[m" + filme.get("imDbRating") + " ");

                    String otimo = "😍";
                    String bom   = "🙂";
                    String ruim  = "😒";
                    
                    if(stars >= 8.0) {
                        System.out.println(otimo);
                    } else if(stars >= 7.0 && stars < 8.0) {
                        System.out.println(bom);
                    }else {
                        System.out.println(ruim);
                    }
                }
            }
		}
        scan.close();
    }
}