package nl.tudelft.thefirstorder.service.impl;

import nl.tudelft.thefirstorder.domain.Player;
import nl.tudelft.thefirstorder.repository.PlayerRepository;
import nl.tudelft.thefirstorder.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing Player.
 */
@Service
@Transactional
class PlayerServiceImpl implements PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Inject
    private PlayerRepository playerRepository;

    /**
     * Save a player.
     *
     * @param player the entity to save
     * @return the persisted entity
     */
    @Transactional
    public Player save(Player player) {
        log.debug("Request to save Player : {}", player);
        Player result = playerRepository.save(player);
        return result;
    }

    /**
     *  Get all the players.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Player> findAll(Pageable pageable) {
        log.debug("Request to get all Players");
        Page<Player> result = playerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one player by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Player findOne(Long id) {
        log.debug("Request to get Player : {}", id);
        Player result = playerRepository.findOne(id);
        return result;
    }

    /**
     *  Delete the  player by id.
     *
     *  @param id the id of the entity
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Player : {}", id);
        playerRepository.delete(id);
    }
}
