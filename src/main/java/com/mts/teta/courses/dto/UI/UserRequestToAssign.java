package com.mts.teta.courses.dto.UI;

import javax.validation.constraints.NotBlank;

public class UserRequestToAssign {
    @NotBlank
    private Long courseId;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
