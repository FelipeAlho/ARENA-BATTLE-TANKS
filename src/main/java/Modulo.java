abstract class Modulo {
    protected Tanque tanque;
    protected double danoBase;
    protected double tempoRecarga;
    protected String tipoMunicao;
    protected double alcanceEficaz;
    
    public Modulo(Tanque tanque, double danoBase, double tempoRecarga, String tipoMunicao) {
        this.tanque = tanque;
        this.danoBase = danoBase;
        this.tempoRecarga = tempoRecarga;
        this.tipoMunicao = tipoMunicao;
        this.alcanceEficaz = definirAlcance();
    }
    
    protected abstract double definirAlcance();
    
    public abstract double calcularDano(Tanque alvo);
    
    public String getTipo() {
        return this.getClass().getSimpleName();
    }
    
    public String getTipoMunicao() { return tipoMunicao; }
    public double getTempoRecarga() { return tempoRecarga; }
    public double getAlcanceEficaz() { return alcanceEficaz; }
}
