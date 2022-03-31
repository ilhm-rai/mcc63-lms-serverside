/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.model.Dto;

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
public class EnrollmentDto {

    private Long id;
    private LocalDate enroll_date;
    private LocalDate expire_date;
    private Long userId;
    private Long courseId;
}