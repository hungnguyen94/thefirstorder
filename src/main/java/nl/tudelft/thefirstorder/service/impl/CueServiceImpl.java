package nl.tudelft.thefirstorder.service.impl;

import nl.tudelft.thefirstorder.domain.Camera;
import nl.tudelft.thefirstorder.domain.Cue;
import nl.tudelft.thefirstorder.domain.Player;
import nl.tudelft.thefirstorder.repository.CueRepository;
import nl.tudelft.thefirstorder.service.CueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Cue.
 */
@Service
@Transactional
class CueServiceImpl implements CueService {

    private final Logger log = LoggerFactory.getLogger(CueServiceImpl.class);

    @Inject
    private CueRepository cueRepository;

    /**
     * Save a cue.
     *
     * @param cue the entity to save
     * @return the persisted entity
     */
    @Transactional
    public Cue save(Cue cue) {
        log.debug("Request to save Cue : {}", cue);
        return cueRepository.save(cue);
    }

    /**
     * Get all the cues.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Cue> findAll(Pageable pageable) {
        log.debug("Request to get all Cues");
        return cueRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cue> findCuesByScript(Long scriptId) {
        return StreamSupport
            .stream(cueRepository.findAll().spliterator(), false)
            .filter(cue -> Objects.equals(cue.getScript().getId(), scriptId))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Player getPlayer(Long cueId) {
        Cue cue = findOne(cueId);
        return cue.getPlayer();
    }

    @Override
    @Transactional(readOnly = true)
    public Camera getCamera(Long cueId) {
        Cue cue = findOne(cueId);
        return cue.getCamera();
    }

    /**
     * Get one cue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Cue findOne(Long id) {
        log.debug("Request to get Cue : {}", id);
        return cueRepository.findOne(id);
    }

    /**
     * Delete the  cue by id.
     *
     * @param id the id of the entity
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Cue : {}", id);
        cueRepository.delete(id);
    }
}
