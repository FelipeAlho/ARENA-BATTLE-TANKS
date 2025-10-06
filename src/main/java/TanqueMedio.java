class TanqueMedio extends Tanque {
    public TanqueMedio(String id, String codinome, String piloto) {
        super(id, codinome, piloto);
        this.classe = "Médio";
        this.blindagem = 50;
        this.velocidade = 60;
        this.poderDeFogo = 60;
    }
    
    @Override
    protected void inicializarModulos() {
        modulos.add(new Canhao(this, 40, 3.0, "perfurante"));
        modulos.add(new Metralhadora(this, 12, 0.8, "fragmentacao"));
    }
    
    @Override
    public double calcularRecarga(String tipoModulo) {
        double base;
        switch (tipoModulo.toLowerCase()) {
            case "canhao": base = 3.0; break;
            case "metralhadora": base = 0.8; break;
            default: base = 1.5;
        }
        return superaquecimento ? base * 1.5 : base;
    }
    
    @Override
    public double calcularBonusTerreno(String terreno) {
        return 1.0; // Sem bônus/penalidade
    }
}