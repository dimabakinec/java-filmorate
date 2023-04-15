package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@SuppressWarnings("checkstyle:WhitespaceAround")
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"id"}, callSuper = false)
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User extends AbstractModel{

    private long  id;
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
}