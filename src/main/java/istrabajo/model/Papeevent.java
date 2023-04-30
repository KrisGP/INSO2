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
 * Clase entidad representa la tabla "papeevent" en la base de datos
 * @author Luis y Cristina
 */
@Entity
@Table(name="papeevent")
public class Papeevent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPapeevent;
    @JoinColumn(name="idPapeleta")
    @ManyToOne
    private Papeleta idPapeleta;
    
    @OneToMany(targetEntity=istrabajo.model.Evento.class)
    @JoinColumn(name="idEvento")
    private Evento idEvento;

    public int getIdPapeevent() {
        return idPapeevent;
    }

    public void setIdPapeevent(int idPapeevent) {
        this.idPapeevent = idPapeevent;
    }

    
    
    public Papeleta getIdPapeleta() {
        return idPapeleta;
    }

    public void setIdPapeleta(Papeleta idPapeleta) {
        this.idPapeleta = idPapeleta;
    }

    public Evento getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Evento idEvento) {
        this.idEvento = idEvento;
    }
    
    
}
