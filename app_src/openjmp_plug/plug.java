package openjmp_plug;

import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.workbench.model.Layer;


import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;
import com.vividsolutions.jump.workbench.ui.*;
import javax.swing.JComboBox;
import java.util.List;

public class plug extends AbstractPlugIn {

	public Layer result=null;
	
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
		
		
		
		//calls the dialog for the selection layer
		
		MultiInputDialog mid = new MultiInputDialog(
				context.getWorkbenchFrame(),
				this.getName(), true );
				String _namelayer = "Choose the layer to process";
				mid.addLayerComboBox( _namelayer, null,
				context.getLayerManager() );
				mid.addAttributeComboBox("Report", _namelayer, new AttributeTypeFilter(AttributeTypeFilter.STRING), "");
				mid.setVisible( true ); // modal dialog
				if( mid.wasOKPressed() == false ) return false;
				
		String name = mid.getText( _namelayer ); 
		
		if(name.equals("")) {
			System.err.println("no layer");
			return false;
		}
		
		Layer inputLayer = context.getLayerManager().getLayer(name);
		
		for(Feature f : inputLayer.getFeatureCollectionWrapper().getFeatures()) {
			System.out.println(f.getAttribute("altitude"));
		}
		
	
		
		MultiInputDialog mid1 = new MultiInputDialog(
				context.getWorkbenchFrame(),
				this.getName(), true );
				String _nameReport = "Choose the layer to process";
				
				mid1.setVisible( true ); // modal dialog
				if( mid1.wasOKPressed() == false ) return false;
		
		
		
		System.out.println(name);
		
		return false;
	}
	
	
	@Override
	public String getName() {
		//name of the plug
		return "super plug";
	}
}
