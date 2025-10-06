class Metralhadora extends Modulo {
    public Metralhadora(Tanque tanque, double danoBase, double tempoRecarga, String tipoMunicao) {
        super(tanque, danoBase, tempoRecarga, tipoMunicao);
    }
    
    @Override
    protected double definirAlcance() {
        return 300.0;
    }
    
    @Override
    public double calcularDano(Tanque alvo) {
        double dano = danoBase;
        
        if (alvo instanceof TanqueLeve) {
            dano *= 1.5;
        } else if (alvo instanceof TanquePesado) {
            dano *= 0.6;
        }
        
        return dano;
    }
}