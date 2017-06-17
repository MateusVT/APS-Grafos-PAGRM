/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author leotr
 */
public class AlgGenetico {
    
    public List<Integer> individuo(Grafo grafo) {

        //lista de labels usados
        List<Integer> rotulosUsados = new ArrayList<>();

        //lista de labels do grafo
        //lista de vertices de grafo
        List<Integer> rotulosGrafo = new ArrayList();
        rotulosGrafo.addAll(grafo.getRotulos());

        List<Integer> verticesGrafo = new ArrayList();
        verticesGrafo.addAll(grafo.getRotulos());

        //Inicializa matriz de listas de adja   cencia
        List<List<Integer>> subgrafo = new ArrayList<>();
        subgrafo = inicializaSubgrafo(grafo.getQtdVertices());

        //Enquanto houver vertices nao conectados 
        while (existeVerticeNConexo(verticesGrafo)) 

        Collections.sort(rotulosUsados);
        return rotulosUsados;
    }

    public List<Integer> crossover(Grafo grafo, List<Integer> s) {

        //Inicailiza array de frequencia
        List<Integer> frequencia = new ArrayList<>();
        frequencia = inicializaListaFrequencia(s.size());

        int i = 0; //posição que será incrementado a frequencia

        //Incrementa o array, com a frequencia dos labels 's' contidos no grafo
        for (Integer label : s) {
            for (List<Integer> lineOfGraph : grafo.getMatrizAdjGrafo()) {
                for (Integer columnOfGrapf : lineOfGraph) {
                    if (columnOfGrapf.equals(label)) {
                        frequencia.set(i, frequencia.get(i) + 1);
                    }
                }
            }
            i++;
        }

        //Inicializa Array de int com os labels do grafo
        List<Integer> labelsGrafo = new ArrayList();
        labelsGrafo.addAll(grafo.getRotulos());
        //Array de labels utilizados para construir o subgrafo
        List<Integer> labelsUsed = new ArrayList<>();

        //Inicializa matriz de listas de adjacencia
        List<List<Integer>> subgrafo = new ArrayList<>();
        subgrafo = inicializaSubgrafo(grafo.getQtdVertices());

        int line = 0, column = 0;

        //Equanto houver vertices nao conectados 
        while (existeVerticeNConexo(labelsGrafo)) {

            int indexMaxValor = frequencia.indexOf(Collections.max(frequencia));
            frequencia.set(indexMaxValor, 0);
            int maxValorDeS = s.get(indexMaxValor);
            if (!labelsUsed.contains(maxValorDeS)) {
                labelsUsed.add(maxValorDeS);
            }

            //Determine the number of connected components when inserting all edges with label i in H
            for (List<Integer> lineOfGraph : grafo.getMatrizAdjGrafo()) {
                for (Integer columnOfGrapf : lineOfGraph) {
                    if (columnOfGrapf.equals(maxValorDeS)) {
                        subgrafo.get(line).add(column + 1);//Add vértice da coluna em uma lista de vértices
                    }
                    column++;
                }
                column = 0;
                line++;
            }
            line = 0;
            column = 0;

            //Remove da lista de vétices do grafo todos os vértices que compunham o subgrafo
            for (List<Integer> list : subgrafo) {
                for (Integer value : list) {
                    if (labelsGrafo.contains(value)) {
                        labelsGrafo.remove(value);
                    }
                }
            }
        }//Ao fim deste laço temos o número mínimo de labels utilizando apenas dois individuos do grafo
        // e aplicando a união dos conjuntos de labels neste método de crossover.

        Collections.sort(labelsUsed);

        return labelsUsed;
    }

