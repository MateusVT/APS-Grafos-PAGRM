/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leotr
 */
public class Arquivo {

    private String nome;

    public static ArrayList<String> leitor(String caminho) {
        ArrayList<String> arquivo = new ArrayList<>();
        File f = new File(caminho);
        if (f.exists()) {
            try {
                //OpenFile
                FileReader fr = new FileReader(caminho);
                BufferedReader br = new BufferedReader(fr);
                String linha = br.readLine();
                while (linha != null) {
                    arquivo.add(linha);
                    linha = br.readLine();
                }
                br.close();
            } catch (Exception e) {//Catch exception if any
                System.err.println("Error: " + e.getMessage());
            }
        } else {
            arquivo.add("");
        }
        return arquivo;
    }

    public List<Grafo> criaGrafos(String path) {
        String[] aux = path.split("/");
        setNome(aux[aux.length - 1]);
        ArrayList<Grafo> grafos = new ArrayList<Grafo>();

        ArrayList<String> arquivo = leitor(path);

        String[] linha = arquivo.get(0).split(" ");
        Integer tamMatriz = Integer.parseInt(linha[0]);
        Integer peso = Integer.parseInt(linha[1]);
        int ini = 1, fim = ini + tamMatriz;

        while (fim <= arquivo.size()) {

            List<String> text = arquivo.subList(ini, fim);
            Collections.reverse(text);

            Grafo grafo = new Grafo(tamMatriz);

            for (Integer i = 0; i < tamMatriz; i++) {
                linha = text.get(i).split(" ");

                for (Integer k = 0; k < linha.length; k++) {
                    Integer n;
                    String s = linha[k];
                    if (s.equals("") || s.equals(peso.toString())) {
                        n = 20;
                    } else {
                        n = Integer.parseInt(linha[k]);
                    }

                    grafo.getMatrizAdjGrafo()[i][k] = n;
                    grafo.getMatrizAdjGrafo()[k][i] = n;

                }
                grafo.getMatrizAdjGrafo()[i][i] = 20;
            }
            grafos.add(grafo);
            ini = fim;
            fim = ini + tamMatriz;

        }

        return grafos;
    }

    public void gravar(List<List<Integer>> mutations, float mediaRotulos, float tempo) {
        File file = new File("saida/" + getNome());
        try {
            FileWriter writer = new FileWriter(file);
            for (List<Integer> rotulos : mutations) {
                Collections.sort(rotulos);
                writer.write("Mutation: [");
                for (Integer rotulo : rotulos) {
                    writer.write(rotulo + " ");
                }
                writer.write("]\n");
            }
            writer.write("\nMédia de Rótulos: " + mediaRotulos);
            writer.write("\nTempo Médio: " + tempo);
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
