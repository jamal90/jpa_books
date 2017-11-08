## Connecting to Cloud DB from Local Runtime

To open a secure DB tunnel to the DB on cloud, we can either do it in two ways. 
a. Using the NEO Console client 
b. Using the db tunnel that's created by Eclipse - for connecting to HANA system. 

### a. Using the NEO Console Client

Note: This didn't work due to some proxy settings inside your corporate network. 

1. Install the SAP HCP Neo Command Line Interface
    Download the sdk, and the the neo client is inside the tools folder
    
    ![image](https://user-images.githubusercontent.com/15712474/32561087-5cd047aa-c479-11e7-9e00-cdfc83489126.png)


2. Execute the following command

    open the command prompt, from within the folder where the neo.bat file is present, and then execute the following command

    neo.bat open-db-tunnel -h <region> -a <subaccount> -u <user> -i <db_id>
    For example, neo.bat open-db-tunnel -h us2.hana.ondemand.com -a d2310a23c -u I076097 -i dil01

### b.  Using DB tunnel from eclipse

1. In eclipse, connect to the cloud DB. 
    You can refer to the steps from this webpage - http://saphanatutorial.com/add-sap-hana-cloud-system-in-hana-studio-or-eclipse/
    
2. Once you are successfully connected, then get the port number that DB tunnel is open. This you can do by Right Click on the system --> properties

![image](https://user-images.githubusercontent.com/15712474/32561600-a38a6ce2-c47a-11e7-8026-5794ff3158b3.png)

3. From there take note of the host and the port number. 


### Setting up the local server

1. You can find the server in the "Project Explorer" pane, where you can add the settings

2. Setup the connection properties in Local Server to connect to Cloud Database. Add the below configuration in the file at
    server --> config_master --> connection_data --> connection.properties
    
    ```
    javax.persistence.jdbc.driver=com.sap.db.jdbc.Driver
    javax.persistence.jdbc.url=jdbc:sap://localhost:30015/?reconnect=true 
    javax.persistence.jdbc.user=<user>
    javax.persistence.jdbc.password=<password>
    com.sap.cloud.persistence.dsname=jdbc/DefaultDB
    eclipselink.target-database=HANA

    ```

Finally you can start the server, and the local java web application will connect to the DB in cloud. 

