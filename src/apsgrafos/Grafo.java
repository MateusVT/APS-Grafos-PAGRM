/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

import java.util.List;

/**
 *
 * @author leotr
 */
public class Grafo {
    private List<List<Integer>> matrizAdjGrafo;
    private List<Integer> rotulos;
    private int qtdVertices;

    public Grafo(List<List<Integer>> grafo, List<Integer> rotulos, int tamanho) {
        this.matrizAdjGrafo = grafo;
        this.rotulos = rotulos;
        this.qtdVertices = tamanho;
    }

    public List<List<Integer>> getMatrizAdjGrafo() {
        return matrizAdjGrafo;
    }

    public void setMatrizAdjGrafo(List<List<Integer>> matrizAdjGrafo) {
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
}
