<?php
if(isset($_POST['patient_id']) && isset($_POST['pulse_rate']) && isset($_POST['oxygen_saturation']) && isset($_POST['body_temperature']) && 
isset($_POST['blood_pressure']) && isset($_POST['respiratory_rate']) && isset($_POST['room_temperature']) && 
isset($_POST['humidity'])  && isset($_POST['air_quality'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patientID = validate($_POST['patient_id']);
    $pulse_rate = validate($_POST['pulse_rate']);
    $oxygen_saturation = validate($_POST['oxygen_saturation']);
    $body_temperature = validate($_POST['body_temperature']);
    $blood_pressure = validate($_POST['blood_pressure']);
    $respiratory_rate = validate($_POST['respiratory_rate']);
    $room_temperature = validate($_POST['room_temperature']);
    $humidity = validate($_POST['humidity']);
    $air_quality = validate($_POST['air_quality']);

    $getPatientName = "SELECT * FROM patient WHERE patient_id='$patientID'";
    $getPatientNameResult = mysqli_query($conn, $getPatientName);

    if($getPatientNameResult){
        while($row1 = mysqli_fetch_assoc($getPatientNameResult)){
            $updatePatientHealthLog = "UPDATE patient_health_log SET pulse_rate='$pulse_rate', oxygen_saturation='$oxygen_saturation', body_temperature='$body_temperature', 
            blood_pressure='$blood_pressure', respiratory_rate='$respiratory_rate', room_temperature='$room_temperature', humidity = '$humidity', air_quality = '$air_quality' WHERE patient_id = '$row1[patient_id]'";
            $updatePatientHealthLogResult = mysqli_query($conn, $updatePatientHealthLog);

            if($updatePatientHealthLogResult){
                $getUpdatePatientHealthInformation = "SELECT * FROM patient_health_log WHERE patient_id = '$row1[patient_id]'";
                $getUpdatePatientHealthInformationResult = mysqli_query($conn, $getUpdatePatientHealthInformation);

                if($getUpdatePatientHealthInformationResult){
                    while($row2 = mysqli_fetch_assoc($getUpdatePatientHealthInformationResult)){
                        $name = $row1['patient_name'];
                        $activity = "Updated patient health records named ".$name;
                        date_default_timezone_set('Asia/Manila');
                        $activityDate = date("F d, Y");
                        $activityDateFinal = new DateTime($activityDate);
                        $newActivityDate = $activityDateFinal->format('F d, Y');
                        $activityTime = date("h:i a");
                        
                        $getAdminId = "SELECT * FROM user WHERE username='admin'";
                        $getAdminIdResult = mysqli_query($conn, $getAdminId);

                        if($getAdminIdResult){
                            while($row = mysqli_fetch_assoc($getAdminIdResult)){
                                $user =  $row['user_id'];

                                $insertHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
                                $insertHistoryResult = mysqli_query($conn, $insertHistory);
                    
                                if($insertHistoryResult){
                                    echo $row1['patient_name'].'`'.$row2['pulse_rate'].'`'.$row2['oxygen_saturation'].'`'.$row2['body_temperature'].'`'.$row2['blood_pressure'].'`'.$row2['respiratory_rate'].'`'.$row2['room_temperature'].'`'.$row2['humidity'].'`'.$row2['air_quality'];
                                }
                    
                                else{
                                    echo "failure";
                                }  
                            }
                            
                        }

                        else{
                            echo 'failure';
                        }
            
                        
                    }
                }

                else{
                    echo 'failure';
                }
            }

            else{
                echo 'failure';
            }
        }
    }

    else{
        echo 'failure';
    }
}
?>