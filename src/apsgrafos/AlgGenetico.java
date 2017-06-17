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

    private List<Integer> inicializaListaFrequencia(int size) {
        List<Integer> frequencia = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            frequencia.add(0);
        }
        return frequencia;
    }
    
    private List<List<Integer>> inicializaSubgrafo(int size) {
        List<List<Integer>> subgrafo = new ArrayList<>();
        for (int i = 0; i < size - 1; i++) {
            List a = new ArrayList();
            subgrafo.add(i, a);
        }
        return subgrafo;
    }
    
    public List<Integer> individuo(Grafo grafo) {

        //lista de rotulos usados
        List<Integer> rotulosUsados = new ArrayList<>();

        //lista de rotulos do grafo
        //lista de vertices de grafo
        List<Integer> rotulosGrafo = new ArrayList();
        rotulosGrafo.addAll(grafo.getRotulos());

        List<Integer> verticesGrafo = new ArrayList();
        verticesGrafo.addAll(grafo.getRotulos());

        //Inicializa matriz de listas de adja   cencia
        List<List<Integer>> subgrafo = new ArrayList<>();
        subgrafo = inicializaSubgrafo(grafo.getQtdVertices());

        //Enquanto houver vertices nao conectados 
        while (existeVerticeNConexo(verticesGrafo)){

            //se não existi rotulos no grafo, não há como gerar um indivíduo
            if (rotulosGrafo.size() <= 0) {
                return null;
            }
            //Embaralha a lista de rotulos do grafo
            Collections.shuffle(rotulosGrafo);

            //Pega o primeiro rotulo, este rotulo é randômico
            Integer randomLabel = rotulosGrafo.get(0);
            rotulosGrafo.remove(randomLabel);
            rotulosUsados.add(randomLabel);

            int line = 0, column = 0;
            //Determina o número de componentes conectados ao inserir todas as arestas com a rotulos i no subgrafo
            for (List<Integer> linha : grafo.getMatrizAdjGrafo()) {
                for (Integer coluna : linha) {
                    if (coluna.equals(randomLabel)) {//se o valor do vértice for igual ao rótulo escolhido
                        subgrafo.get(line).add(column + 1);//Add vértice da coluna em uma lista de vértices
                    }
                    column++;
                }
                line++;
                column = 0;
            }
            line = 0;
            column = 0;

            //Remove da lista de vétices do grafo todos os vértices que compunham o subgrafo
            for (List<Integer> list : subgrafo) {
                for (Integer value : list) {
                    if (verticesGrafo.contains(value)) {
                        verticesGrafo.remove(value);
                    }
                }
            }
        }
 

        Collections.sort(rotulosUsados);
        return rotulosUsados;
    }

    public List<Integer> crossover(Grafo grafo, List<Integer> s) {

        List<Integer> frequencia = new ArrayList<>();
        frequencia = inicializaListaFrequencia(s.size());

        int i = 0; //posição que será incrementado a frequencia

        //Incrementa o array, com a frequencia dos rotulos s contidos no grafo
        for (Integer rotulo : s) {
            for (List<Integer> linha : grafo.getMatrizAdjGrafo()) {
                for (Integer coluna : linha) {
                    if (coluna.equals(rotulo)) {
                        frequencia.set(i, frequencia.get(i) + 1);
                    }
                }
            }
            i++;
        }

        List<Integer> rotulosGrafo = new ArrayList();
        rotulosGrafo.addAll(grafo.getRotulos());
        
        List<Integer> rotulosSubGrafo = new ArrayList<>();

        List<List<Integer>> subgrafo = new ArrayList<>();
        subgrafo = inicializaSubgrafo(grafo.getQtdVertices());

        int line = 0, column = 0;

        //Enquanto houver vertices nao conectados 
        while (existeVerticeNConexo(rotulosGrafo)) {

            int indexMaxValor = frequencia.indexOf(Collections.max(frequencia));
            frequencia.set(indexMaxValor, 0);
            int maxValorDeS = s.get(indexMaxValor);
            if (!rotulosSubGrafo.contains(maxValorDeS)) {
                rotulosSubGrafo.add(maxValorDeS);
            }

            //Determina o número de componentes conectados ao inserir todas as arestas com a rotulos i no subgrafo
            for (List<Integer> linha : grafo.getMatrizAdjGrafo()) {
                for (Integer coluna : linha) {
                    if (coluna.equals(maxValorDeS)) {
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
                    if (rotulosGrafo.contains(value)) {
                        rotulosGrafo.remove(value);
                    }
                }
            }
        }//Ao fim deste laço temos o número mínimo de rotulos utilizando apenas dois individuos do grafo
        // e aplicando a união dos conjuntos de rotulos neste método de crossover.

        Collections.sort(rotulosSubGrafo);

        return rotulosSubGrafo;
    }

    public List<Integer> mutation(Grafo grafo, List<Integer> s) {

        //Inicializa o array de rótulos não utlizados
        List<Integer> rotulosNaoUtilizados = new ArrayList<>();
        rotulosNaoUtilizados.addAll(grafo.getRotulos());
//        System.out.println("S antes: " + s);

        //Encontre um rótulo não utilizado em 's' e adicione em s
        while (true) {
            Collections.shuffle(rotulosNaoUtilizados);
            Integer rotuloRandom = rotulosNaoUtilizados.get(0);
            if (!s.contains(rotuloRandom)) {
                s.add(rotuloRandom);
                Collections.sort(s);
                break;
            } else {
                rotulosNaoUtilizados.remove(rotulosNaoUtilizados.get(0));
            }
        }

        //Inicializa o array de frequencia de rotulos
        List<Integer> frequencia = new ArrayList<>();
        frequencia = inicializaListaFrequencia(s.size());

        int i = 0; //posição que será incrementado a frequencia

        //Incrementa o array, com a frequencia dos rotulos 's' contidos no grafo
        for (Integer rotulo : s) {
            for (List<Integer> linha : grafo.getMatrizAdjGrafo()) {
                for (Integer coluna : linha) {
                    if (coluna.equals(rotulo)) {
                        frequencia.set(i, frequencia.get(i) + 1);
                    }
                }
            }
            i++;
        }

        int column = 0;
        int line = 0;
        int tamanhoS = s.size() - 1;
        for (int x = 0; x < tamanhoS; x++) {

            //Inicializa matriz de listas de adjacencia
            List<List<Integer>> listaAdjacencia = new ArrayList<>();
            for (i = 0; i < grafo.getQtdVertices()- 1; i++) {
                List a = new ArrayList();
                listaAdjacencia.add(i, a);
            }

            //Inicializa a lista de rotulos do grafo
            List<Integer> listaRotulos = new ArrayList<>();
            for (int k = 1; k < grafo.getQtdVertices(); k++) {
                listaRotulos.add(k);
            }

            int indexMinValor = frequencia.indexOf(Collections.min(frequencia));
            Integer verticeRemovido = s.get(indexMinValor);
            frequencia.remove(indexMinValor);
            s.remove(indexMinValor);

            for (Integer rotulo : s) {
                for (List<Integer> linha : grafo.getMatrizAdjGrafo()) {
                    for (Integer coluna : linha) {
                        if (coluna.equals(rotulo)) {
                            listaAdjacencia.get(line).add(column + 1);//Add vértice da coluna em uma lista de vértices
                        }
                        column++;
                    }
                    column = 0;
                    line++;
                }
                line = 0;
                column = 0;
            }

            for (List<Integer> list : listaAdjacencia) {
                for (Integer value : list) {
                    if (listaRotulos.contains(value)) {
                        listaRotulos.remove(value);
                    }
                }
            }
            if (existeVerticeNConexo(listaRotulos)) {
                frequencia.add(10000);
                s.add(verticeRemovido);
                Collections.sort(s);
            }
        }

        List<List<Integer>> subgrafo = new ArrayList<>();
        subgrafo = inicializaSubgrafo(grafo.getQtdVertices());

        line = 0;
        column = 0;

        for (Integer rotulo : s) {
            for (List<Integer> linha : grafo.getMatrizAdjGrafo()) {
                for (Integer coluna : linha) {
                    if (coluna.equals(rotulo)) {
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

        for (List<Integer> lista : subgrafo) {
            for (Integer integer : lista) {
                integer = grafo.getQtdVertices()- integer;
            }
        }

        List<List<Integer>> novaListaAdjacencia = new ArrayList<>();
        novaListaAdjacencia = inicializaSubgrafo(grafo.getQtdVertices());

        List<Integer> rotulosUsados = new ArrayList<>();
        rotulosUsados.addAll(grafo.getRotulos());

        for (List<Integer> list : subgrafo) {
            for (Integer integer : list) {
                for (int j = 0; j < rotulosUsados.size(); j++) {
                    Integer vertice = rotulosUsados.get(j);
                    if (integer.equals(vertice)) {
                        rotulosUsados.remove(vertice);
                        novaListaAdjacencia.get(line).add(integer);
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

    public boolean existeVerticeNConexo(List<Integer> rotuloList) {
        if (rotuloList.size() > 0) {
            return true;
        }
        return false;
    }

}
