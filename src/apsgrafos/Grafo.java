/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author leotr
 */
public class Grafo {

    private Integer[][] matrizAdjGrafo;
    private List<Integer> rotulos;
    private List<Aresta> arestas;
    private HashMap<Integer, List<Aresta>> listaAdjacencia = new HashMap<>();
    private int qtdVertices;

    public Grafo(int qtdVertices) {
        this.qtdVertices = qtdVertices;
        Integer[][] matriz = new Integer[qtdVertices][qtdVertices];
        for (int i = 0; i < getQtdVertices() - 1; i++) {
            for (int j = 0; j < getQtdVertices() - 1; j++) {
                matriz[i][j] = 0;
            }
        }
        this.matrizAdjGrafo = matriz;
    }

    public Integer[][] getMatrizAdjGrafo() {
        return matrizAdjGrafo;
    }

    public void setMatrizAdjGrafo(Integer[][] matrizAdjGrafo) {
        this.matrizAdjGrafo = matrizAdjGrafo;
    }

    public List<Integer> getRotulos() {
        return rotulos;
    }

    public void setRotulos(List<Integer> rotulos) {
        this.rotulos = rotulos;
    }

    public int getQtdVertices() {
        return qtdVertices;
    }

    public void setQtdVertices(int qtdVertices) {
        this.qtdVertices = qtdVertices;
    }

    public HashMap<Integer, List<Aresta>> getListaAdjacencia() {
        return listaAdjacencia;
    }

    public void setListaAdjacencia(HashMap<Integer, List<Aresta>> listaAdjacencia) {
        this.listaAdjacencia = listaAdjacencia;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public void setArestas(List<Aresta> arestas) {
        this.arestas = arestas;
    }

    public void constroiAdjacencia() {
        for (int i = 0; i < getQtdVertices() - 1; i++) {
            List<Aresta> la = new ArrayList<>();
            for (int j = 0; j < getQtdVertices() - 1; j++) {
                if (getMatrizAdjGrafo()[i][j] != getQtdVertices()) {
                    la.add(new Aresta(j, getMatrizAdjGrafo()[i][j]));
                }
            }
            getListaAdjacencia().put(i, la);
        }
    }

    public void printAdjacencia() {
        for (int i = 0; i < getQtdVertices() - 1; i++) {
            System.out.print(i+"->");
            for (Aresta a: getListaAdjacencia().get(i)) {
                System.out.print(a.getDestino()+ " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public void printMatriz() {
        for (int i = 0; i < getQtdVertices() - 1; i++) {
            for (int j = 0; j < getQtdVertices() - 1; j++) {
                System.out.print(getMatrizAdjGrafo()[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
