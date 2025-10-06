import java.util.*;

class Partida {
    private static int nextId = 1;
    private int id;
    private String modo;
    private String arena;
    private int duracao;
    private List<Tanque> tanques;
    private List<String> eventos;
    private boolean finalizada;
    private Date dataCriacao;
    private String vencedor;
    
    public Partida(String modo, String arena, int duracao) {
        this.id = nextId++;
        this.modo = modo;
        this.arena = arena;
        this.duracao = duracao;
        this.tanques = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.finalizada = false;
        this.dataCriacao = new Date();
        this.vencedor = "N/A";
    }
    
    public void adicionarTanque(Tanque tanque) {
        if (tanques.size() < getMaxTanques()) {
            tanques.add(tanque);
            registrarEvento(tanque.getCodinome() + " entrou na partida");
        }
    }
    
    private int getMaxTanques() {
        switch (modo.toLowerCase()) {
            case "1v1": return 2;
            case "3v3": return 6;
            case "5v5": return 10;
            default: return 12;
        }
    }
    
    public boolean verificarConflitoTanque(Tanque tanque) {
        for (Partida p : Partida.getPartidasAtivas()) {
            if (p != this && p.tanques.contains(tanque)) {
                return true;
            }
        }
        return false;
    }
    
    public void simular() {
        System.out.println("\nINICIANDO SIMULACAO: " + this);
        System.out.println("Arena: " + arena + " | Modo: " + modo);
        
        if (tanques.size() < 2) {
            System.out.println("Minimo de 2 tanques necessario!");
            return;
        }
        
        for (Tanque t : tanques) {
            t.setIntegridade(100);
            t.setStatus("Ativo");
        }
        
        Random random = new Random();
        int rounds = 5;
        
        for (int round = 1; round <= rounds; round++) {
            System.out.println("\n--- ROUND " + round + " ---");
            
            List<Tanque> tanquesAtivos = getTanquesAtivos();
            Collections.shuffle(tanquesAtivos);
            
            for (Tanque atacante : tanquesAtivos) {
                if (atacante.getIntegridade() <= 0) continue;
                
                List<Tanque> alvosPossiveis = getTanquesAtivos();
                alvosPossiveis.remove(atacante);
                
                if (alvosPossiveis.isEmpty()) break;
                
                Tanque alvo = alvosPossiveis.get(random.nextInt(alvosPossiveis.size()));
                
                String modulo = escolherModulo(atacante);
                
                atacante.atacar(alvo, modulo);
                
                atacante.atualizar(1.0);
                
                if (getTanquesAtivos().size() <= 1) break;
            }
            
            if (getTanquesAtivos().size() <= 1) break;
        }
        
        finalizada = true;
        determinarVencedor();
        mostrarResultado();
    }
    
    private String escolherModulo(Tanque tanque) {
        Random random = new Random();
        if (tanque instanceof TanqueLeve) {
            return random.nextBoolean() ? "Canhao" : "Metralhadora";
        } else if (tanque instanceof TanquePesado) {
            return random.nextBoolean() ? "Canhao" : "Missil";
        } else {
            String[] modulos = {"Canhao", "Metralhadora"};
            return modulos[random.nextInt(modulos.length)];
        }
    }
    
    private List<Tanque> getTanquesAtivos() {
        List<Tanque> ativos = new ArrayList<>();
        for (Tanque t : tanques) {
            if (t.getIntegridade() > 0 && t.isDisponivel()) {
                ativos.add(t);
            }
        }
        return ativos;
    }
    
    private int contarTanquesAtivos() {
        return getTanquesAtivos().size();
    }
    
    private void determinarVencedor() {
        List<Tanque> ativos = getTanquesAtivos();
        if (ativos.size() == 1) {
            vencedor = ativos.get(0).getCodinome();
        } else {
            tanques.sort((a, b) -> Double.compare(b.getDanoCausado(), a.getDanoCausado()));
            vencedor = tanques.get(0).getCodinome();
        }
        registrarEvento("VENCEDOR: " + vencedor);
    }
    
    private void mostrarResultado() {
        System.out.println("\nRESULTADO FINAL:");
        System.out.println("VENCEDOR: " + vencedor);
        
        tanques.sort((a, b) -> Double.compare(b.getDanoCausado(), a.getDanoCausado()));
        
        for (int i = 0; i < tanques.size(); i++) {
            Tanque t = tanques.get(i);
            System.out.printf("%d. %s | Dano: %.1f | Abates: %d | Precisao: %.1f%% | Integridade: %.1f%%\n", 
                            i+1, t.getCodinome(), t.getDanoCausado(), t.getAbates(), 
                            t.getPrecisao(), t.getIntegridade());
        }
        
        Tanque vencedorObj = null;
        for (Tanque t : tanques) {
            if (t.getCodinome().equals(vencedor)) {
                vencedorObj = t;
                break;
            }
        }
        
        if (vencedorObj != null) {
            System.out.println("\nRELATORIO DETALHADO DO VENCEDOR:");
            vencedorObj.mostrarTimeline();
        }
    }
    
    private void registrarEvento(String evento) {
        String timestamp = String.format("[%tH:%tM:%tS]", new Date(), new Date(), new Date());
        eventos.add(timestamp + " " + evento);
    }
    
    public int getId() { return id; }
    public String getModo() { return modo; }
    public String getArena() { return arena; }
    public boolean isFinalizada() { return finalizada; }
    public List<Tanque> getTanques() { return tanques; }
    
    public String getVencedor() {
        return finalizada ? "Vencedor: " + vencedor : "Em andamento";
    }
    
    public String getInfoSimples() {
        return String.format("Partida #%d: %s em %s (%d tanques)", id, modo, arena, tanques.size());
    }
    
    @Override
    public String toString() {
        return String.format("Partida #%d | %s | %s | %d min | %d tanques | %s", 
                           id, modo, arena, duracao, tanques.size(), 
                           finalizada ? "Finalizada - " + vencedor + " venceu" : "Agendada");
    }
    
    public static List<Partida> getPartidasAtivas() {
        return new ArrayList<>();
    }
}