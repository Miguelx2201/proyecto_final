import model.Cliente;
import model.Monedero;
import model.TipoMonedero;
import model.Transferencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransferenciaTest {
    Cliente c=null;
    Monedero origen=null;
    Monedero destino=null;
    @BeforeEach
    public void iniciarClienteYMonedero(){
        c=new Cliente("Ana", "14928233", "cll 2-23", "anis@gmail.con", 20,"3214");
        Cliente n=new Cliente("esteban","12217","cll 54-12","est@gmail.com",34,"0127");
        origen = new Monedero(c, 300000, "1832", TipoMonedero.AHORROS);
        destino = new Monedero(n, 100000, "1832", TipoMonedero.AHORROS);
    }

    @Test
    void testEjecutarTransferencia() {
        Transferencia t = new Transferencia(50000,origen, destino);
        t.ejecutar();
        assertAll(() -> assertEquals(300000 - 50000, origen.getSaldo()),
                () -> assertEquals(150000, destino.getSaldo())
        );
    }

    @Test
    void testCalcularPuntos() {
        Transferencia t = new Transferencia(300000,origen, destino);
        assertEquals(9000, t.calcularPuntos());
    }
}
