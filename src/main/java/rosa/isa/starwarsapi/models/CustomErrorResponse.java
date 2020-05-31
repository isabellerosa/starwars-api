package rosa.isa.starwarsapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CustomErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private Integer errorCode;
    private String message;

    public CustomErrorResponse(Integer errorCode, String message) {
        this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
        this.errorCode = errorCode;
        this.message = message;
    }
}
