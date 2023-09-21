import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface {
    private JPanel mainPanel;
    private JLabel codigoLabel;
    private JLabel precoLabel;
    private JLabel limiteLabel;
    private JTextField codigoInput;
    private JTextField precoInput;
    private JTextField limiteInput;
    private JButton limparBtn;
    private JButton criarExcursaoBtn;
    private JLabel nomeLabel;
    private JTextField nomeInput;
    private JLabel cpfLabel;
    private JTextField cpfInput;
    private JButton criarReservaBtn;
    private JButton cancelarReservaBtn;
    private JButton listarPorCpfBtn;
    private JButton listarPorNomeBbtn;
    private JButton calculaTotalBtn;
    private JLabel resultadoLabel;

    public UserInterface() {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UserInterface");
        frame.setContentPane(new UserInterface().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
