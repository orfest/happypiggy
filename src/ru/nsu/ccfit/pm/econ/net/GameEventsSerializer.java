package ru.nsu.ccfit.pm.econ.net;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.events.IUGameEvent;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.net.annotations.BewareCollectionOf;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.engine.data.CompanyMessage;
import ru.nsu.ccfit.pm.econ.net.engine.data.TextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.net.engine.events.AddCashEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.BankPercentEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.BankRequestEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.BankTransactionEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.BuyOffersChangeEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.BuyRequestEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.ChatMessageEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.CompanyMessageEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.DividendPayoutEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.DividendVoteEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.DividendVotingEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.DividendVotingProposalEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.GameEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.GameSnapshotEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.ShareAllocationRequestEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.TransactionEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.TransferSharesEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.TurnEndEvent;
import ru.nsu.ccfit.pm.econ.net.engine.events.TurnStartEvent;
import ru.nsu.ccfit.pm.econ.net.engine.roles.Player;
import ru.nsu.ccfit.pm.econ.net.engine.roles.Teacher;
import ru.nsu.ccfit.pm.econ.net.engine.roles.Student;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.GameEventProto;

import com.google.gag.annotation.disclaimer.HandsOff;
import com.google.gag.annotation.disclaimer.ProbablyIllegalIn;
import com.google.gag.enumeration.Consequence;
import com.google.gag.enumeration.RegionType;

/**
 * This is a true black magic class that encapsulates all the
 * serialization/deserialization. It exposes a single static instance to the
 * outer world. Initialization is very slow due to excessive use of reflection.
 * Usage is absolutely thread safe so, a single static instance fits well. There
 * are only two instance methods - one for serialization and another one for
 * deserialization. The implementation has a number of limitations. It is able
 * to deserialize only IUGameEvent types. It is not able to handle List of Lists
 * of smth. It has a special black magic dealing with dates, so don't be
 * surprised by unexpected parse errors. The author promises to refine the code
 * and make it beautiful
 * 
 * @author orfest
 */
@HandsOff(byOrderOf = "orfest", onPainOf = Consequence.ICE_COLD_STARE)
public class GameEventsSerializer {

	/**
	 * Internal logging
	 */
	private static final Logger logger = LoggerFactory.getLogger(GameEventsSerializer.class);

	/**
	 * Available to the outside world
	 */
	private static GameEventsSerializer instance = new GameEventsSerializer();

	/**
	 * Used for something very internal. Maps common interfaces to concrete
	 * classes implementing them
	 */
	@ProbablyIllegalIn(number = 100500, region = RegionType.PARALLEL_UNIVERSES)
	private final Map<Class<?>, Class<?>> interfacesToConreteTypeMap;
	/**
	 * Dispatches incoming messages by their type. Matches ints to concrete
	 * annotated classes
	 */
	private final Map<Integer, Class<?>> intToConcreteTypeMap;

