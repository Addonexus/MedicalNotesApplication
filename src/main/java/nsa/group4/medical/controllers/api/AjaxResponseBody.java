package nsa.group4.medical.controllers.api;

import lombok.Data;

import java.util.List;
@Data
public class AjaxResponseBody {
    String msg;
    List<ReturnedData> result;
}
