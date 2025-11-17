import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class BancoTest {
    Banco banco=null;
    Cliente c=null;
    @BeforeEach
    public void iniciarBanco(){
        banco=new Banco("Bancolombia", "12453");
        c=new Cliente("Ana", "14928233", "cll 2-23", "anis@gmail.con", 20);
    }

    @Test
    void testRegistrarCliente() {
        boolean agregado = banco.registrarCliente(c);
        assertTrue(agregado);
        boolean duplicado = banco.registrarCliente(c);
        assertFalse(duplicado);
    }

    @Test
    void testBuscarCliente() {
        banco.registrarCliente(c);
        Cliente encontrado = banco.buscarCliente("14928233");
        assertNotNull(encontrado);
        assertEquals("Ana", encontrado.getNombre());
        assertNull(banco.buscarCliente("999"));
    }

    @Test
    void testActualizarCliente() {
        banco.registrarCliente(c);
        boolean actualizado = banco.actualizarCliente("14928233", "Nuevo Nombre", "Nueva Dir", "nuevo@mail");
        assertTrue(actualizado);
        assertEquals("Nuevo Nombre", c.getNombre());
        assertEquals("Nueva Dir", c.getDireccion());
        assertEquals("nuevo@mail", c.getCorreo());
    }

    @Test
    void testEliminarCliente() {
        banco.registrarCliente(c);
        assertTrue(banco.eliminarCliente("14928233"));
        assertFalse(banco.eliminarCliente("999"));
    }

    @Test
    void testRegistrarMonedero() {
        Monedero m = new Monedero(c, 100000, "1282", TipoMonedero.AHORROS);
        assertTrue(banco.registrarMonedero(m));
        assertFalse(banco.registrarMonedero(m));
    }

    @Test
    void testBuscarMonedero() {
        Monedero m = new Monedero(c, 0, "4282", TipoMonedero.AHORROS);
        banco.registrarMonedero(m);
        assertNotNull(banco.buscarMonedero("4282"));
        assertNull(banco.buscarMonedero("9439"));
    }

    @Test
    void testActualizarMonedero() {
        Monedero m = new Monedero(c, 0, "1017", TipoMonedero.AHORROS);
        banco.registrarMonedero(m);
        boolean actualizado = banco.actualizarMonedero("1017", TipoMonedero.GASTOS_DIARIOS);
        assertTrue(actualizado);
        assertEquals(TipoMonedero.GASTOS_DIARIOS, m.getTipoMonedero());
    }

    @Test
    void testEliminarMonedero() {
        Monedero m = new Monedero(c, 10000, "1828", TipoMonedero.AHORROS);
        banco.registrarMonedero(m);
        assertTrue(banco.eliminarMonedero("1828"));
        assertFalse(banco.eliminarMonedero("3822"));
    }

    @Test
    void testRegistrarTransaccionProgramada() {
        Monedero m = new Monedero(c, 100000, "M1", TipoMonedero.AHORROS);
        LocalDate fecha = LocalDate.now().plusDays(3);
        TransaccionProgramada tp1 = new TransaccionProgramada(new Deposito(50000,m), fecha, FrecuenciaTransaccionProg.UNICA);
        assertTrue(banco.registrarTransaccionProgramada(tp1));
        assertFalse(banco.registrarTransaccionProgramada(tp1));
    }

    @Test
    void testBuscarTransaccionProgramada() {
        Monedero m = new Monedero(c, 100000, "M1", TipoMonedero.AHORROS);
        LocalDate fecha = LocalDate.now().plusDays(2);
        TransaccionProgramada tp = new TransaccionProgramada(new Deposito(10000, m), fecha, FrecuenciaTransaccionProg.UNICA);
        banco.registrarTransaccionProgramada(tp);
        assertNotNull(banco.buscarTransaccionProgramada(fecha));
        assertNull(banco.buscarTransaccionProgramada(LocalDate.now().plusDays(10)));
    }

    @Test
    void testActualizarTransaccionProgramada() {
        Monedero m = new Monedero(c, 100, "M1", TipoMonedero.AHORROS);
        LocalDate fecha = LocalDate.now().plusDays(2);
        TransaccionProgramada tp = new TransaccionProgramada(new Deposito(10000,m), fecha, FrecuenciaTransaccionProg.UNICA);
        banco.registrarTransaccionProgramada(tp);
        LocalDate nueva = fecha.plusDays(5);
        assertTrue(banco.actualizarTransaccionProgramada(fecha, nueva));
        assertNotNull(banco.buscarTransaccionProgramada(nueva));
    }

    @Test
    void testEliminarTransaccionProgramada() {
        Monedero m = new Monedero(c, 100000, "M1", TipoMonedero.AHORROS);
        LocalDate fecha = LocalDate.now().plusDays(2);
        TransaccionProgramada tp = new TransaccionProgramada(new Deposito(10, m), fecha, FrecuenciaTransaccionProg.UNICA);
        banco.registrarTransaccionProgramada(tp);
        assertTrue(banco.eliminarTransaccionProgramada(fecha));
        assertFalse(banco.eliminarTransaccionProgramada(fecha));
    }

    @Test
    void testOrdenarTransaccionesPorFecha() {
        Monedero origen = new Monedero(c, 100000, "231", TipoMonedero.AHORROS);
        TransaccionProgramada t1 = new TransaccionProgramada(new Deposito(10000, origen), LocalDate.now().plusDays(3), FrecuenciaTransaccionProg.UNICA);
        TransaccionProgramada t2 = new TransaccionProgramada(new Deposito(10000, origen), LocalDate.now().plusDays(1),FrecuenciaTransaccionProg.UNICA);
        banco.registrarTransaccionProgramada(t1);
        banco.registrarTransaccionProgramada(t2);
        banco.ordenarTransaccionesPorFecha();
        assertEquals(t2, banco.getListaTransaccionesProgramadas().get(0));
    }

    @Test
    void testProcesarTransaccionesOrdenadas() {
        Monedero m = new Monedero(c, 100000, "M1", TipoMonedero.AHORROS);
        LocalDate fecha = LocalDate.now();
        TransaccionProgramada tp = new TransaccionProgramada(new Deposito(50000, m), fecha, FrecuenciaTransaccionProg.UNICA);
        banco.registrarTransaccionProgramada(tp);
        banco.procesarTransaccionesOrdenadas(fecha);
        assertEquals(150000, m.getSaldo());
    }

    @Test
    void testRevisarRecordatoriosNoLanzaErrores() {
        Monedero m = new Monedero(c, 100000, "M1", TipoMonedero.AHORROS);
        LocalDate fecha = LocalDate.now().plusDays(1);
        TransaccionProgramada tp = new TransaccionProgramada(new Deposito(50000, m), fecha, FrecuenciaTransaccionProg.UNICA);
        banco.registrarTransaccionProgramada(tp);
        assertDoesNotThrow(banco::revisarRecordatorios);
    }

}
