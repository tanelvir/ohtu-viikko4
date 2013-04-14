/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olutopas.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author taneli
 */
@Entity
public class User {
    
    private String name;
    
    @Id
    private Integer id;

    public User() {
    }
    
    public User(String nimi) {
        this.name = nimi;
    }
    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "User: " + getName();
    }
    
}
