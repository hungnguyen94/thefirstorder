package nl.tudelft.thefirstorder.service.impl;

import nl.tudelft.thefirstorder.domain.Project;
import nl.tudelft.thefirstorder.repository.ProjectRepository;
import nl.tudelft.thefirstorder.repository.UserRepository;
import nl.tudelft.thefirstorder.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
class ProjectServiceImpl implements ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * Save a project.
     *
     * @param project the entity to save
     * @return the persisted entity
     */
    @Transactional
    public Project save(Project project) {
        log.debug("Request to save Project : {}", project);
        return projectRepository.save(project);
    }

    /**
     * Get all the projects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Project> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        return projectRepository.findAll(pageable);
    }

    /**
     * Get one project by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Project findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        return projectRepository.findOne(id);
    }

    /**
     * Delete the  project by id.
     * Also sets the current project id of all users
     * which have this project as their current project to null.
     *
     * @param id the id of the entity
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.delete(id);

//        List<User> users = userRepository.findAllByCurrentProjectId(id);
//        for (User user : users) {
//            user.setCurrentProjectId(null);
//        }
    }
}
