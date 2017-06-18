/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author leotr
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Arquivo arquivo = new Arquivo();
        ArrayList<String> diretorios = arquivo.leitor("instancias.txt");
        for (int k = 0; k < diretorios.size(); k++) {

            int i = 1;
            List<Float> listaDeTemposDosGrafos = new ArrayList<>();
            List<Integer> quantidadeRotulos = new ArrayList<>();
            ArrayList<ArrayList<Rotulo>> rotulosMin = new ArrayList<>();

            Arquivo instancias = new Arquivo();
            ArrayList<Grafo> grafos = instancias.carregaListas(instancias.carregaMatrizes(diretorios.get(k)));

            System.out.println(instancias.getNome());

            for (Grafo grafo : grafos) {
                long tempoInicial = System.currentTimeMillis();
                System.out.println("Grafo: " + i);
                MVCA mvca = new MVCA();
                mvca.rotulos(grafo);
                quantidadeRotulos.add(mvca.getRotulosMin().size());
                rotulosMin.add(mvca.getRotulosMin());
                long tempoFinal = System.currentTimeMillis();
                float tempo = (tempoFinal - tempoInicial);
                listaDeTemposDosGrafos.add(tempo);
                i++;
            }

            float tempoTotal = 0;
            float tempoMedio = 0;
            float mediaRotulos = 0;

            for (int j = 0; j < listaDeTemposDosGrafos.size(); j++) {
                tempoTotal += listaDeTemposDosGrafos.get(j);
            }

            for (int j = 0; j < quantidadeRotulos.size(); j++) {
                mediaRotulos += quantidadeRotulos.get(j);
            }

            tempoMedio = tempoTotal / grafos.size();
            mediaRotulos = mediaRotulos / grafos.size();

            System.out.println("Tempo Total: " + tempoTotal + " ms");
            System.out.println("Tempo Médio: " + tempoMedio + " ms");
            System.out.println("Média de rótulos: " + mediaRotulos);

            instancias.gravador(rotulosMin, mediaRotulos, tempoTotal, tempoMedio);
            System.out.println("---------------------");
            System.out.println("");
        }
    }
}
