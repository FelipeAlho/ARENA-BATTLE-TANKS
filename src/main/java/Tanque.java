import java.util.*;

abstract class Tanque {
    protected String id;
    protected String codinome;
    protected String classe;
    protected String piloto;
    protected double blindagem;
    protected double velocidade;
    protected double poderDeFogo;
    protected double integridade;
    protected List<Modulo> modulos;
    protected String status;
    protected Date horaEntradaArena;
    
    protected int abates;
    protected int assistencias;
    protected double danoCausado;
    protected double danoRecebido;
    protected int tirosDisparados;
    protected int tirosAcertados;
    protected Map<String, Double> danoPorArma;
    protected List<String> eventos;
    
    protected boolean superaquecimento;
    protected boolean pane;
    protected double tempoPane;
    
    public Tanque(String id, String codinome, String piloto) {
        this.id = id;
        this.codinome = codinome;
        this.piloto = piloto;
        this.integridade = 100.0;
        this.status = "Ativo";
        this.modulos = new ArrayList<>();
        this.danoPorArma = new HashMap<>();
        this.eventos = new ArrayList<>();
        this.superaquecimento = false;
        this.pane = false;
        this.abates = 0;
        this.assistencias = 0;
        this.danoCausado = 0;
        this.danoRecebido = 0;
        this.tirosDisparados = 0;
        this.tirosAcertados = 0;
        this.horaEntradaArena = new Date();
        inicializarModulos();
    }
    
    protected abstract void inicializarModulos();
    public abstract double calcularRecarga(String tipoModulo);
    public abstract double calcularBonusTerreno(String terreno);
    
    public double atacar(Tanque alvo, String tipoModulo) {
        if (!isDisponivel()) {
            System.out.println(codinome + " nao pode atacar - " + status);
            return 0;
        }
        
        if (pane) {
            System.out.println(codinome + " em pane! Modulos bloqueados");
            return 0;
        }
        
        Modulo modulo = getModulo(tipoModulo);
        if (modulo == null) {
            System.out.println("Modulo " + tipoModulo + " nao encontrado!");
            return 0;
        }
        
        tirosDisparados++;
        
        if (superaquecimento) {
            System.out.println(codinome + " superaquecido! Recarga aumentada");
        }
        
        double dano = modulo.calcularDano(alvo);
        
        String setor = alvo.calcularSetorAtingido(this);
        double modificadorSetor = alvo.getModificadorSetor(setor);
        dano *= modificadorSetor;
        
        double precisao = calcularPrecisao();
        if (Math.random() > precisao) {
            System.out.println(codinome + " errou o tiro!");
            return 0;
        }
        
        tirosAcertados++;
        alvo.receberDano(dano);
        
        this.danoCausado += dano;
        alvo.danoRecebido += dano;
        danoPorArma.put(tipoModulo, danoPorArma.getOrDefault(tipoModulo, 0.0) + dano);
        
        registrarEvento("Atacou " + alvo.codinome + " com " + tipoModulo + ": " + String.format("%.1f", dano) + " dano");
        
        System.out.printf("%s ataca %s no setor %s com %s: %.1f de dano! (%s)\n", 
                         codinome, alvo.codinome, setor, tipoModulo, dano, alvo.getStatusIntegridade());
        
        if (Math.random() < 0.1) {
            alvo.aplicarPane();
        }
        if (Math.random() < 0.15) {
            aplicarSuperaquecimento();
        }
        
        if (alvo.integridade <= 0) {
            this.abates++;
            registrarEvento("DESTRUIU " + alvo.codinome + "!");
            System.out.println("TANQUE DESTRUIDO! " + codinome + " fez um abate!");
        }
        
        return dano;
    }
    
    public void receberDano(double dano) {
        double danoReal = dano * (1 - (blindagem / 200));
        this.integridade -= danoReal;
        if (this.integridade < 0) {
            this.integridade = 0;
            this.status = "Destruido";
            registrarEvento("FOI DESTRUIDO!");
        }
        registrarEvento("Recebeu " + String.format("%.1f", danoReal) + " de dano");
    }
    
