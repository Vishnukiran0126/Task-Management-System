    package com.example.Task.controllers;

    import com.example.Task.Dtos.Pagination.PaginatedResponse;
    import com.example.Task.Dtos.Pagination.PaginationRequest;
    import com.example.Task.Dtos.ProjectRequestDto;
    import com.example.Task.Dtos.ProjectResponseDto;
    import com.example.Task.entites.Project;
    import com.example.Task.service.ProjectService;
    import org.springframework.data.domain.Sort;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api")
    @CrossOrigin(origins = "http://localhost:3000/")
    public class ProjectController {
        private ProjectService projectService;

        public ProjectController(ProjectService projectService) {
            this.projectService = projectService;
        }

        @GetMapping("/projects")
        public ResponseEntity<PaginatedResponse<ProjectResponseDto>> getProjects(@RequestParam(defaultValue = "10") Integer size,
                                                                                 @RequestParam(defaultValue = "0") Integer page,
                                                                                 @RequestParam(defaultValue = "ASC") Sort.Direction direction,
                                                                                 @RequestParam(defaultValue = "id") String sortField,
                                                                                 @RequestParam(required = false) String createdBy,
                                                                                 @RequestParam(required = false) String projectName)
        {
            PaginationRequest pr=PaginationRequest.builder()
                    .size(size)
                    .page(page)
                    .sortField(sortField)
                    .direction(direction)
                    .build();

            return new ResponseEntity<>(projectService.getAllProjects(pr,createdBy,projectName), HttpStatus.OK);
        }

        @GetMapping("/project/{id}")
        public ResponseEntity<Project> getProjectById(@PathVariable long id) {
            return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
        }

        @PostMapping("/projects")
        public ResponseEntity<ProjectResponseDto> createProject(@RequestBody ProjectRequestDto project) {

            return new ResponseEntity<>(projectService.addProject(project), HttpStatus.CREATED);
        }

        @PutMapping("/project/{id}")
        public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable long id,@RequestBody ProjectRequestDto dto){
            System.out.println("Called update endpoint");
            System.out.println(dto.getName());
            return new ResponseEntity<>(projectService.updateProject(id,dto),HttpStatus.OK);

        }

        @DeleteMapping("/projects/{id}")
        public ResponseEntity<Project> deleteProject(@PathVariable long id) {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }
