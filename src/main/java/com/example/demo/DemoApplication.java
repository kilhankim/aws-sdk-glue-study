package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.glue.GlueClient;
import software.amazon.awssdk.services.glue.model.GetDatabaseRequest;
import software.amazon.awssdk.services.glue.model.GetDatabaseResponse;
import software.amazon.awssdk.services.glue.model.GlueException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
        String databaseName = "jjouhiu-glue-database";
        Region region = Region.US_EAST_1;
        System.out.println("region : " + region);
        GlueClient glueClient = GlueClient.builder()
                .region(region)
                .build();

        getSpecificDatabase(glueClient, databaseName);
		glueClient.close();

		SpringApplication.run(DemoApplication.class, args);
	}

	public static void getSpecificDatabase(GlueClient glueClient, String databaseName) {

		try {
			  GetDatabaseRequest databasesRequest = GetDatabaseRequest.builder()
				  .name(databaseName)
				  .build();
  
			  GetDatabaseResponse response = glueClient.getDatabase(databasesRequest);
			  Instant createDate = response.database().createTime();
  
			  // Convert the Instant to readable date
			  DateTimeFormatter formatter =
					  DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
							  .withLocale( Locale.US)
							  .withZone( ZoneId.systemDefault() );
  
			  formatter.format( createDate );
			  System.out.println("The create date of the database is " + createDate );
  
			} catch (Exception e) {
			 // System.err.println(e.awsErrorDetails().errorMessage());
			  e.printStackTrace();
			  System.exit(1);
		  }
	  }		

}
