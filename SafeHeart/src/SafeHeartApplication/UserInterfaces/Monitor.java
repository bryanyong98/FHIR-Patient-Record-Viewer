/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SafeHeartApplication.UserInterfaces;

import SafeHeartApplication.EventUpdate.* ; 
import SafeHeartApplication.ServerDatas.* ; 
import SafeHeartApplication.UserInterfaces.PractitionerForm;
import SafeHeartApplication.Users.Patient;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

// importing of all graph plotting libraries 
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 *
 * @author Bryan
 * Monitor: The main interface that displays the cholesterol levels 
 * of patients, if any. 
 * 
 */
public class Monitor extends javax.swing.JFrame {
    private static Patient obj ;
    private static PractitionerForm prevForm ;
    private static PatientVitalsData subject ; 
    private static String clinicianid ; 
    
    // The attributes for graph plotting purposes. 
    private XYSeriesCollection dataset = new XYSeriesCollection();
    private XYSeries cholesterolSeries = new XYSeries("Cholesterol Levels (mg/dL)");   
    private XYSeries systolicSeries = new XYSeries("Systolic Pressure (mm Hg)");
    private XYSeries diastolicSeries = new XYSeries("Diastolic Pressure (mm Hg)");    
    
    // Attributes for the cholesterol level table model. 
    private DefaultTableModel model ; 
    private int lengthOfModel ; 
    
    // initialise the observers for this particular patient's monitor page. 
    private TobaccoObserver tobaccoObs ; 
    private BloodPressureObserver bpObs ; 
    private CholesterolObserver cholObs ; 
    
    // The three core datas -- patient's vitals -- cholesterol, blood pressure and tobacco.  
    private TreeMap<String, String> cholestrolLevels ; 
    private TreeMap<String, Double[]>  bpMap ; 
    private ArrayList<String[]> tobaccoList ; 
    
    
    /**
     * Monitor: Constructor for the monitor.
     * Creates new form patientForm
     * @param obj: the patient object that has been passed from the practitioner form. 
     * @param frm: the reference of practitioner form. 
     * @param subject: the subject that changes. 
     * @param clinicianid : clinician id, self explanatory. 
     * 
     */
    public Monitor(Patient obj, PractitionerForm frm, PatientVitalsData subject, String clinicianid) {
        initComponents();
        this.prevForm =frm;
        this.obj = obj;
        this.patient_fname_lname.setText(this.obj.getFamilyName() + " " + this.obj.getGivenName());
        this.patientid.setText(this.obj.getId());
        this.gender.setText(this.obj.getGender());
        this.clinicianid =  clinicianid ;  
        this.subject = subject;       
    }
      
    /**
     *
    * @author Bryan
    * 
    *  Enabling the datasets to be populated prior to plotting. 
     * @param keys: The list of keys from the blood pressure tree map. 
    * 
    */
    
    public void populateDatasetBP(Set<String> keys)
    {
        // Populate the datasets with the relevant datas. 
        for (String key: keys)
        {
            Double[] bp = bpMap.get(key) ;
            Double systolic = bp[0] ; 
            Double diastolic = bp[1] ; 
                             
            
            String newDateKey = new String();        
            newDateKey = key.substring(0,4) + "." + key.substring(5,7);            
                  
            systolicSeries.add(Double.parseDouble(newDateKey), systolic ) ;
            diastolicSeries.add(Double.parseDouble(newDateKey), diastolic) ;         
        }                     
    }
    
    /**
    *
    * @author Bryan
    * 
    *  Enabling the datasets to be populated prior to plotting. 
    * @param keys: The list of keys from the cholesterol tree map. 
    * 
    */    
    
