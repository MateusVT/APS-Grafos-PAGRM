package apsgrafos;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        ArrayList<String> diretorios = arquivo.leitor("instancias.txt");//Testes menores Insira os diretorios que você quer testar aqui
//        ArrayList<String> diretorios = arquivo.leitor("diretorios.txt");//Arquivo que contém o nome dos diretórios das instancias a serem testadas

        for (int k = 0; k < diretorios.size(); k++) {//Itera os diretorios

            int i = 1;//Auxiliar para exibir o grafo sendo processado
            List<Float> listaDeTemposDosGrafos = new ArrayList<>();//Armazena os tempos de execução de cada grafo do arquivo
            List<Integer> quantidadeRotulos = new ArrayList<>();//Armazena a quantidade de rotulos utilizada na solução de cada grafo do arquivo
            ArrayList<ArrayList<Rotulo>> rotulosMin = new ArrayList<>();//Armazena os rótulos utilizados na solução

            Arquivo instancias = new Arquivo();//Instancia o arquivo da vez
            ArrayList<Grafo> matrizesAdj = instancias.geraMatrizesAdj(diretorios.get(k));//Gera as matrizes de adjacência
            ArrayList<Grafo> grafos = instancias.geraListasAdj(matrizesAdj);//Gera as listas de adjacência com base nas matrizes de adjacência
            System.out.println("Arquivo: " + instancias.getNome());//Printa o nome do arquivo processado
            System.out.println("Quantidade de grafos no arquivo: " + grafos.size());

            for (Grafo grafo : grafos) {//Itera sobre cada grafo do arquivo sendo processado

                long tempoInicial = System.currentTimeMillis();//Inicia o "cronômetro"
                System.out.println("Grafo: (" + i + ")");//Printa a posição do grafo sendo processado
                MVCA mvca = new MVCA();//Instancia o MVCA
                mvca.run(grafo);//Inicia a execução passando o grafo da vez como parâmetro
//                grafo.printaListaAdj();//Printa lista de adjacência do grafo
//                grafo.printaMatrizAdj(matrizesAdj.get(i-1));//Printa matriz de adjacência do grafo
                quantidadeRotulos.add(mvca.getRotulosMinimos().size());//Armazena na lista a quantidade de rótulos utilizado na solução do grafo sendo iterado
                rotulosMin.add(mvca.getRotulosMinimos());//Armazena os rótulos utilizados na solução
                long tempoFinal = System.currentTimeMillis();//Para o "cronômetro"
                float tempo = (tempoFinal - tempoInicial);//Calcula o tempo
                listaDeTemposDosGrafos.add(tempo);//Adiciona o tempo calculado a lista de tempos de cada grafo
                i++;

            }

            float tempoTotal = 0;
            float tempoMedio = 0;
            float mediaRotulos = 0;
            int grafosDesconexos = 0;

            NumberFormat formatter = NumberFormat.getInstance(Locale.US);
            formatter.setMaximumFractionDigits(2);
            formatter.setRoundingMode(RoundingMode.HALF_UP);

            for (int j = 0; j < listaDeTemposDosGrafos.size(); j++) {//Calcula o tempo total
                tempoTotal += listaDeTemposDosGrafos.get(j);
            }

            for (int j = 0; j < quantidadeRotulos.size(); j++) {//Calcula total de rótulos utilizados na instância inteira
                if (quantidadeRotulos.get(j).equals(1)) {
                    grafosDesconexos += 1;
                }
                mediaRotulos += quantidadeRotulos.get(j);
            }

            mediaRotulos = mediaRotulos - grafosDesconexos;//Retira os grafos que não possuem solução da média de rótulos        

            tempoMedio = tempoTotal / (grafos.size() - grafosDesconexos);//Calcula o tempo médio para solução de cada grafo desconsiderando os grafos que não possuem solução
            mediaRotulos = mediaRotulos / grafos.size();//Calcula a média de rótulos utilizada nas soluções
            Float tempoMedioArredondado = new Float(formatter.format(tempoMedio));

            System.out.println("Tempo Total: " + tempoTotal + " ms");
            System.out.println("Tempo Médio: " + tempoMedioArredondado + " ms");
            System.out.println("Média de rótulos: " + mediaRotulos);

            instancias.gravador(rotulosMin, mediaRotulos, tempoTotal, tempoMedioArredondado);//Grava na pasta "saida" os dados coletados
            System.out.println("----------------------------------");
            System.out.println("");
        }
    }
}
