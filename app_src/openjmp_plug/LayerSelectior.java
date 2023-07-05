package openjmp_plug;


import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import com.vividsolutions.jump.workbench.model.Layer;

public class LayerSelectior extends JDialog implements ActionListener{
	
	private JLabel label;
	private JRadioButton radioButton[];
	private JButton b;
	private String result;

	
	public LayerSelectior(List<Layer> list){
		setModal(true);

		
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
        JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		Container c = getContentPane();
		setTitle("Select a layer");
		
		JPanel confirmPanel = new JPanel();
		
		
		//controlls if there are any layers
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
    		setMinimumSize(new Dimension(400, 300));
    		setResizable(false);
    		setVisible(true);
    		return;
		}
		
		
        //creates the button for going in the next operation
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
		// create radio button with label
		for (Layer l : list){
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

		//set size
		setMinimumSize(new Dimension(400, 300));
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	
	
	public void actionPerformed(ActionEvent e){
		
		//closed operation
		if(e.getSource()==b) {
			if(!b.getName().toString().equals("error")) {
				
			}
			dispose();
			return;
		}
		
		//finds the radio button selected
		for(int i =0; i< radioButton.length ; i++) {
			if(e.getSource()==radioButton[i]) { 
				result = radioButton[i].getName().toString();
			}
		}
		
		 
	}
	
	
	//returns the result
	public String getResult() {return result;}
	
	
}