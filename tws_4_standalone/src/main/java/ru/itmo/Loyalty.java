package ru.itmo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Loyalty {

    private int id;
    private String spbso;
    private String user;
    private String event;
    private String cash;
    private String brigade;

    public Loyalty(){
    }

    public Loyalty (int id, String spbsoId, String fullName, String event, String cash, String brigade){
        this.id = id;
        this.spbso = spbsoId;
        this.user = fullName;
        this.event = event;
        this.cash = cash;
        this.brigade = brigade;
    }

    public int getId(){
        return id;
    }
    
    public String getName(){
        return user;
    }

    public String getEvent(){
        return event;
    }

    public String getBrigade(){
        return brigade;
    }

    public String getSpbso(){
        return spbso;
    }

    public String getCash() {
        return cash;
    }

    public void setBrigade(String brigade) {
        this.brigade = brigade;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setName(String fullName) {
        this.user = fullName;
    }

    public void setSpbso(String spbsoId) {
        this.spbso = spbsoId;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public void setId(int id){
        this.id = id;
    }
    @Override
    public String toString() {
        return "Loyalty {" + "Id=" + id
                + ", spbsoId=" + spbso +
                ", fullName=" + user +
                ", brigade=" + brigade +
                ", event=" + event +
                ", organizationFee=" + cash + '}';
    }
}
