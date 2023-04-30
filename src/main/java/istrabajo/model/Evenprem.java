/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package istrabajo.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase entidad representa la tabla "evenprem" en la base de datos
 * @author Luis y Cristina
 */
@Entity
@Table(name="evenprem")
public class Evenprem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEvenprem;
    
    @JoinColumn(name="idEvento")
    @OneToMany(targetEntity=istrabajo.model.Evento.class)
    private Evento idEvento;
    
    @JoinColumn(name="idPremio")
    @ManyToOne
    private Premio idPremio;

    public Evento getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Evento idEvento) {
        this.idEvento = idEvento;
    }

    public Premio getIdPremio() {
        return idPremio;
    }

    public void setIdPremio(Premio idPremio) {
        this.idPremio = idPremio;
    }
}
