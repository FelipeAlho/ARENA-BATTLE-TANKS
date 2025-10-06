import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TanqueTest {

    @Test
    void recargaTanqueLeve() {
        TanqueLeve t = new TanqueLeve("L-1", "arg2", "arg3");
        assertEquals(2.0, t.calcularRecarga("canhao"), 1e-9);
        assertEquals(0.5, t.calcularRecarga("metralhadora"), 1e-9);
    }

    @Test
    void bonusTerrenoTanqueLeve() {
        TanqueLeve t = new TanqueLeve("L-2", "arg2", "arg3");
        assertEquals(1.2, t.calcularBonusTerreno("urbano"), 1e-9);
        assertEquals(0.8, t.calcularBonusTerreno("deserto"), 1e-9);
        assertEquals(1.0, t.calcularBonusTerreno("floresta"), 1e-9);
    }

    @Test
    void recargaTanqueMedio() {
        TanqueMedio t = new TanqueMedio("M-1", "arg2", "arg3");
        assertEquals(3.0, t.calcularRecarga("canhao"), 1e-9);
        assertEquals(0.8, t.calcularRecarga("metralhadora"), 1e-9);
    }

    @Test
    void bonusTerrenoTanqueMedio() {
        TanqueMedio t = new TanqueMedio("M-2", "arg2", "arg3");
        assertEquals(1.0, t.calcularBonusTerreno("urbano"), 1e-9);
        assertEquals(1.0, t.calcularBonusTerreno("deserto"), 1e-9);
        assertEquals(1.0, t.calcularBonusTerreno("floresta"), 1e-9);
    }

    @Test
    void recargaTanquePesado() {
        TanquePesado t = new TanquePesado("P-1", "arg2", "arg3");
        assertEquals(5.0, t.calcularRecarga("canhao"), 1e-9);
        assertEquals(8.0, t.calcularRecarga("missil"), 1e-9);
    }

    @Test
    void bonusTerrenoTanquePesado() {
        TanquePesado t = new TanquePesado("P-2", "arg2", "arg3");
        assertEquals(0.7, t.calcularBonusTerreno("deserto"), 1e-9);
        assertEquals(1.0, t.calcularBonusTerreno("urbano"), 1e-9);
    }
}