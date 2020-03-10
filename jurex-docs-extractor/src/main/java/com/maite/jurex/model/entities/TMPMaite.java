package com.maite.jurex.model.entities;

import java.sql.Blob;
import java.sql.Time;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TMP_MAITE")
public class TMPMaite {
	
	@Column
	private String asunto;
	@Column
	private String caratula;
	
	@Column
	private String proceso;
	
	@Column(name = "FECHATAREA")
	private Time fechaTarea;
	
	@Id
	@Column(name = "IDDOCUMENTO")
	private String idDocumento;
	
	@Column(name = "NOMBREADJUNTO")
	private String nombreAdjunto;
	
	@Column
	private Blob archivo;
	
	@Column
	private String extension;
	
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getCaratula() {
		return caratula;
	}
	public void setCaratula(String caratula) {
		this.caratula = caratula;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public Time getFechaTarea() {
		return fechaTarea;
	}
	public void setFechaTarea(Time fechaTarea) {
		this.fechaTarea = fechaTarea;
	}
	public String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getNombreAdjunto() {
		return nombreAdjunto;
	}
	public void setNombreAdjunto(String nombreAdjunto) {
		this.nombreAdjunto = nombreAdjunto;
	}
	public Blob getArchivo() {
		return archivo;
	}
	public void setArchivo(Blob archivo) {
		this.archivo = archivo;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public Optional<TMPMaite> getObjectIfIsPdf(){
		return this.nombreAdjunto.toUpperCase().contains("PDF")? Optional.of(this) : Optional.empty();
	}
	
}
