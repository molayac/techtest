package co.com.ias.controller;

import co.com.ias.model.Ave;
import co.com.ias.model.Pais;
import co.com.ias.repository.AveRepository;
import co.com.ias.repository.PaisRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marlon Olaya on 18/03/2017.
 */
@RestController("aves")
public class AveRestController {
    @Autowired
    private AveRepository repo;
    @Autowired
    private PaisRepository paisRepo;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Ave> findItems() {
        List<Ave> aves = repo.findAll();
        return aves;
    }

    /**
     * Este metodo permite crear nuevas aves, a su vez que crea el Pais.
     * @param ave Recibe un Json que contiene los datos del ave y el pais.
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Ave addItem(@RequestBody Ave ave) {
        List<Pais> paises = new ArrayList<Pais>();
        ave.setId(null);
        /*Encuentra los paises viejos y les pone el id, para que no se creen otra vez*/
        ave.getPaises().forEach(
                p ->{
                    if(paisRepo.findByDsNombreCount(p.getDsNombre())==0)
                        paises.add(p);
                    else
                        paises.add(paisRepo.findByDsNombre(p.getDsNombre()));
                });
        ave.setPaises(paises);
        System.out.println("HERE: "+ ave.getDsNombreCientifico()+" PAIS: "+ave.getPaises().size());
        return repo.saveAndFlush(ave);
    }

    @RequestMapping(value="/aves/searchBasic", method = RequestMethod.GET)
    @ResponseBody
    public List<Ave> listAveByName(@RequestParam String search) {
        System.out.println("LIST AVE By Name HERE: "+ search);
        return repo.findByNameContaining(search);
    }

    /**
     * Este metodo permite buscar por uno o los dos campos, por defecto siempre es obligatorio el campo n de nombre
     * @param zona ID de zona a buscar Puede o no solicitarse
     * @param n Nombre A buscar en los comun y cientifico OBLIGATORIO
     * @return Listado de Aves
     */
    @RequestMapping(value="/aves/search", method = RequestMethod.GET)
    public List<Ave> searchAvesByNameAndZone(@RequestParam(required=false) String zona, String n) {
        if(zona == null || zona.isEmpty()){
            return repo.findByNameContaining(n);
        }
        // Si se indican ambos campos se retorna la interseccion. :)
        return repo.findByPaises_Zona_Id(Integer.parseInt(zona), n);
    }

    @RequestMapping(value="/searchAndOr", method = RequestMethod.GET)
    public List<Ave> listAveByNameAnd(@RequestParam String zona, @RequestParam String search) {
        return repo.findByPaises_Zona_IdAndDsNombreComunLike(Integer.parseInt(zona), search);
    }

    @RequestMapping(value="/searchAnd", method = RequestMethod.GET)
    public List<Ave> listAveByNameAndOR(@RequestParam String zona, @RequestParam String search) {
        return repo.findByPaises_Zona_IdAndDsNombreComunLike(Integer.parseInt(zona), search);
    }

    @RequestMapping(value = "/aves/{id}", method = RequestMethod.PUT)
    public Ave updateItem(@RequestBody Ave updatedItem, @PathVariable Integer id) {
        updatedItem.setId(id);
        return repo.saveAndFlush(updatedItem);
    }

    @RequestMapping(value = "/aves/{id}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable Integer id) {
        repo.delete(id);
    }

}
