package an.dpr.enbizzi.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import an.dpr.enbizzi.beans.CyclingType;
import an.dpr.enbizzi.beans.Difficulty;
import an.dpr.enbizzi.jpa.repository.CyclingTypeRepository;
import an.dpr.enbizzi.jpa.repository.DifficultyRepository;

public class TablasAuxiliaresDAO {
    
    private static final Logger log = Logger.getLogger(TablasAuxiliaresDAO.class);

    @Autowired
    CyclingTypeRepository ctRepo;
    @Autowired
    DifficultyRepository difRepo;
    
    public void createCyclingTypeValues(){
	log.debug("inicio");
	an.dpr.enbizzi.domain.CyclingType ctD;
	for(CyclingType ct : CyclingType.values()){
	    ctD = ctRepo.findByName(ct.name());
	    if (ctD == null){
		ctD = ct.getDomain();
		ctD = ctRepo.save(ctD);
		log.debug("add "+ctD.getName());
	    }
	}
    }
    
    public void createDifficultyValues(){
	log.debug("inicio");
	an.dpr.enbizzi.domain.Difficulty difD;
	for (Difficulty dif : Difficulty.values()){
	    difD = difRepo.findByName(dif.name());
	    if (difD == null){
		difD = dif.getDomain();
		difD = difRepo.save(difD);
		log.debug("add "+difD.getName());
	    }
	}
    }
}
