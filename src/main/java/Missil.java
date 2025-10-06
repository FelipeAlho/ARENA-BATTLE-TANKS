class Missil extends Modulo {
    public Missil(Tanque tanque, double danoBase, double tempoRecarga, String tipoMunicao) {
        super(tanque, danoBase, tempoRecarga, tipoMunicao);
    }
    
    @Override
    protected double definirAlcance() {
        return 800.0; // Alcance longo
    }
    
    @Override
    public double calcularDano(Tanque alvo) {
        double dano = danoBase;
        
        // Míssil causa dano área (splash) - afeta todos os setores
        if (tipoMunicao.equalsIgnoreCase("explosiva")) {
            dano *= 1.4; // Bônus para explosivos
        }
        
        // Efeito de fragmentação contra múltiplos alvos
        // (simulação simplificada)
        
        return dano;
    }
}
