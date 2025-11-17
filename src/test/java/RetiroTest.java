import model.Cliente;
import model.Monedero;
import model.Retiro;
import model.TipoMonedero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RetiroTest {
    Cliente c=null;
    Monedero m=null;
    @BeforeEach
    public void iniciarClienteYMonedero(){
        c=new Cliente("Ana", "14928233", "cll 2-23", "anis@gmail.con", 20);
        m = new Monedero(c, 200000, "1832", TipoMonedero.AHORROS);
    }

    @Test
    void testEjecutarRetiro() {
        Retiro r = new Retiro(50000, m);
        r.ejecutar();
        double esperado = 200000 - (50000 + (50000 * 0.01));
        assertEquals(esperado, m.getSaldo());
    }

    @Test
    void testCalcularPuntos() {
        Retiro r = new Retiro(20000, m);
        assertEquals(400, r.calcularPuntos());
    }
}
