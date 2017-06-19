package apsgrafos;

/**
 *
 * @author leotr
 */
public class Vertice {

    private String id;
    private String cor;
    private Vertice pi;
    private boolean visited;

    private Double d;
    private Integer tempoDescoberta;
    private Integer tempoFinal;

    public Vertice(String nome) {
        this.id = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public Vertice getPi() {
        return pi;
    }

    public void setPi(Vertice pi) {
        this.pi = pi;
    }

    public Integer getTempoDescoberta() {
        return tempoDescoberta;
    }

    public void setTempoDescoberta(Integer tempoDescoberta) {
        this.tempoDescoberta = tempoDescoberta;
    }

    public Integer getTempoFinal() {
        return tempoFinal;
    }

    public void setTempoFinal(Integer tempoFinal) {
        this.tempoFinal = tempoFinal;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

}
