package mdr.ap.patterns.singleton;

import static mdr.ap.util.TestLogger.log;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.function.Consumer;

import mdr.ap.util.NotNull;
import org.junit.jupiter.api.Test;

/**
 *  Tests of the singletons classes
 *  @author mross
 */
public class SingletonTests {

    
    //
    // Public test methods
    //

    /**
     *  Ensures the same instance is returned by the singleton's "factory" method and
     *  a subsequent "re-serialization" of that object
     *  @throws Exception unhandled exception
     */
    @Test
    public void testSameInstance() throws Exception {

        final Consumer<Class<?>> testSameInstanceConsumer = (
            fc -> {
                try {
                    testSameInstance(fc);
                } catch (final Exception e) {
                    throw new RuntimeException(e);
                }
            }
        );

        runTestSubCases(
            testSameInstanceConsumer, 
            DoubleLockingLazySingleton.class,
            SerializableLazySingleton.class, 
            EnumSingleton.class
        );

    }

    /**
     *  Ensures the same instance is returned by a call to the singleton's "factory"
     *  method and a subsequent "deserialization" of that (or any) object.
     *  @throws Exception unhandled exception
     */
    @Test
    public void testSerializableSingletons() throws Exception {

        final Consumer<Class<?>> testSerializableSingletonConsumer = (
            fc -> {
                try {
                    testSerializableSingleton(fc);
                } catch (final Exception e) {
                    throw new RuntimeException(e);
                }
            }
        );

        runTestSubCases(
            testSerializableSingletonConsumer, 
            SerializableLazySingleton.class
        );

    }

    //
    // Private supporting class members
    //

    /**
     *  Ensures the same instance is returned by a call to the singleton's "factory"
     *  method and a subsequent "deserialization" of that (or any) object.
     *  @param factoryClass class implementing factory of the singleton
     *  @throws Exception unhandled exception
     */
    private void testSerializableSingleton(
        @NotNull final Class<?> factoryClass
    ) throws Exception, IOException, ClassNotFoundException {

        final Object firstInstance = getInstance(factoryClass);
        assertTrue(firstInstance instanceof Serializable);

        final ByteArrayOutputStream firstInstanceBytes = new ByteArrayOutputStream();
        final ObjectOutput firstInstanceOutputStream = new ObjectOutputStream(firstInstanceBytes);
        firstInstanceOutputStream.writeObject(firstInstance);
        firstInstanceOutputStream.close();

        // deserialize from file to object
        final ByteArrayInputStream secondInstanceBytes = new ByteArrayInputStream(firstInstanceBytes.toByteArray());
        final ObjectInput secondInstanceInputStream = new ObjectInputStream(secondInstanceBytes);
        final Object secondInstance = secondInstanceInputStream.readObject();
        secondInstanceInputStream.close();

        assertEquals(firstInstance.hashCode(), secondInstance.hashCode());
    }

    private void testSameInstance(@NotNull final Class<?> factoryClass) throws Exception {
        final Object firstInstance = getInstance(factoryClass);
        assertNotNull(firstInstance);
        assertEquals(firstInstance, getInstance(factoryClass));
    }

    private <SC> void runTestSubCases(
        @NotNull final Consumer<SC> inTestRunner,
        @NotNull final SC... inTestSubCases
    ) {
        final String testMethodName = "testMethod"; // TODO get from JUnit5
        for (final SC testSubCase : inTestSubCases) {
            log(String.format("%s(%s)", testMethodName, testSubCase));
            inTestRunner.accept(testSubCase);
        }
    }

    @NotNull
    private static <T> T getInstance(@NotNull final Class<T> inSingletonFactoryClass) throws Exception {
        try {
            final Method factoryMethod = inSingletonFactoryClass.getMethod("getInstance", new Class[0]);
            final T instance = (T) factoryMethod.invoke(null, new Object[0]);
            return instance;
        } catch (final NoSuchMethodException | SecurityException e) {
            throw new Exception(e);
        }
    }

}
