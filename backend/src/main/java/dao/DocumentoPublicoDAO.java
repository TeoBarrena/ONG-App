package dao;

import jakarta.enterprise.context.RequestScoped;
import modelos.DocumentoPublico;

@RequestScoped
public class DocumentoPublicoDAO extends GenericDAO<DocumentoPublico> {
	
	public DocumentoPublicoDAO() {
		super(DocumentoPublico.class);
	}

}
