package rest;

import dao.DocumentoPublicoDAO;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import modelos.DocumentoPublico;


@Path("/documentospublicos")
public class DocumentoPublicoResource extends GenericResource<DocumentoPublico> {
	
	@Inject 
	private DocumentoPublicoDAO dao;
	
	public DocumentoPublicoResource() {
        super();
    }
	
	@PostConstruct
    public void postConstruct() {
        this.setDAO(dao);
    }
}
