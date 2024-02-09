package ecomarkets.domain.core.product.stock;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Embeddable
public record ValidityPeriod(@NotNull LocalDateTime start, @NotNull LocalDateTime end) {

    public ValidityPeriod{
        if(!end.isAfter(start)){
            throw new IllegalStateException("end date should be after start date.");
        }
    }

    public static ValidityPeriod of(LocalDateTime start, LocalDateTime end){
        return new ValidityPeriod(start, end);
    }
}
