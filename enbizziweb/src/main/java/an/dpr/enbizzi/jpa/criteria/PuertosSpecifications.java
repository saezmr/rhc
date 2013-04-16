package an.dpr.enbizzi.jpa.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import an.dpr.enbizzi.domain.Puerto;

/**
 * TODO clase a medio hacer
 * @author rsaez
 *
 */
public class PuertosSpecifications {

    public static Specification<Puerto> lastPuertos(){
	return new Specification<Puerto>(){

	    @Override
	    public Predicate toPredicate(Root<Puerto> puertoRoot,
		    CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate ret = null;
		query.orderBy(cb.desc(puertoRoot.get("idPuerto")));
		return ret;
	    }};
    }
}
