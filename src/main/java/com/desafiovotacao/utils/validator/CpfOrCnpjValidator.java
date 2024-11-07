package com.desafiovotacao.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.util.StringUtils;

public class CpfOrCnpjValidator implements ConstraintValidator<CpfOrCnpj, String> {
    @Override
    public boolean isValid( String value, ConstraintValidatorContext context )
    {
        if( StringUtils.isEmpty( value ))
        {
            return true;
        }

        return isCpf( value ) ? validateCpf( value, context ) : validateCnpj( value, context );
    }

    public boolean isCpf( String value )
    {
        return value.length() == 11;
    }

    public boolean validateCpf( String value, ConstraintValidatorContext context )
    {
        CPFValidator cpfValidator = new CPFValidator();
        cpfValidator.initialize( null );

        return cpfValidator.isValid( value, context );
    }

    public boolean validateCnpj( String value, ConstraintValidatorContext context )
    {
        CNPJValidator cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize( null );

        return cnpjValidator.isValid( value, context );
    }
}
