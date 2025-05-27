package com.TestAPI_AMS.API_AMS.domain.dto.ParsedDocuments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class DataLectura {
    private Date fecha;
    private List<Object> values; // Values corresponding to the labels
}
