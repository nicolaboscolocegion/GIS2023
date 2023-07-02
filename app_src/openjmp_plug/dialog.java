package openjmp_plug;


import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
// import window.JWindowFrame;

import com.vividsolutions.jump.workbench.model.Layer;

public class Dialog extends JFrame implements ActionListener
{
	private JLabel label;
	private JRadioButton radioButton[];
	private String result;
	private JButton b;

	//only for test
	public Dialog(){
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		Container c = getContentPane();
		setTitle("Radio Buttons Demo");
		

		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1));
		

		// group radio button
		ButtonGroup group = new ButtonGroup();
		radioButton = new JRadioButton[3];
		// string labels for radio buttons
		String buttonName[] = {"12", "18", "36"};

		for (int i = 0; i < radioButton.length; i++){
			// create radio button with label
			radioButton[i] = new JRadioButton(buttonName[i]);
			radioButton[i].addActionListener(this);
			group.add(radioButton[i]);
			buttonPanel.add(radioButton[i]);
		}
		
		
		
		JButton button = new JButton("fine!");
		button.setName("prova");
		JPanel confirmPanel = new JPanel();
		confirmPanel.add(button);
		b=button;
		
		container.add(buttonPanel);
		container.add(confirmPanel);
		
		c.add(container);

		setMinimumSize(new Dimension(500, 300));
		pack();
		
		
		//setMinimumSize(new Dimension(500, 300));
		//pack();
		
		setVisible(true);
	}
	
	
	public Dialog(List<Layer> list){
		
		
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        
        JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		Container c = getContentPane();
		setTitle("Select a layer");
		
		JPanel confirmPanel = new JPanel();
		
        
        if(list.size()==0) {
        	
        	JLabel label = new JLabel("no layers");
        	label.setText("Add at least 1 leayer to openJump before lounching the plug-in");
        	container.add(label);
        	
        	JButton button = new JButton("Return");
    		button.setName("error");
    		button.addActionListener(this);
    		b=button;
    		confirmPanel.add(button);
    		container.add(confirmPanel);
    		
    		c.add(container);
    		setVisible(true);
    		setMinimumSize(new Dimension(400, 300));
    		return;
		}
        
        JButton button = new JButton("Next");
		button.setName("next");
		button.addActionListener(this);
		b=button;
		confirmPanel.add(button);
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(list.size(), 1));
		

		// group radio button
		ButtonGroup group = new ButtonGroup();
		radioButton = new JRadioButton[list.size()];
		// string labels for radio buttons

		int i=0;
		for (Layer l : list){
			// create radio button with label
			radioButton[i] = new JRadioButton(l.getName());
			radioButton[i].addActionListener(this);
			radioButton[i].setName(l.getName());
			group.add(radioButton[i]);
			buttonPanel.add(radioButton[i]);
			i++;
		}
		
		container.add(buttonPanel);
		container.add(confirmPanel);
		
		c.add(container);

		setMinimumSize(new Dimension(400, 300));
		pack();
		
		
		setVisible(true);
	}
	
	
	
	public void actionPerformed(ActionEvent e){
		int size = 0;
		
		if(e.getSource()==b) {
			if(!b.getName().toString().equals("error")) {
				System.out.println(result);
			}
				
			dispose();
			return;
		}
		
		for(int i =0; i< radioButton.length ; i++) {
			if(e.getSource()==radioButton[i]) { 
				result= radioButton[i].getName().toString();
			}
		}
		
	}
	
	
	public static void main(String args[]){
		new Dialog();
	}
}