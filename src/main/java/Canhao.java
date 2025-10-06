class Canhao extends Modulo {
    public Canhao(Tanque tanque, double danoBase, double tempoRecarga, String tipoMunicao) {
        super(tanque, danoBase, tempoRecarga, tipoMunicao);
    }
    
    @Override
    protected double definirAlcance() {
        return 500.0;
    }
    
    @Override
    public double calcularDano(Tanque alvo) {
        double dano = danoBase;
        
        switch (tipoMunicao.toLowerCase()) {
            case "perfurante": 
                dano *= 1.3;
                break;
            case "explosiva": 
                dano *= 1.1;
                if (alvo instanceof TanqueLeve) dano *= 1.2;
                break;
            case "fragmentacao": 
                dano *= 0.9;
                break;
        }
        
        if (tanque instanceof TanquePesado) {
            dano *= 1.2;
        }
        
        return dano;
    }
}