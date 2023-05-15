import javax.swing.JFrame;

public class GFrame extends JFrame {
	GFrame(){
//		GPanel panel=new GPanel();   // for this we can directly use new GPanel();
		this.add(new GPanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
