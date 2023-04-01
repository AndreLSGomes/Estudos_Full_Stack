import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

        Integer opcao               = -999;
        Scanner scan                = new Scanner(System.in);
        String url                  = "";
        ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();
        List<Conteudo> conteudos    = new ArrayList<>();
        var geradora                = new GeradoraDeFigurinhas();             
        Double stars;

        while (opcao != 9) {

            System.out.println("\n \u001b[41mEscolha a lista de filmes e ou series que deseja visualizar!\u001b[m \n");
            System.out.println("\u001b[33m" + "1 - Top Movies");
            System.out.println("2 - Top Series");
            System.out.println("3 - Most Popular Movies");
            System.out.println("4 - Most popular Series");
            System.out.println("5 - Nasa Astronomy Pictures");
            System.out.println("9 - SAIR DO SISTEMA\n");

            System.out.print("O que deseja visualizar? " + "\u001b[m");
            opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
                    System.out.println("\n\u001b[31mTop Movies \u001b[m");
                    break;
                case 2:
                    url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";
                    System.out.println("\n\u001b[31mTop Series \u001b[m");
                    break;
                case 3:
                    url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
                    System.out.println("\n\u001b[31mPopular Movies \u001b[m");
                    break;
                case 4:
                    url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";
                    System.out.println("\n\u001b[31mPopular Series \u001b[m");
                    break;
                case 5:
                    url = "https://api.nasa.gov/planetary/apod?api_key=ab2Tua2vGOm2J0wN5op99L2UVWlROU8tykD32QUI&start_date=2022-06-12&end_date=2022-06-16";
                    System.out.println("\n\u001b[31mNasa Astronomy Pictures \u001b[m");
                    break;
                case 9:
                    System.out.println("\n\u001b[31mVOLTE SEMPRE QUE DESEJAR! \u001b[m \n");
                    break;
                default:
                    System.out.println("\nOPCAO INVALIDA!");
                    continue;
            }

            if(opcao >= 1 && opcao <= 5 ) {

                var http = new ClienteHttp();
                String json = http.buscaDados(url);

                if(opcao != 5) {
                    extrator = new ExtratorDeConteudoImdb();
                }else {
                    extrator = new ExtratorDeConteudoDaNasa();
                }

                String textoFigurinha;
                InputStream imgRating;

                conteudos = extrator.extraiConteudos(json);
            
                for (int i = 0; i < conteudos.size(); i++) {

                    Conteudo conteudo = conteudos.get(i);

                    InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();

                    // Setando nome da figurinha
                    String nomeArquivo = conteudo.getTitulo();
                    nomeArquivo = nomeArquivo.replaceAll("[^a-zA-Z0-9]","_") + ".png";

                    System.out.println("\n\u001b[1m  Title: \u001b[m" + conteudo.getTitulo());
                    System.out.println("\u001b[1m Poster: \u001b[m" + conteudo.getUrlImagem());

                    // Gerando a figurinha para API's do IMDB e printando sua classificação
                    if(opcao != 5) {
                        stars = Double.parseDouble(conteudo.getImDbRating());
                        
                        System.out.print("\u001b[1m Rating: \u001b[m" + " ");

                        String otimo = "😍";
                        String bom   = "🙂";
                        String ruim  = "😒";
                        
                        if(stars >= 8.0) {
                            System.out.println(otimo);
                            textoFigurinha    = "TOPZERA";
                            imgRating         = new FileInputStream(new File("sobreposicao/otimo.png"));
                        } else if(stars >= 7.0 && stars < 8.0) {
                            System.out.println(bom);
                            textoFigurinha    = "BOM";
                            imgRating         = new FileInputStream(new File("sobreposicao/bom.png"));
                        }else {
                            System.out.println(ruim);
                            textoFigurinha    = "RUIM";
                            imgRating         = new FileInputStream(new File("sobreposicao/ruim.png"));
                        }
                    } else {
                        textoFigurinha = conteudo.getCopyright();
                        imgRating      = new FileInputStream(new File("sobreposicao/otimo.png"));
                    }
                    
                    geradora.cria(inputStream, nomeArquivo, textoFigurinha, imgRating);
                }
            }
		}
        scan.close();
    }
}