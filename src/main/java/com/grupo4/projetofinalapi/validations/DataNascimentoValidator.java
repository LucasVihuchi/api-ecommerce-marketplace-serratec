package com.grupo4.projetofinalapi.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

/** Classe para validação customização da data de nascimento de um usuário
 */
public class DataNascimentoValidator implements ConstraintValidator<ValidDataNascimento, LocalDate>{

	/** Método para validar uma data de nascimento fornecido, verificando se o usuário possui 18 anos ou mais e 120 anos ou menos
	 *
	 * @param dataRecebida data a ser validada
	 * @param context contexto do validador
	 * @return Verdadeiro se data nula ou usuário possuir mais 18 anos e menos de 120 anos. Caso contrário, falso
	 */
	@Override
	public boolean isValid(LocalDate dataRecebida, ConstraintValidatorContext context) {
		if(dataRecebida == null) {
			return true;
		}

		LocalDate hoje = LocalDate.now();
		
		Period diferencaEntreDatas = Period.between(dataRecebida, hoje);
		return ((diferencaEntreDatas.getYears() >= 18) && (diferencaEntreDatas.getYears() <=120) && (!diferencaEntreDatas.isNegative()));
	}
}
