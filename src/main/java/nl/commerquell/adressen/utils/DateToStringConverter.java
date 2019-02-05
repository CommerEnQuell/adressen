package nl.commerquell.adressen.utils;

import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DateToStringConverter implements AttributeConverter<String, Date> {
	private static final String defaultDateFormat = "DD.MM.YYYY";

	@Override
	public Date convertToDatabaseColumn(String attribute) {
		return Utils.formatDate(attribute, defaultDateFormat);
	}

	@Override
	public String convertToEntityAttribute(Date dbData) {
		return Utils.dateToString(dbData, defaultDateFormat);
	}

}
