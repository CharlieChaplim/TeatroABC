package br.eng.gjkl.teatro.classes;

public class Cadeira {
    private Area area;
    private int posicao;
    private boolean comprada = false;

    public Cadeira(Area area, int posicao) {
        this.area = area;
        this.posicao = posicao;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public boolean isComprada() {
        return comprada;
    }

    public void setComprada(boolean comprada) {
        this.comprada = comprada;
    }
}
