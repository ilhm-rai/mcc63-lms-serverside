/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.model.dto;

import org.springframework.web.multipart.MultipartFile;
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
public class CourseData {

    private Long id;
    private String title;
    private String description;
    private Long price;
    private Long userId;
    private Long categoryId;
    private MultipartFile courseImage;
}
