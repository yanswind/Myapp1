package com.example.myapp;

public class RateItem {
    private int id;
    private String cname;
    private String cval;

    public RateItem() {
        super();
        cname = "";
        cval = "";
    }
    public RateItem(String curName, String curRate) {
        super();
        this.cname = curName;
        this.cval = curRate;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getCval() {
        return cval;
    }
    public void setCval(String cval) {
        this.cval = cval;
    }
}
