package edu.eci.arsw.model;

import javax.persistence.*;

@Entity
@Table(name = "code_lines")
public class Code_Line {

    @EmbeddedId
    private Code_LineID code_lineID;

    @Column(name = "code")
    private String code;

    public Code_Line() {
    }

    public Code_Line(Code_LineID code_lineID, String code) {
        this.code_lineID = code_lineID;
        this.code = code;
    }

    public Code_LineID getCode_lineID() {
        return code_lineID;
    }

    public void setCode_lineID(Code_LineID code_lineID) {
        this.code_lineID = code_lineID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}