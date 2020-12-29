package com.zoey.ppmtools.services;

import com.zoey.ppmtools.domain.Backlog;
import com.zoey.ppmtools.domain.Project;
import com.zoey.ppmtools.domain.ProjectTask;
import com.zoey.ppmtools.exceptions.ProjectNotFoundException;
import com.zoey.ppmtools.repositories.BacklogRepository;
import com.zoey.ppmtools.repositories.ProjectRepository;
import com.zoey.ppmtools.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);

            Integer backlogSequence = backlog.getPTSequence() + 1;
            projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            backlog.setPTSequence(backlogSequence);

            if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
                projectTask.setPriority(3);
            }

            if (projectTask.getStatus() == null || projectTask.getStatus().equals("")) {
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project not Found.");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String id) {
        Project project = projectRepository.findByProjectIdentifier(id);
        if (project == null) {
            throw new ProjectNotFoundException("Project with ID '" + id + "' does not exist.");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
