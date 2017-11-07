## Setting up HANA database in trial landscape. 

Follow the steps belew to setup the hana database in trial account

1. Follow the steps from tutorial at https://www.sap.com/developer/tutorials/hcpps-hana-create-mdc-instance.html to setup the database

2. Once the database is in status STARTED, open the development tools "SAP HANA Web-Based Development Workbench"

![image](https://user-images.githubusercontent.com/15712474/32507156-bb4bae70-c3b4-11e7-9f17-da08bf8e3254.png)

Login to the system with the database "SYSTEM" user password that you provided when provisioning the DB. 

3. Open the link, https://<host>/sap/hana/ide/security/
  
  If you get an unauthorized page, try opening "SAP HANA Cockpit" page and then retry. This step would assign the required roles to get the securit page. 

4. Assing the required roles to the "Editor" and the "Catalog" to the SYSTEM user

From the granted roles tab, hit "+" icon and search for developer. Add all roles listed by selecting and hit ok (To select multiple, hold shift and select the last entry in the list). 

![image](https://user-images.githubusercontent.com/15712474/32507390-4333022a-c3b5-11e7-94bc-e717ff249d51.png)


## Importing the HANA XS project to eclipse

1. Import the project shared (in mail, jpa_books_db) to the eclipse workspace. (File --> Import --> General --> Existing projects in workspace)
   
![image](https://user-images.githubusercontent.com/15712474/32507539-b43e4b6e-c3b5-11e7-81cf-8f1e63783dc0.png)

2. Set the encoding type of file to UTF-8

![image](https://user-images.githubusercontent.com/15712474/32507638-004c9aba-c3b6-11e7-854d-da8a7feac28a.png)

3. Log into the HANA system from eclipse and the setup the repository.

  - Go to Window -> Perspective -> Other -> and select HANA Development
  - From the Systems tab, right click on the white space and from Context Menu --> Connect to Cloud System
  
  ![image](https://user-images.githubusercontent.com/15712474/32507739-50afbfa0-c3b6-11e7-9a09-d3ac40715b8a.png)

4. Now on the project, right click --> team -> Share Project, and then select the repository for the connected HANA system. 

5. Activate the projet. Right click on project --> team --> Activate All. 

6. From SQL Console, execute the following command to grant the SYSTEM user with the privileges to access the schema created. 

  call _SYS_REPO.GRANT_SCHEMA_PRIVILEGE_ON_ACTIVATED_CONTENT('select, create any, insert, delete, update, execute, alter, drop',
     'JPA_BOOKS',
     'SYSTEM');




