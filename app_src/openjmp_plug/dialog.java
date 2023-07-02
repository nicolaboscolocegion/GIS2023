package openjmp_plug;


import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
// import window.JWindowFrame;

import com.vividsolutions.jump.workbench.model.Layer;

public class dialog<T> extends JFrame implements ActionListener
{
	private JLabel label;
	private JRadioButton radioButton[];
	private T result;

	//only for test
	public dialog(){
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
		
		container.add(buttonPanel);
		container.add(confirmPanel);
		
		c.add(container);

		setMinimumSize(new Dimension(500, 300));
		pack();
		
		
		//setMinimumSize(new Dimension(500, 300));
		//pack();
		
		setVisible(true);
	}
	
	
	dialog(List<Layer> list){
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		Container c = getContentPane();
		setTitle("Select a layer");
		
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
			group.add(radioButton[i]);
			buttonPanel.add(radioButton[i]);
			i++;
		}
		
		
		
		JButton button = new JButton("fine!");
		button.setName("prova");
		
		JPanel confirmPanel = new JPanel();
		confirmPanel.add(button);
		
		container.add(buttonPanel);
		container.add(confirmPanel);
		
		c.add(container);

		setMinimumSize(new Dimension(400, 300));
		pack();
		
		
		setVisible(true);
	}
	
	
	
	public void actionPerformed(ActionEvent e){
		int size = 0;
		if (e.getSource() == radioButton[0])
			size = 12;
		else if (e.getSource() == radioButton[1])
			size = 18;
		else size = 36;

		label.setFont(new Font("TimesRoman", Font.PLAIN, size));
	}
	
	public static void main(String args[]){
		new dialog();
	}
}