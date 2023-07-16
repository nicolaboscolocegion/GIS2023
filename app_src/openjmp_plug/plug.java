package openjmp_plug;

import com.vividsolutions.jump.feature.*;
import com.vividsolutions.jump.workbench.ui.plugin.FeatureInstaller;



import java.util.LinkedList;
import com.vividsolutions.jump.workbench.model.Layer;


import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;


import org.locationtech.jts.geom.*;



import com.vividsolutions.jump.workbench.ui.*;

import java.util.List;



public class plug extends AbstractPlugIn {

	
	/**
	 * inizialize the plugin
	 */
	@Override
	public void initialize(PlugInContext context) throws Exception {
		
		//feature of the plug
		FeatureInstaller fi = FeatureInstaller.getInstance(context.getWorkbenchContext()); // OpenJUMP >= 2.0
		fi.addMainMenuPlugin(
			this, //Exe
			new String[] {"GIS", "project" }, //menu path
			this.getName(), //name .getName received by AbstractPlugIn
			false, //Checkboxes
			null, //Icon
			null );
	}
	
	/**
	 * this plugin let's the use choose a report and it finds the 3 nearest stations 
	 * then it finds all the report near it in a range of 500m
	 */
	@Override
	public boolean execute(PlugInContext context) throws Exception {
		
		Login login = new Login();
		
		if(login.getPassword()==null || login.getUserName()==null) {
			System.err.println("Username or password not written");
			return false;
		}
		
		
		Database app = new Database(login.getUserName() , login.getPassword());
		
		FeatureCollection reports = app.getReports();
		context.getLayerManager().addLayer("Result", "reports", reports);
		//calls the dialog for the selection layer
		
		FeatureCollection[] stations = app.getStations();
		app.close();
		
		context.getLayerManager().addLayer("Result", "sensor1", stations[0]);
		context.getLayerManager().addLayer("Result", "sensor2", stations[1]);
		context.getLayerManager().addLayer("Result", "sensor3", stations[2]);
		context.getLayerManager().addLayer("Result", "sensor4", stations[3]);
		
		
		List<String> reportsNames = new LinkedList<String>();
		
		
		for(Feature f : reports.getFeatures()) {
			reportsNames.add(f.getString("id_report"));
		}
		
	
		//dialog for selecting report
		MultiInputDialog mid1 = new MultiInputDialog(
				context.getWorkbenchFrame(),
				this.getName(), true );
				String _nameReport = "Choose the report to process";
				mid1.addComboBox(_nameReport, reportsNames.get(0), reportsNames, "yes");
				mid1.setVisible( true ); // modal dialog
				if( mid1.wasOKPressed() == false ) return false;
		
		String reportSelectedName = mid1.getText(_nameReport);
		
		Feature reportSelected=null;
		
		
		for(Feature f : reports.getFeatures()) {
			
			if(reportSelectedName.equals(f.getString("id_report"))) {
				reportSelected =f;
			}	
		}
		
		Geometry reportGeo = reportSelected.getGeometry();
		
		
		Feature[] upstreamStations = new Feature[2];
		Feature downstramStation = null;
		
		double[] upstreamDistances = new double[2];
		upstreamDistances[0]=-1;
		upstreamDistances[1]=-1;
		double downstramDistant = -1;
		
		
		
		int reportAltitude= reportSelected.getInteger(reportSelected.getSchema().getAttributeIndex("elevation"));
		
		List<Feature> staionList = new LinkedList<Feature>();
			
		
		for(FeatureCollection fc : stations) {
			staionList.addAll(fc.getFeatures());
		}
		
		//fid upstream and downstream nearest stations
		for(Feature f : staionList) {
			
			int altitudeIndex = f.getSchema().getAttributeIndex("altitude");
			double distanceTemp =0;
			//if upstream
			if(f.getInteger(altitudeIndex)>reportAltitude) {
				if(upstreamDistances[0] !=-1 && upstreamDistances[0] >= (distanceTemp = reportGeo.distance(f.getGeometry()))) {
					//swap the first with the second nearest
					upstreamDistances[1]= upstreamDistances[0];
					upstreamStations[1] = upstreamStations[0];
					
					upstreamDistances[0] = distanceTemp;
					upstreamStations[0] = f;
					continue;
				}
				
				if(upstreamDistances[1] !=-1 && upstreamDistances[1] >= (distanceTemp = reportGeo.distance(f.getGeometry()))) {
					//Controls the second distance
					upstreamDistances[1] =distanceTemp;
					upstreamStations[1] = f;
					continue;
				}
					
				if(upstreamDistances[0] == -1) {
					upstreamDistances[0] = reportGeo.distance(f.getGeometry());
					upstreamStations[0] = f;
					continue;
				}
				
				if(upstreamDistances[1] == -1) {
					upstreamDistances[1]=reportGeo.distance(f.getGeometry());
					upstreamStations[1]=f;
					continue;
				}
				
			//else downstream
			}else{
				if(downstramDistant!=-1 && downstramDistant > (distanceTemp = reportGeo.distance(f.getGeometry()))) {
					//swap the first with the second nearest
					
					downstramDistant =distanceTemp;
					downstramStation = f;
					continue;
				}
				
				if(downstramDistant==-1) {
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
		fs.addAttribute("cod_staz", AttributeType.DOUBLE);
		
		FeatureCollection nearestStations = new FeatureDataset(fs);
		
		GeometryFactory gf =new GeometryFactory();
		//create feature for nearest upstream monitor unit
		if(upstreamStations[0]!=null) {
			Feature nearestMonitorUnit = new BasicFeature(fs);
			nearestMonitorUnit.setGeometry(upstreamStations[0].getGeometry());
			nearestMonitorUnit.setAttribute("type", "monitor unit");
			nearestMonitorUnit.setAttribute("altitude", upstreamStations[0].getAttribute("altitude"));
			nearestMonitorUnit.setAttribute("cod_staz", upstreamStations[0].getAttribute("cod_staz"));
			nearestStations.add(nearestMonitorUnit);
			//create line
			Feature line = new BasicFeature(fs);
			Coordinate[] lineCoordinate= new Coordinate[2];
			lineCoordinate[0] = nearestMonitorUnit.getGeometry().getCoordinate();
			lineCoordinate[1] = reportSelected.getGeometry().getCoordinate();
			line.setGeometry(gf.createLineString(lineCoordinate));
			line.setAttribute("type", "line");
			nearestStations.add(line);
		}
		//create feature for second nearest upstream monitor unit
		if(upstreamStations[1]!=null) {
			Feature farMonitorUnit = new BasicFeature(fs);
			farMonitorUnit.setGeometry(upstreamStations[1].getGeometry());
			farMonitorUnit.setAttribute("type", "monitor unit");
			farMonitorUnit.setAttribute("altitude", upstreamStations[1].getAttribute("altitude"));
			farMonitorUnit.setAttribute("cod_staz", upstreamStations[1].getAttribute("cod_staz"));
			nearestStations.add(farMonitorUnit);
			//create line
			Feature line = new BasicFeature(fs);
			Coordinate[] lineCoordinate= new Coordinate[2];
			lineCoordinate[0] = farMonitorUnit.getGeometry().getCoordinate();
			lineCoordinate[1] = reportSelected.getGeometry().getCoordinate();
			line.setGeometry(gf.createLineString(lineCoordinate));
			line.setAttribute("type", "line");
			nearestStations.add(line);
		}
		if(downstramStation!=null) {
			//create feature for the nearest downstram unit
			Feature downStreamMonitorUnit = new BasicFeature(fs);
			downStreamMonitorUnit.setGeometry(downstramStation.getGeometry());
			downStreamMonitorUnit.setAttribute("type", "monitor unit");
			downStreamMonitorUnit.setAttribute("altitude", downstramStation.getAttribute("altitude"));
			downStreamMonitorUnit.setAttribute("cod_staz", downstramStation.getAttribute("cod_staz"));
			nearestStations.add(downStreamMonitorUnit);
			//create line
			Feature line = new BasicFeature(fs);
			Coordinate[] lineCoordinate= new Coordinate[2];
			lineCoordinate[0] = downStreamMonitorUnit.getGeometry().getCoordinate();
			lineCoordinate[1] = reportSelected.getGeometry().getCoordinate();
			line.setGeometry(gf.createLineString(lineCoordinate));
			line.setAttribute("type", "line");
			nearestStations.add(line);
		}
		
		//add report to the layer
		Feature copyReport = new BasicFeature(fs);
		copyReport.setGeometry(reportSelected.getGeometry());
		copyReport.setAttribute("type", "report");
		copyReport.setAttribute("altitude", reportAltitude);
		nearestStations.add(copyReport);
		
		
		
		//finds all reports in the 500m range
		Geometry bufferReport = reportGeo.buffer(500);
		
		FeatureCollection nearReports = new FeatureDataset(reports.getFeatures().get(0).getSchema().clone());
		
		for(Feature r : reports.getFeatures()) {
			if(bufferReport.contains(r.getGeometry()) && !r.getAttribute("id_report").equals(reportSelectedName)) {
				nearReports.add(r.clone());
				Feature line = new BasicFeature(fs);
				Coordinate[] lineCoordinate= new Coordinate[2];
				lineCoordinate[0] = r.getGeometry().getCoordinate();
				lineCoordinate[1] = reportSelected.getGeometry().getCoordinate();
				line.setGeometry(gf.createLineString(lineCoordinate));
				nearReports.add(line);
			}
		}
		
		nearReports.add(reportSelected.clone());
		
		//output layers
		context.getLayerManager().addLayer("Result", "near stations", nearestStations);
		
		context.getLayerManager().addLayer("Result", "near reports", nearReports);
		
		
		return false;
	}
	
	/**
	 * gets the name of the plugin
	 * @return the string
	 */
	@Override
	public String getName() {
		//name of the plug
		return "Re";
	}
}
