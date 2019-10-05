import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Hisogram {

   public static void main2() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Hisogram();
            }
        });
    }

    public Hisogram() {

        int width = 100000;
        int height = 1;
        
        int[][] data = new int[width][height];
        
        for(int f = 0; f < 100000; ++f) {
        	int t = 0;
        	for(int h = 0; h < 50; ++h) {
            	int[][] res = new int[6][];
        		for(int i = 0; i < 6; i++) {
        			res[i] = new int[1];
        			res[i][0] = 0;
        		}
        		for(int i = 0; i < 50; ++i) {
        			int k = GUI.roll();
        			res[k - 1][0] = res[k - 1][0] + 1;
        		}
        		t = res[4][0] + res[5][0];   
            }
        	data[f][0] = t;
        }		     
        
        JFrame frame = new JFrame("Test");
    	frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    	
        // For this example, I just randomised some data, you would
        // Need to load it yourself...
        
        Thread t = new Thread(new Runnable() {
        	public void run(){    		
        		
        		JScrollPane pane = new JScrollPane();
        		for(int i = 0; i < 100000; ++i) {
        			frame.remove(pane);
                    Map<Integer, Integer> mapHistory = new TreeMap<Integer, Integer>();
                    for (int c = 0; c < i; c++) {
                        for (int r = 0; r < data[c].length; r++) {
                            int value = data[c][r];
                            int amount = 0;
                            if (mapHistory.containsKey(value)) {
                                amount = mapHistory.get(value);
                                amount++;
                            } else {
                                amount = 1;
                            }
                            mapHistory.put(value, amount);
                        }
                    }
                    pane = new JScrollPane(new Graph(mapHistory));
                	frame.add(pane);
                	frame.revalidate();
                	frame.invalidate();
                	frame.validate();
                	frame.repaint();
                	
                	try {
						Thread.sleep(10L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	}
        });
        t.start();
    }

    protected class Graph extends JPanel {

		private static final long serialVersionUID = -3336010613809329255L;
		protected static final int MIN_BAR_WIDTH = 4;
        private Map<Integer, Integer> mapHistory;

        public Graph(Map<Integer, Integer> mapHistory) {
            this.mapHistory = mapHistory;
            int width = (mapHistory.size() * MIN_BAR_WIDTH) + 11;
            Dimension minSize = new Dimension(width, 128);
            Dimension prefSize = new Dimension(width, 256);
            setMinimumSize(minSize);
            setPreferredSize(prefSize);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (mapHistory != null) {
                int xOffset = 5;
                int yOffset = 5;
                int width = getWidth() - 1 - (xOffset * 2);
                int height = getHeight() - 1 - (yOffset * 2);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawRect(xOffset, yOffset, width, height);
                int barWidth = Math.max(MIN_BAR_WIDTH,
                        (int) Math.floor((float) width
                        / (float) mapHistory.size()));
                int maxValue = 0;
                for (Integer key : mapHistory.keySet()) {
                    int value = mapHistory.get(key);
                    maxValue = Math.max(maxValue, value);
                }
                int xPos = xOffset;
                for (Integer key : mapHistory.keySet()) {
                    int value = mapHistory.get(key);
                    int barHeight = Math.round(((float) value
                            / (float) maxValue) * height);
                    key = 3;
                    g2d.setColor(new Color(key, key, key));
                    int yPos = height + yOffset - barHeight;
                    Rectangle2D bar = new Rectangle2D.Float(
                            xPos, yPos, barWidth, barHeight);
                    g2d.fill(bar);
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.draw(bar);
                    xPos += barWidth;
                }
                g2d.dispose();
            }
        }
    }
}