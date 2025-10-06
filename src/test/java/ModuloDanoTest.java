import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ModuloDanoTest {

    @Test
    void canhaoPerfuranteMultiplica130() {
        TanqueMedio alvo = new TanqueMedio("M-X", "param2", "param3");
        TanqueLeve dono = new TanqueLeve("L-X", "lobato", "Caderno");
        Canhao c = new Canhao(dono, 20.0, 2.0, "perfurante");
        double dano = c.calcularDano(alvo);
        assertEquals(20.0 * 1.3, dano, 1e-9);
    }

    @Test
    void canhaoExplosivoBonusEmAlvoLeve() {
        TanqueLeve alvo = new TanqueLeve("L-Y" ,"Poro", "Alho");
        TanqueMedio dono = new TanqueMedio("M-Y" ,"Gelado", "Gabriel");
        Canhao c = new Canhao(dono, 10.0, 2.0, "explosiva");
        double dano = c.calcularDano(alvo);
        // 10 * 1.1 (explosiva) * 1.2 (alvo leve)
        assertEquals(10.0 * 1.1 * 1.2, dano, 1e-9);
    }
}