
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {

    public void cria(InputStream inputStream, String nomeArquivo, Double rating) throws Exception {

        /*
            Fazer a leitura da imagem
            Criar nova imagem com transparência e novo tamanho (em memória)
            Copiar imagem original para nova imagem (em memória)
            Escrever uma frase na nova imagem
            Escrever a nova imagem em um arquivo
        */
        
        /* Carregando imagem original
            LENDO IMAGEM APÓS DOWNLOAD PARA O PROJETO
            InputStream inputStream = new FileInputStream(new File("entrada/filme.jpg"));
        */
        /*  Carregando imagem original
            LENDO IMAGEM DA URL
            InputStream inputStream = 
                            new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies_1.jpg")
                            .openStream();
        */
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        // Obtendo altura e largura da imagem
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();

        // Setando nova altura da imagem
        int novaAltura = altura + 200;
        
        // Criando a nova imagem
        BufferedImage novaImagem =  new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // Copiando a imagem
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        // Setando a fonte para escrever na imagem
        var fonte = new Font(Font.SANS_SERIF, Font.BOLD, 84);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fonte);

        // Escrever na nova Imagem de acordo com a classificação do filme
        if(rating >= 8.0) {
            graphics.drawString("TOPZERA", 350, novaAltura - 70);
        } else if (rating >= 7.0 && rating < 8.0) {
            graphics.drawString("NICE", 350, novaAltura - 70);
        } else {
            graphics.drawString("BLAH!", 350, novaAltura - 70);
        }

        ImageIO.write(novaImagem, "png", new File("saida/" + nomeArquivo));
    }
    
}