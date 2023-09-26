import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class UserInterface {
    private HashMap<Integer, Excursao> excursoes = new HashMap<>();

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

    public UserInterface() {
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
                    double preco = Double.parseDouble(precoPorPessoaText);
                    int limiteReservas = Integer.parseInt(limiteText);

                    try {
                        Excursao novaExcursao = new Excursao(codigo, preco, limiteReservas);
                        excursoes.put(codigo, novaExcursao);
                        mensagemLabel.setText("status: Excursão criada com sucesso!");
                    } catch (Exception e) {
                        mensagemLabel.setText("status: Os dados da excursão devem ser positivos.");
                    }
                } catch (NumberFormatException numberFormatException) {
                    mensagemLabel.setText("status: Os valores dos campos devem ser numéricos.");
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("UserInterface");
        frame.setContentPane(new UserInterface().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
