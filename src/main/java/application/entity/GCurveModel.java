package application.entity;

import javax.persistence.*;

@Entity
@Table(name="GCurve")
public class GCurveModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordId")
    private Long recordId;

    @Column(name = "tradedate")
    private String tradedate;

    @Column(name = "tradetime")
    private String tradetime;

    @Column(name = "B1")
    private double B1;

    @Column(name = "B2")
    private double B2;

    @Column(name = "B3")
    private double B3;

    @Column(name = "T1")
    private double T1;

    @Column(name = "G1")
    private double G1;

    @Column(name = "G2")
    private double G2;

    @Column(name = "G3")
    private double G3;

    @Column(name = "G4")
    private double G4;

    @Column(name = "G5")
    private double G5;

    @Column(name = "G6")
    private double G6;

    @Column(name = "G7")
    private double G7;

    @Column(name = "G8")
    private double G8;

    @Column(name = "G9")
    private double G9;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getTradedate() {
        return tradedate;
    }

    public void setTradedate(String tradedate) {
        this.tradedate = tradedate;
    }

    public String getTradetime() {
        return tradetime;
    }

    public void setTradetime(String tradetime) {
        this.tradetime = tradetime;
    }

    public double getB1() {
        return B1;
    }

    public void setB1(double b1) {
        B1 = b1;
    }

    public double getB2() {
        return B2;
    }

    public void setB2(double b2) {
        B2 = b2;
    }

    public double getB3() {
        return B3;
    }

    public void setB3(double b3) {
        B3 = b3;
    }

    public double getT1() {
        return T1;
    }

    public void setT1(double t1) {
        T1 = t1;
    }

    public double getG1() {
        return G1;
    }

    public void setG1(double g1) {
        G1 = g1;
    }

    public double getG2() {
        return G2;
    }

    public void setG2(double g2) {
        G2 = g2;
    }

    public double getG3() {
        return G3;
    }

    public void setG3(double g3) {
        G3 = g3;
    }

    public double getG4() {
        return G4;
    }

    public void setG4(double g4) {
        G4 = g4;
    }

    public double getG5() {
        return G5;
    }

    public void setG5(double g5) {
        G5 = g5;
    }

    public double getG6() {
        return G6;
    }

    public void setG6(double g6) {
        G6 = g6;
    }

    public double getG7() {
        return G7;
    }

    public void setG7(double g7) {
        G7 = g7;
    }

    public double getG8() {
        return G8;
    }

    public void setG8(double g8) {
        G8 = g8;
    }

    public double getG9() {
        return G9;
    }

    public void setG9(double g9) {
        G9 = g9;
    }

    @Override
    public String toString() {
        return "GCurveModel{" +
                "tradedate='" + tradedate + '\'' +
                ", tradetime='" + tradetime + '\'' +
                ", B1=" + B1 +
                ", B2=" + B2 +
                ", B3=" + B3 +
                ", T1=" + T1 +
                ", G1=" + G1 +
                ", G2=" + G2 +
                ", G3=" + G3 +
                ", G4=" + G4 +
                ", G5=" + G5 +
                ", G6=" + G6 +
                ", G7=" + G7 +
                ", G8=" + G8 +
                ", G9=" + G9 +
                '}';
    }
}
