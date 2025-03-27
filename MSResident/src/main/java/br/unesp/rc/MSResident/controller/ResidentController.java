package br.unesp.rc.MSResident.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.unesp.rc.MSResident.model.Resident;
import br.unesp.rc.MSResident.service.ResidentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Resident Controller", description = "CRUD operations for Resident")
@RestController("ResidentController")
@RequestMapping("/resident")
public class ResidentController {
    
    @Autowired
    ResidentService residentService;

    @Operation(
        summary = "Find all Residents",
        description = "Find all Residents in database and return a list of Residents"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a list of Residents",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Resident.class))
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Error.class)
            )
        )
    })
    @GetMapping(value = "/findAll", produces = "application/json")
    public ResponseEntity<?> findAll() {
        try {
            List<Resident> residents = residentService.findAll();

            return new ResponseEntity<List<Resident>>(residents, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Find resident by id",
        description = "Find resident by id specified in path and return a resident object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a resident object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Resident.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Error.class)
            )
        )
    })
    @GetMapping(value = "/findById/{id}", produces = "application/json")
    public ResponseEntity<?> findById(@PathVariable String id) {

        try {
            Resident resident = residentService.findById(id);

            return new ResponseEntity<Resident>(resident, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Save resident",
        description = "Save the resident specified in request body and return a resident object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a resident object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Resident.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Error.class)
            )
        )
    })
    @PostMapping(value = "/save", produces = "application/json")
    public ResponseEntity<?> save(@RequestBody Resident resident) {

        try {
            Resident newResident = residentService.save(resident);

            return new ResponseEntity<Resident>(newResident, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Update resident",
        description = "Update the resident specified in request body and return a resident object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a resident object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Resident.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Error.class)
            )
        )
    })
    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<?> update(@RequestBody Resident resident) {
        try {
            Resident updatedResident = residentService.update(resident);

            return new ResponseEntity<Resident>(updatedResident, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Delete resident",
        description = "Delete resident specified in path and return a resident object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Resident deleted"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Error.class)
            )
        )
    })
    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            residentService.delete(id);

            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }


}
