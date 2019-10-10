import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
	
public static void main(String[] args) throws ClassNotFoundException, IntrospectionException, IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		
		while(reader.ready()) {
			try {
				str = reader.readLine();
				Class<?> cls = Class.forName(str);
				BeanInfo b = Introspector.getBeanInfo(cls);
				
				// General info
				System.out.print("JavaBean Name: ");
				System.out.println(cls.getName());
				
				// Properties
				PropertyDescriptor[] properties = b.getPropertyDescriptors();
				if(properties.length != 0) {
					for(PropertyDescriptor k: properties) {
						StringBuilder propertyType = new StringBuilder();
						if(k.getWriteMethod() == null) propertyType.append("readonly ");
						if(k.isBound()) propertyType.append("bound ");
						if(k.isConstrained()) propertyType.append("constrained");
						String type = propertyType.toString().trim();
						if(type.length() != 0) System.out.println(type + " property " + k.getPropertyType().getName() + " " + k.getName());
					}
				}
				
				ArrayList<String> list = new ArrayList<>();
				
				// Listeners
				EventSetDescriptor[] descr = b.getEventSetDescriptors();
				ArrayList<String> listMet = new ArrayList<>();
				
				ArrayList<String> names = new ArrayList<>();
				for(EventSetDescriptor h: descr) {
					names.add(h.getDisplayName());
				}
				names.sort(String::compareTo);
				
				ArrayList<EventSetDescriptor> dlist = new ArrayList<>();
				for(String j: names) {
					for(int iter = 0; iter < descr.length; iter++) {
						if(j.equals(descr[iter].getDisplayName())) {
							dlist.add(descr[iter]);
							break;
						}
					}
				}
				
				EventSetDescriptor[] arr = new EventSetDescriptor[dlist.size()];
				dlist.toArray(arr);
				
				for(EventSetDescriptor k: arr) {
					Method[] mt = k.getListenerMethods();
					for(Method f: mt) {
						listMet.add(f.getName());
					}
					
					Collections.sort(listMet);
					
					StringBuilder strB = new StringBuilder();
					strB.append("listener " + k.getListenerType().getName());
					for(String h: listMet) {
						strB.append('\n' + "  " + h);
					}
					list.add(strB.toString());
					listMet = new ArrayList<>();
				}

				for(String f: list) {
					System.out.println(f);
				}
				
				list = new ArrayList<>();
				
				// Methods
				MethodDescriptor[] mtds = b.getMethodDescriptors();
				for(MethodDescriptor k: mtds) {
					list.add(k.getName());
				}
				list.sort(String::compareToIgnoreCase);
				Collections.sort(list);
				
				for(String k: list) {
					System.out.println("method " + k);
				}
			} catch (Exception e) {
				System.out.println('\"' + str + '\"' + " does not exist");
			}
			if(reader.ready()) System.out.println();
		}
		
	}
	
}
