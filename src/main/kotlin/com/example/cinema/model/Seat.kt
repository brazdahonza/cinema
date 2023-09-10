package cinema.model

import com.fasterxml.jackson.annotation.JsonIgnore

class Seat(val row: Int,
           val column: Int,
           val price: Int,
           @JsonIgnore
           var reserved: Boolean = false,
           @JsonIgnore
           var ticket: Ticket? = null
    )
