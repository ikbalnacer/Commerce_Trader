package voiyageur_de_commerce;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.SLDParser;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.tool.CursorTool;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.identity.FeatureId;


public class Main {
	static SimpleFeatureSource featureSource ;
     static SimpleFeatureSource featureSource1;
     static SimpleFeatureSource featureSource2 ;
    static  MapContent map = null;
	public static void main(String[] args) throws Exception {
 
        File file = new File("DZA_adm1.shp");
        File file1 = new File("le_chemin.shp");
        File file2 = new File("point_intrer_1.shp");
  
        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        FileDataStore store1 = FileDataStoreFinder.getDataStore(file1);
        FileDataStore store2 = FileDataStoreFinder.getDataStore(file2);
         featureSource = store.getFeatureSource();
         featureSource1 = store1.getFeatureSource();
         featureSource2 = store2.getFeatureSource();
  
        BufferedReader sld1 = new BufferedReader(new InputStreamReader(new FileInputStream("linesld.sld")));

		StyleFactory stylef1 = CommonFactoryFinder.getStyleFactory();
		SLDParser stylereader1 = new SLDParser(stylef1, sld1);
		
		Style styles1[] = stylereader1.readXML();
		 Style style= SLD.createSimpleStyle(featureSource.getSchema());
        Layer layer = new FeatureLayer(featureSource, styles1[0]);
        
        
        Style style1 = SLD.createSimpleStyle(featureSource1.getSchema());
        Layer layer1 = new FeatureLayer(featureSource1, style1);
        
        BufferedReader  sld = new BufferedReader(new InputStreamReader(new FileInputStream("point.sld")));

        StyleFactory stylef = CommonFactoryFinder.getStyleFactory();
        SLDParser stylereader = new SLDParser(stylef, sld);
		
        Style[] styles = stylereader.readXML();
       Layer layer2 = new FeatureLayer(featureSource2, styles[0]);
        
         map = new MapContent();
        map.setTitle("Quickstart");
        map.addLayer(layer);
        map.addLayer(layer1);
        map.addLayer(layer2);
        JMapFrame mapFrame = new JMapFrame(map);
        mapFrame.enableToolBar(true);
        mapFrame.enableStatusBar(true);
      
        JToolBar toolBar = mapFrame.getToolBar();
        JButton btn = new JButton("Select");
        toolBar.addSeparator();
        toolBar.add(btn);

        btn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                mapFrame.getMapPane().setCursorTool(
                        new CursorTool() {
                            @Override
                            public void onMouseClicked(MapMouseEvent ev) {
                            	   Point screenPos = ev.getPoint();
                                   Rectangle screenRect = new Rectangle(screenPos.x-2, screenPos.y-2,10,10);
                                   AffineTransform screenToWorld = mapFrame.getMapPane().getScreenToWorldTransform();
                                   
                                   Rectangle2D worldRect = screenToWorld.createTransformedShape(screenRect).getBounds2D();
                                   
                                   ReferencedEnvelope bbox = new ReferencedEnvelope(
                                           worldRect,
                                           mapFrame.getMapContent().getCoordinateReferenceSystem());

                             FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();  
                             try {
                             Filter filter = ff.bbox(ff.property("the_geom"), bbox);
                             SimpleFeatureCollection selectedFeatures = featureSource.getFeatures(filter);
                    
                             SimpleFeatureIterator iter = selectedFeatures.features();
                             Set<FeatureId> IDs = new HashSet<FeatureId>();

                             try {
                             while (iter.hasNext()) {
                             SimpleFeature feature = iter.next();
                             IDs.add(feature.getIdentifier());
                             String str  = (String) feature.getAttribute("CCA_1");
                             
                             int i = Integer.parseInt(str);
                             Filter filter1 =ff.equal(ff.property("id"), ff.literal(i),false);
                         	 System.out.println("yahahaaha");
                         	
                         	 SimpleFeatureCollection selectedFeatures1 = featureSource2.getFeatures(filter1);

                         	 SimpleFeatureIterator iter1 = selectedFeatures1.features();
                         	 Set<FeatureId> IDs1 = new HashSet<FeatureId>();

                              try {
                              while (iter1.hasNext()) {
                              feature = iter1.next();
                              IDs1.add(feature.getIdentifier());
                              System.out.println( feature.getAttribute("the_geom"));
                              }}catch(Exception e){}
                             
                             } 
                             if (IDs.isEmpty()) {
                                 System.out.println("   no feature selected");
                             }

                             }finally {
                             iter.close();                               
                             }
                             } catch (Exception ex) {

                                ex.printStackTrace();
                                 }

                            }
                        });
            }
        });
        mapFrame.setSize(700, 700);
        mapFrame.setVisible(true);
}
}