    protected Modulo getModulo(String tipo) {
        for (Modulo m : modulos) {
            if (m.getTipo().equalsIgnoreCase(tipo)) {
                return m;
            }
        }
        return null;
    }
    
    public String calcularSetorAtingido(Tanque atacante) {
        double random = Math.random();
        if (random < 0.4) return "frontal";
        if (random < 0.7) return "lateral";
        return "traseiro";
    }
    
    public double getModificadorSetor(String setor) {
        switch (setor.toLowerCase()) {
            case "frontal": return 1.0;
            case "lateral": return 1.2;
            case "traseiro": return 1.4;
            default: return 1.0;
        }
    }
    
    public double calcularPrecisao() {
        double base = 0.7;
        if (this instanceof TanqueLeve) base += 0.1;
        if (this instanceof TanquePesado) base -= 0.1;
        return Math.min(0.9, Math.max(0.5, base));
    }
    
    public void aplicarPane() {
        this.pane = true;
        this.tempoPane = 5.0;
        registrarEvento("SOFREU PANE! Modulos bloqueados");
        System.out.println(codinome + " sofreu pane! Modulos bloqueados por 5s");
    }
    
    public void aplicarSuperaquecimento() {
        this.superaquecimento = true;
        registrarEvento("SUPERAQUECIMENTO! Recarga aumentada");
        System.out.println(codinome + " superaquecido!");
    }
    
    public void atualizar(double deltaTime) {
        if (pane) {
            tempoPane -= deltaTime;
            if (tempoPane <= 0) {
                pane = false;
                registrarEvento("Pane recuperada");
            }
        }
        
        if (superaquecimento && Math.random() < 0.3) {
            superaquecimento = false;
            registrarEvento("Superaquecimento normalizado");
        }
    }
    
    public void registrarEvento(String evento) {
        String timestamp = String.format("[%tH:%tM:%tS]", new Date(), new Date(), new Date());
        eventos.add(timestamp + " " + evento);
    }
    
    public String getId() { return id; }
    public String getCodinome() { return codinome; }
    public String getClasse() { return classe; }
    public String getPiloto() { return piloto; }
    public double getIntegridade() { return integridade; }
    public int getAbates() { return abates; }
    public int getAssistencias() { return assistencias; }
    public double getDanoCausado() { return danoCausado; }
    public double getDanoRecebido() { return danoRecebido; }
    public String getStatus() { return status; }
    public boolean isDisponivel() { return status.equals("Ativo") && integridade > 0; }
    
    public void setStatus(String status) { this.status = status; }
    public void setIntegridade(double integridade) { this.integridade = integridade; }
    
    public double getPrecisao() {
        return tirosDisparados > 0 ? (double) tirosAcertados / tirosDisparados * 100 : 0;
    }
    
    public List<String> getArmasUsadas() {
        return new ArrayList<>(danoPorArma.keySet());
    }
    
    public double getDanoPorArma(String arma) {
        return danoPorArma.getOrDefault(arma, 0.0);
    }
    
    public String getStatusIntegridade() {
        if (integridade >= 70) return "Excelente";
        if (integridade >= 40) return "Danificado";
        if (integridade >= 10) return "Critico";
        return "Destruido";
    }
    
    @Override
    public String toString() {
        return String.format("%s [%s] | %s | Forca%.0f Vida%.0f%%", 
                           codinome, classe, piloto, poderDeFogo, integridade);
    }
    
    public String getInfoCompleta() {
        return String.format("%s (%s) | %s | %s | Forca%.0f Vida%.0f%% | Abates%d Dano%.1f | %s", 
                           codinome, id, classe, piloto, poderDeFogo, integridade, 
                           abates, danoCausado, getStatusIntegridade());
    }
    
    public String getInfoManutencao() {
        return String.format("%s | Integridade: %.1f%% | Status: %s | Pane: %s | Superaq: %s", 
                           codinome, integridade, status, pane ? "SIM" : "nao", superaquecimento ? "SIM" : "nao");
    }
    
    public void mostrarTimeline() {
        System.out.println("\nTIMELINE DE " + codinome + ":");
        for (String evento : eventos) {
            System.out.println("  " + evento);
        }
    }
}