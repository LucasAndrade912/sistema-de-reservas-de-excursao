import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.*;

public class UserInterface {
    private HashMap<Integer, Excursao> excursoes = new HashMap<>();
    private Excursao excursaoAtual;

    private JPanel mainPanel;
    private JLabel codigoLabel;
    private JLabel precoPorPessoaLabel;
    private JLabel limiteLabel;
    private JTextField codigoInput;
    private JTextField precoPorPessoaInput;
    private JTextField limiteInput;
    private JButton limparCamposExcursaoBtn;
    private JButton criarExcursaoBtn;
    private JLabel nomeLabel;
    private JTextField nomeInput;
    private JLabel cpfLabel;
    private JTextField cpfInput;
    private JButton criarReservaBtn;
    private JButton cancelarReservaIndividualBtn;
    private JButton listarPorCpfBtn;
    private JButton listarPorNomeBbtn;
    private JButton calculaTotalBtn;
    private JLabel mensagemLabel;
    private JButton resgatarExcursaoBtn;
    private JButton cancelarReservaGrupoBtn;
    private JButton salvarEmArquivoBtn;
    private JButton carregarDoArquivoBtn;
    private JButton limparCamposReservasBtn;
    private JLabel excursaoAtualLabel;

    public UserInterface() {
        File pastaArquivos = new File("arquivos");
        File[] arquivos = pastaArquivos.listFiles();

        for (File arquivo : arquivos) {
            int codigo = Integer.parseInt(arquivo.getName().split("\\.")[0]);

            try {
                excursoes.put(codigo, new Excursao(codigo));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        criarExcursaoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String codigoText = codigoInput.getText();
                String precoPorPessoaText = precoPorPessoaInput.getText();
                String limiteText = limiteInput.getText();
                limparCamposCriarExcursao();

                if (codigoText.isEmpty() || precoPorPessoaText.isEmpty() || limiteText.isEmpty()) {
                    mensagemLabel.setText("status: Informe código, preço por pessoa e limite de reservas para criar uma excursão.");
                    return;
                }

                try {
                    int codigo = Integer.parseInt(codigoText);

                    if (Objects.nonNull(excursoes.get(codigo))) {
                        mensagemLabel.setText("status: Este código já existe, informe outro.");
                        return;
                    }

                    double preco = Double.parseDouble(precoPorPessoaText);
                    int limiteReservas = Integer.parseInt(limiteText);

                    try {
                        Excursao novaExcursao = new Excursao(codigo, preco, limiteReservas);
                        excursoes.put(codigo, novaExcursao);
                        mensagemLabel.setText("status: Excursão criada com sucesso!");
                    } catch (Exception e) {
                        mensagemLabel.setText("status: " + e.getMessage());
                    }
                } catch (NumberFormatException numberFormatException) {
                    mensagemLabel.setText("status: Os valores dos campos devem ser numéricos.");
                }
            }
        });

        resgatarExcursaoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigoText = codigoInput.getText();
                limparCamposCriarExcursao();

                if (codigoText.isEmpty()) {
                    mensagemLabel.setText("status: Informe o código da excursão.");
                    return;
                }

