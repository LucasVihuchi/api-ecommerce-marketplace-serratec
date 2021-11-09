package com.grupo4.projetofinalapi.validations;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DataNascimentoValidator implements ConstraintValidator<ValidDataNascimento, LocalDate>{

	@Override
	public boolean isValid(LocalDate dataRecebida, ConstraintValidatorContext context) {
		LocalDate hoje = LocalDate.now();
		
		Period diferencaEntreDatas = Period.between(dataRecebida, hoje);
		return ((diferencaEntreDatas.getYears() >= 18) && (diferencaEntreDatas.getYears() <=120) && (!diferencaEntreDatas.isNegative()));
		
	}
	
}
