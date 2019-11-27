package nsa.group4.medical.controllers.api;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReturnedData {
    @NotNull
    private String tag;
}
