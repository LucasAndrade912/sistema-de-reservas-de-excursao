import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Excursao {
    // Inicializador de variáveis
    private int codigo;
    private double precoPorPessoa;
    private int limiteDeReservas;
    private ArrayList<String> reservas = new ArrayList<>();

    public Excursao(int codigo, double precoPorPessoa, int limiteDeReservas) throws Exception {
        if (codigo > 0 && precoPorPessoa > 0 && limiteDeReservas > 0) {
            this.codigo = codigo;
            this.precoPorPessoa = precoPorPessoa;
            this.limiteDeReservas = limiteDeReservas;

            String filePath = new File("arquivos/" + codigo + ".txt").getCanonicalPath();
            FileWriter arq = new FileWriter(filePath, false);

            arq.write(precoPorPessoa + "\n");
            arq.write(limiteDeReservas + "\n");
            arq.close();
            return;
        }

        throw new Exception("Os dados passados para excursão precisam ser maior que 0.");
    }

    public Excursao(int codigo) throws Exception {
        if (codigo > 0) {
            this.codigo = codigo;
            this.carregar();
            return;
        }

        throw new Exception("O código da excursão precisa ser maior que 0.");
    }

//    Criar Reserva
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
        salvar();
    }

//    Listar as reservas por cpf
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

//  Listar as reservas por nome
    public ArrayList<String> listarReservasPorNome(String digitos) {
        if (digitos.isEmpty()) return this.reservas;

        ArrayList<String> reservasPorNome = new ArrayList<>();

        for (String reserva : this.reservas) {
            if (reserva.toUpperCase().contains(digitos.toUpperCase())) {
                reservasPorNome.add(reserva);
            }
        }

        return reservasPorNome;
    }

//  Calcular valor total das reservas
    public double calcularValorTotal() {
        return this.precoPorPessoa * this.reservas.size();
    }

//  Concelar as reservas por cpf e nome
    public void cancelarReserva(String cpf, String nome) throws Exception {
       if (this.reservas.contains((cpf + "/" + nome))) {
           this.reservas.remove(cpf + "/" + nome);
           salvar();
           return;
       }

       throw new Exception("Não foi possivel cancelar sua reserva, não há nenhuma reserva registrada no seu nome ou cpf");
    }

//  Cancelar as reservas por cpf
    public void cancelarReserva(String cpf) throws Exception {
        if (this.reservas.removeIf(reserva -> (reserva.split("/")[0].contains((cpf))))){
            salvar();
            return;
        }

        throw new Exception("Não foi possivel cancelar sua reserva, não há nenhuma reserva registrada no seu cpf");
    }

//  Seta preço por pessoa
    public void setPrecoPorPessoa(double precoPorPessoa) throws Exception {
        if (precoPorPessoa > 0) {
            this.precoPorPessoa = precoPorPessoa;
            return;
        }

        throw new Exception("O valor precisa ser maior que 0.");
    }

//  Seta limites de reserva
    public void setLimiteDeReservas(int limiteDeReservas) throws Exception {
        if (limiteDeReservas > 0) {
            this.limiteDeReservas = limiteDeReservas;
            return;
        }

        throw new Exception("O valor precisa ser maior que 0.");
    }

    public void salvar() throws Exception {
        String filePath = new File("arquivos/" + codigo + ".txt").getCanonicalPath();
        FileWriter arq = new FileWriter(filePath, false);

        arq.write(precoPorPessoa + "\n");
        arq.write(limiteDeReservas + "\n");

        if (!reservas.isEmpty()) {
            for (String reserva : reservas) {
                arq.write(reserva + "\n");
            }
        }

        arq.close();
    }

    public void carregar() throws Exception {
        File file = new File("arquivos/" + codigo + ".txt");
        Scanner reader = new Scanner(file);
        int i = 0;

        while (reader.hasNextLine()) {
            String linha = reader.nextLine();

            if (i == 0) {
                setPrecoPorPessoa(Double.parseDouble(linha));
                i++;
            } else if (i == 1) {
                setLimiteDeReservas(Integer.parseInt(linha));
                i++;
            } else {
                reservas.add(linha);
            }
        }

        reader.close();
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