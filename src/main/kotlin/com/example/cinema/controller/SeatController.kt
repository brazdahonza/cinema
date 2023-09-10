package cinema.controller

import cinema.dto.PurchaseRequestDto
import cinema.dto.PurchaseResponseDto
import cinema.dto.ReturnedRequestDto
import cinema.dto.ReturnedResponseDto
import cinema.exception.OutOfBoundsException
import cinema.exception.TicketAlreadyPurchasedException
import cinema.exception.TicketNotFoundException
import cinema.model.MovieTheater
import cinema.model.Seat
import cinema.model.Ticket
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class SeatController(val movieTheater: MovieTheater) {

    @PostMapping("/purchase")
    fun reserveSeat(@RequestBody purchaseRequest: PurchaseRequestDto): PurchaseResponseDto {
        val purchaseResponseDto: PurchaseResponseDto
        val rowNum = purchaseRequest.row
        val columnNum = purchaseRequest.column

        if(rowNum > movieTheater.total_rows || columnNum > movieTheater.total_columns) {
            throw OutOfBoundsException("The number of a row or a column is out of bounds!")
        }

        val seat = movieTheater.getSeat(rowNum, columnNum)
            ?: throw OutOfBoundsException("The number of a row or a column is out of bounds!")

        if(seat.reserved) {
            throw TicketAlreadyPurchasedException("The ticket has been already purchased!")
        }
        seat.reserved = true

        purchaseResponseDto = PurchaseResponseDto(seat.ticket!!.token,
            seat.ticket!!
        )

        return purchaseResponseDto
    }

    @PostMapping("/return")
    fun returnTicket(@RequestBody returnedRequestDto: ReturnedRequestDto): ReturnedResponseDto{
        val returnedResponseDto: ReturnedResponseDto
        val token = returnedRequestDto.token
        val ticket = movieTheater.getTicket(token) ?: throw TicketNotFoundException("Wrong token!")

        returnedResponseDto = ReturnedResponseDto(ticket)


        val seat = movieTheater.getSeat(ticket.row, ticket.column)
            ?: throw OutOfBoundsException("The number of a row or a column is out of bounds!")

        if(seat.reserved) {
            seat.reserved = false
            return returnedResponseDto
        } else {
            throw TicketNotFoundException("Wrong token!")
        }
    }
}