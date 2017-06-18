/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apsgrafos;

/**
 *
 * @author leotr
 */
public class Aresta {

    private Vertice d;
    private Vertice o;
    private Integer p;

    public Aresta(Vertice destino) {
        this.d = destino;
    }

    public Aresta(Vertice destino, Integer peso) {
        this.d = destino;
        this.p = peso;
    }

    public Vertice getD() {
        return d;
    }

    public void setD(Vertice d) {
        this.d = d;
    }

    public Vertice getO() {
        return o;
    }

    public void setO(Vertice o) {
        this.o = o;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

}
