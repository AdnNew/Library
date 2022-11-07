package com.library.book.model;

import com.netflix.discovery.converters.Auto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
public class Book {

    @MongoId
    private String bookId;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotNull
    private Long publicationYear;

    @NotNull
    @Min(1)
    @Max(5)
    private Double rating;

    @NotNull
    @Min(0)
    private Integer quantityInStock;

    List<Borrower> borrowerList = new ArrayList<>();

}
