package de.propra.splitter.domaene;

import org.javamoney.moneta.Money;

import java.util.Objects;

public final class NutzerSaldo {
    private final Nutzer nutzer;
    private Money saldo;

    public NutzerSaldo(Nutzer nutzer, Money saldo) {
        this.nutzer = nutzer;
        this.saldo = saldo;
    }

    public Nutzer nutzer() {
        return nutzer;
    }

    public Money saldo() {
        return saldo;
    }

    public void setSaldo(Money saldo){
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (NutzerSaldo) obj;
        return Objects.equals(this.nutzer, that.nutzer) &&
                Objects.equals(this.saldo, that.saldo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nutzer, saldo);
    }

    @Override
    public String toString() {
        return "NutzerSaldo[" +
                "nutzer=" + nutzer + ", " +
                "saldo=" + saldo + ']';
    }

}
