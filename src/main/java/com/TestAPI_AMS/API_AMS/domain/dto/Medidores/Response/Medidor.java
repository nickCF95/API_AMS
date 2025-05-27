package com.TestAPI_AMS.API_AMS.domain.dto.Medidores.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Medidor {
    private Integer id;
    private String serial;
}
