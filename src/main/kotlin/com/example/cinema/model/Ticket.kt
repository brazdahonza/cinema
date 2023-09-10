package cinema.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*

class Ticket(
    val row: Int,
    val column: Int,
    val price: Int,
    @JsonIgnore
    val token: UUID
)