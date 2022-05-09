package edu.eci.arsw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "code_lines")
public class Code_Line {

    @Id
    @Column(name = "numline")
    private int numline;

    @Column(name = "idfile")
    private String idfile;

    @Column(name = "code")
    private String code;

    public Code_Line(int numline, String idfile, String code) {
        this.numline = numline;
        this.idfile = idfile;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}