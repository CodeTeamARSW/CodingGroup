package edu.eci.arsw.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class Code_LineID {

    @Column(name = "numline")
    private int numline;


    @Column(name = "idfile")
    private String idfile;

    public Code_LineID() {
    }

    public Code_LineID(int numline, String idfile) {
        this.numline = numline;
        this.idfile = idfile;
    }

    public int getNumline() {
        return numline;
    }

    public void setNumline(int numline) {
        this.numline = numline;
    }

    public String getIdfile() {
        return idfile;
    }

    public void setIdfile(String idfile) {
        this.idfile = idfile;
    }

}