    public void populateDatasetChol(Set<String> keys)
    {
        for (String key: keys)
        {
            String modifiedCholLevel = new String();
            String cholLv = cholestrolLevels.get(key) ; 
            modifiedCholLevel = cholLv.substring(0,3) ;              
                                         
            System.out.println(key + "  " +cholestrolLevels.get(key)) ;
            
            String newDateKey = new String();        
            newDateKey = key.substring(0,4) + "." + key.substring(5,7);            
                  
            cholesterolSeries.add(Double.parseDouble(newDateKey), Double.parseDouble(modifiedCholLevel) ) ;
        }                             
    }
    
    /**
    *
    * @author Bryan
    * 
    * Customize the chart layout, chart line background etc. 
    * @param chart: JFree chart reference generated. 
    * 
    */     
    
    public void customizeChart(JFreeChart chart)
    {
        XYPlot plot = chart.getXYPlot() ; 
                
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // customisation part of the graph..     
        // sets paint color for each series
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);

        // sets thickness for series (using strokes)
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
    
        // sets paint color for plot outlines
        plot.setOutlinePaint(Color.BLUE);
        plot.setOutlineStroke(new BasicStroke(2.0f));
    
        // sets renderer for lines
        plot.setRenderer(renderer);
    
        // sets plot background
        plot.setBackgroundPaint(Color.DARK_GRAY);
    
