## Creating JPA Entites from HANA table

This document describes the steps to import the hana tables as JPA entities. 

1. Right click on the project, and the in the context menu, JPA Tools -> Create entities from table

![image](https://user-images.githubusercontent.com/15712474/32521963-68c7718c-c3e3-11e7-85cb-d564dfde3462.png)


2. Click on the Add Connection button. 

![image](https://user-images.githubusercontent.com/15712474/32522030-a452d278-c3e3-11e7-9984-b8daacec486a.png)

3. Select Generic JDBC from the list of Profile Types (as shown in the image above)

4. Click on the "New Driver Definition" button that's next to the Drivers dropdown box. 

![image](https://user-images.githubusercontent.com/15712474/32521688-7942a4e2-c3e2-11e7-8748-8fed8a5ecc37.png)


5. In the new Driver Defnition window, select the "Generic JDBC Driver", and the add the ngdbc.jar driver to the jar list

![image](https://user-images.githubusercontent.com/15712474/32521737-b4d290bc-c3e2-11e7-9a64-ada3b723ea24.png)

The ngdbc driver can be found at either in the HANA studio (if you have it installed). Or we can also get it from the libraries that are already part of the SCP Web Tomcat8 Runtime at this location

<hcp_sdk>\repository\.archive\lib

![image](https://user-images.githubusercontent.com/15712474/32521737-b4d290bc-c3e2-11e7-9a64-ada3b723ea24.png)

6. In the properties tab, enter the DriverClass Property. You can search for this, and select the class "com.sap.db.jdbc.Driver"

![image](https://user-images.githubusercontent.com/15712474/32522110-0153405c-c3e4-11e7-9858-72ea29d85930.png)


7. Enter the DB credentials and save. 

8. The schemas and tables from HANA will be listed, and you can follow the wizard to import the entites. 

9. Please note to change the entity name as desired, and also the types of attributes. 

When you finish the wizard, this should generate the entities for you. 

Note: This approach is not part of the officical documentation. And this might have some limitations. 
