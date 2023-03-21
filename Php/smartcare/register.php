<?php
if(isset($_POST['name']) && isset($_POST['gender']) && isset($_POST['number']) && isset($_POST['birthdate']) && isset($_POST['address']) && isset($_POST['disease']) && isset($_POST['deviceID']) && isset($_POST['room'])  && isset($_POST['encoder'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $name = validate($_POST['name']);
    $gender = validate($_POST['gender']);
    $number = validate($_POST['number']);
    $birthdate = validate($_POST['birthdate']);
    $address = validate($_POST['address']);
    $disease = validate($_POST['disease']);
    $deviceID = validate($_POST['deviceID']);
    $room = validate($_POST['room']);
    $encoder = validate($_POST['encoder']);

    $getUserName = "SELECT * FROM user WHERE staff_name = '$encoder'";
    $getUserNameResult = mysqli_query($conn, $getUserName);

    if($getUserNameResult){
        while($row = mysqli_fetch_assoc($getUserNameResult)){
            $user_id = $row['user_id'];

            date_default_timezone_set('Asia/Manila');
            $dateAdmitted = date("F d, Y");
            $dateAdmittedFinal = new DateTime($dateAdmitted);
            $newDateAdmitted = $dateAdmittedFinal->format('F d, Y');

            $birthdate = new DateTime($birthdate);
            $birthdateFinal = $birthdate->format('F d, Y');
                
            $sql = "INSERT INTO patient(patient_name, gender, number, birthdate, address, disease, deviceID, room, encoder, date_admit, isExist) VALUES('$name', '$gender', '$number', '$birthdateFinal', '$address', '$disease', '$deviceID', '$room', '$user_id', '$newDateAdmitted', 1) ";
            
            if(!$conn->query($sql)){
                echo "failure";
            }

            else{
                
                $last_id = mysqli_insert_id($conn);
                
                
                $sql1 = "UPDATE deviceid SET device_status='Not Available' WHERE device_id =  '$deviceID'";
                
                if(!$conn->query($sql1)){
                    echo "failure";
                }
            
                else{
                    $sql3 = "INSERT INTO patient_health_log(patient_id, pulse_rate, oxygen_saturation, body_temperature, room_temperature, humidity, air_quality, blood_pressure, respiratory_rate, isExist) VALUES('$last_id', 0, 0, 0, 0, 0, 0, 0, 0, 1)";

                        if(!$conn->query($sql3)){
                            echo "failure";
                        }
                        
                        else{
                            $activity = "Admitted a patient named ".$name;
                            date_default_timezone_set('Asia/Manila');
                            $activityDate = date("F d, Y");
                            $activityDateFinal = new DateTime($activityDate);
                            $newActivityDate = $activityDateFinal->format('F d, Y');
                            $activityTime = date("h:i a");
                            $user = $user_id;

                            $insertHistory = "INSERT INTO history_log(activity,dateStamp,timeStamp,user, isExist) VALUES('$activity', '$newActivityDate','$activityTime','$user',1)";
                            $insertHistoryResult = mysqli_query($conn, $insertHistory);

                            if($insertHistoryResult){
                                echo "success";
                            }

                            else{
                                echo "failure";
                            }
                        }
                }
            }
        }
    }

    else{
        echo 'failure';
    }

    
}
?>