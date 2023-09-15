import java.util.ArrayList;

public class Excursao {
    private int codigo;
    private double precoPorPessoa;
    private int limiteDeReservas;
    private ArrayList<String> reservas = new ArrayList<>();

    public Excursao(int codigo, double precoPorPessoa, int limiteDeReservas) {
        this.codigo = codigo;
        this.precoPorPessoa = precoPorPessoa;
        this.limiteDeReservas = limiteDeReservas;
    }

    public Excursao(int codigo) {
        this.codigo = codigo;
    }
}
