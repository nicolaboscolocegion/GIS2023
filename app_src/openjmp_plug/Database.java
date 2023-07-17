package openjmp_plug;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.ui.GeometryEditor;
import com.vividsolutions.jump.workbench.ui.GeometryEditor.GeometryEditorOperation;
import com.vividsolutions.jump.feature.*;
import java.sql.SQLException;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;



/**
 * Class for manage database query
 */
public class Database {

	
	    private final String url = "jdbc:postgresql://gis2023.bitsei.it:5432/gis2023";
	  

	    private Connection con;
	    /**
	     * Connect to the PostgreSQL database
	     */
	    
	    public Database(String username, char[] password) {
	    	try {
	            con = DriverManager.getConnection(url, username, new String(password));
	            System.out.println("Connected to the PostgreSQL server successfully.");
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    
	    /**
		 * Retrives the feature collection with all the reports
		 * @return a @code{FeatureCollection} with all the reports
		 */
	    public FeatureCollection getReports(){
	    	
	    	FeatureSchema fs= new FeatureSchema();
			
			//crate schema
			fs.addAttribute("id_report" , AttributeType.INTEGER);
			fs.addAttribute("data_report" , AttributeType.STRING);
			fs.addAttribute("pollutant" , AttributeType.STRING);
			fs.addAttribute("report_description" , AttributeType.STRING);
			fs.addAttribute("elevation" , AttributeType.INTEGER);
			fs.addAttribute("geo", AttributeType.GEOMETRY);

			
			FeatureCollection fc = new FeatureDataset(fs);
			
			GeometryFactory gf =new GeometryFactory();
			
			WKTReader wkt = new WKTReader(gf);
			
			//String query = "SELECT data_report, pollutant, report_description, ST_AsText (position, 4326) as pos, id_report, elevation FROM reports";
			String query = "SELECT  ST_AsText(ST_Transform(position, 3003)) as pos, *FROM reports";

			
			
	        try (Statement stmt = con.createStatement()) {
	          ResultSet rs = stmt.executeQuery(query);
	          while (rs.next()) {
	        	  Feature f = new BasicFeature(fs);
	            
	        	  f.setAttribute("id_report", rs.getInt("id_report"));
	        	  f.setAttribute("data_report", rs.getString("data_report"));
	        	  f.setAttribute("pollutant", rs.getString("pollutant"));
	        	  f.setAttribute("report_description", rs.getString("report_description"));
	        	  f.setAttribute("elevation", rs.getInt("elevation"));
	        	  
	        	  
	        	  
	        	  f.setGeometry(wkt.read(rs.getString("pos")));
	        	  
	        	  fc.add(f);
	          }
	        } catch (SQLException e) {
	        	System.out.println(e);
	        } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return fc;
	    }
	    
	    /**
		 * Retrives the feature collection with all the monitor units
		 * @return a @code{FeatureCollection} with all the monitor units, there are 4 groups that are the 4 groups of the database
		 */
	    public FeatureCollection[] getStations(){
	    	
	    	
	    	FeatureCollection[] stations = new FeatureCollection[4];
	    	
			GeometryFactory gf =new GeometryFactory();
			
			WKTReader wkt = new WKTReader(gf);
			
			String querys[] = {
					"SELECT ST_AsText (geom) as geom, * FROM \"idrografia-belluno-stazioni-fiumi-bio\";",
					"SELECT ST_AsText (geom) as geom, * FROM \"idrografia-belluno-stazioni-fiumi-chimico\"",
					"SELECT ST_AsText (geom) as geom, * FROM \"idrografia-belluno-stazioni-laghi-bio\";",
					"SELECT ST_AsText (geom) as geom, * FROM \"idrografia-belluno-stazioni-laghi-chimico\";"
			};
			
			
			FeatureSchema fs = new FeatureSchema();
			
			//horrible code, but it's the easiest solution that I've imagine
			
			fs.addAttribute("gid", AttributeType.INTEGER);
			fs.addAttribute("cod_staz", AttributeType.DOUBLE);
			fs.addAttribute("data_fine", AttributeType.STRING);
			fs.addAttribute("statnm", AttributeType.STRING);
			fs.addAttribute("comunecd", AttributeType.INTEGER);
			fs.addAttribute("provcd", AttributeType.INTEGER);
			fs.addAttribute("altitude", AttributeType.INTEGER);
			fs.addAttribute("localita", AttributeType.STRING);
			fs.addAttribute("provincia", AttributeType.STRING);
			fs.addAttribute("comune", AttributeType.STRING);
			fs.addAttribute("x_gbo", AttributeType.DOUBLE);
			fs.addAttribute("y_gbo", AttributeType.DOUBLE);
			fs.addAttribute("codseqst", AttributeType.INTEGER);
			fs.addAttribute("geom", AttributeType.GEOMETRY);
			
			//adds the different paramiters to all schemas
			FeatureSchema fs1=new FeatureSchema();
			
			FeatureSchema.cloneFromTo(fs, fs1);
			fs1.addAttribute("data_iniz", AttributeType.STRING);
			fs1.addAttribute("ci", AttributeType.STRING);
			FeatureSchema fs2=new FeatureSchema();
			
			FeatureSchema.cloneFromTo(fs1, fs2);
			
			
			fs.addAttribute("data_inizi", AttributeType.STRING);
			stations[0] = new FeatureDataset(fs);
			stations[1] = new FeatureDataset(fs);
			
			
			stations[2] = new FeatureDataset(fs1);
			
		
			fs2.addAttribute("prof_preli", AttributeType.STRING);
			stations[3] = new FeatureDataset(fs2);
			
			GeometryEditor geometryEditor = new GeometryEditor();
			//Loads all stations in the FeatureCollection stations
			for(int j =0; j<querys.length; j++) {
				try (Statement stmt = con.createStatement()) {
					ResultSet rs = stmt.executeQuery(querys[j]);
					
					while(rs.next()) {
						
						ResultSetMetaData coloums = rs.getMetaData();
						Feature f = new BasicFeature(stations[j].getFeatureSchema());
						
						for(int i=1; i<coloums.getColumnCount(); i++) {
							//special parsion for geometry
							if(coloums.getColumnName(i).equals("geom")) {
								
								f.setAttribute("geom", wkt.read(rs.getString(i)));
								continue;
							}
							
							f.setAttribute(coloums.getColumnName(i), rs.getObject(i));
						}
						stations[j].add(f);
					}
	
		        } catch (SQLException e) {
		        	System.err.println("sql exception\n" + e.toString());

		        } catch (ParseException e) {
					
		        	System.err.println("parsing geometry error\n" + e.toString());
				}
			}
			
			return stations;
	    }
	    
	    /**
	     * info if the connection is established 
	     * @return true if is established
	     */
	    public boolean isConnetted() {
	    	return con!=null;
	    }
	    
		/**
		 * close the connetion
		 * @return @code{true} if the connetion is closed
		 */
	    public boolean close() {
		    try{
		    	con.close();
		    	return true;
		    }catch(SQLException e) {
		    	return false;
		    }
	    }
	    
}


