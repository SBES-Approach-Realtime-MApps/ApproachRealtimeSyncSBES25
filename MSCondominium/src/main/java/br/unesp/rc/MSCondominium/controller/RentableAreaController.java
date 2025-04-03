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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unesp.rc.CondominiumModel.model.RentableArea;
import br.unesp.rc.CondominiumModel.service.RentableAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Rentable Area Controller", description = "CRUD operation for Rentable Area")
@RestController("RentableAreaController")
@RequestMapping("/rentableArea")
public class RentableAreaController {
    
    @Autowired
    RentableAreaService rentableAreaService;

    @Operation(
        summary = "Find all rentable areas",
        description = "Find all rentable areas in database and return a list of rentable areas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a list of rentable areas",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = RentableArea.class))
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
            List<RentableArea> rentableAreas = rentableAreaService.findAll();
            return new ResponseEntity<List<RentableArea>>(rentableAreas, HttpStatus.OK);
        } catch (Exception e) {            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Find rentable area by id",
        description = "Find a rentable area by id specified in path and return a rentable area object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a rentable area object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RentableArea.class)
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
            RentableArea rentableArea = rentableAreaService.findById(id);
            return new ResponseEntity<RentableArea>(rentableArea, HttpStatus.OK);
        } catch (Exception e) {            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Save rentable area",
        description = "Save a rentable area specified in request body and return a rentable area object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a rentable area object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RentableArea.class)
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
    public ResponseEntity<?> save(@RequestBody RentableArea rentableArea) {
        try {            RentableArea newRentableArea = rentableAreaService.save(rentableArea);
            return new ResponseEntity<RentableArea>(newRentableArea, HttpStatus.OK);
        } catch (Exception e) {            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Update rentable area",
        description = "Update a rentable area specified in request body and return a rentable area object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a rentable area object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RentableArea.class)
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
    public ResponseEntity<?> update(@RequestBody RentableArea rentableArea) {
        try {            
            RentableArea newRentableArea = rentableAreaService.update(rentableArea);
            return new ResponseEntity<RentableArea>(newRentableArea, HttpStatus.OK);
        } catch (Exception e) {            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Delete rentable area",
        description = "Delete a rentable area by id specified in path"
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
            rentableAreaService.delete(id);

            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }
}
