import model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MonederoTest {

    Cliente c = new Cliente("Luis", "999", "Dir", "mail", 25);

    @Test
    void testDepositarAumentaSaldo() {
        Monedero m = new Monedero(c, 100000, "M1", TipoMonedero.AHORROS);
        m.depositar(50000);
        assertEquals(150000, m.getSaldo());
    }

    @Test
    void testDepositoInvalidoNoCambiaSaldo() {
        Monedero m = new Monedero(c, 100000, "M1", TipoMonedero.AHORROS);
        m.depositar(-20000);
        assertEquals(100000, m.getSaldo());
    }

    @Test
    void testRetiroValido() {
        Monedero m = new Monedero(c, 200000, "M1", TipoMonedero.AHORROS);
        m.retirar(80000);
        assertEquals(120000, m.getSaldo());
    }

    @Test
    void testRetiroInvalido() {
        Monedero m = new Monedero(c, 30000, "M1", TipoMonedero.AHORROS);
        m.retirar(100000);
        assertEquals(30000, m.getSaldo());
    }

    @Test
    void testRegistrarTransaccion() {
        Monedero m = new Monedero(c, 100000, "M1", TipoMonedero.AHORROS);
        Transaccion t1 = new Deposito(100000,m);
        m.registrarTransaccion(t1);

        assertEquals(1, m.getListaTransacciones().size());
    }

    @Test
    void testHistorialTransacciones() {
        Monedero m = new Monedero(c, 100000, "M1", TipoMonedero.AHORROS);
        Transaccion d = new Deposito(50000, m);
        Transaccion r = new Retiro(30000, m);
        m.registrarTransaccion(d);
        m.registrarTransaccion(r);
        assertIterableEquals(List.of(d, r), m.getListaTransacciones());
    }
}
