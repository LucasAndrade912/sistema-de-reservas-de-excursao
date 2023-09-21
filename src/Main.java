public class Main {
    public static void main(String[] args) throws Exception {
        Excursao e = new Excursao(10, 13, 5);

        e.criarReserva("111", "Johnner");
        e.criarReserva("111", "Lucas");
        e.criarReserva("121", "Luquitas");
        System.out.println(e.calcularValorTotal());
        //e.cancelarReserva("111");

        System.out.println(e.listarReservasPorCpf("2"));
        System.out.println(e.listarReservasPorNome("LU"));

        System.out.println(e.calcularValorTotal());
    }
}