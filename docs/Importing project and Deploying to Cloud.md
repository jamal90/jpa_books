## Import project from Github

1. Go to the github project https://github.com/jamal90/jpa_books/

2. From the dropdown "Branch: master", expand it, and select Tags. From tags selcet the day2 from the list of tags avaialble.

![image](https://user-images.githubusercontent.com/15712474/32519486-f105bfa8-c3da-11e7-97bc-7050f3614df1.png)

3. Click on Clone or download, and the download the project as zip. Extract to some location in your system. 

![image](https://user-images.githubusercontent.com/15712474/32519541-18e46434-c3db-11e7-814c-d8e81b5c3ea4.png)

4. From eclipse, import the project as File --> Import --> Maven --> Existing Maven Project

![image](https://user-images.githubusercontent.com/15712474/32519600-5307fc5c-c3db-11e7-884e-9a2bc07948ff.png)

5. Select the location where you have extracted the downlaod project from Github.

6. After the project is imported successfully, right click on the project --> Maven --> Update Project

7. You can now build the project (Right Click on project --> Run As --> Maven Build --> Goals: Clean Install). 

8. Either deploy the project from eclipse or by deploying the war artifact to the cloud cockpit. 

9. Create the Datasource Binding
   In Cloud cockpit, open the java application. Under Configuration -> Data Source Bindings (Left Navigation Pane)
   
   
  ![image](https://user-images.githubusercontent.com/15712474/32519929-4ab1624a-c3dc-11e7-8398-0378e550cfcc.png)
  
  After creating the binding, restart the application. 
