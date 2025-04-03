package br.unesp.rc.MSCondominium.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unesp.rc.CondominiumModel.model.Unit;
import br.unesp.rc.CondominiumModel.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Unit Controller", description = "CRUD operations for Unit")
@RestController("UnitnewUnitController")
@RequestMapping("/unit")
public class UnitController {
    
    @Autowired
    UnitService unitService;

    @Operation(
        summary = "Find all units",
        description = "Find all units in database and return a list of units"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a list of units",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Unit.class))
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
            List<Unit> units = unitService.findAll();
            return new ResponseEntity<List<Unit>>(units, HttpStatus.OK);
        } catch (Exception e) {            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Find unit by id",
        description = "Find a unit by id specified in path and return a unit object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a unit object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Unit.class)
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
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Unit unit = unitService.findById(id);
            return new ResponseEntity<Unit>(unit, HttpStatus.OK);
        } catch (Exception e) {            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Save unit",
        description = "Save a unit specified in request body and return a unit object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a unit object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Unit.class)
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
    public ResponseEntity<?> save(Unit unit) {
        try {            Unit newUnit = unitService.save(unit);
            return new ResponseEntity<Unit>(newUnit, HttpStatus.OK);
        } catch (Exception e) {            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Update unit",
        description = "Update a unit specified in request body and return a unit object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a unit object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Unit.class)
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
    public ResponseEntity<?> update(Unit unit) {
        try {            Unit newUnit = unitService.update(unit);
            return new ResponseEntity<Unit>(newUnit, HttpStatus.OK);
        } catch (Exception e) {            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Delete unit",
        description = "Delete a unit by id specified in path"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns OK"
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
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            unitService.delete(id);

            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }
}
