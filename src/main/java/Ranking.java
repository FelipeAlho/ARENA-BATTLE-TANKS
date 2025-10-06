import java.util.*;

class Ranking {
    private Map<String, Integer> pontosPorTanque;
    private Map<String, Integer> vitoriasPorClasse;
    
    public Ranking() {
        this.pontosPorTanque = new HashMap<>();
        this.vitoriasPorClasse = new HashMap<>();
    }
    
    public void atualizarRanking(Partida partida) {
        if (!partida.isFinalizada()) return;
        
        for (Tanque tanque : partida.getTanques()) {
            String nomeTanque = tanque.getCodinome();
            int pontos = calcularPontuacao(tanque);
            
            pontosPorTanque.put(nomeTanque, 
                pontosPorTanque.getOrDefault(nomeTanque, 0) + pontos);
        }
        
        for (Tanque tanque : partida.getTanques()) {
            if (tanque.getCodinome().equals(partida.getVencedor())) {
                String classe = tanque.getClasse();
                vitoriasPorClasse.put(classe, 
                    vitoriasPorClasse.getOrDefault(classe, 0) + 1);
            }
        }
    }
    
    private int calcularPontuacao(Tanque tanque) {
        int pontos = 0;
        pontos += tanque.getAbates() * 100;
        pontos += tanque.getAssistencias() * 40;
        pontos += (int)(tanque.getDanoCausado() * 0.1);
        pontos += (int)(tanque.getPrecisao() * 2);
        
        return pontos;
    }
    
    public void mostrarRanking() {
        if (pontosPorTanque.isEmpty()) {
            System.out.println("  Nenhuma partida finalizada ainda");
            return;
        }
        
        pontosPorTanque.entrySet().stream()
            .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
            .limit(10)
            .forEach(entry -> System.out.printf("  %s: %d pontos\n", entry.getKey(), entry.getValue()));
    }
    
    public void mostrarVitoriasPorClasse() {
        for (Map.Entry<String, Integer> entry : vitoriasPorClasse.entrySet()) {
            System.out.printf("  %s: %d vitorias\n", entry.getKey(), entry.getValue());
        }
    }
}