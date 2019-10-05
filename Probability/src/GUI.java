import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUI implements ActionListener{
	
	private JFrame main;
	
	private JLabel result;
	private JButton roll;
	private JLabel losewin;
	private JButton repeat;
	private JLabel expectedT;
	private JLabel expectedF;
	private JButton repeatMil;
	
	private static Random rnd = new Random(System.currentTimeMillis());
	
	GUI(){
		main = new JFrame("Probability");
		main.setSize(300, 400);
		main.setResizable(false);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setLocationRelativeTo(null);
		main.setLayout(new GridLayout(7, 1));
		
		result = new JLabel("Roll a dice");
		Font myFont_1 = new Font("MyFont", Font.BOLD, 36);
		result.setFont(myFont_1);
		result.setHorizontalAlignment(JLabel.CENTER);
		roll = new JButton("Roll");
		roll.addActionListener(this);
		Font myFont_2 = new Font("MyFont", Font.PLAIN, 11);
		losewin = new JLabel("No result");
		losewin.setFont(myFont_2);
		losewin.setHorizontalAlignment(JLabel.CENTER);
		
		repeat = new JButton("Repeat 50 times");
		repeat.addActionListener(this);
		
		expectedT = new JLabel("Theoretical EX: perform experiment");
		expectedF = new JLabel("Factual EX: perform experiment");
		
		repeatMil = new JButton("Repeat 100 000 times");
		repeatMil.addActionListener(this);
		
		main.add(result);
		main.add(losewin);
		main.add(roll);
		main.add(repeat);
		main.add(expectedT);
		main.add(expectedF);
		main.add(repeatMil);
		
		main.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();
		String text = source.getText();

		switch(text) {
		
		case "Roll":
			int p = roll();
			if(p > 4) {
				losewin.setText("Win!");
			} else {
				losewin.setText("Loose");
			}
			String s = new Integer(p).toString();
			result.setText(s);
			break;
			
		case "Repeat 50 times":
			double t = 50.0 / 3.0;
			expectedT.setText("Theoretical EX: " + t);
			int[][] res = new int[6][];
			for(int i = 0; i < 6; i++) {
				res[i] = new int[1];
				res[i][0] = 0;
			}
			for(int i = 0; i < 50; ++i) {
				int k = roll();
				res[k - 1][0] = res[k - 1][0] + 1;
			}
			t = res[4][0] + res[5][0];
			expectedF.setText("Factual EX: " + t);
			break;
		case "Repeat 100 000 times":
			Hisogram.main2();	    
		    break;
		}
	}
	
	public static int roll() {
		return rnd.nextInt(6) + 1;
	}
}
