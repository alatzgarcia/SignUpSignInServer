/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author 2dam
 */
public class Incidents {
    private int id;
    private String incidentType;
    private String implicators;
    private String descricption;
    private String employee;
    private int room;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getImplicators() {
        return implicators;
    }

    public void setImplicators(String implicators) {
        this.implicators = implicators;
    }

    public String getDescricption() {
        return descricption;
    }

    public void setDescricption(String descricption) {
        this.descricption = descricption;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
    
}
