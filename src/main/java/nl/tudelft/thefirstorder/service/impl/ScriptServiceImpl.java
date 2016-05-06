package nl.tudelft.thefirstorder.service.impl;

import nl.tudelft.thefirstorder.domain.Script;
import nl.tudelft.thefirstorder.repository.ScriptRepository;
import nl.tudelft.thefirstorder.service.ScriptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Script.
 */
@Service
@Transactional
public class ScriptServiceImpl implements ScriptService {

    private final Logger log = LoggerFactory.getLogger(ScriptServiceImpl.class);
    
    @Inject
    private ScriptRepository scriptRepository;
    
    /**
     * Save a script.
     * 
     * @param script the entity to save
     * @return the persisted entity
     */
    public Script save(Script script) {
        log.debug("Request to save Script : {}", script);
        Script result = scriptRepository.save(script);
        return result;
    }

    /**
     *  Get all the scripts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Script> findAll(Pageable pageable) {
        log.debug("Request to get all Scripts");
        Page<Script> result = scriptRepository.findAll(pageable); 
        return result;
    }


    /**
     *  get all the scripts where Project is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Script> findAllWhereProjectIsNull() {
        log.debug("Request to get all scripts where Project is null");
        return StreamSupport
            .stream(scriptRepository.findAll().spliterator(), false)
            .filter(script -> script.getProject() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one script by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Script findOne(Long id) {
        log.debug("Request to get Script : {}", id);
        Script script = scriptRepository.findOne(id);
        return script;
    }

    /**
     *  Delete the  script by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Script : {}", id);
        scriptRepository.delete(id);
    }
}
