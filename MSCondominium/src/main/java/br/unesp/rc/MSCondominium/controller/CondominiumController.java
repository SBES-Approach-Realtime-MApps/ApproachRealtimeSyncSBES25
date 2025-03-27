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

import br.unesp.rc.MSCondominium.model.Condominium;
import br.unesp.rc.MSCondominium.service.CondominiumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Condominium Controller", description = "CRUD operations for condominiums")
@RestController("CondominiumController")
@RequestMapping("/condominium")
public class CondominiumController {
    
    @Autowired
    CondominiumService condominiumService;

    @Operation(
        summary = "Find all condominiuns",
        description = "Find all condominiuns in database and return a list of condominium"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a list of condominiuns",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Condominium.class))
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
            List<Condominium> condominiums = condominiumService.findAll();

            return new ResponseEntity<List<Condominium>>(condominiums, HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Find condominium by id",
        description = "Find a condominiuns by id specified in path and return a condominium object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a condominium object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Condominium.class)
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
            Condominium condominium = condominiumService.findById(id);

            return new ResponseEntity<Condominium>(condominium, HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Save condominium",
        description = "save a condominiuns specified in request body and return a condominium object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a condominium object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Condominium.class)
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
    public ResponseEntity<?> save(Condominium condominium) {
        try {
            Condominium newCondominium = condominiumService.save(condominium);

            return new ResponseEntity<Condominium>(newCondominium, HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Update condominium",
        description = "Update a condominiuns specified in request body and return a condominium object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a condominium object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Condominium.class)
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
    public ResponseEntity<?> update(Condominium condominium) {
        try {
            Condominium newCondominium = condominiumService.update(condominium);

            return new ResponseEntity<Condominium>(newCondominium, HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Delete condominium",
        description = "Delete a condominiuns by id specified in path"
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
            condominiumService.delete(id);

            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

}
