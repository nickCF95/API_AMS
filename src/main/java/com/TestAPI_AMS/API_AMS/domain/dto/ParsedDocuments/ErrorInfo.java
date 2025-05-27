package com.TestAPI_AMS.API_AMS.domain.dto.ParsedDocuments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ErrorInfo {
    private int codigo;
    private String string;
}
