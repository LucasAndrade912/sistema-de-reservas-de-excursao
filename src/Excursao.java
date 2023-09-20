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

    public ArrayList<String> listarReservasPorCpf(String digitos) {
        if (digitos.isEmpty()) return this.reservas;

        ArrayList<String> reservasPorCpf = new ArrayList<>();

        for (String reserva : this.reservas) {
            if (reserva.contains(digitos)) {
                reservasPorCpf.add(reserva);
            }
        }

        return reservasPorCpf;
    }

    public double calcularValorTotal() {
        return this.precoPorPessoa * this.reservas.size();
    }

    public void cancelarReserva(String cpf, String nome) throws Exception {
       if (this.reservas.contains((cpf + "/" + nome))) {
           this.reservas.remove(cpf + "/" + nome);
           return;
       }

       throw new Exception("Não foi possivel cancelar sua reserva, não há nenhuma reserva registrada no seu nome ou cpf");
    }

    public void cancelarReserva(String cpf) throws Exception {
        if (this.reservas.removeIf(reserva -> (reserva.split("/")[0].contains((cpf))))){
            return;
        }

        throw new Exception("Não foi possivel cancelar sua reserva, não há nenhuma reserva registrada no seu cpf");
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

    @Override
    public String toString() {
        return "Excursao{" +
                "codigo=" + codigo +
                ", precoPorPessoa=" + precoPorPessoa +
                ", limiteDeReservas=" + limiteDeReservas +
                ", totalDeReservas=" + reservas.size() +
                '}';
    }
}