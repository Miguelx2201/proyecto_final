import model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    void testCrearClienteDatosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente("", "123", "cll 13-34", "juana@gmail.com", 20,"3214"));
    }

    @Test
    void testCalcularPuntosTotal() {
        Cliente c = new Cliente("Ana", "14928233", "cll 2-23", "anis@gmail.con", 20,"3214");
        Monedero m = new Monedero(c, 0, "M1", TipoMonedero.AHORROS);
        c.getListaMonederos().add(m);
        m.depositar(200000);
        m.registrarTransaccion(new Deposito(200000, m));
        assertTrue(c.calcularPuntos() > 0);
    }
}
