package fr.drakogia.api.nms;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflection {

	public static Object invoke(Class<?> paramClass, String paramString, Class<?>[] paramArrayOfClass, Object paramObject, Object[] paramArrayOfObject) {
		try {
			Method localMethod = paramClass.getDeclaredMethod(paramString, paramArrayOfClass);
			localMethod.setAccessible(true);
			return localMethod.invoke(paramObject, paramArrayOfObject);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}

	public static void setValue(Object paramObject1, String paramString, Object paramObject2) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field localField = paramObject1.getClass().getDeclaredField(paramString);
		localField.setAccessible(true);
		localField.set(paramObject1, paramObject2);
	}

	public static Method getMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) throws NoSuchMethodException, SecurityException {
		Method localMethod = paramClass.getDeclaredMethod(paramString, paramVarArgs);
		localMethod.setAccessible(true);
		return localMethod;
	}

	public static Object getFieldObject(Class<?> paramClass, String paramString, Object paramObject) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field localField = paramClass.getDeclaredField(paramString);
		localField.setAccessible(true);
		return localField.get(paramObject);
	}

	public static Field getField(Class<?> paramClass, String paramString) throws NoSuchFieldException, SecurityException {
		Field localField = paramClass.getDeclaredField(paramString);
		localField.setAccessible(true);
		return localField;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Field field, Object object) {
		try {
			return (T) field.get(object);
		} catch (Exception var2_2) {
			var2_2.printStackTrace();
			return null;
		}
	}

}
