package com.testehan.hibernate.basics.converter1;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/*
With autoApply enabled, any MonetaryAmount property in your domain model, be it of an entity or an embeddable class,
without further mapping will now be handled by this converter automatically
 */
@Converter(autoApply = true)
public class MonetaryAmountConverter  implements AttributeConverter<MonetaryAmount, String> {
    @Override
    public String convertToDatabaseColumn(MonetaryAmount monetaryAmount) {
        return monetaryAmount.toString();
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(String s) {
        return MonetaryAmount.fromString(s);
    }
}
