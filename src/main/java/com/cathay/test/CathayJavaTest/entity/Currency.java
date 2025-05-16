package com.cathay.test.CathayJavaTest.entity;

import javax.persistence.*;

@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(unique = true, nullable = false)
    private String chineseCode;

    public int getId() {
        return id;
    }

    public Currency setId(int id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Currency setCode(String code) {
        this.code = code;
        return this;
    }

    public String getChineseCode() {
        return chineseCode;
    }

    public Currency setChineseCode(String chineseCode) {
        this.chineseCode = chineseCode;
        return this;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", chineseCode='" + chineseCode + '\'' +
                '}';
    }
}
