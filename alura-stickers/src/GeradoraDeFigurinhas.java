
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

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
        int novaAltura = altura + 200;
        
        // Criando a nova imagem
        BufferedImage novaImagem =  new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // Copiando a imagem
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        // Setando a fonte para escrever na imagem
        var font = new Font("Impact", Font.BOLD, 80 );
        graphics.setFont(font);
        graphics.setColor(Color.yellow);

        // Setando o texto a ser escrito na imagem
        String word = "";

        if(rating >= 9.0) {
            word = "TOPZERA";
        } else if(rating >= 8.0 && rating < 9.0) {
            word = "LEGAL";
        } else {
            word = "MAOMENOS";
        }

        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D rectangle   = fontMetrics.getStringBounds(word, graphics);
        int wordWidth           = (int)rectangle.getWidth();
        int wordPositionX       = (largura - wordWidth) / 2;
        int wordPositionY       = (novaAltura - 100);

        // Escrevendo na nova imagem
        graphics.drawString(word, wordPositionX, wordPositionY);

        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout = new TextLayout(word, font, fontRenderContext);
        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(wordPositionX, wordPositionY);
        graphics.setTransform(transform);
        var outlineStroke = new BasicStroke(largura * 0.004f);
        graphics.setStroke(outlineStroke);
        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);

        // Setando o diretório onde serão armazenadas as imagens
        String saida = "saida/";

        File dir = new File(saida);
        if (!dir.exists()) {
            new File(saida).mkdir();
        }
        ImageIO.write(novaImagem, "png", new File(saida + nomeArquivo));
    }
    
}