	private GameEventsSerializer() {
		intToConcreteTypeMap = new HashMap<Integer, Class<?>>();
		interfacesToConreteTypeMap = new HashMap<Class<?>, Class<?>>();

		// Initialize with all known and maintained GameEvent derived classes
		// use of some kind of annotation processor may automatize this
		try {
			add(Player.class);
			add(Student.class);
			add(Teacher.class);

			add(TextOnlyCompanyMessage.class);
			add(CompanyMessage.class);

			add(GameEvent.class);
			add(AddCashEvent.class);
			add(BankPercentEvent.class);
			add(BankRequestEvent.class);
			add(BankTransactionEvent.class);
			add(BuyOffersChangeEvent.class);
			add(BuyRequestEvent.class);
			add(ChatMessageEvent.class);
			add(CompanyMessageEvent.class);
			add(DividendPayoutEvent.class);
			add(DividendVoteEvent.class);
			add(DividendVotingEvent.class);
			add(DividendVotingProposalEvent.class);
			add(GameSnapshotEvent.class);
			add(ShareAllocationRequestEvent.class);
			add(TransactionEvent.class);
			add(TransferSharesEvent.class);
			add(TurnEndEvent.class);
			add(TurnStartEvent.class);
			logger.info("Construction successful");

		} catch (Exception e) {
			// if this component cannot be started nothing worth living any
			// longer
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * 
	 * @return reference to the static instance
	 */
	public static GameEventsSerializer getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param realWorldObject
	 *            - object that will be serialized to a protobuffer message
	 * @return a protobuffer message object that is to be written to output
	 *         stream or null is something went wrong
	 */
	public GameEventProto serializeGameEvent(IUGameEvent realWorldObject) {
		try {
			Object protoObject = serializeToProto(realWorldObject);
			return (GameEventProto) protoObject;
		} catch (Exception e) {
			// serialization unsuccessful
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param gameEventProtos
	 *            - a received message
	 * @return IUGameEvent that was encoded in the message or null is something
	 *         went wrong
	 */
	public IUGameEvent deserializeProto(GameEventProto gameEventProtos) {
		if (gameEventProtos == null)
			return null;
		try {
			return (IUGameEvent) deserializeObject(gameEventProtos, null);
		} catch (Exception e) {
			// something went wrong
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Very internal method to initialize maps
	 * 
	 * @param clazz
	 *            to be processed and analyzed
	 * @throws Exception
	 *             - a lot of different exceptions can be thrown. But if
	 *             everything is ok, none should
	 */
	private void add(Class<?> clazz) throws Exception {

		// class already processed
		if (intToConcreteTypeMap.containsValue(clazz)) {
			return;
		}

		// a primitive type (double, String, Long)
		if (clazz.getInterfaces() == null || clazz.getInterfaces().length == 0) {
			return;
		}
		// rely that IUSomething interface is the first interface in the list
		Class<?> interf = clazz.getInterfaces()[0];

		if (getProtoClass(clazz) != null) {
			int cnt = intToConcreteTypeMap.size();
			intToConcreteTypeMap.put(cnt, clazz);
			interfacesToConreteTypeMap.put(interf, clazz);
		}

		// construct a dummy object to be able to further in depth
		Object dummyObject = constructDefault(clazz);
		List<Field> fields = getSerializableFields(clazz);

		for (Field field : fields) {

			Class<?> fieldClazz = null;
			Object fieldValue = null;

			if (!field.isAnnotationPresent(BewareCollectionOf.class)) {

				// not a collection
				fieldClazz = field.getType();
				fieldValue = getFieldValue(field, false, dummyObject, false);

			} else {

				// collections are treated separately
				fieldClazz = field.getAnnotation(BewareCollectionOf.class).value();
				fieldValue = constructDefault(fieldClazz);

			}

			// field is of a an interesting type
			if (fieldValue != null && getProtoClass(fieldValue.getClass()) != null) {
				add(fieldValue.getClass());
			}
		}
	}

	/**
	 * 
	 * @param clazz
	 *            to be analyzed
	 * @return the most relevant Message class or null if none found
	 */
	private Class<?> getProtoClass(Class<?> clazz) {
		for (; clazz != null; clazz = clazz.getSuperclass()) {
			if (clazz.isAnnotationPresent(ProtoClass.class))
				return clazz.getAnnotation(ProtoClass.class).value();
		}
		return null;
	}

	/**
	 * Recursive function turning everything into protos
	 * 
	 * @param realWorldObject
	 *            - object to be converted to Message
	 * @return an object that is resulting object or can be set as a value of a
	 *         field of larger protos, or null
	 * @throws Exception
	 *             , if any, serialization failed
	 */
	public Object serializeToProto(Object realWorldObject) throws Exception {

		if (realWorldObject == null) {
			return null;
		}

		Class<?> convertFrom = realWorldObject.getClass();
		Class<?> protoType = lookupProtoClass(convertFrom);

		if (protoType == null) {
			return realWorldObject;
		}

		Object builder = getBuilder(protoType);

		// constructing a dummy object of required type to traverse its field
		// and annotations
		Class<?> dummyObjectType = lookupConcreteClass(realWorldObject.getClass());
		Object dummyObject = constructDefault(dummyObjectType);

		List<Field> fields = getSerializableFields(dummyObject.getClass());
		for (Field field : fields) {

			Object fieldValue = getFieldValue(field, false, realWorldObject, false);
			if (fieldValue != null && fieldValue.getClass().isEnum()) {

				Enum<?>[] z = (Enum<?>[]) fieldValue.getClass().getEnumConstants();
				int ind = -1;
				for (ind = 0; ind < z.length; ind++)
					if (z[ind].equals(fieldValue)) {
						break;
					}
				setFieldValue(field, false, int.class, builder, ind);

			} else if (!field.isAnnotationPresent(BewareCollectionOf.class)) {

				Class<?> fieldType = field.getType();
				Class<?> convertTo = lookupProtoClass(fieldType);

				// khm, to be fixed, dirty way to replace Date with its String
				// representation
				if (fieldType.equals(Date.class)) {
					if (fieldValue != null) {
						Date date = (Date) fieldValue;
						String stringDate = DateFormat.getDateTimeInstance(
								DateFormat.LONG, DateFormat.LONG, Locale.ROOT).format(date);
						fieldValue = stringDate;
					}
					fieldType = String.class;
					convertTo = fieldType;
				}

				Object valueToSet = serializeToProto(fieldValue);

				if (valueToSet != null) {
					setFieldValue(field, false, convertTo, builder, valueToSet);
				}

			} else {

				List<Object> resultList = new LinkedList<Object>();
				if (fieldValue == null) {
					resultList = null;
				} else {
					List<?> list = new LinkedList<Object>((Collection<?>) fieldValue);
					for (Object element : list) {
						Object valueToSet = serializeToProto(element);
						resultList.add(valueToSet);
					}
				}
				if (resultList != null){
					setFieldValue(field, true, Iterable.class, builder, resultList);
				}

			}
		}

		// is required only by GameEvent derivate classes
		setVirtualTypeIfRequired(realWorldObject, builder);

		// use builder.build() to get the resulting object
		Method buildMethod = builder.getClass().getMethod("build", (Class<?>[]) null);
		return buildMethod.invoke(builder, (Object[]) null);
	}

	/**
	 * During GameEvents serialization adds type field
	 * 
	 * @param realWorldObject
	 * @param builder
	 * @throws Exception
	 */
	private void setVirtualTypeIfRequired(Object realWorldObject, Object builder) throws Exception {
		boolean isGameEvent = false;
		// checking that this object is anyhow related to IUGameEvent class
		for (Class<?> i : getAllInterfaces(realWorldObject.getClass())) {
			if (i.equals(IUGameEvent.class) || i.equals(IUPlayer.class) || i.equals(IUTextOnlyCompanyMessage.class)) {
				isGameEvent = true;
				break;
			}
		}

		if (!isGameEvent) {
			return;
		}

		// getting proper message type
		int type = -1;
		for (Class<?> i : getAllInterfaces(realWorldObject.getClass())) {
			type = getTypeForGameEventInterface(i);
			if (type >= 0) {
				break;
			}
		}

		if (type < 0) {
			// not found
			return;
		}

		// set type value
		Method method = builder.getClass().getMethod("setVirtualType", new Class<?>[] { int.class });
		method.invoke(builder, new Object[] { type });
	}

	// definitely buggy
	// searches for a message type that represents the message the best
	private int getTypeForGameEventInterface(Class<?> i) {
		if (!interfacesToConreteTypeMap.containsKey(i)) {
			return -1;
		}
		Class<?> concrete = interfacesToConreteTypeMap.get(i);
		for (Entry<Integer, Class<?>> entry : intToConcreteTypeMap.entrySet()) {
			if (entry.getValue().equals(concrete)) {
				return entry.getKey();
			}
		}
		return -1;
	}

	/**
	 * Recursive deserializing function
	 * 
	 * @param protos
	 * @return
	 * @throws Exception
	 */
	public Object deserializeObject(Object protos, Class<?> objectType) throws Exception {
		if (protos == null) {
			return null;
		}

		Class<?> virtualType = getVirtualType(protos);
		if (virtualType != null) {
			objectType = virtualType;
		}

		if (objectType == null)
			return protos;

		Object deserializedObject = constructDefault(objectType);

		List<Field> fields = getSerializableFields(objectType);
		for (Field field : fields) {

			boolean isCollection = field.isAnnotationPresent(BewareCollectionOf.class);

			Object subProto = getFieldValue(field, isCollection, protos, true);

			if (field.getType().isEnum()) {

				Integer ind = (Integer) subProto;
				Enum<?>[] z = (Enum<?>[]) field.getType().getEnumConstants();
				subProto = z[ind];

			} else if (!isCollection) {

				Class<?> fieldType = field.getType();

				// dirty hack symmetric to mentioned somewhere above
				if (fieldType.equals(Date.class)) {
					subProto = DateFormat.getDateTimeInstance(
							DateFormat.LONG, DateFormat.LONG, Locale.ROOT).parse((String) subProto);
				}

				Class<?> concreteClass = lookupConcreteClass(fieldType);
				if (concreteClass != null) {
					subProto = deserializeObject(subProto, concreteClass);
				}

			} else {

				Class<?> elementType = field.getAnnotation(BewareCollectionOf.class).value();
				List<Object> list = new LinkedList<Object>();
				List<?> subProtoList = (List<?>) subProto;

				Class<?> concreteClass = lookupConcreteClass(elementType);

				for (Object subProtoListElement : subProtoList) {

					Object deserializedListElement = subProtoListElement;
					if (concreteClass != null) {
						deserializedListElement = deserializeObject(subProtoListElement, concreteClass);
					}
					list.add(deserializedListElement);
				}
				subProto = list;
			}

			setFieldValue(field, false, null, deserializedObject, subProto);
		}

		return deserializedObject;

	}

	private Class<?> getVirtualType(Object protos) throws Exception {
		Method[] methods = protos.getClass().getDeclaredMethods();
		for (Method m : methods)
			if (m.getName().equals("getVirtualType"))
				return lookupVirtualType(protos, m);
		return null;
	}

	private Class<?> lookupVirtualType(Object protos, Method getter) throws Exception {
		Integer virtualTypeInt = (Integer) getter.invoke(protos, (Object[]) null);
		if (!intToConcreteTypeMap.containsKey(virtualTypeInt)) {
			throw new IllegalArgumentException("Incorrect virtual type");
		}
		return intToConcreteTypeMap.get(virtualTypeInt);
	}

	/**
	 * 
	 * @param clazz
	 *            - something like IUScenario, IUGameTime, IUGameSnapshotEvent
	 * @return something like Scenario, GameTime, GameSnapshotEvent
	 */
	private Class<?> lookupConcreteClass(Class<?> clazz) {
		for (Class<?> interf : getAllInterfaces(clazz)) {
			if (interfacesToConreteTypeMap.containsKey(interf)) {
				return interfacesToConreteTypeMap.get(interf);
			}
		}
		return null;
	}

	/**
	 * searches for a proto class that fits the best
	 * 
	 * @param clazz
	 * @return proto class or null if none fits
	 */
	private Class<?> lookupProtoClass(Class<?> clazz) {
		for (Class<?> interf : getAllInterfaces(clazz)) {
			if (interfacesToConreteTypeMap.containsKey(interf)) {
				Class<?> concrete = interfacesToConreteTypeMap.get(interf);
				if (!concrete.isAnnotationPresent(ProtoClass.class)) {
					continue;
				}
				return concrete.getAnnotation(ProtoClass.class).value();
			}
		}
		return null;
	}

	/**
	 * gets appropriate settor-function, uses it to set objects field
	 * 
	 * @param field
	 * @param b
	 * @param convertTo
	 * @param builder
	 * @param valueToSet
	 * @throws Exception
	 *             should not
	 */
	private void setFieldValue(Field field, boolean b, Class<?> convertTo, Object builder, Object valueToSet)
			throws Exception {

		String setterName = field.getAnnotation(SerializeThis.class).set();
		if (setterName.isEmpty()) {
			setterName = getSetterName(field.getName(), b);
		}

		if (convertTo == null) {
			convertTo = field.getType();
		}
		Method setter = builder.getClass().getMethod(setterName, new Class<?>[] { convertTo });
		setter.invoke(builder, valueToSet);
	}

	/**
	 * Gets field value through a corresponding getter
	 * 
	 * @param field
	 * @param isCollection
	 * @param object
	 * @param alwaysGenerate
	 * @return
	 * @throws Exception
	 *             should not
	 */
	private Object getFieldValue(Field field, boolean isCollection, Object object, boolean alwaysGenerate)
			throws Exception {

		String getterName = field.getAnnotation(SerializeThis.class).get();
		if (getterName.isEmpty() || alwaysGenerate) {
			getterName = getGetterName(field.getName(), isCollection);
		}
		Method getter = object.getClass().getMethod(getterName, (Class<?>[]) null);

		return getter.invoke(object, (Object[]) null);
	}

	/**
	 * Constructs an object of given type using a default constructor
	 * 
	 * @param objectType
	 * @return constructed object
	 * @throws Exception
	 */
	private Object constructDefault(Class<?> objectType) throws Exception {
		try {
			Constructor<?> constructor = objectType.getConstructor((Class<?>[]) null);
			return constructor.newInstance((Object[]) null);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * recursively collects all interfaces into a single list
	 * 
	 * @param type
	 * @return given type if it is interface and all declared interfaces and all
	 *         interfaces of all ancestor types
	 */
	private Collection<Class<?>> getAllInterfaces(Class<?> clazz) {
		LinkedList<Class<?>> result = new LinkedList<Class<?>>();
		if (clazz.isInterface()) {
			result.add(clazz);
		}
		for (Class<?> type = clazz; type != null; type = type.getSuperclass()) {
			result.addAll(Arrays.asList(type.getInterfaces()));
		}
		for (int i = 0; i < result.size(); i++) {
			Class<?> interf = result.get(i);
			Class<?>[] iall = interf.getInterfaces();
			for (Class<?> c : iall) {
				boolean ok = true;
				for (Class<?> z : result) {
					if (z.equals(c)) {
						ok = false;
						break;
					}
				}
				if (!ok)
					continue;
				result.add(c);
			}
		}
		return result;
	}

	/**
	 * Chooses all fields of given class and it's ancestors
	 * 
	 * @param clazz
	 * @return
	 */
	private List<Field> getSerializableFields(Class<? extends Object> clazz) {
		List<Field> fields = new LinkedList<Field>();
		for (Class<?> type = clazz; type != null; type = type.getSuperclass()) {
			for (Field f : type.getDeclaredFields())
				if (f.isAnnotationPresent(SerializeThis.class))
					fields.add(f);
		}
		return fields;
	}

	/**
	 * gets builder for proto objects
	 * 
	 * @param protoType
	 * @return
	 * @throws Exception
	 */
	private Object getBuilder(Class<?> protoType) throws Exception {
		Class<?>[] enclosedTypes = protoType.getDeclaredClasses();
		if (enclosedTypes.length != 1) {
			throw new IllegalArgumentException("Problems finding Builder class for " + protoType.getCanonicalName());
		}
		Method newBuilderMethod = protoType.getDeclaredMethod("newBuilder", (Class<?>[]) null);
		return newBuilderMethod.invoke(null, (Object[]) null);
	}

	/**
	 * Calculates getter name basing on field name
	 * 
	 * @param name
	 * @param isCollection
	 *            - if is set, "List" is appended to the name of the generated
	 *            name
	 * @return
	 */
	private String getSetterName(String name, boolean isCollection) {
		StringBuilder builder = new StringBuilder();
		if (!isCollection)
			builder.append("set");
		else
			builder.append("addAll");
		builder.append(Character.toUpperCase(name.charAt(0)));
		builder.append(name.substring(1));
		return builder.toString();
	}

	/**
	 * calculates setter name basing on field name
	 * 
	 * @param name
	 * @param isCollection
	 *            prepends "addAll" if set to true
	 * @return
	 */
	private String getGetterName(String name, boolean isCollection) {
		StringBuilder builder = new StringBuilder();
		builder.append("get");
		builder.append(Character.toUpperCase(name.charAt(0)));
		builder.append(name.substring(1));

		if (isCollection) {
			builder.append("List");
		}
		return builder.toString();
	}

}
