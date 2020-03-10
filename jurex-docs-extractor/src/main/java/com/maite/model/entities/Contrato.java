package com.maite.model.entities;

import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="contratos")
public class Contrato implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cont_id")
	private Long id ;

	@Column(name = "id_jurex")
	private String idJurex;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_procesamiento")
	private Date fechaProcesamiento;

	@Column(name = "path_del_archivo")
	private String pathDelArchivo;

	public Long getId() {
		return id;
	}

	public Contrato() {
	}

	public String getIdJurex() {
		return idJurex;
	}

	public void setIdJurex(String idJurex) {
		this.idJurex = idJurex;
	}

	public Date getFechaProcesamiento() {
		return fechaProcesamiento;
	}

	public void setFechaProcesamiento(Date fechaProcesamiento) {
		this.fechaProcesamiento = fechaProcesamiento;
	}

	public String getPathDelArchivo() {
		return pathDelArchivo;
	}

	public void setPathDelArchivo(String pathDelArchivo) {
		this.pathDelArchivo = pathDelArchivo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = -7049957706738879274L;


}
