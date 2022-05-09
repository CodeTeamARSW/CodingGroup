package edu.eci.arsw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "files")
public class File {

    @Id
    @Column(name = "idfile")
    private String idfile;

    @Column(name = "filename")
    private String filename;

    @Column(name = "room")
    private String room;

    public File() {
    }

    public File(String idfile, String filename, String room) {
        this.idfile = idfile;
        this.filename = filename;
        this.room = room;
    }

    public String getIdfile() {
        return idfile;
    }

    public void setIdfile(String idfile) {
        this.idfile = idfile;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}