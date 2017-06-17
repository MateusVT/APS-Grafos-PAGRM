/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

import java.io.BufferedReader;
import java.io.File;
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
    private java.io.FileReader fileReader;
    private BufferedReader bufferReader;

    public List<Grafo> leitor(String path) {
        String linha = null;
        int numeroRotulos;
        String[] aux = path.split("/");
        setNome(aux[aux.length-1]);
        Grafo grafoMatriz;

        List<List<Integer>> grafo = new ArrayList<>();

        List<Grafo> grafos = new ArrayList<>();

        try {
            fileReader = new java.io.FileReader(path);
            bufferReader = new BufferedReader(fileReader);

            linha = bufferReader.readLine();
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
            return null;
        }

        String elementosLinha[] = linha.split(" ");
        numeroRotulos = Integer.parseInt(elementosLinha[0]);

        List<Integer> rotulos = new ArrayList<>();

        for (int i = 1; i < numeroRotulos; i++) {
            rotulos.add(i);
        }

        try {
            while ((linha = bufferReader.readLine()) != null) {
                if (!linha.equals("")) {
                    String splitLinha[] = linha.split(" ");
                    List<Integer> splitLinhaInt = new ArrayList<>();
                    for (int i = 0; i < splitLinha.length; i++) {
                        splitLinhaInt.add(Integer.parseInt(splitLinha[i]));

                    }
                    grafo.add(splitLinhaInt);
                } else {
                    grafoMatriz = new Grafo(grafo, rotulos, numeroRotulos);
                    grafos.add(grafoMatriz);
                    grafo = new ArrayList<>();
                }
            }
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }

        return grafos;
    }

    public void gravar(List<List<Integer>> mutations, float mediaRotulos, float tempo) {
        File file = new File("saida/"+getNome());
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
            writer.write("\nMédia de Rótulos: "+mediaRotulos);
            writer.write("\nTempo Médio: "+tempo);
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
