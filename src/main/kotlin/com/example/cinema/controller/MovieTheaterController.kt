package cinema.controller

import cinema.dto.StatisticsResponseDto
import cinema.exception.IncorrectPasswordException
import cinema.model.MovieTheater
import org.springframework.web.bind.annotation.*


@RestController
class MovieTheaterController(val movieTheater: MovieTheater) {

    private final val PASSWORD = "super_secret"

    @GetMapping("/seats")
    fun getSeats(): MovieTheater {
        return movieTheater
    }

    @GetMapping("/stats")
    fun getStatsForm(@RequestParam password: String?): StatisticsResponseDto {
        val statisticsResponseDto: StatisticsResponseDto
        try {
            if (password!! == PASSWORD) {
                statisticsResponseDto = StatisticsResponseDto(
                    movieTheater.getCurrentIncome(),
                    movieTheater.getAvailableSeatsCount(),
                    movieTheater.getNumberOfPurchasedTickets()
                )
                return statisticsResponseDto
            }
            throw IncorrectPasswordException("The password is wrong!")
        } catch(e: RuntimeException) {
            throw IncorrectPasswordException("The password is wrong!")
        }
    }

}