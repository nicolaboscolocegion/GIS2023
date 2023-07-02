package openjmp_plug;

import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.vividsolutions.jump.feature.BasicFeature;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;





public class plug extends AbstractPlugIn{

	@Override
	public void initialize(PlugInContext context) throws Exception {
		
		//feature of the plug
		FeatureInstaller fi = FeatureInstaller.getInstance(context.getWorkbenchContext()); // OpenJUMP >= 2.0
		fi.addMainMenuPlugin(
			this, //exe
			new String[] {"GIS", "project" }, //menu path
			this.getName(), //name .getName received by AbstractPlugIn
			false, //Checkbooxs
			null, //icon
			null );
	}
	
	@Override
	public boolean execute(PlugInContext context) throws Exception {
		
		List<Layer> layers  = context.getLayerManager().getLayers();
		
		for(Layer l : layers) {
			System.out.println(l.getName());
		}
		
		new dialog<Layer>(layers);
		
		 
		return false;
	}
	
	
	
	@Override
	public String getName() {
		//name of the plug
		return "super plug";
	}
}
