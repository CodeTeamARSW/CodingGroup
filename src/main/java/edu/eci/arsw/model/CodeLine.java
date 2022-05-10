package edu.eci.arsw.model;

import javax.persistence.*;

@Entity
@Table(name = "code_lines")
public class CodeLine {

    @Id
    @Column(name = "idcodeline")
    private String idcodeline;

    @Column(name = "numline")
    private int numline;

    @Column(name = "idfile")
    private String idfile;

    @Column(name = "code")
    private String code;

    public CodeLine() {
    }

    public CodeLine(String idcodeline, int numline, String idfile, String code) {
        this.idcodeline = idcodeline;
        this.numline = numline;
        this.idfile = idfile;
        this.code = code;
    }

    public String getIdcodeline() {
        return idcodeline;
    }

    public void setIdcodeline(String idcodeline) {
        this.idcodeline = idcodeline;
    }

    public CodeLine(int numline, String idfile, String code) {
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