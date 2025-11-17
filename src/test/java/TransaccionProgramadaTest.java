import model.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TransaccionProgramadaTest {

    @Test
    void testEjecutaEnFecha() {
        Cliente c = new Cliente("Ana", "14928233", "cll 2-23", "anis@gmail.con", 20);
        Monedero m = new Monedero(c, 100000, "M1", TipoMonedero.AHORROS);
        Deposito d = new Deposito(50000,m);
        LocalDate fecha = LocalDate.now();
        TransaccionProgramada tp = new TransaccionProgramada(d, fecha, FrecuenciaTransaccionProg.UNICA);
        tp.ejecutarSiCorresponde(fecha);
        assertEquals(150000, m.getSaldo());
    }
}
