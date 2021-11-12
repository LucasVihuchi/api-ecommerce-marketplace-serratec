package com.grupo4.projetofinalapi.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DataNascimentoValidator.class)
public @interface ValidDataNascimento {
	String message() default "O cliente não pode ter menos de 18 anos ou mais de 120 anos.";
    Class<?>[] groups() default {};
    Class<?extends Payload> [] payload() default {};
}
