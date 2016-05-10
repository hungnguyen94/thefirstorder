package nl.tudelft.thefirstorder.service.impl;

import nl.tudelft.thefirstorder.domain.Cue;
import nl.tudelft.thefirstorder.repository.CueRepository;
import nl.tudelft.thefirstorder.service.CueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing Cue.
 */
@Service
@Transactional
public class CueServiceImpl implements CueService {

    private final Logger log = LoggerFactory.getLogger(CueServiceImpl.class);
    
    @Inject
    private CueRepository cueRepository;
    
    /**
     * Save a cue.
     * 
     * @param cue the entity to save
     * @return the persisted entity
     */
    public Cue save(Cue cue) {
        log.debug("Request to save Cue : {}", cue);
        Cue result = cueRepository.save(cue);
        return result;
    }

    /**
     *  Get all the cues.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Cue> findAll(Pageable pageable) {
        log.debug("Request to get all Cues");
        Page<Cue> result = cueRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one cue by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Cue findOne(Long id) {
        log.debug("Request to get Cue : {}", id);
        Cue cue = cueRepository.findOne(id);
        return cue;
    }

    /**
     *  Delete the  cue by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cue : {}", id);
        cueRepository.delete(id);
    }
}