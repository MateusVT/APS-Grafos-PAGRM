package apsgrafos;

/**
 *
 * @author leotr
 */
public class Aresta {

    private Vertice destino;
    private Vertice origem;
    private Integer peso;

    public Aresta(Vertice destino) {
        this.destino = destino;
    }

    public Aresta(Vertice destino, Integer peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public void setOrigem(Vertice origem) {
        this.origem = origem;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

}
