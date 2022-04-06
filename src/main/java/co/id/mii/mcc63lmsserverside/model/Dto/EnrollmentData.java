/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.model.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Agung
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentData {

    private Long id;
    private LocalDate enrollDate;
    private LocalDate expireDate;
    private Long userId;
    private Long courseId;
}