        // sets paint color for the grid lines
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
    
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);     
    }
    
    /*
    *
    * @author Bryan
    * 
    * Placing the graph in the main UI.  
    * @param chart: chart generated, reference by j chart. 
    * 
    */     
    public void addGraphToPanel(JFreeChart chart)
    {
        graphPanel.setLayout(new java.awt.BorderLayout());
        ChartPanel barPanel = new ChartPanel(chart) ;    
        graphPanel.add(barPanel, BorderLayout.CENTER) ; 
        graphPanel.validate();             
    }
    
    /*
    *
    * @author Bryan
    * 
    * Update the display of the current monitor for cholesterol level. 
    * @param cholesterolLevels : the cholesterol level of specific patient. 
    * 
    */     
        public void updateCholesterolMonitor(TreeMap<String, String> cholesterolLevels)
    {
        cholesterolSeries.clear() ;  // clear all the plot points..  
        
        Set<String> keys = cholestrolLevels.keySet() ;     
             
        // Populate the datasets with the relevant datas.  
        populateDatasetChol(keys) ; 
                
        // place datasets into the plot. 
        JFreeChart chart = ChartFactory.createXYLineChart("Patient vitals", 
                                                          "date", 
                                                          "reading", 
                                                          dataset); 
        
        // Create chart and customize. Take a jfreechart as param
        customizeChart(chart) ;      
 
        // Placing the graph in the JPanel. 
        addGraphToPanel(chart) ;                             
    }
  
    /*
    *
    * @author Bryan
    * 
    * Update the display of the current monitor for blood pressure. 
    * @param bpMap : the blood pressure levels of specific patient. 
    * 
    */           
        
    public void updateBloodPressureMonitor(TreeMap<String, Double[]> bpMap)
    {
        // find a way to update the existing graph..... 
        systolicSeries.clear();    // clear the previous series, and repopulate the data again. 
        diastolicSeries.clear(); 
        
        Set<String> keys = bpMap.keySet() ; 
        int lastVal = bpMap.size() - 1;  
        int count = 0 ; 
        
        // hypertension function alert....
        for (String key: keys)
        {  
            if (count == lastVal && ( bpMap.get(key)[0] > 180 || bpMap.get(key)[1] > 120))
            {
                JOptionPane.showMessageDialog(new JFrame(), "Patient is in risk of hypertension");
            }
            count ++ ; // increment count later on.               
        }        
        
        // populate data again....
        this.populateDatasetBP(keys);
                                        
        // place datasets into the plot. 
        JFreeChart chart = ChartFactory.createXYLineChart("Patient vitals", 
                                                          "date", 
                                                          "reading", 
                                                          dataset); 
        // Create chart and customize. Take a jfreechart as param
        customizeChart(chart) ;      
 
        // Placing the graph in the JPanel. 
        addGraphToPanel(chart) ;        
    } 
    
    /*
    *
    * @author Bryan
    * 
    * Update the display of the current monitor for tobacco. 
    * @param tobaccoList : the tobacco status of specific patient. 
    * 
    */    
    public void updateTobaccoMonitor(ArrayList<String[]> tobaccoList)
    {
       // Comparison of the date to get the latest tobacco status of the patient. 
       String latestDate = tobaccoList.get(0)[1]  ; 
       String latestTobaccoStatus = "" ; 

       // loop thru each of the tobacco status. 
       for(int c =0 ; c < tobaccoList.size(); c++)
            {   
                String date = tobaccoList.get(c)[1] ; 
                              
                // if the current date is more latest. 
                if (date.compareTo(latestDate) == 1)
                {
                    latestDate = date ; 
                    latestTobaccoStatus = tobaccoList.get(c)[2] ;                 
                }        
            }       
        this.tobaccoStatusMonitor.setText(latestTobaccoStatus);            
    }    

    /*
    *
    * @author Bryan
    * 
    * Adding new rows to the table model to be displayed in the UI
    * 
    */     
    
    public void addRowToTable()
    {
        try
        {
            model = (DefaultTableModel) this.cholesterolMonitor.getModel();
        
            /* Retrieve the cholesterol levels data for the specific patient.  */            
            cholestrolLevels   = CholesterolData.queryCholesterolLevels(this.obj.getId());        
        
            Set<String> keys = cholestrolLevels.keySet() ; 
            Object rowData [] = new Object[2];            
            
            // initialise it to be 1.            
            lengthOfModel = 0 ;    
            
            // Populate the datasets with the relevant datas. 
            for (String key: keys)
            {
                rowData[0] = cholestrolLevels.get(key);
                rowData[1] = key;   // the date. 
                model.addRow(rowData);   
                lengthOfModel ++  ;    // keep track of the number of rows. 
            }            
        }  
        
        catch(java.lang.NullPointerException e)
        {
           JOptionPane.showMessageDialog(new JFrame(), "No Cholesterol Data");
        }     
    }

       
   /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        patientid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        gender = new javax.swing.JTextField();
        patient_fname_lname = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        cholesterolMonitor = new javax.swing.JTable();
        stopMonitorButton = new java.awt.Button();
        tobaccoStatusMonitor = new javax.swing.JTextField();
        graphPanel = new javax.swing.JPanel();
        tobaccoButton = new javax.swing.JButton();
        cholesterolButton = new javax.swing.JButton();
        bloodPressureButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Patient ID:");

        patientid.setText("jTextField1");

        jLabel2.setText("Gender:");

        gender.setText("jTextField2");

        patient_fname_lname.setText("jTextField5");

        cholesterolMonitor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Total Cholestrol Level", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(cholesterolMonitor);

        stopMonitorButton.setLabel("Stop Monitor");
        stopMonitorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopMonitorButtonActionPerformed(evt);
            }
        });

        tobaccoStatusMonitor.setText("     ");

        graphPanel.setBackground(new java.awt.Color(102, 255, 255));

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 791, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );

        tobaccoButton.setText("Tobacco Status");
        tobaccoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tobaccoButtonActionPerformed(evt);
            }
        });

        cholesterolButton.setText("Cholesterol");
        cholesterolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cholesterolButtonActionPerformed(evt);
            }
        });

        bloodPressureButton.setText("Blood Pressure");
        bloodPressureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bloodPressureButtonActionPerformed(evt);
            }
        });

        resetButton.setText("RESET DATA");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(31, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(patientid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(81, 81, 81)
                                        .addComponent(cholesterolButton)))
                                .addGap(72, 72, 72)
                                .addComponent(tobaccoButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addComponent(bloodPressureButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(resetButton)))
                        .addGap(89, 89, 89)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(patient_fname_lname, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tobaccoStatusMonitor, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(534, 534, 534)
                .addComponent(stopMonitorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(patient_fname_lname, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tobaccoStatusMonitor, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(patientid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tobaccoButton)
                            .addComponent(cholesterolButton))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bloodPressureButton)
                            .addComponent(resetButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(stopMonitorButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*
    *
    * @author Bryan
    * 
    * This method ensures that the monitor page visibility is off upon user clicking it.     
    * @param evt : action event
    * 
    */     
    private void stopMonitorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopMonitorButtonActionPerformed
        
        /* setting this particular form to invisible.  */                  
        this.setVisible(false);
        this.prevForm.setVisible(true);
    }//GEN-LAST:event_stopMonitorButtonActionPerformed

    /*
    *
    * @author Bryan
    * 
    * This method ensures that the tobacco monitor displays relevant text upon clicking it.     
    * @param evt : action event
    * 
    */    
    
    private void tobaccoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tobaccoButtonActionPerformed

       // Tobacco button pressed. 
       // upon clicking the button, an observer is created and attached to the main subject.        
       // Creating a new cholesterol observer each time there's a monitor
        
       tobaccoObs = new TobaccoObserver(this, obj.getId()) ; 
            
       // attach the cholesterol observer to the SUBJECT.
       this.subject.attach(tobaccoObs) ; 

       // retrieving the data from the FHIR Server. 
       tobaccoList = TobaccoStatusData.queryTobaccoStatusData(this.obj.getId()) ; 
       
       // Comparison of the date to get the latest tobacco status of the patient. 
       String latestDate = tobaccoList.get(0)[1]  ; 
       String latestTobaccoStatus = "" ; 

       // loop thru each of the tobacco status. 
       for(int c =0 ; c < tobaccoList.size(); c++)
       {   
                String date = tobaccoList.get(c)[1] ; 
          
                // if the current date is more latest. 
                if (date.compareTo(latestDate) == 1)
                {
                    latestDate = date ; 
                    latestTobaccoStatus = tobaccoList.get(c)[2] ;                 
                }
       }     
  
        // set the status as the latest. 
        this.tobaccoStatusMonitor.setText(latestTobaccoStatus);
        
        // Disabling the toggling of tobacco Button. 
        tobaccoButton.setEnabled(false); 
    }//GEN-LAST:event_tobaccoButtonActionPerformed
  
    /*
    *
    * @author Bryan
    * 
    * This method ensures that the blood pressure monitor displays relevant graphs upon clicking it.     
    * @param evt : action event
    * 
    */        
    private void bloodPressureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bloodPressureButtonActionPerformed

        // upon clicking the button, an observer is created and attached to the main subject.                
        // Creating a new cholesterol observer each time there's a monitor
        bpObs = new BloodPressureObserver(this, obj.getId()) ; 
            
        // attach the cholesterol observer to the SUBJECT.
        this.subject.attach(bpObs) ; 
        
        bpMap = BloodPressureData.queryBloodPressureData(this.obj.getId()) ;   
        
        // code to determine whether if the blood pressure of the patient is in high risk. 
        int lastVal = bpMap.size() - 1;  // last value will be the latest date of the patient last monitored. 
        int count = 0 ; 
        Set<String> keys = bpMap.keySet() ; 
        
        // hypertension function alert....
        for (String key: keys)
        {
            
            System.out.println(key + bpMap.get(key)) ; 
            // check if it's the last index and whether the blood pressure systole >180 or 
            // diastole >120 (any of them)
            if (count == lastVal && ( bpMap.get(key)[0] > 180 || bpMap.get(key)[1] > 120))
            {
                JOptionPane.showMessageDialog(new JFrame(), "Patient is in risk of hypertension");
            }
            count ++ ; // increment count later on.               
        }
             
        populateDatasetBP(keys) ; 
        dataset.addSeries(systolicSeries) ; 
        dataset.addSeries(diastolicSeries) ;         
        
        // place datasets into the plot. 
        JFreeChart chart = ChartFactory.createXYLineChart("Patient vitals", 
                                                          "date", 
                                                          "reading", 
                                                          dataset); 
        
        // Create chart and customize. Take a jfreechart as param
        customizeChart(chart) ;      
 
        // Placing the graph in the JPanel. 
        addGraphToPanel(chart) ;     
    
        // Disabling the toggling of button now. 
        bloodPressureButton.setEnabled(false);
      
    }//GEN-LAST:event_bloodPressureButtonActionPerformed

    /*
    *
    * @author Bryan
    * 
    * This method ensures that all graphical and text monitors are off upon clicking it. 
    * @param evt : action event
    * 
    */    
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        
        // remove the tobacco status, and enable the clicking of the tobacco button.      
        tobaccoStatusMonitor.setText("") ; 
        tobaccoButton.setEnabled(true);
    
        // Check if the panel has any graphs on it, if yes, remove them.   
        Component[] components = graphPanel.getComponents();

        for (Component component : components) {
            graphPanel.remove(component);  
        }    
    
        graphPanel.revalidate();
        graphPanel.repaint();
    
    
        dataset.removeAllSeries();   
    
        if (model != null){
            int rowCount = model.getRowCount() ; 
            int j =  model.getRowCount(); 
    
            if (model.getRowCount() > 0)
            {        
                for (int i = 0 ; i < rowCount ; i++)
                {
                    model.removeRow(j-1);
                    j-- ; 
                }
            }
        }    
    
        // reset the table length. 
        lengthOfModel = 0 ; 
    
        bloodPressureButton.setEnabled(true);     
        cholesterolButton.setEnabled(true);
        
        // detaching all the observers after resetting is clicked. 
        if (tobaccoObs != null || bpObs!= null || cholObs != null)
        {
            this.subject.detachAll();
        }
               
    }//GEN-LAST:event_resetButtonActionPerformed

    /*
    *
    * @author Bryan
    * 
    * This method ensures that the cholesterol monitor displays relevant text and graph upon clicking it.     
    * @param evt : action event
    * 
    */        
    private void cholesterolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cholesterolButtonActionPerformed
        
        // upon clicking the button, an observer is created and attached to the main subject.        
        
        // Creating a new cholesterol observer each time there's a monitor
        cholObs = new CholesterolObserver(this, obj.getId()) ; 
            
        // attach the cholesterol observer to the SUBJECT.
        this.subject.attach(cholObs) ; 
        
        // builds up the cholesterol text monitor from stage 1. 
        this.addRowToTable();
               
        Set<String> keys = cholestrolLevels.keySet() ;     
       
        // Populate the datasets with the relevant datas, then add series.         
        populateDatasetChol(keys) ; 
        dataset.addSeries(cholesterolSeries) ; 
                        
        // place datasets into the plot. 
        JFreeChart chart = ChartFactory.createXYLineChart("Patient vitals", 
                                                          "date", 
                                                          "reading", 
                                                          dataset); 
        
        // the two steps.    
        customizeChart(chart);
        addGraphToPanel(chart);

        // This is the last part of the button functionality. 
        cholesterolButton.setEnabled(false); 
    }//GEN-LAST:event_cholesterolButtonActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Monitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Monitor(obj,prevForm,subject, clinicianid).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bloodPressureButton;
    private javax.swing.JButton cholesterolButton;
    private javax.swing.JTable cholesterolMonitor;
    private javax.swing.JTextField gender;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField patient_fname_lname;
    private javax.swing.JTextField patientid;
    private javax.swing.JButton resetButton;
    private java.awt.Button stopMonitorButton;
    private javax.swing.JButton tobaccoButton;
    private javax.swing.JTextField tobaccoStatusMonitor;
    // End of variables declaration//GEN-END:variables
}
