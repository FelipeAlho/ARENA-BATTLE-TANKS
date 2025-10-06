class TanqueLeve extends Tanque {
    public TanqueLeve(String id, String codinome, String piloto) {
        super(id, codinome, piloto);
        this.classe = "Leve";
        this.blindagem = 30;
        this.velocidade = 80;
        this.poderDeFogo = 40;
    }
    
    @Override
    protected void inicializarModulos() {
        modulos.add(new Canhao(this, 25, 2.0, "perfurante"));
        modulos.add(new Metralhadora(this, 8, 0.5, "fragmentacao"));
    }
    
    @Override
    public double calcularRecarga(String tipoModulo) {
        double base;
        switch (tipoModulo.toLowerCase()) {
            case "canhao": base = 2.0; break;
            case "metralhadora": base = 0.5; break;
            default: base = 1.0;
        }
        return superaquecimento ? base * 1.5 : base;
    }
    
    @Override
    public double calcularBonusTerreno(String terreno) {
        if (terreno.equalsIgnoreCase("urbano")) return 1.2; // Bônus em urbano
        if (terreno.equalsIgnoreCase("deserto")) return 0.8; // Penalidade em deserto
        return 1.0;
    }
}
