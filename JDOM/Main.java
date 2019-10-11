import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;

public class Main {
	
	public static void main(String[] args) {	
		
		try {
			InputStream input = System.in;
			File file = new File("File.xml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			while(reader.ready()) {
				writer.write(reader.read());
			}
			writer.close();
			
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(file);
			Element root = doc.getRootElement();
			
			ElementFilter filter = new ElementFilter("link");
			
			String currentSection = " ";
			
			for(Element con: root.getDescendants(filter)) {
				String section = con.getParentElement().getParentElement().getChild("title").getValue();
				if(!currentSection.equals(section)) System.out.println(section + ":");
				System.out.print("    " + con.getValue() + " ");
				String name = con.getAttributeValue("linkend");
				
				for(Content sub: root.getDescendants()) {
					
					Element parent = sub.getParentElement();
					
					if(parent.getAttributeValue("id") != null && parent.getAttributeValue("id").equals(name)) {
						if(parent.getName().equals("title")) {
							System.out.println("(" + parent.getValue() + ")");
						}
						else {
							Element child = parent.getChild("title");
							while(child == null) {
								parent = parent.getParentElement();
								child = parent.getChild("title");
							}
							System.out.println("(" + child.getValue() + ")");
						}
						break;
					}
				}
				currentSection = section;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}