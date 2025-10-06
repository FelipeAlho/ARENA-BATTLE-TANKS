class TanquePesado extends Tanque {
    public TanquePesado(String id, String codinome, String piloto) {
        super(id, codinome, piloto);
        this.classe = "Pesado";
        this.blindagem = 80;
        this.velocidade = 30;
        this.poderDeFogo = 90;
    }
    
    @Override
    protected void inicializarModulos() {
        modulos.add(new Canhao(this, 60, 5.0, "explosiva"));
        modulos.add(new Missil(this, 75, 8.0, "explosiva"));
    }
    
    @Override
    public double calcularRecarga(String tipoModulo) {
        double base;
        switch (tipoModulo.toLowerCase()) {
            case "canhao": base = 5.0; break;
            case "missil": base = 8.0; break;
            default: base = 3.0;
        }
        return superaquecimento ? base * 1.5 : base;
    }
    
    @Override
    public double calcularBonusTerreno(String terreno) {
        if (terreno.equalsIgnoreCase("deserto")) return 0.7;
        return 1.0;
    }
}
