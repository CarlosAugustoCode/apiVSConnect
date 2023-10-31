package com.senai.apivsconnect.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileUploadService {
    private final Path diretorioImg = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img");// Configuração do diretório para salvamento da imagem

    public String FazerUpload(MultipartFile imagem) throws IOException { // metodo para retornar o nome da imagem "String"

        if (imagem.isEmpty()) {
            System.out.println("Imagem vazia!");
            return null;
        }

        String nomeOriginal = imagem.getOriginalFilename(); // Pega o nome da imagem
        String[] nomeArquivoArray = nomeOriginal.split("\\.");
        String extensaoArquivo = nomeArquivoArray[nomeArquivoArray.length - 1]; // jpg

        String prefixoArquivo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")); // 27102023202134

        String novoNomeImagem = prefixoArquivo + "." + extensaoArquivo;

        File imagemCriada = new File(diretorioImg + "\\" + novoNomeImagem); // Criar um novo arquivo com o nome gerado na imagem criada

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(imagemCriada)); // Ler os bytes da imagem criada e transmitir no novo arquivo
        stream.write(imagem.getBytes());
        stream.close();

        return novoNomeImagem;
    }
}
