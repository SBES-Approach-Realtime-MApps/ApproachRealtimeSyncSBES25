package br.unesp.rc.MSReservation.controller;

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

import br.unesp.rc.ReservationModel.model.Reservation;
import br.unesp.rc.ReservationModel.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reservation Controller", description = "CRUD operations for reservations")
@RestController("ReservationController")
@RequestMapping("/reservation")
public class ReservationController {
    
    @Autowired
    ReservationService reservationService;

    @Operation(
        summary = "Find all Reservations",
        description = "Find all Reservations in database and return a list of Reservations"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a list of Reservations",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Reservation.class))
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
    public ResponseEntity<?> findAll()  {

        try {
            List<Reservation> reservations = reservationService.findAll();

            return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Find Reservations by resident id",
        description = "Find all Reservations by resident id specified in path and return a list of Reservations"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a list of Reservations",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Reservation.class))
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
    @GetMapping(value = "/findByResident/{idResident}", produces = "application/json")
    public ResponseEntity<?> findByResident(@PathVariable String idResident) {
       
        try {
            List<Reservation> reservations = reservationService.findByResident(idResident);

            return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Find a Reservation by ID",
        description = "Find a reservation by id specified in path and return a reservation object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a reservation object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Reservation.class)
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
            Reservation reservation = reservationService.findById(id);

            return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Save reservation",
        description = "Save a reservation specified in request body and return a reservation object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a reservation object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Reservation.class)
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
    public ResponseEntity<?> save(@RequestBody Reservation reservation) {
        
        try {
            Reservation newReservation = reservationService.save(reservation);

            return new ResponseEntity<Reservation>(newReservation, HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Update reservation",
        description = "Update a reservation specified in request body and return a reservation object"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Returns a reservation object",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Reservation.class)
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
    public ResponseEntity<?> update(@RequestBody Reservation reservation) {
        
        try {
            Reservation newReservation = reservationService.update(reservation);

            return new ResponseEntity<Reservation>(newReservation, HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "Delete reservation",
        description = "Delete a reservation by id specified in path"
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
            reservationService.delete(id);

            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<Error>(new Error(e), HttpStatus.BAD_REQUEST);
        }
    }
}
