<?php

require_once "conn.php";
require_once "validate.php";
// Establish connection to MySQL database

// If values send by NodeMCU are not empty then insert into MySQL database table

  if(!empty($_POST['emergency'])){

        $getPatientID = "SELECT * FROM patient WHERE deviceID = '202201' and isExist=1";
        $getPatientIDResult = mysqli_query($conn, $getPatientID);

        if($getPatientIDResult){
            while($row = mysqli_fetch_assoc($getPatientIDResult)){
                $getEmergencyStatus = "SELECT * FROM patient_health_log WHERE patient_id = '$row[patient_id]' and isExist=1";
                $getEmergencyStatusResult = mysqli_query($conn, $getEmergencyStatus);

                if($getEmergencyStatusResult){
                    while($row1 = mysqli_fetch_assoc($getEmergencyStatusResult)){
                        echo $row1['emergency']."`".$row['number'];  
                    }
                }
        
                else{
                    echo "Error: " . $sql . "<br>" . $conn->error; 	
                }
            }
        }

        else{
            echo "Error: " . $sql . "<br>" . $conn->error; 	
        }
	}


// Close MySQL connection
$conn->close();



?>