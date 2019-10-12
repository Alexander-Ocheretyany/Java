import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;

public class Reflection {
	
	public static void main(String... args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			while(reader.ready()) {
				String str = reader.readLine();
				reflect(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void reflect(String type) {
		try {
			
			Class<?> objectClass = Class.forName(type); // Get an object class
			
			System.out.println(objectClass.getName()); // Print name of the object
			
			// Print type
			if(objectClass.isArray()) System.out.println("array"); 
			else if(objectClass.isAnnotation()) System.out.println("annotation");
			else if(objectClass.isInterface()) System.out.println("interface");
			else if(objectClass.isEnum()) System.out.println("enum");
			else if(objectClass.isPrimitive()) System.out.println("primitive");
			else if(objectClass.isAnonymousClass()) System.out.println("anonymous class");
			else System.out.println("class");
			// ----------
			
			// Print generic
			TypeVariable<?>[] vars = objectClass.getTypeParameters();
			if(vars.length == 0) {
				System.out.println("Generic: no");
			} else {
				System.out.print("Generic: yes, Variables:");
				for(int i = 0; i < vars.length; ++i) {
					System.out.print(" " + vars[i]);
				}
				System.out.println();
			}
			// -------------
			
			// Print superclass
			Class<?> superClass = objectClass.getSuperclass();
			if(superClass == null) System.out.println("null");
			else System.out.println(superClass.getName());
			// ----------------
			
			// Print interfaces
			Class<?> interfaces[] = objectClass.getInterfaces();
			System.out.println(interfaces.length);
			for(int i = 0; i < interfaces.length; ++i) {
				System.out.println(interfaces[i].getName());
			}
			// ----------------	
			
			// Print info about methods
			Method[] methods = objectClass.getMethods();
			int counter = 0;
			for(int i = 0; i < methods.length; ++i) {
				if(Modifier.isPublic(methods[i].getModifiers())) ++counter;
			}
			System.out.println(counter);
			counter = 0;
			for(int i = 0; i < methods.length; ++i) {
				if(Modifier.isStatic(methods[i].getModifiers())) ++counter;
			}
			System.out.println(counter);
			// ------------------------
			
			// Print info about inner classes
			Class<?> innerClasses[] = objectClass.getClasses();
			System.out.println(innerClasses.length); // Print number of inner classes
			for(int i = 0; i < innerClasses.length; ++i) {
				System.out.println(innerClasses[i].getName());
			}
			// ------------------------------			
		} catch (ClassNotFoundException e) {
			System.out.println(type + " does not exist");
		}
		System.out.println(); // Separator
	}
}
