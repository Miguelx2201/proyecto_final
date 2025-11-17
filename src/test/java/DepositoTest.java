import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DepositoTest {
        Cliente c=null;
        Monedero m=null;
        @BeforeEach
    public void iniciarClienteYMonedero(){
        c=new Cliente("Ana", "14928233", "cll 2-23", "anis@gmail.con", 20);
        m = new Monedero(c, 100000, "1832", TipoMonedero.AHORROS);
    }

    @Test
    void testEjecutarDeposito() {
        Deposito d = new Deposito(50000, m);
        d.ejecutar();
        assertEquals(150000, m.getSaldo());
    }

    @Test
    void testCalcularPuntos() {
        Deposito d = new Deposito(200000, m);
        assertEquals(2000, d.calcularPuntos());
    }
}
