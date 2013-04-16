package an.dpr.enbizzi.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import an.dpr.enbizzi.domain.Comarca;
import an.dpr.enbizzi.domain.Puerto;

/**
 * Crud repositorio de puertos
 * @author rsaez
 *
 */
public interface PuertosRepository extends CrudRepository<Puerto, Long>{
    
    /**
     * Buscamos puerto por el nombre, que es una clave unica
     * @param nombre
     * @return
     */
    public Puerto findByNombre(String nombre);
    
    /**
     * ultimos puertos subidos
     * @param pageable limite y ordenacion de resultados
     * @return
     */
    public Page<Puerto> findAll(Pageable pageable);
    
    public List<Puerto> findByComarca(Comarca comarca);
    
    @Query("FROM Puerto p WHERE p.comarca.codProvincia = :codProvincia")
    public List<Puerto> findByProvincia(Integer codProvincia);
    
    @Query("FROM Puerto p WHERE p.fechaAlta BETWEEN :ini AND :fin")
    public List<Puerto> findByFechaAlta(Date ini, Date fin);
    
    @Query("FROM Puerto p WHERE p.desnivelMedio > :min")
    public List<Puerto> findByDesnivelMedio(Float min);

    @Query("FROM Puerto p WHERE p.desnivelMetros > :min")
    public List<Puerto> findByDesnivel(Integer min);
    
}
