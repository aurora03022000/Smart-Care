<?php
if(isset($_POST['currentUser']) && isset($_POST['patientID']) && isset($_POST['pulse_rate_value']) && isset($_POST['air_quality_value']) && isset($_POST['blood_pressure_value']) && isset($_POST['body_temperature_value']) && isset($_POST['humidity_value']) && isset($_POST['oxygen_saturation_value']) && isset($_POST['respiratory_rate_value'])  && isset($_POST['room_temperature_value'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $currentUser = validate($_POST['currentUser']);
    $patientID = validate($_POST['patientID']);
    $pulse_rate_value = validate($_POST['pulse_rate_value']);
    $air_quality_value = validate($_POST['air_quality_value']);
    $blood_pressure_value = validate($_POST['blood_pressure_value']);
    $body_temperature_value = validate($_POST['body_temperature_value']);
    $humidity_value = validate($_POST['humidity_value']);
    $oxygen_saturation_value = validate($_POST['oxygen_saturation_value']);
    $respiratory_rate_value = validate($_POST['respiratory_rate_value']);
    $room_temperature_value = validate($_POST['room_temperature_value']);
        
    $sql = "UPDATE patient_health_log SET pulse_rate = '$pulse_rate_value', air_quality = '$air_quality_value', blood_pressure = '$blood_pressure_value', body_temperature = '$body_temperature_value', humidity = '$humidity_value',
     oxygen_saturation = '$oxygen_saturation_value', respiratory_rate = '$respiratory_rate_value', room_temperature = '$room_temperature_value' WHERE patient_id = '$patientID' and isExist = 1";
    
    if(!$conn->query($sql)){
        echo "failure";
    }

    else{
            $sql2 = "SELECT * FROM patient WHERE patient_id = '$patientID' and isExist = 1";
            $sql2Result = mysqli_query($conn, $sql2);

            if($sql2Result){
                while($row = mysqli_fetch_assoc($sql2Result)){
                    if($row['share_id'] == "" || $row['share_id'] == " "){
                        $name = $row['patient_name'];
                        $activity = "Updated patient health records named ".$name;
                        date_default_timezone_set('Asia/Manila');
                        $activityDate = date("F d, Y");
                        $activityDateFinal = new DateTime($activityDate);
                        $newActivityDate = $activityDateFinal->format('F d, Y');
                        $activityTime = date("h:i a");
                        $user = $currentUser;
            
                        $insertHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
                        $insertHistoryResult = mysqli_query($conn, $insertHistory);
            
                        if($insertHistoryResult){
                            $dateLogged = $activityDate;
                            $timeLogged = $activityTime;
    
                            $insert_patient_log = "INSERT INTO patient_logs(patient_id,date,time,pulse_rate, oxygen_saturation, body_temperature, blood_pressure, respiratory_rate, 
                            room_temperature, humidity, air_quality) VALUES('$patientID', '$dateLogged','$timeLogged','$pulse_rate_value','$oxygen_saturation_value', '$body_temperature_value', 
                            '$blood_pressure_value', '$respiratory_rate_value', '$room_temperature_value', '$humidity_value', '$air_quality_value')";
                            $insert_patient_log_Result = mysqli_query($conn, $insert_patient_log);
    
                            if($insert_patient_log_Result){
                                echo 'success';
                            }
    
                            else{
                                echo "failure";
                            }
                        }
            
                        else{
                            echo "failure";
                        } 
                    }

                    else{
                        $name = $row['patient_name'];
                        $activity = "Updated patient health records named ".$name;
                        date_default_timezone_set('Asia/Manila');
                        $activityDate = date("F d, Y");
                        $activityDateFinal = new DateTime($activityDate);
                        $newActivityDate = $activityDateFinal->format('F d, Y');
                        $activityTime = date("h:i a");
                        $user = $row['share_id'];
            
                        $insertHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
                        $insertHistoryResult = mysqli_query($conn, $insertHistory);
            
                        if($insertHistoryResult){
                            $dateLogged = $activityDate;
                            $timeLogged = $activityTime;
    
                            $insert_patient_log = "INSERT INTO patient_logs(patient_id,date,time,pulse_rate, oxygen_saturation, body_temperature, blood_pressure, respiratory_rate, 
                            room_temperature, humidity, air_quality) VALUES('$patientID', '$dateLogged','$timeLogged','$pulse_rate_value','$oxygen_saturation_value', '$body_temperature_value', 
                            '$blood_pressure_value', '$respiratory_rate_value', '$room_temperature_value', '$humidity_value', '$air_quality_value')";
                            $insert_patient_log_Result = mysqli_query($conn, $insert_patient_log);
    
                            if($insert_patient_log_Result){
                                echo 'success';
                            }
    
                            else{
                                echo "failure";
                            }
                        }
            
                        else{
                            echo "failure";
                        } 
                    }
                     
                }
            }

            else{
                //echo "failure"; 
            }
                 
        }
    }
?>