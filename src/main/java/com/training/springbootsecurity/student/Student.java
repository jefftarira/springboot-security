package com.training.springbootsecurity.student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
  private final Long studentId;
  private final String studentName;

  public Student(Long studentId, String studentName) {
    this.studentId = studentId;
    this.studentName = studentName;
  }
}