                try {
                    int codigo = Integer.parseInt(codigoText);
                    Excursao excursao = excursoes.get(codigo);

                    if (Objects.isNull(excursao)) {
                        mensagemLabel.setText("status: Excursão inexistente");
                        return;
                    }

                    excursaoAtual = excursao;
                    mensagemLabel.setText("status: Excursão " + codigo + " selecionada.");
                    excursaoAtualLabel.setText("excursão atual: Excursão " + codigo);
                } catch (NumberFormatException numberFormatException) {
                    mensagemLabel.setText("status: O valores do campo deve ser numérico.");
                }
            }
        });
        limparCamposExcursaoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                limparCamposCriarExcursao();
            }
        });

        limparCamposReservasBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                limparCamposReservas();
            }
        });

        cancelarReservaIndividualBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (excursaoAtualEstaVazia()) {
                    mensagemLabel.setText("status: Selecione uma excursão antes de cancelar uma reserva.");
                    return;
                }

                String nomeText = nomeInput.getText();
                String cpfText = cpfInput.getText();
                limparCamposReservas();

                if (nomeText.isEmpty() || cpfText.isEmpty()) {
                    mensagemLabel.setText("status: Informe nome e cpf para cancelar uma reserva.");
                    return;
                }

                try {
                    excursaoAtual.cancelarReserva(cpfText, nomeText);
                    mensagemLabel.setText("status: Reserva individual cancelada com sucesso.");
                } catch (Exception ex) {
                    mensagemLabel.setText("status: " + ex.getMessage());
                }
            }
        });

        listarPorNomeBbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (excursaoAtualEstaVazia()) {
                    mensagemLabel.setText("status: Selecione uma excursão antes de realizar a listagem.");
                    return;
                }

                String nomeText = nomeInput.getText();
                limparCamposReservas();
                if (nomeText.isEmpty()) {
                    mensagemLabel.setText("status: Digite um nome para a realização da busca");
                    return;
                }

                ArrayList<String> usuarios = excursaoAtual.listarReservasPorNome(nomeText);
                String usuariosFormatados = "";

                if (!usuarios.isEmpty()) {
                    for (String usuario : usuarios) {
                        usuariosFormatados = usuariosFormatados.concat(usuario + "....");
                    }

                    usuariosFormatados = usuariosFormatados.substring(0, usuariosFormatados.length() - 4); // remove o último "...."
                } else {
                    usuariosFormatados = "Não existem usuários cadastrados nessa excursão";
                }

                mensagemLabel.setText("status: " + usuariosFormatados);
            }
        });

        calculaTotalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (excursaoAtualEstaVazia()) {
                    mensagemLabel.setText("status: Selecione uma excursão antes de cancelar uma reserva.");
                    return;
                }

                double valorTotal = excursaoAtual.calcularValorTotal();
                Locale localBrasil = new Locale("pt", "BR");
                String valorEmReal = NumberFormat.getCurrencyInstance(localBrasil).format(valorTotal);

                mensagemLabel.setText("status: " + valorEmReal);
            }
        });

        criarReservaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (excursaoAtualEstaVazia()) {
                    mensagemLabel.setText("status: Selecione uma excursão antes de realizar a reserva.");
                    return;
                }

                try {
                    String nome = nomeInput.getText();
                    String cpf = cpfInput.getText();
                    excursaoAtual.criarReserva(cpf, nome);
                    mensagemLabel.setText("status: A reserva no nome de "  + nome + " e no cpf " + cpf + " foi criada.");
                    limparCamposReservas();
                } catch (Exception ex) {
                    mensagemLabel.setText(ex.getMessage());
                    limparCamposReservas();
                }
            }
        });

        listarPorCpfBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (excursaoAtualEstaVazia()) {
                    mensagemLabel.setText("status: Selecione uma excursão antes de realizar a listagem.");
                    return;
                }

                String cpfText = cpfInput.getText();
                limparCamposReservas();
                if (cpfText.isEmpty()) {
                    mensagemLabel.setText("status: Digite um cpf para a realização da busca");
                    return;
                }

                ArrayList<String> usuarios = excursaoAtual.listarReservasPorCpf(cpfText);
                String usuariosFormatados = "";

                if (!usuarios.isEmpty()) {
                    for (String usuario : usuarios) {
                        usuariosFormatados = usuariosFormatados.concat(usuario + "....");
                    }

                    usuariosFormatados = usuariosFormatados.substring(0, usuariosFormatados.length() - 4); // remove o último "...."
                } else {
                    usuariosFormatados = "Não existem usuários cadastrados nessa excursão com esse nome";
                }

                mensagemLabel.setText("status: " + usuariosFormatados);
            }
        });

        cancelarReservaGrupoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (excursaoAtualEstaVazia()) {
                    mensagemLabel.setText("status: Selecione uma excursão antes de cancelar uma reserva.");
                    return;
                }

                String cpfText = cpfInput.getText();
                limparCamposReservas();
                if (cpfText.isEmpty()) {
                    mensagemLabel.setText("status: Digite um cpf para a cancelar  todas as reservas nesse cpf");
                    return;
                }

                try {
                    excursaoAtual.cancelarReserva(cpfText);
                    mensagemLabel.setText("status: Todas as reservas nesse cpf cancelada com sucesso.");
                } catch (Exception ex) {
                    mensagemLabel.setText("status: " + ex.getMessage());
                }
            }
        });
    }

    private void limparCamposCriarExcursao() {
        codigoInput.setText("");
        precoPorPessoaInput.setText("");
        limiteInput.setText("");
    }

    private void limparCamposReservas() {
        nomeInput.setText("");
        cpfInput.setText("");
    }

    private boolean excursaoAtualEstaVazia() {
        return Objects.isNull(excursaoAtual);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UserInterface");
        frame.setContentPane(new UserInterface().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
