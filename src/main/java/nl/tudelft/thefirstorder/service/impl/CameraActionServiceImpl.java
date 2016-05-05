package nl.tudelft.thefirstorder.service.impl;

import nl.tudelft.thefirstorder.domain.CameraAction;
import nl.tudelft.thefirstorder.repository.CameraActionRepository;
import nl.tudelft.thefirstorder.service.CameraActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing CameraAction.
 */
@Service
@Transactional
public class CameraActionServiceImpl implements CameraActionService {

    private final Logger log = LoggerFactory.getLogger(CameraActionServiceImpl.class);
    
    @Inject
    private CameraActionRepository cameraActionRepository;
    
    /**
     * Save a cameraAction.
     * 
     * @param cameraAction the entity to save
     * @return the persisted entity
     */
    public CameraAction save(CameraAction cameraAction) {
        log.debug("Request to save CameraAction : {}", cameraAction);
        CameraAction result = cameraActionRepository.save(cameraAction);
        return result;
    }

    /**
     *  Get all the cameraActions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<CameraAction> findAll(Pageable pageable) {
        log.debug("Request to get all CameraActions");
        Page<CameraAction> result = cameraActionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one cameraAction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CameraAction findOne(Long id) {
        log.debug("Request to get CameraAction : {}", id);
        CameraAction cameraAction = cameraActionRepository.findOne(id);
        return cameraAction;
    }

    /**
     *  Delete the  cameraAction by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CameraAction : {}", id);
        cameraActionRepository.delete(id);
    }
}
