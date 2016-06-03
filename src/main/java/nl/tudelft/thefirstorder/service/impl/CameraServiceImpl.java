package nl.tudelft.thefirstorder.service.impl;

import nl.tudelft.thefirstorder.domain.Camera;
import nl.tudelft.thefirstorder.repository.CameraRepository;
import nl.tudelft.thefirstorder.service.CameraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing Camera.
 */
@Service
@Transactional
class CameraServiceImpl implements CameraService {

    private final Logger log = LoggerFactory.getLogger(CameraServiceImpl.class);

    @Inject
    private CameraRepository cameraRepository;

    /**
     * Save a camera.
     *
     * @param camera the entity to save
     * @return the persisted entity
     */
    public Camera save(Camera camera) {
        log.debug("Request to save Camera : {}", camera);
        return cameraRepository.save(camera);
    }

    /**
     *  Get all the cameras.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Camera> findAll(Pageable pageable) {
        log.debug("Request to get all Cameras");
        return cameraRepository.findAll(pageable);
    }

    /**
     *  Get one camera by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Camera findOne(Long id) {
        log.debug("Request to get Camera : {}", id);
        Camera camera = cameraRepository.findOne(id);
        return camera;
    }

    /**
     *  Delete the  camera by id.
     *
     *  @param id the id of the entity
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Camera : {}", id);
        cameraRepository.delete(id);
    }
}