    public List<Integer> mutation(Grafo grafo, List<Integer> s) {

        //Inicializa o array de rótulos não utlizados
        List<Integer> rotulosNaoUtilizados = new ArrayList<>();
        rotulosNaoUtilizados.addAll(grafo.getRotulos());
//        System.out.println("S antes: " + s);

        //Encontre um rótulo não utilizado em 's' e adicione em s
        while (true) {
            Collections.shuffle(rotulosNaoUtilizados);
            Integer randomLabel = rotulosNaoUtilizados.get(0);
            if (!s.contains(randomLabel)) {
                s.add(randomLabel);
                Collections.sort(s);
                break;
            } else {
                rotulosNaoUtilizados.remove(rotulosNaoUtilizados.get(0));
            }
        }

        //Inicializa o array de frequencia de labels
        List<Integer> frequencia = new ArrayList<>();
        frequencia = inicializaListaFrequencia(s.size());

        int i = 0; //posição que será incrementado a frequencia

        //Incrementa o array, com a frequencia dos labels 's' contidos no grafo
        for (Integer label : s) {
            for (List<Integer> lineOfGraph : grafo.getMatrizAdjGrafo()) {
                for (Integer columnOfGrapf : lineOfGraph) {
                    if (columnOfGrapf.equals(label)) {
                        frequencia.set(i, frequencia.get(i) + 1);
                    }
                }
            }
            i++;
        }

        int column = 0;
        int line = 0;
        int tamanhoDeS = s.size() - 1;
        for (int x = 0; x < tamanhoDeS; x++) {

            //Inicializa matriz de listas de adjacencia
            List<List<Integer>> adjacencyList = new ArrayList<>();
            for (i = 0; i < grafo.getQtdVertices()- 1; i++) {
                List a = new ArrayList();
                adjacencyList.add(i, a);
            }

            //Inicializa a lista de labels do grafo
            List<Integer> listLabel = new ArrayList<>();
            for (int k = 1; k < grafo.getQtdVertices(); k++) {
                listLabel.add(k);
            }

            int indexMinValor = frequencia.indexOf(Collections.min(frequencia));
            Integer verticeRemovido = s.get(indexMinValor);
            frequencia.remove(indexMinValor);
            s.remove(indexMinValor);

            for (Integer label : s) {
                for (List<Integer> lineOfGraph : grafo.getMatrizAdjGrafo()) {
                    for (Integer columnOfGrapf : lineOfGraph) {
                        if (columnOfGrapf.equals(label)) {
                            adjacencyList.get(line).add(column + 1);//Add vértice da coluna em uma lista de vértices
                        }
                        column++;
                    }
                    column = 0;
                    line++;
                }
                line = 0;
                column = 0;
            }

            for (List<Integer> list : adjacencyList) {
                for (Integer value : list) {
                    if (listLabel.contains(value)) {
                        listLabel.remove(value);
                    }
                }
            }
            if (existeVerticeNConexo(listLabel)) {
                frequencia.add(10000);
                s.add(verticeRemovido);
                Collections.sort(s);
            }
        }

        List<List<Integer>> subgrafo = new ArrayList<>();
        subgrafo = inicializaSubgrafo(grafo.getQtdVertices());

        line = 0;
        column = 0;

        for (Integer label : s) {
            for (List<Integer> lineOfGraph : grafo.getMatrizAdjGrafo()) {
                for (Integer columnOfGrapf : lineOfGraph) {
                    if (columnOfGrapf.equals(label)) {
                        subgrafo.get(line).add(column + 1);//Add vértice da coluna em uma lista de vértices
                    }
                    column++;
                }
                column = 0;
                line++;
            }
            line = 0;
            column = 0;
        }

        for (List<Integer> list : subgrafo) {
            for (Integer integer : list) {
                integer = grafo.getQtdVertices()- integer;
            }
        }

        List<List<Integer>> newAdjacencyList = new ArrayList<>();
        newAdjacencyList = inicializaSubgrafo(grafo.getQtdVertices());

        List<Integer> labelsUsados = new ArrayList<>();
        labelsUsados.addAll(grafo.getRotulos());

        for (List<Integer> list : subgrafo) {
            for (Integer integer : list) {
                for (int j = 0; j < labelsUsados.size(); j++) {
                    Integer vertice = labelsUsados.get(j);
                    if (integer.equals(vertice)) {
                        labelsUsados.remove(vertice);
                        newAdjacencyList.get(line).add(integer);
                    }
                }
            }
        }

        if (s != null) {
            System.out.println("Rotulos Utilizados na Mutação: " + s);
        }
        Collections.sort(s);
        return s;
    }

    public boolean existeVerticeNConexo(List<Integer> labelList) {
        if (labelList.size() > 0) {
            return true;
        }
        return false;
    }

    private List<List<Integer>> inicializaSubgrafo(int size) {
        List<List<Integer>> subgrafo = new ArrayList<>();
        for (int i = 0; i < size - 1; i++) {
            List a = new ArrayList();
            subgrafo.add(i, a);
        }
        return subgrafo;
    }

    private List<Integer> inicializaListaFrequencia(int size) {
        List<Integer> frequencia = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            frequencia.add(0);
        }
        return frequencia;
    }
}
