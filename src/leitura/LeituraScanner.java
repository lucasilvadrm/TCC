/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leitura;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lucas
 */
public class LeituraScanner {

    public static float[][] leitura() throws FileNotFoundException, IOException {
        String conteudo = "";
        FileReader arquivo = new FileReader("C:/Users/User/Google Drive/NETBEANS/TCC/src/instancias/atsp/ftv170.atsp");
        BufferedReader lerArq = new BufferedReader(arquivo);
        String linha = "";

        linha = lerArq.readLine();
        linha = lerArq.readLine();
        linha = lerArq.readLine();
        linha = lerArq.readLine();

        String dimensao = linha.substring(linha.indexOf(':') + 1).trim();

        //System.out.println(dimensao);

        int tam = Integer.parseInt(dimensao);

        float[][] matrizCusto = new float[tam][tam];

        linha = lerArq.readLine();

        while (linha != null) {
            conteudo += linha;
            linha = lerArq.readLine();
        }

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(conteudo);

        int[] lista = new int[tam * tam];
        int cont = 0;
        while (matcher.find()) {

            lista[cont] = Integer.parseInt(matcher.group());
            if (cont <= lista.length) {
                cont++;
            }
        }
        
        if(conteudo.contains("FULL_MATRIX")){
            matrizFull(matrizCusto, lista);
        }else if(conteudo.contains("UPPER_ROW")){
            matrizTriSuperior(matrizCusto, lista);
        }
       
        return matrizCusto;
    }
    
    static void matrizFull(float [][] matriz, int [] lista ){
        int contadorLista = 0;
        for (int li = 0; li < matriz.length; li++) {
            for (int ci = 0; ci < matriz.length; ci++) {
                matriz[li][ci] = lista[contadorLista];
                contadorLista++;
            }
        }
    }
    
    static void matrizTriSuperior(float [][] matriz, int [] lista){
        int contadorLista = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = i + 1; j < matriz.length; j++) {
                matriz[i][j] = lista[contadorLista];
                matriz[j][i] = lista[contadorLista];
                contadorLista++;
            }
        }
    }
}
