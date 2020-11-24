package com.postit.postit_.Objects;

public class comment {
    private String commID;
    private String noteID;
    private String comm;
    private String comR;


    public comment(String commID, String noteID, String comm,String comR) {
        this.commID = commID;
        this.noteID = noteID;
        this.comm = comm;
        this.comR = comR;
    }

    public comment() {
    }


    public String getComR() {
        return comR;
    }

    public void setComR(String comR) {
        this.comR = comR;
    }

    public String getCommID() {
        return commID;
    }

    public void setCommID(String commID) {
        this.commID = commID;
    }

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }
}
