import java.util.ArrayList;
import java.util.Objects;

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
            return;
        }

        throw new Exception("Os dados passados para excursão precisam ser maior que 0.");
    }

    public Excursao(int codigo) throws Exception {
        if (codigo > 0) {
            this.codigo = codigo;
            return;
        }

        throw new Exception("O código da excursão precisa ser maior que 0.");
    }

    public void criarReserva(String cpf, String nome) throws Exception {
        if (this.reservas.size() == this.limiteDeReservas) {
            throw new Exception("Limite de reservas atingido.");
        }

        for (String reserva : this.reservas) {
            String nomeAtual = reserva.split("/")[1];

            if (nomeAtual.equals(nome)) {
                throw new Exception("Não pode haver nomes repetidos");
            }
        }

        String reserva = cpf + "/" + nome;
        this.reservas.add(reserva);
    }

    public double calcularValorTotal() {
        return this.precoPorPessoa * this.reservas.size();
    }

    public void setPrecoPorPessoa(double precoPorPessoa) throws Exception {
        if (precoPorPessoa > 0) {
            this.precoPorPessoa = precoPorPessoa;
            return;
        }

        throw new Exception("O valor precisa ser maior que 0.");
    }

    public void setLimiteDeReservas(int limiteDeReservas) throws Exception {
        if (limiteDeReservas > 0) {
            this.limiteDeReservas = limiteDeReservas;
            return;
        }

        throw new Exception("O valor precisa ser maior que 0.");
    }
}