
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

    public void cria(InputStream inputStream, String nomeArquivo, String texto, InputStream inputStreamSobreposicao) throws Exception {
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

        // Obtendo altura e largura da imagem e setando a largura da imagem a ser criada
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 100;
        
        // Criando a nova imagem
        BufferedImage novaImagem =  new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // Copiando a imagem original para a nova imagem
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal, 0, 0, null);

        // Desenhando a sobreposição na imagem
        BufferedImage imagemSobreposicao = ImageIO.read(inputStreamSobreposicao);
        int posicaoImagemSobreposicaoY = novaAltura - imagemSobreposicao.getHeight();
        graphics.drawImage(imagemSobreposicao, 0, posicaoImagemSobreposicaoY, null);

        // Setando a fonte para escrever na imagem
        var font = new Font("Impact", Font.BOLD, 64);
        graphics.setColor(Color.BLACK);
        graphics.setFont(font);

        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D rectangle   = fontMetrics.getStringBounds(texto, graphics);
        int textodWidth         = (int)rectangle.getWidth();
        int textoPositionX      = (largura - textodWidth) / 2;
        int textoPositionY      = (novaAltura - 20);

        // Escrevendo na nova imagem
        graphics.drawString(texto, textoPositionX, textoPositionY);

        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout = new TextLayout(texto, font, fontRenderContext);
        
        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(textoPositionX, textoPositionY);
        graphics.setTransform(transform);

        var outlineStroke = new BasicStroke(largura * 0.004f);
        graphics.setStroke(outlineStroke);

        graphics.setColor(Color.WHITE);
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