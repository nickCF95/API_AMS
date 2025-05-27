package com.TestAPI_AMS.API_AMS.domain.dto.ParsedDocuments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ParsedLecturasDocument implements ParsedDocument {
    private Date fechahorainicio;
    private Integer id;
    private List<String> labels; // List of column headers
    private List<DataLectura> lecturas; // List of rows (data)
}
