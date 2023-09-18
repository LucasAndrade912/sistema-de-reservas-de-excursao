import java.util.ArrayList;

public class Excursao {
    private int codigo;
    private double precoPorPessoa;
    private int limiteDeReservas;
    private ArrayList<String> reservas = new ArrayList<>();

    public Excursao(int codigo, double precoPorPessoa, int limiteDeReservas) throws Exception {
        if (codigo > 0 && precoPorPessoa > 0 && limiteDeReservas > 0) {
            this.codigo = codigo;
            this.precoPorPessoa = precoPorPessoa;
            this.limiteDeReservas = limiteDeReservas;
        }

        throw new Exception("Os dados passados para excursão precisam ser maior que 0.");
    }

    public Excursao(int codigo) throws Exception {
        if (codigo > 0) {
            this.codigo = codigo;
        }

        throw new Exception("O código da excursão precisa ser maior que 0.");
    }
}