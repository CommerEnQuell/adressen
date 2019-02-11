package nl.commerquell.adressen.utils;

import java.util.logging.Logger;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class IntToBooleanConverter implements AttributeConverter<Boolean, Integer> {
	private static Logger logger = Logger.getLogger(IntToBooleanConverter.class.getName());

	@Override
	public Integer convertToDatabaseColumn(Boolean attribute) {
		if (attribute == null) {
			return null;
		}
		int retval = (attribute.booleanValue() ? 1 : 0);
		return Integer.valueOf(retval);
	}

	@Override
	public Boolean convertToEntityAttribute(Integer dbData) {
		if (dbData == null) {
			return null;
		}
		
		int inval = dbData.intValue();
		if (inval < 0 || inval > 1) {
			logger.warning("Attempt to convert int value " + inval + " to boolean returns <null>");
			return null;
		}
		return Boolean.valueOf(inval == 1);
	}

}
