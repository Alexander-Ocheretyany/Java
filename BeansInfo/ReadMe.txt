The program prints out information about JavaBean components. It reads from the standard input and prints to the standard output. The program reads names of components (each one on a separate line) and prints information about them - the component name, all its properties, listeners, and methods. If a type of a property cannot be discovered, then the property is not printed out. Output formating is as follows:

JavaBean Name: <full_name_of_component>
[readonly] [bound] [constrained] property <full_name_of_type> <property_name>
... other properties ...
listener <full_name_of_listener>
  <name_of_listener_s_first_method>
  <name_of_listener_s_second_method>
  ... other methods ...
... other listeners ...
method <name_of_method>
... other methods ...

The program uses java.beans.Introspector class to obtain information about JavaBeans. The component elements are printed out sorted lexicographically (to sort listeners it uses the name of the event from the EventSetDescriptor). Information about particular components are separated by an empty line. In a case the component does not exist the component name is printed in quotes and followed by the text "does not exists".
