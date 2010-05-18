package com.floreysoft.jmte.guts;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Util {

	@SuppressWarnings("unchecked")
	public static List arrayAsList(Object value) {
		List list = null;
		if (value instanceof int[]) {
			list = new ArrayList();
			int[] array = (int[]) value;
			for (int i : array) {
				list.add(i);
			}
		} else if (value instanceof short[]) {
			list = new ArrayList();
			short[] array = (short[]) value;
			for (short i : array) {
				list.add(i);
			}
		} else if (value instanceof char[]) {
			list = new ArrayList();
			char[] array = (char[]) value;
			for (char i : array) {
				list.add(i);
			}
		} else if (value instanceof byte[]) {
			list = new ArrayList();
			byte[] array = (byte[]) value;
			for (byte i : array) {
				list.add(i);
			}
		} else if (value instanceof long[]) {
			list = new ArrayList();
			long[] array = (long[]) value;
			for (long i : array) {
				list.add(i);
			}
		} else if (value instanceof double[]) {
			list = new ArrayList();
			double[] array = (double[]) value;
			for (double i : array) {
				list.add(i);
			}
		} else if (value instanceof float[]) {
			list = new ArrayList();
			float[] array = (float[]) value;
			for (float i : array) {
				list.add(i);
			}
		} else if (value instanceof boolean[]) {
			list = new ArrayList();
			boolean[] array = (boolean[]) value;
			for (boolean i : array) {
				list.add(i);
			}
		} else if (value.getClass().isArray()) {
			Object[] array = (Object[]) value;
			list = Arrays.asList(array);
		}
		return list;
	}

	public static String trimFront(String input) {
		int i = 0;
		while (i < input.length() && Character.isWhitespace(input.charAt(i)))
			i++;
		return input.substring(i);
	}

	@SuppressWarnings("unchecked")
	public static Object getPropertyValue(Object o, String attributeName)
			throws IntrospectionException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchFieldException {
		Object result = null;
		BeanInfo beanInfo = Introspector.getBeanInfo(o.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		// XXX this is so strange, can not call invoke on key and value for
		// Map.Entry, so we have to get this done like this:
		if (o instanceof Map.Entry) {
			Map.Entry entry = (Entry) o;
			if (attributeName.equals("key")) {
				result = entry.getKey();
			} else if (attributeName.equals("value")) {
				result = entry.getValue();
			}

		} else {

			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if (propertyDescriptor.getName().equals(attributeName)) {
					Method readMethod = propertyDescriptor.getReadMethod();
					if (readMethod != null) {
						result = readMethod.invoke(o);
						break;
					}
				}
			}
			if (result == null) {
				Field field = o.getClass().getField(attributeName);
				if (Modifier.isPublic(field.getModifiers())) {
					result = field.get(o);
				}
			}
		}
		return result;
	}

}
