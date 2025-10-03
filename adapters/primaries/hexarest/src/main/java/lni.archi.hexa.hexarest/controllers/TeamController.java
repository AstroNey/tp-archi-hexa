package lni.archi.hexa.hexarest.controllers;

import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.exceptions.job.JobListException;
import lni.archi.hexa.core.model.TeamML;
import lni.archi.hexa.core.model.TeamMLF;
import lni.archi.hexa.hexarest.configs.cleanArchi.usescases.team.TeamUseCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class TeamController {

    private final TeamUseCases teamUseCases;

    @Autowired
    public TeamController(TeamUseCases teamUseCases) {
        this.teamUseCases = teamUseCases;
    }

    @PostMapping("/teams")
    public ResponseEntity<TeamML> createTeam(final @RequestBody TeamDN team) throws JobException {
        try {
            TeamDN createdTeam = this.teamUseCases.getCreateTeamUE().execute(team.getName(), team.getPersons());
            TeamMLF result = new TeamMLF(createdTeam);
            return ResponseEntity.ok(result);
        } catch (JobListException e) {
            throw e;
        } catch (Exception e) {
            throw new JobException(e.getMessage(), ((JobException) e).getErrorMessage());
        }
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamMLF> getTeamById(@PathVariable String id) {
        try {
            TeamDN receivedTeam = this.teamUseCases.getGetTeamByIdUE().execute(Integer.parseInt(id));
            return ResponseEntity.ok(new TeamMLF(receivedTeam));
        } catch (JobListException e) {
            throw e;
        } catch (Exception e) {
            throw new JobException(e.getMessage(), ((JobException) e).getErrorMessage());
        }
    }

    @GetMapping("/teams")
    public ResponseEntity<List<TeamML>> getAllPersons() {
        try {
            List<TeamML> result = new ArrayList<>();

            List<TeamDN> toTransform = this.teamUseCases.getGetAllTeamUE().execute();
            for (TeamDN team : toTransform) {
                result.add(new TeamML(team));
            }
            return ResponseEntity.ok(result);
        } catch (JobListException e) {
            throw e;
        } catch (Exception e) {
            throw new JobException(e.getMessage(), ((JobException) e).getErrorMessage());
        }
    }
}
