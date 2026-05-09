package seeds;

import dao.BarrioDAO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import modelos.Barrio;
import modelos.Ubicacion;
import modelos.Zona;
import modelos.Area;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class BarrioSeeds implements Seed {

    @Inject
    private BarrioDAO barrioDAO;

    @Override
    public void seed() {
        // --- VILLA ARGÜELLO ---
        Barrio villaArguello = new Barrio(
            "Villa Argüello",
            "Barrio residencial y universitario",
            new Ubicacion("-34.90498097201398", "-57.9184599990333")
        );

        Zona zonaVA1 = new Zona(
            "Zona Norte Villa Argüello",
            new Area(new ArrayList<>(List.of(
                new Ubicacion("-34.908119969740085", "-57.916648248219"),
                new Ubicacion("-34.903359897715795", "-57.92365635204614"),
                new Ubicacion("-34.90047373524415", "-57.92109795174812"),
                new Ubicacion("-34.90390475979942", "-57.91675174160987"),
                new Ubicacion("-34.89793380891084", "-57.911737193519706"),
                new Ubicacion("-34.89861154573878", "-57.910482419001504"),
                new Ubicacion("-34.899557304501286", "-57.91133209953176"),
                new Ubicacion("-34.903589386301995", "-57.914668234769486"),
                new Ubicacion("-34.9042844821252", "-57.913455845412685")
            ))),
            new Ubicacion("-34.90220387667528", "-57.91553667620647"),
            villaArguello
        );

        Zona zonaVA2 = new Zona(
            "Zona Sur Villa Argüello",
            new Area(new ArrayList<>(List.of(
                new Ubicacion("-34.908119969740085", "-57.916648248219"),
                new Ubicacion("-34.903359897715795", "-57.92365635204614"),
                new Ubicacion("-34.90739467969351", "-57.92646722077933"),
                new Ubicacion("-34.912301405187506", "-57.91999363008803")
            ))),
            new Ubicacion("-34.90779398808422", "-57.92169136278312"),
            villaArguello
        );

        Zona zonaVA3 = new Zona(
            "Zona Este Villa Argüello",
            new Area(new ArrayList<>(List.of(
            		new Ubicacion("-34.912301405187506", "-57.91999363008803"),
            		new Ubicacion("-34.904478055905464", "-57.902523161283625"),
            		new Ubicacion("-34.899557304501286", "-57.91133209953176"),
            		new Ubicacion("-34.9042844821252", "-57.913455845412685"),
            		new Ubicacion("-34.903589386301995", "-57.914668234769486")
            ))),
            new Ubicacion("-34.90484212680429", "-57.91239459421712"),
            villaArguello
        );

        villaArguello.addZonas(List.of(zonaVA1, zonaVA2, zonaVA3));

        // --- BERISSO CENTRO ---
        Barrio berissoCentro = new Barrio(
            "Berisso Centro",
            "Centro urbano de Berisso",
            new Ubicacion("-34.8695", "-57.8862")
        );

        Zona zonaB1 = new Zona(
            "Zona Oeste Berisso",
            new Area(new ArrayList<>(List.of(
                new Ubicacion("-34.858035013186964", "-57.888250936296714"),
                new Ubicacion("-34.873292815813876", "-57.89602547706639"),
                new Ubicacion("-34.8795847572917", "-57.87890047510075"),
                new Ubicacion("-34.8696498528923", "-57.871470700869125")
            ))),
            new Ubicacion("-34.87014060979621", "-57.88366189733324"),
            berissoCentro
        );

        Zona zonaB2 = new Zona(
            "Zona Este Berisso",
            new Area(new ArrayList<>(List.of(
            		new Ubicacion("-34.88508227020497", "-57.833036919171484"),
            		new Ubicacion("-34.87179631636188", "-57.85294653232949"),
            		new Ubicacion("-34.8696498528923", "-57.871470700869125"),
            		new Ubicacion("-34.8795847572917", "-57.87890047510075"),
            		new Ubicacion("-34.887519826726674", "-57.87686778395963"),
            		new Ubicacion("-34.89093228437955", "-57.87218753907548")
            ))),
            new Ubicacion("-34.88076088464285", "-57.86423499175099"),
            berissoCentro
        );

        berissoCentro.addZonas(List.of(zonaB1, zonaB2));

        // --- LA PLATA CASCO URBANO ---
        Barrio laPlataCentro = new Barrio(
            "La Plata Casco Urbano",
            "Centro administrativo y comercial de La Plata",
            new Ubicacion("-34.9214", "-57.9544")
        );

        Zona zonaLP1 = new Zona(
            "Centro comercial calle 12",
            new Area(new ArrayList<>(List.of(
                new Ubicacion("-34.922259254668525", "-57.95315443920679"),
                new Ubicacion("-34.92052323761369", "-57.951260054372725"),
                new Ubicacion("-34.92621845442036", "-57.941268102993064"),
                new Ubicacion("-34.92892215601104", "-57.944195013376216")
            ))),
            new Ubicacion("-34.924480775678404", "-57.9474694024872"),
            laPlataCentro
        );

        Zona zonaLP2 = new Zona(
            "Zona Catedral",
            new Area(new ArrayList<>(List.of(
                new Ubicacion("-34.92226488103779", "-57.95318076597215"),
                new Ubicacion("-34.916642761551635", "-57.961336509455364"),
                new Ubicacion("-34.92202816798396", "-57.96743527338749"),
                new Ubicacion("-34.92797537652061", "-57.95942387934647")
            ))),
            new Ubicacion("-34.92222779627349", "-57.96034497479087"),
            laPlataCentro
        );

        Zona zonaLP3 = new Zona(
            "La Muni",
            new Area(new ArrayList<>(List.of(
                new Ubicacion("-34.92086936921155", "-57.95301753060123"),
                new Ubicacion("-34.91986505900372", "-57.954165823382276"),
                new Ubicacion("-34.91825395237458", "-57.9520988963764"),
                new Ubicacion("-34.91906997137311", "-57.95087405074327")
            ))),
            new Ubicacion("-34.91951433142302", "-57.9525383240611"),
            laPlataCentro
        );

        laPlataCentro.addZonas(List.of(zonaLP1, zonaLP2, zonaLP3));

        // Persistir solo los barrios, para que cascade guarde zonas, areas y ubicaciones
        barrioDAO.create(villaArguello);
        barrioDAO.create(berissoCentro);
        barrioDAO.create(laPlataCentro);
    }
}
