package an.dpr.enbizzi.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "noticias")
@XmlRootElement(name = "Noticia")
public class Noticia {
	
	private Long idNoticia;
	private String titulo;
	private String entradilla;
	private String cuerpo;
	private Date fechaCreacion;
	private Date fechaPublicacion;
	private Boolean publicada;

	@Override
	public String toString() {
		return "Noticia [idNoticia=" + idNoticia + ", titulo=" + titulo
				+ ", entradilla=" + entradilla + ", cuerpo=" + cuerpo
				+ ", fechaCreacion=" + fechaCreacion + ", fechaPublicacion="
				+ fechaPublicacion + ", publicada=" + publicada + "]";
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	public Long getIdNoticia() {
		return idNoticia;
	}

	public void setIdNoticia(Long idNoticia) {
		this.idNoticia = idNoticia;
	}

	@Column
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Column
	public String getEntradilla() {
		return entradilla;
	}

	public void setEntradilla(String entradilla) {
		this.entradilla = entradilla;
	}

	@Column
	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	@Column
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Column
	public Date getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(Date fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	@Column
	public Boolean getPublicada() {
		return publicada;
	}

	public void setPublicada(Boolean publicada) {
		this.publicada = publicada;
	}

	public static Noticia createNoticia(Long idNoticia, String titulo,
			String entradilla, String cuerpo, Date fechaCreacion, 
			Date fechaPublicacion, Boolean publicada) {
		Noticia noticia = new Noticia();
		noticia.setIdNoticia(idNoticia);
		noticia.setCuerpo(cuerpo);
		noticia.setEntradilla(entradilla);
		noticia.setFechaCreacion(fechaCreacion);
		noticia.setFechaPublicacion(fechaPublicacion);
		noticia.setPublicada(publicada);
		noticia.setTitulo(titulo);
		
		return noticia;
	}

}
