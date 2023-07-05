package openjmp_plug;

import com.vividsolutions.jump.feature.*;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;



import java.util.LinkedList;
import com.vividsolutions.jump.workbench.model.Layer;


import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.util.GeometricShapeFactory;


import com.vividsolutions.jump.workbench.ui.*;

import org.locationtech.jts.geom.Geometry;

import java.util.List;

import com.vividsolutions.jump.feature.*;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.openjump.core.geomutils.Circle;

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
				mid.addLayerComboBox( _namelayer, null, context.getLayerManager() );
				mid.setVisible( true ); // modal dialog
				if( mid.wasOKPressed() == false ) return false;
				
		String name = mid.getText( _namelayer ); 
		
		if(name.equals("")) {
			System.err.println("no layer");
			return false;
		}
		
		Layer inputLayer = context.getLayerManager().getLayer(name);
		
		List<String> reports = new LinkedList<String>();
		
		
		for(Feature f : inputLayer.getFeatureCollectionWrapper().getFeatures()) {
			reports.add(f.getString("name"));
			System.out.println(f.getString("name"));
		}
		
	
		//dialog for selecting report
		MultiInputDialog mid1 = new MultiInputDialog(
				context.getWorkbenchFrame(),
				this.getName(), true );
				String _nameReport = "Choose the layer to process";
				mid1.addComboBox(_nameReport, reports.get(0), reports, "yes");
				mid1.setVisible( true ); // modal dialog
				if( mid1.wasOKPressed() == false ) return false;
		
		String reportSelectedName = mid1.getText(_nameReport);
		
		Feature reportSelected=null;
		
		inputLayer.getFeatureCollectionWrapper().getFeatures().get(0).getSchema().getAttributeIndex("name");
		for(Feature f : inputLayer.getFeatureCollectionWrapper().getFeatures()) {
			
			if(reportSelectedName.equals(f.getAttribute("name"))) {
				reportSelected =f;
			}	
		}
		
		Geometry reportGeo = reportSelected.getGeometry();
		
		String[] stationLayers = {"idrografia-belluno-stazioni-fiumi-bio", "idrografia-belluno-stazioni-fiumi-chimico", "idrografia-belluno-stazioni-laghi-bio", "idrografia-belluno-stazioni-laghi-chimico"};
		
		Feature[] upstreamStations = new Feature[2];
		Feature downstramStation = null;
		
		double[] upstreamDistances = new double[2];
		double downstramDistant = 0;
		
		//for test
		int reportAltitude= 700;
		
		List<Feature> stations = new LinkedList();
				
		stations.addAll(context.getLayerManager().getLayer(stationLayers[0]).getFeatureCollectionWrapper().getFeatures());
		stations.addAll(context.getLayerManager().getLayer(stationLayers[1]).getFeatureCollectionWrapper().getFeatures());
		stations.addAll(context.getLayerManager().getLayer(stationLayers[2]).getFeatureCollectionWrapper().getFeatures());
		stations.addAll(context.getLayerManager().getLayer(stationLayers[3]).getFeatureCollectionWrapper().getFeatures());

		
		//fid upstream and downstream nearest stations
		for(Feature f : stations) {
			
			int altitudeIndex = f.getSchema().getAttributeIndex("altitude");
			double distanceTemp =0;
			//if upstream
			if(f.getInteger(altitudeIndex)>reportAltitude) {
				if(upstreamDistances[0]!=0 && upstreamDistances[0] > (distanceTemp = reportGeo.distance(f.getGeometry()))) {
					//swap the first with the second nearest
					upstreamDistances[1]= upstreamDistances[0];
					upstreamStations[1] =upstreamStations[0];
					
					upstreamDistances[0] =distanceTemp;
					upstreamStations[0] = f;
					continue;
				}
				
				if(upstreamDistances[0]==0) {
					upstreamDistances[0] =reportGeo.distance(f.getGeometry());
					upstreamStations[0] = f;
					continue;
				}
				
			//if downstream
			}else{
				if(downstramDistant!=0 && downstramDistant > (distanceTemp = reportGeo.distance(f.getGeometry()))) {
					//swap the first with the second nearest
					
					downstramDistant =distanceTemp;
					downstramStation = f;
					continue;
				}
				
				if(downstramDistant==0) {
					downstramDistant = reportGeo.distance(f.getGeometry());
					downstramStation = f;
					continue;
				}
			}
		}
		
		//create features for the monitor units
		FeatureSchema fs= new FeatureSchema();
		
		//crate schema
		fs.addAttribute("type" , AttributeType.STRING);
		fs.addAttribute("altitude", AttributeType.INTEGER);
		fs.addAttribute("geo", AttributeType.GEOMETRY);
		
		FeatureCollection fc = new FeatureDataset(fs);
		
		//create feature for nearest upstream monitor unit
		if(upstreamStations[0]!=null) {
			Feature nearestMonitorUnit = new BasicFeature(fs);
			nearestMonitorUnit.setGeometry(upstreamStations[0].getGeometry());
			nearestMonitorUnit.setAttribute("type", "monitor unit");
			nearestMonitorUnit.setAttribute("altitude", upstreamStations[0].getAttribute("altitude"));
			fc.add(nearestMonitorUnit);
		}
		//create feature for second nearest upstream monitor unit
		if(upstreamStations[1]!=null) {
			Feature farMonitorUnit = new BasicFeature(fs);
			farMonitorUnit.setGeometry(upstreamStations[1].getGeometry());
			farMonitorUnit.setAttribute("type", "monitor unit");
			farMonitorUnit.setAttribute("altitude", upstreamStations[1].getAttribute("altitude"));
			fc.add(farMonitorUnit);
		}
		if(downstramStation!=null) {
		//create feature for the nearest downstram unit
		Feature downStreamMonitorUnit = new BasicFeature(fs);
		downStreamMonitorUnit.setGeometry(downstramStation.getGeometry());
		downStreamMonitorUnit.setAttribute("type", "monitor unit");
		downStreamMonitorUnit.setAttribute("altitude", downstramStation.getAttribute("altitude"));
		fc.add(downStreamMonitorUnit);
		}
		
		
		Layer outputLayer= new Layer();
		
		context.getLayerManager().addLayer("Result", "output", fc);
		
		System.out.println(name);
		
		return false;
	}
	
	
	@Override
	public String getName() {
		//name of the plug
		return "super plug";
	}
}
